package eu.anastasis.tulliniHelpGest.modules;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Attivita;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Pratica;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.VoceFattura;

 
public class DeleteVoceFatturaMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DeleteVoceFatturaMethod.class);


	private static final String METHOD_NAME = "deletevoceproforma";

	public DeleteVoceFatturaMethod(DefaultModule parentModule,String[] defaultParameters)
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
		String id_pratica = request.getParameter("PRATICA");
		String id_voce = request.getParameter("VOCE");
		String id_proforma= request.getParameter("PROFORMA");
		String message ="";
		Pratica p=null;
		if (id_pratica!=null && ! id_pratica.isEmpty())	{
			// solo se c'e' una pratuca associata!!!!!
			TulliniHelpGestBeanDataManager tdm = new TulliniHelpGestBeanDataManager();
			p = tdm.getPratica(request, 2, id_pratica);
		}
		logger.trace("Cancellazione intelligenete voce ("+id_voce+ ") di proforma  ("+id_proforma+ ") con ripristino pratica ("+id_pratica+ ") e relative attivita");
		try	{
			deleteVoce(request,id_voce);
			if (p!=null)	{
				message = "Pratiche e attivita correttamente ripristinati allo stato precedente";
				if (Pratica.TIPO__A_ORA.equals(p.getTipo())){
					logger.trace("Pratica a ore: rollback attivita a minuti fatturazione precedenti");
					message = rollbackAttivita(request,p.getAttivita());
				} else if (Pratica.TIPO__A_PRESTAZIONE.equals(p.getTipo())){
					logger.trace("Pratica a prestazione: rollback a stato chiusa (con data odierna)");
					message = rollbackPrestazione(request,id_pratica);
				} else if (Pratica.TIPO__A_FORFAIT.equals(p.getTipo())){
					logger.trace("Pratica a forfait: rollback a stato aperta");
					message = rollbackForfait(request,id_pratica);
				}

			} else {
				message = "Nessuna pratica da ripristinare allo stato precedente";
			}
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS",message);
			template.replaceTagInBlock("ID", "RESULT_SUCCESS",id_proforma);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Errore nella cancellazione della voce "+ id_voce + " di proforma "+id_proforma;
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		} 
	}

	private String rollbackPrestazione(HttpServletRequest request,
			String id_pratica) throws SerenaException {
		String ret = "";
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(id_pratica);
			Element tmp= currentElement.addElement(Pratica.SLOT_STATO);
			tmp.setText(Pratica.STATO__CHIUSA);
			tmp= currentElement.addElement(Pratica.SLOT_DATA_CHIUSURA);
			tmp.setText(new SerenaDate().toString());
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot restore stato pratica: "+messages2[0]);
				ret = "Impossibile ripristinare stato pratica relativa ("+id_pratica+") a CHIUSA";
			}			
			return ret;
		} catch (Exception e) {
			logger.error("cannot restore stato pratica"+e.getMessage());
			throw new SerenaException("cannot restore stato pratica"+e.getMessage());
		}
	}

	private String rollbackForfait(HttpServletRequest request,
			String id_pratica) throws SerenaException {
		String ret = "";
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Pratica.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(id_pratica);
			Element tmp= currentElement.addElement(Pratica.SLOT_STATO);
			tmp.setText(Pratica.STATO__APERTA);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Pratica.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot restore stato pratica: "+messages2[0]);
				ret = "Impossibile ripristinare stato pratica relativa ("+id_pratica+") a CHIUSA";
			}			
			return ret;
		} catch (Exception e) {
			logger.error("cannot restore stato pratica"+e.getMessage());
			throw new SerenaException("cannot restore stato pratica"+e.getMessage());
		}
	}

	private String rollbackAttivita(HttpServletRequest request, ArrayList<Attivita> attivita) throws SerenaException {
		String ret = "";
		try	{
			for (Attivita a: attivita)	{
				Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Attivita.INSTANCE_NAME);
				currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
				Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
				condition = condition.addElement("ID");
				condition.setText(a.getId());
				currentElement = currentElement.addElement(Attivita.SLOT_STATO_ATTIVITA);
				currentElement.setText(a.getMinuti_ultima_fattura().toString());
				Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
				String[] messages2={"",""};
				int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,Attivita.INSTANCE_NAME);
				if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
				{
					logger.error("cannot restore minuti fatturati: "+messages2[0]);
					ret += "<br />Impossibile ripristinare precedenti minuti fatturati attivita "+a.getTitolo();
				}
			}
			return ret;
		} catch (Exception e) {
			logger.error("cannot restore minuti fatturati"+e.getMessage());
			throw new SerenaException("cannot restore minuti fatturati"+e.getMessage());
		}
		
	}

	private void deleteVoce(HttpServletRequest request, String id_voce) throws SerenaException {
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET, VoceFattura.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_DELETE_PHYSICAL);
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement("ID");
			condition.setText(id_voce);
			Document data = ApplicationLibrary.setData(currentElement.getDocument(),request, true);
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,VoceFattura.INSTANCE_NAME);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("cannot delete  voce fattura: "+messages2[0]);
				throw new SerenaException("cannot delete  voce fattura: "+messages2[0]);
			}			
		} catch (Exception e) {
			logger.error("cannot cannot delete  voce fattura "+e.getMessage());
			throw new SerenaException("cannot delete  voce fattura"+e.getMessage());
		}
		
	}
	
 
	 
}
