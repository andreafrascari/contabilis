package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class MyFattura extends Fattura{
	
	private static final Logger logger = Logger.getLogger(MyFattura.class);
	
	public static Fattura fromProforma(ProForma p, HttpServletRequest request) throws SerenaException	{
		Fattura f = new Fattura();
		f.setData(new Date());
		String annoContabile = new Integer(new SerenaDate().getYear()).toString();
		
		Integer numeroFattura = (p.getCompetenza().equals(COMPETENZA__CONTABILIS))?MyFattura.getNextNumberContabilis(annoContabile, request):MyFattura.getNextNumberStudio( p.getAnno_contabile(), request);
		if (p.getNumero()!=null) {
			f.setNumero(numeroFattura);
		}
		// f.setAnno_contabile(p.getAnno_contabile());
		f.setAnno_contabile(annoContabile);
		if (p.getCompetenza()!=null && !p.getCompetenza().isEmpty()){
			f.setCompetenza(p.getCompetenza());
		}
		f.setDa_proforma(p);
		if (p.getData_pagamento()!=null){
			f.setData_pagamento(p.getData_pagamento());
			f.setData(p.getData_pagamento());
		}
		if (p.getNote()!=null){
			f.setNote(p.getNote());
		}
		if (p.getRa()!=null && p.getRa()){
			f.setRa(p.getRa());
		}
		if (p.getRiv_prev()!=null && p.getRiv_prev()){
			f.setRiv_prev(p.getRiv_prev());
		}
		if (p.getSpese_anticipate_desc()!=null && !p.getSpese_anticipate_desc().isEmpty()){
			f.setSpese_anticipate_desc(p.getSpese_anticipate_desc());
		}
		if (p.getSpese_anticipate_fattura()!=null && p.getSpese_anticipate_fattura()!=0){
			f.setSpese_anticipate_fattura(p.getSpese_anticipate_fattura());
		}
		f.setInverse_of_fatture(p.getInverse_of_proforma());
		
		ArrayList<VoceFattura> vf = p.getVoci_proforma();
		if (vf!=null){
			f.setVoci_fattura(vf);
			for (VoceFattura v: vf){
				if (v.getIva().isEmpty())
					v.setIva(null);	// se l'iva e' vuota dall'importazione (es: 20%) prende il default del db
			}
			
		}
		return f;
	}
	
	public static Integer getNextNumber(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero", anno_contabile, request);
	}
		
	public static Integer getNextNumberStudio(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero-studio", anno_contabile, request);
	}

	public static Integer getNextNumberContabilis(String anno_contabile, HttpServletRequest request) throws SerenaException {
		return getNextNumber("max-numero-contabilis", anno_contabile, request);
	}
	
	/**
	 * Torna il prossimo numero per l'anno contabile passato a parametro
	 * @param anno_contabile
	 * @return
	 */
	public static Integer getNextNumber(String view, String anno_contabile, HttpServletRequest request) throws SerenaException {
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addAttribute(ConstantsXSerena.ATTR_FORCED_DATASOURCE, view);
			currentElement.addElement(SLOT_NUMERO);
			currentElement.addElement(SLOT_DATA);
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition = condition.addElement(SLOT_NOTE);
			condition.setText(anno_contabile);
			Document data = ApplicationLibrary.getData(currentElement.getDocument(),request);
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data); 
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getNextNumber has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return new Integer("1");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				Element theInstance = (Element)dataElem.selectSingleNode(INSTANCE_NAME);
				String currentId =theInstance.elementText(SLOT_NUMERO);
				if (currentId==null || currentId.isEmpty())
					return new Integer("1");
				else
					return new Integer((new Integer(currentId).intValue())+1);
			}
			else
				return null;
		} catch (Exception e) {
			String msg = "getNextNumber has resulted into sql error:"+e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

}
