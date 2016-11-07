package eu.anastasis.tulliniHelpGest.modules;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.ModuleParameterConfiguration;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateFactory;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Metasollecito;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.SollecitoPagamento;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

 
public class Proforma2SollecitoMethod extends DefaultMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Proforma2SollecitoMethod.class);


	private static final String METHOD_NAME = "proforma2sollecito";
	private static final String PROFORMA_TOTALI_CLASS= "ProForma_Totali";

	public Proforma2SollecitoMethod(DefaultModule parentModule,String[] defaultParameters)
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
		String id_proforma = request.getParameter("PROFORMA");
		String id_sollecito = request.getParameter("SOLLECITO");
		try	{
			Element theProforma = getProforma(request, id_proforma);
			String data = theProforma.elementText(ProForma.SLOT_DATA);
			String numero = theProforma.elementText(ProForma.SLOT_NUMERO);
			String competenza = theProforma.elementText(ProForma.SLOT_COMPETENZA);
			String importo = theProforma.elementText("Totale_Da_Pagare");
			ProForma p = new ProForma();
			p.setId(id_proforma);
			TulliniHelpGestBeanDataManager thdm = new TulliniHelpGestBeanDataManager();
			Metasollecito ms = thdm.getMetasollecito(request, 0, id_sollecito);
			SollecitoPagamento s = new SollecitoPagamento();
			String testo = ms.getTesto();
			testo = testo.replace("@DATA@", data);
			testo = testo.replace("@NUMERO@", numero);
			testo = testo.replace("@IMPORTO@", formatImporto(importo));
			String fullCompetenza = (competenza.equals(ProForma.COMPETENZA__CONTABILIS))?"Contabilis Srl":"Dott. Fabio Tullini";
			testo = testo.replace("@COMPETENZA@", fullCompetenza);
			String coordinateBancarie = (competenza.equals(ProForma.COMPETENZA__CONTABILIS))?ModuleParameterConfiguration.getInstance().getValue("helpgest","COORDINATE_BANCARIE_CONTABILIS"):ModuleParameterConfiguration.getInstance().getValue("helpgest","COORDINATE_BANCARIE_STUDIO"); 
			testo = testo.replace("@COORDINATE_BANCARIE@", coordinateBancarie);
			s.setDescrizione(testo);
			s.setOggetto(ms.getOggetto());
			s.setNumero_sollecito(ms.getNumero_sollecito());
			s.setInverse_of_solleciti_pagamento(p);
			String newId = thdm.insertSollecitoPagamento(request, s);
			template.replaceTagInBlock("ID", "RESULT_SUCCESS",newId);
			template.publishBlock("RESULT_SUCCESS");
			template.publish();
			return template.getContenuto();
			
		} catch (ObjectAlreadyExistsException e)
		{
			String errorMessage = "Sollecito n. "+ id_sollecito +" gi&agrave; presente.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		} 
		catch (Exception e)
		{
			String errorMessage = "Impossibile generare il sollecito a causa di un errore: contattare l'assistenza.";
			logger.error(errorMessage, e);
			template.replaceTagInBlock("ERROR_MESSAGE", "RESULT_ERROR",errorMessage );
			template.publishBlock("RESULT_ERROR");
			template.publish();
			return template.getContenuto();
		}
	}
	

	 
	/**
	 * Torna la proforma identificata da idproforma
	 * @param request
	 * @param anno_contabile
	 * @param idproforma
	 * @return
	 * @throws SerenaException
	 */
	private Element getProforma(HttpServletRequest request,
			String idproforma) throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, PROFORMA_TOTALI_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			Element item=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			item =item.addElement("ID");
			item.setText(idproforma);
			currentElement.addElement("ID");
			currentElement.addElement(ProForma.SLOT_DATA);
			currentElement.addElement(ProForma.SLOT_NUMERO);
			currentElement.addElement(ProForma.SLOT_COMPETENZA);
			currentElement.addElement("Totale_Da_Pagare");
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,PROFORMA_TOTALI_CLASS);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getProforma has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return (Element)dataElem.selectSingleNode(".//"+PROFORMA_TOTALI_CLASS);
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getProforma has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getPratiche for proforma has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}

	 
	private String formatImporto(String importo) {
		final Float f = new Float(importo);
		String theValue = null;
		try
		{
			final DecimalFormat currency = new DecimalFormat("#,###.00");
			theValue = currency.format(f);
		} catch (final Exception e)
		{
			logger.error("impossibile formattare importo " + new Float(f).toString() +": "+ e);
			return "0";
		}
		if (",00".equals(theValue))	{
			theValue = "0";
		}
		return theValue;
	}
	 
}
