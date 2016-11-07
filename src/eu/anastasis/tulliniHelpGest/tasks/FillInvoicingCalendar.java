package eu.anastasis.tulliniHelpGest.tasks;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.modules.StoriaDocumento;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.exceptions.PermissionException;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.dataManager.ObjectAlreadyExistsException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.persistence.CostantiArchitettura;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.CalendarioFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.DatiFatturazione;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProForma;

public class FillInvoicingCalendar implements Runnable  {
	
	protected static String date_format=null;
	private static CronPersistenceHome cph = null;

	private static final Logger logger = Logger.getLogger(FillInvoicingCalendar.class);
	
	private String message = "";
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void run() 
	{
		String res = "";
		try
		{
			logger.info("Starting FillInvoicingCalendar...");
			removeDeletedEntries();
			Element itemCandidati = this.getItemCandidati();
			Vector<String> clientiCalendarizzati = getCaledarForCurrentYear();
			List<Element> candidati =itemCandidati.selectNodes(".//"+DatiFatturazione.INSTANCE_NAME);
			for (Element aCandidate: candidati){
				Element clienteElem =aCandidate.element(DatiFatturazione.SLOT_INVERSE_OF_DATI_FATTURAZIONE).element(Cliente.INSTANCE_NAME);
				if (clienteElem==null){
					logger.warn("Dati fatturazione senza cliente ... ignorati");
					continue;
				}
				String cliente = clienteElem.elementText(Cliente.SLOT_CLIENTE);
				String idcliente = clienteElem.elementText("ID");
				res +="<br />Processing "+cliente+ ": ";
				// ATTENZIONE!!!! se esistono gia entries di calendario di quest'anno per il cliente .... non si aggiornano
				if (clientiCalendarizzati!=null && clientiCalendarizzati.contains(idcliente))	{
					logger.trace("Cliente gia calendarizzato: "+cliente);
					res +="Cliente gia calendarizzato";
					continue;
				}
				int nRate = new Integer(aCandidate.elementText(DatiFatturazione.SLOT_N_RATE)).intValue();
				if (nRate<1 || nRate>12){
					logger.error("Cannot fill invoicing calendar as number of installments is not between 1 and 12");
					res+="NO";
				} else {
					try	{
						int nRegEntries = processCandidate(aCandidate);
						res+="<strong>OK: registrate " + nRegEntries + " entries su " + nRate + ".</strong> Le eventuali rimanenti erano gia presenti o scadute.";
					}catch (Exception e) {
						res+="NO";
					}
				}
			logger.info("Esito FillInvoicingCalendar: "+res);	
			}
			
		} catch (SerenaException e)
		{
			setMessage("Errore: "+e.getMessage());
			logger.error(e.getMessage());
			return;
		} catch (Exception e)
		{
			setMessage("Errore: "+e.getMessage());
			logger.error(e.getMessage());
			return;
		}
		setMessage(res);
	}
		
	private int processCandidate(Element aCandidate) throws SerenaException	{
		int nRate = new Integer(aCandidate.elementText(DatiFatturazione.SLOT_N_RATE)).intValue();
		int nEntriesOk = 0;
		String idCliente = aCandidate.element(DatiFatturazione.SLOT_INVERSE_OF_DATI_FATTURAZIONE).element(Cliente.INSTANCE_NAME).elementText("ID");
		String eventualeData = (aCandidate.element(DatiFatturazione.SLOT_DATA_PRIMA_RATA)!=null)?aCandidate.element(DatiFatturazione.SLOT_DATA_PRIMA_RATA).getText():null;
		try	{
			String[] programmaFatturazione = computeInvoicingProgramme(nRate,eventualeData);
			for (int i=0; i<programmaFatturazione.length; i++){
				/* modifica richiesta da Fabio in mail del 9/10/12 e confermata il 15/11/12:
				 * E' possibile, lasciando 4 rate a beneficio degli anni futuri, fare in modo che il calendario non generi le rate già scadute? 
				 * In pratica, gli importi potrebbero andare bene divisi per 4, ma escono solo le rate in scadenza dopo la generazione del listino?
				 */
				if (!new SerenaDate().withoutTime().isAfter(new SerenaDate(programmaFatturazione[i]))){
					boolean res = insertCalendarEntry(i+1,nRate,programmaFatturazione[i],idCliente);
					if (res)	{
						nEntriesOk++;
					} 
				}else {
					logger.info("Entry candidata di calendario ignorata in quanto scaduta: "+programmaFatturazione[i]);
				}
			}
		}catch (Exception e) {
			String mess ="Cannot register invoicing calendar entries for customer " + idCliente + ": "+e.getMessage(); 
			logger.error(mess);
			throw new SerenaException(mess);
		}
		return nEntriesOk;
	}

	/**
	 * inserisce entry nel calendario fatturazione (anno corrente)
	 * @param i = rata
	 * @param nRate
	 * @param dateEntry
	 * @param idCliente
	 * @return true=OK; false=data già presente exception=error
	 * @throws SerenaException
	 */
	private boolean insertCalendarEntry(int i, int nRate, String dateEntry,
				String idCliente) throws SerenaException	{
		boolean retVal=false;
		try	{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(CalendarioFatturazione.INSTANCE_NAME, ConstantsXSerena.ACTION_SET, CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
			
			Element anItem = currentElement.addElement("data");
			anItem.setText(dateEntry);
			anItem = currentElement.addElement("per_cliente");
			anItem = anItem.addElement("Cliente");
			anItem = anItem.addElement("ID");
			anItem.setText(idCliente);
			anItem = currentElement.addElement("rata");
			anItem.setText(new Integer(i).toString());
			anItem = currentElement.addElement("su_rate");
			anItem.setText(new Integer(nRate).toString());

			Document data = this.getMyCronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				String mess ="Cannot register invoicing calendar entry " + dateEntry + " of customer "+ idCliente + ": "+messages2[0]; 
				logger.error(mess);
				throw new SerenaException(mess);
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	{
				retVal = true;
			}		
		} catch (ObjectAlreadyExistsException e)
		{
			logger.warn("Invoicing calendar entry " + dateEntry + " of customer "+ idCliente + " already registered!");
			retVal = false;
		} catch (Exception e) {
			String mess ="Cannot register invoicing calendar entry " + dateEntry + " of customer "+ idCliente + ": "+e.getMessage(); 
			logger.error(mess);
			throw new SerenaException(mess);
		}
	return retVal;					
	}

	/**
	 * desume un calendario di fatturazione con in base al n. di rate
	 * @param dataPrimaFattura
	 * @param nRate
	 * @return
	 */
	private String[] computeInvoicingProgramme(int nRate,String eventualeData) {
		String[] res = new String[nRate];
		if (eventualeData!=null && !eventualeData.isEmpty() && nRate==1)	{
			// una sola rata ... con data specificata
			SerenaDate theDate = new SerenaDate(eventualeData);
			int yearsBefore = new SerenaDate().getYear()-theDate.getYear();
			theDate.addYears(yearsBefore);
			res[0] = theDate.toString(); 
		} else {
			String primoGennaio = "01/01/"+new SerenaDate().getYear();
			SerenaDate sdPrimoGennaio = new SerenaDate(primoGennaio);
			int monthLapse = 12 / nRate;
			for (int i=0; i<nRate-1; i++)	{
				sdPrimoGennaio.addMonths(monthLapse);
				res[i] = sdPrimoGennaio.toString();
			}
			res[nRate-1]=(new SerenaDate("31/12/"+new SerenaDate().getYear())).toString();
		}
		return res;
	}
	
	/**
	 * Torna array con tutte gli utenti delle entries dell'anno corrente ... DA NON SOVRASCRIVERE!
	 * @return
	 * @throws SerenaException
	 */
	protected Vector<String> getCaledarForCurrentYear() throws SerenaException	{
		Vector<String> retArray = new Vector<String>();
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(CalendarioFatturazione.INSTANCE_NAME, ConstantsXSerena.ACTION_GET, CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION); 
			condition =condition.addElement(CalendarioFatturazione.SLOT_DATA);
			condition.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_GREATER_EQUAL_THAN);
			condition.setText(new SerenaDate("1/1/"+new SerenaDate().getYear()).toString());
			
			Document data = getMyCronPersistenceHome().get(currentElement.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,CalendarioFatturazione.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				List<Element> entries =dataElem.selectNodes(".//"+CalendarioFatturazione.INSTANCE_NAME);
				
				for (Element aCandidate: entries){
					String idCliente =aCandidate.element(CalendarioFatturazione.SLOT_PER_CLIENTE).element(Cliente.INSTANCE_NAME).elementText("ID"); 
					if (!retArray.contains(idCliente))
						retArray.add(idCliente);
				}
				return retArray;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	
/*
	protected void addNoLongerClients(Vector<String> offClientArray) throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(Cliente.INSTANCE_NAME, ConstantsXSerena.ACTION_GET, Cliente.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION); 
			condition =condition.addElement(Cliente.SLOT_CESSATA_ASSISTENZA_IL);
			condition.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_NOT);
			
			Document data = getMyCronPersistenceHome().get(currentElement.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,CalendarioFatturazione.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				List<Element> entries =dataElem.selectNodes(".//"+CalendarioFatturazione.INSTANCE_NAME);
				
				for (Element aCandidate: entries){
					String idCliente =aCandidate.element(CalendarioFatturazione.SLOT_PER_CLIENTE).element(Cliente.INSTANCE_NAME).elementText("ID"); 
					if (!retArray.contains(idCliente))
						retArray.add(idCliente);
				}
				return retArray;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getCaledarForCurrentYear for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	*/

	/** Tutti i dati fatturazione degli utenti che non hanno entries nel calendario per quest'anno
	 * 
	 * @return
	 * @throws SerenaException
	 */
	protected Element getItemCandidati() throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(DatiFatturazione.INSTANCE_NAME, ConstantsXSerena.ACTION_GET, DatiFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			condition =condition.addElement(DatiFatturazione.SLOT_INVERSE_OF_DATI_FATTURAZIONE);
			condition =condition.addElement(Cliente.INSTANCE_NAME);
			condition =condition.addElement(Cliente.SLOT_CESSATA_ASSISTENZA_IL);
			//condition.addAttribute(ConstantsXSerena.ATTR_OPERATOR, ConstantsXSerena.VAL_NOT);
			condition.setText(ConstantsXSerena.VAL_NULL);
			
			
			Document data = getMyCronPersistenceHome().get(currentElement.getDocument());
			
			Element dataElem = ApplicationLibrary.prepareDataForPresentation(data);
			String[] messages={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(dataElem, messages,DatiFatturazione.INSTANCE_NAME);
			
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("getItemCandidati for invoicing calendar has resulted into sql error:"+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				return null;
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return dataElem;
			}
			else
				return null;
		} catch (PermissionException e) {
			logger.error("getItemCandidati for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("getItemCandidati for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	
	
	protected boolean removeDeletedEntries() throws SerenaException	{
		try {
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(CalendarioFatturazione.INSTANCE_NAME, ConstantsXSerena.ACTION_SET, CalendarioFatturazione.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_DELETE);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATOR, CostantiArchitettura.DELETE_PHYSICAL);
			
			currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement(CalendarioFatturazione.SLOT_PROFORMA_EMESSA);
			currentElement = currentElement.addElement(ProForma.INSTANCE_NAME);
			currentElement = currentElement.addElement("deletion_flag");
			currentElement.setText("1");

			
			Document data = this.getMyCronPersistenceHome().set(currentElement.getDocument());
			String[] messages2={"",""};
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages2,StoriaDocumento.MY_CLASS);
			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("removeDeletedEntries for invoicing calendar has resulted into sql error:"+messages2[0]);
				throw new SerenaException(messages2[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				return true;
			}
			else
				return false;
		} catch (PermissionException e) {
			logger.error("removeDeletedEntries for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		} catch (DataManagerException e) {
			logger.error("removeDeletedEntries for invoicing calendar has resulted into sql error"+e.getMessage());
			throw new SerenaException(e.getMessage());
		}
	}
	
	/**
	 * Attention!!! It's static
	 * @return
	 */
	private CronPersistenceHome getMyCronPersistenceHome(){
		if (cph==null)
			cph = new CronPersistenceHome();
		return cph; 
	}

	
}
