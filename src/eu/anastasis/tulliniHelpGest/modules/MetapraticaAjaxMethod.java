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
import eu.anastasis.tulliniHelpGest.beans.Metapratica;

/**
 * Metodo ajax per tornare tutti i dati di una metapratica dato l'ID
 * Usato dalle pagine degli item listino per "copiare" i dati della metapratica
 * @author afrascari
 *
 */
public class MetapraticaAjaxMethod extends AjaxMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MetapraticaAjaxMethod.class);


	private static final String METHOD_NAME = "metapratica";

	
	public MetapraticaAjaxMethod(DefaultModule parentModule,String[] defaultParameters)
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
		String id=getMethodParameter(request,"id");
		try
		{
			msg=new XMessage("reply");
			Element mp=DocumentHelper.createElement("metapratica");
			msg.add(mp);
			Metapratica m = new Metapratica();
			m = m.getInstance(id);
			String lAtt = getListaAttivitaAsHtml(m.getLista_attivita());
			Element node = mp.addElement("ID");
			node.setText(m.getID());
			node = mp.addElement("titolo");
			node.setText(m.getTitolo());
			node = mp.addElement("titolo_per_fattura");
			node.setText(m.getTitolo_per_fattura());
			node = mp.addElement("tipo");
			node.setText(m.getTipo());
			node = mp.addElement("prezzo");
			node.setText(m.getPrezzo());
			node = mp.addElement("descrizione");
			node.setText(m.getDescrizione());
			node = mp.addElement("lista_attivita");
			node.setText((lAtt!=null)?lAtt:"");
		} catch (Exception e)
		{
			String errorMessage = "Impossibile trovare metapratica" + id;
			msg=XMessage.getMessaggioErrore(errorMessage);
			logger.error(errorMessage, e);
			return msg.asXML();
		}
		return msg.asXML();
	}

	private String getListaAttivitaAsHtml(String[] lista_attivita)
	{
		if (lista_attivita==null || lista_attivita.length==0){
			return null;
		}
		String res = "<ul>";
		for (String att:lista_attivita)	{
			res += "<li>" + att + "</li>";
		}
		return res + "</ul>";
	}
	 
}
