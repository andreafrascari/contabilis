package eu.anastasis.tulliniHelpGest.modules;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.SollecitoPagamento;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.utils.MailAndSmsSender;

/**
 * Genera le pratiche dell'anno corrente di un cliente a partire dal listino
 * @author afrascari
 *
 */
public class SendSollecitoMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SendSollecitoMethod.class);

	private static final String METHOD_NAME = "sendsollecito";

	
	public SendSollecitoMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

 	
	@SuppressWarnings("unchecked")
	@Override
	public String doMethod(HttpServletRequest request,HttpServletResponse response) throws SerenaException
	{
		ActiveTemplate template = new ActiveTemplate(TemplateFactory.getTemplate(
				retrieveDefaultTemplateContext(),retrieveTemplateContext(), getName()));
		String mess = "";
		try	{
			String id_sollecito = request.getParameter("ID");
			TulliniHelpGestBeanDataManager thdm = new TulliniHelpGestBeanDataManager();
			Element s = getSollecito(request,  id_sollecito );
			String oggetto = s.elementText(SollecitoPagamento.SLOT_OGGETTO);
			String testo = s.elementText(SollecitoPagamento.SLOT_DESCRIZIONE);
			String idCliente = s.element(SollecitoPagamento.SLOT_INVERSE_OF_SOLLECITI_PAGAMENTO).element(ProForma.INSTANCE_NAME).element(ProForma.SLOT_INVERSE_OF_PROFORMA).element(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente.INSTANCE_NAME).elementText("ID");
			Cliente questoCliente = new Cliente().getInstance(idCliente);
			if (questoCliente==null)	{
				String errorMessage = "Impossibile reperire cliente "+idCliente+ ", probabilmente a causa di cessata assistenza.";
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			MailAndSmsSender sender = new MailAndSmsSender();
			boolean res = false;
			// mail/fax/sms
			if (questoCliente.notificheViaSms())	
				mess +="Impossibile spedire solleciti pagamento via sms, per quanto sia il canale privilegiato del cliente.<br />";
			if (questoCliente.notificheViaFax())	{
				String esito = sender.sendFax(oggetto, testo, questoCliente,null);
				if (esito==null)	{
					res = true;
					mess+="Sollecito inviato via fax.";
				} else { 
					mess+=esito;
				}
			} 	else	{ 
				String esito = sender.sendMail(oggetto, testo, questoCliente);
				if (esito==null)	{
					res = true;
					mess+="Sollecito inviato via mail.";
				} else { 
					mess+=esito;
				}
			}
			if (res)	{
				// update data spedizione:
				if (!updateDataSpedizione(request,id_sollecito))	{
					mess +=" - Data spedizione non registrata!";
				}
				template.replaceTagInBlock("MESS", "RESULT_SUCCESS", mess);
				template.replaceTagInBlock("ID", "RESULT_SUCCESS", id_sollecito);
				template.publishBlock("RESULT_SUCCESS");
			} else 	{
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR", mess);
				template.publishBlock("RESULT_ERROR");
			}
			template.publish();
			return template.getContenuto();
		} catch (Exception e)		{
			String errorMessage = "Impossibile spedire il sollecito a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
	
	private boolean updateDataSpedizione(HttpServletRequest request,
			String id_sollecito) {
	 		try	{
				Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, SollecitoPagamento.INSTANCE_NAME);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
				
				Element anItem = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				anItem = anItem.addElement("ID");
				anItem.setText(id_sollecito);
				
				anItem = currentElement.addElement(SollecitoPagamento.SLOT_DATA_SPEDIZIONE);
				anItem.setText(new SerenaDate().toString());

				Document data = ApplicationLibrary.setData(currentElement.getDocument(),request);
				String[] messages2={"",""};
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,SollecitoPagamento.INSTANCE_NAME);
				String newID = null;
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					logger.error("cannot update data spedizione "+messages2[0]);
					return false;
				}
				return true;
			} catch (Exception e) {
				String msg = "cannot update data spedizione "+e.getMessage();
				logger.error(msg);
				return false;
			}	
			
	}

	private Element getSollecito(HttpServletRequest request,
			String idsollecito) throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, SollecitoPagamento.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			Element item=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			item =item.addElement("ID");
			item.setText(idsollecito);
			currentElement.addElement("ID");
			currentElement.addElement(SollecitoPagamento.SLOT_OGGETTO);
			currentElement.addElement(SollecitoPagamento.SLOT_DESCRIZIONE);
			item = currentElement.addElement(SollecitoPagamento.SLOT_INVERSE_OF_SOLLECITI_PAGAMENTO);
			item = item.addElement(ProForma.INSTANCE_NAME);
			item = item.addElement(ProForma.SLOT_INVERSE_OF_PROFORMA);
			item = item.addElement(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente.INSTANCE_NAME);
			item = item.addElement("ID");
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,SollecitoPagamento.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getSollecito has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return (Element)dataElem.selectSingleNode(".//"+SollecitoPagamento.INSTANCE_NAME);
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getSollecito has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getSollecito for proforma has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	 
	 
}
