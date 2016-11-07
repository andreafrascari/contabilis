package eu.anastasis.tulliniHelpGest.modules;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.serena.persistence.PersistenceConfiguration;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Fattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyFattura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

/**
 * Genera le pratiche dell'anno corrente di un cliente a partire dal listino
 * @author afrascari
 *
 */
public class Proforma2FatturaMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Proforma2FatturaMethod.class);

	private static final String METHOD_NAME = "proforma2fattura";

	
	public Proforma2FatturaMethod(DefaultModule parentModule,String[] defaultParameters)
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
		String mess="";
		try	{
			String id_proforma = request.getParameter("ID");
			String data_pagamento = request.getParameter("INP_data_pagamento");
			if (id_proforma==null || data_pagamento == null){
				String errorMessage = "Impossibile generare la fattura senza una proforma o senza la data di pagamento della stessa";
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();
			}
			TulliniHelpGestBeanDataManager tbdm = new TulliniHelpGestBeanDataManager();
			ProForma p= tbdm.getProForma(request, 2, id_proforma);
			if (p.getCompetenza()==null || p.getCompetenza().isEmpty())	{
				String errorMessage = "Impossibile generare fattura di proforma senza competenza";
				logger.error(errorMessage);
				template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
				template.publishBlock("RESULT_ERROR");
				template.publish();
				return template.getContenuto();				
			}
			MyProForma p2 = new MyProForma(p.getId());
			p2.updatePagamento(data_pagamento,request);
			Fattura f = MyFattura.fromProforma(p,request);
			 
			SimpleDateFormat myDateFormat = new SimpleDateFormat(PersistenceConfiguration.GetInstance().getParametro(CostantiArchitettura.INTERFACE_DATE_FORMAT));
			Date myDate = myDateFormat.parse(data_pagamento);
			f.setData_pagamento(myDate);
			f.setData(myDate);
			String newId = tbdm.insertFattura(request, f);

			mess+="Vai alla <a href=\"?q=object/detail&amp;p="+Fattura.INSTANCE_NAME+"/_a_ID/_v_"+newId+"\" title=\"vai alla nuva fattura\">nuova fattura</a><br />";			 
			template.replaceTagInBlock("MESS", "RESULT_SUCCESS",mess);
			template.publishBlock("RESULT_SUCCESS");
			template.replaceTag("FATTURA", newId);
			template.publish();
			return template.getContenuto();
			
		} catch (Exception e)
		{
			String errorMessage = "Impossibile generare la fattura a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
	 
	 
}
