package eu.anastasis.tulliniHelpGest.modules;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.AjaxMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.common.XMessage;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Fattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyFattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;

/**
 * Metodo ajax per tornare tutti i dati di una metapratica dato l'ID
 * Usato dalle pagine degli item listino per "copiare" i dati della metapratica
 * @author afrascari
 *
 */
public class NextNumberAjaxMethod extends AjaxMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NextNumberAjaxMethod.class);


	private static final String METHOD_NAME = "nextnumber";

	
	public NextNumberAjaxMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		XMessage msg=null;
		String what=getMethodParameter(request,"cosa");
		String competenza=getMethodParameter(request,"competenza");
		String anno=getMethodParameter(request,"anno");
		
		try
		{
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement(what);
			msg.add(mp);
			Element node = mp.addElement("nextnumber");
			node.setText(getNextNumber(request,what,competenza, anno));
		} catch (Exception e)
		{
			String errorMessage = "Impossibile calcolare prossismo numero" + what;
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}

	private String getNextNumber(HttpServletRequest request,  String what, String competenza, String anno) {
		Integer res;
		try	{
			if (Fattura.INSTANCE_NAME.equals(what))	{
				if (competenza.equals(Fattura.COMPETENZA__CONTABILIS))
					res = MyFattura.getNextNumberContabilis(anno, request);
				else
					res = MyFattura.getNextNumberStudio(anno, request);
			} else {
				if (competenza.equals(ProForma.COMPETENZA__CONTABILIS))
					res = MyProForma.getNextNumberContabilis(anno, request);
				else
					res = MyProForma.getNextNumberStudio(anno, request);
			}
		} catch (Exception e) {
			logger.error("getNextNumber: "+e.getMessage());
			return "";
		}
		return res.toString();
	}
	 
}
