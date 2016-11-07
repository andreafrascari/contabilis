package eu.anastasis.tulliniHelpGest.modules;




import javax.servlet.http.HttpServletRequest;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;


/**
 * Da un birt report + dati descrittivi crea e registra un'istanza di Documento
 * @author afrascari
 *
 */
public class BirtReportProforma2DocumentoMethod extends BirtReport2DocumentoMethod
{

	private static final String METHOD_NAME = "birtproforma2documento";
	
	private static final Logger logger = Logger.getLogger(BirtReportProforma2DocumentoMethod.class);
	
	public BirtReportProforma2DocumentoMethod(DefaultModule parentModule, String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	 
	
	/** aggiorna la proforma settando lo stato a spedita
	 * @param request
	 * @param theClass
	 * @param theID
	 */
	protected void updateIstanzaDominio(HttpServletRequest request,
			String theClass, String theID) throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, ProForma.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(theID);
			currentElement = currentElement.addElement(ProForma.SLOT_STATO_PROFORMA);
			currentElement.setText(ProForma.STATO_PROFORMA__SPEDITA);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,ProForma.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot update stato proforma: "+messages2[0]);
				throw new SerenaException("cannot update stato proforma: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot update stato proforma "+e.getMessage());
			throw new SerenaException("cannot update stato proforma "+e.getMessage());
		}

	}

	 

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	
	
}

