package eu.anastasis.tulliniHelpGest.utils;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.fileimport.ExcelFileImportProcessor;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;


public class Car4uFleetImporter extends ExcelFileImportProcessor {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused") private static final Logger logger = Logger.getLogger(Car4uFleetImporter.class);
	
	private static final String Car4uFleetClass = "Car4uFleet";
	private static final String Car4uFleetTarga= "targa";
	private static final String Car4uFleetCliente= "cliente";
	
	private String outcome = null;
	
	
	@Override
	public String getOutcome()	{
		return outcome;
	}
	
	@Override
	public boolean doImport(HttpServletRequest request, List<List<List<Object>>> fileMap, boolean override) throws SerenaException {
		// only care about first sheet
		List<List<Object>> dexter =  fileMap.get(0);
		int inserted=0, updated=0, total=-1;
		ArrayList<String> storedDates = new ArrayList<String>();
		loadExistingData(request,storedDates);
		// rolling over rows:
		for (Iterator<List<Object>> i = dexter.iterator(); i.hasNext(); )	{
			List<Object> aRow = i.next();
			total++;
			if (total==0)	{
				continue; // first row is header
			}
			String targa = null;
			try	{
				targa= aRow.get(2).toString();
			} catch (Exception e){
				// file must be over!!!
			}
			if (targa!=null)	{
				String cliente = aRow.get(11).toString();
				
				if (this.targaExists(request, targa, storedDates))
					updated+=existingTargaFound(request, override,targa,cliente);
				else
					inserted+=newTargaFound(request, targa,cliente);
			}
		}
		outcome = "Importazione avvenuta correttamente: inserite " + inserted + " righe e aggiornate "+updated+ " su "+ total + " (+/-1!!)";
		if (override)
			outcome+= " con sovrascrittura.";
		else
			outcome+= " senza sovrascrittura.";
		if ((inserted+updated)<total)
			outcome+= "<br /><br />Attenzione! Il numero di righe inserite o aggiornate non corrisponde al totale: si consiglia di controllare il file caricato, correggere eventuali errori, ricaricarlo e ripetere la procedura."; 
		return true;
	}
	
	/**
	 * Prepare insert x-query
	 * @param request
	 * @return
	 */
	private Element prepareInsertXQuery(HttpServletRequest request)	{
		Element xqueryInsert= ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Car4uFleetClass);
		xqueryInsert.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_INSERT);
		xqueryInsert.addElement(Car4uFleetTarga);
		xqueryInsert.addElement(Car4uFleetCliente);
		return xqueryInsert;
	}

	/**
	 * Prepare update x-query
	 * @param request
	 * @return
	 */
	private Element prepareUpdateXQuery(HttpServletRequest request)	{
		Element xqueryUpdate= ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_SET,Car4uFleetClass);
		xqueryUpdate.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_UPDATE);
		xqueryUpdate.addElement(Car4uFleetTarga);
		xqueryUpdate.addElement(Car4uFleetCliente);
		Element currentEl = xqueryUpdate.addElement(ConstantsXSerena.TAG_CONDITION);
		currentEl.addElement(Car4uFleetTarga);
		return xqueryUpdate;
	}

	/**
	 * Process an already stored instance
	 * @param request
	 * @param override
	 * @param theDate
	 * @param t
	 * @param gg
	 * @return 1 if updated, 0 otherwise
	 * @throws SerenaException
	 */
	private int existingTargaFound(HttpServletRequest request, boolean override,String targa,String cliente)	throws SerenaException {
		System.out.println("ESISTING "+targa+ " " + cliente);
		if (override)	{
			Element xqueryUpdate = this.prepareUpdateXQuery(request);
			xqueryUpdate.element(Car4uFleetTarga).setText(targa);
			xqueryUpdate.element(Car4uFleetCliente).setText(cliente);
			xqueryUpdate.element(ConstantsXSerena.TAG_CONDITION).element(Car4uFleetTarga).setText(targa);
			try
			{
				Document dataResponse = ApplicationLibrary.setData(xqueryUpdate.getDocument(), request);
				if (XSerenaLibrary.isValidResult(dataResponse))
				{
					return 1;
				}
				else
					return 0;
			} catch (Exception e)
			{
				logger.error("[Car4uFleetImporter]->existingTargaFound cannot insert: "+e.toString());
				return 0;
			}
		} else
			return 0;
	}

	/**
	 * Process a new instance (-> insert)
	 * @param request
	 * @param theDate
	 * @param t
	 * @param gg
	 * @return 1 if inserted, 0 otherwise
	 * @throws SerenaException
	 */
	private int newTargaFound(HttpServletRequest request, String targa,String cliente)	throws SerenaException {
		System.out.println("NEW "+targa+ " "+cliente);
		Element xqueryInsert = this.prepareInsertXQuery(request);
		xqueryInsert.element(Car4uFleetTarga).setText(targa);
		xqueryInsert.element(Car4uFleetCliente).setText(cliente);
		try
		{
			Document dataResponse = ApplicationLibrary.setData(xqueryInsert.getDocument(), request);
			if (XSerenaLibrary.isValidResult(dataResponse))
			{
				return 1;
			}
			else return 0;
		} catch (Exception e)
		{
			logger.error("[Car4uFleetImporter]->newTargaFound cannot insert: "+e.toString());
			return 0;
		}
	}

	/**
	 * Does the current date aDate exist into the cache? (e.g. the existing date-instances)
	 * @param request
	 * @param aDate
	 * @param storedDates
	 * @return
	 * @throws SerenaException
	 */
	private boolean targaExists(HttpServletRequest request, String targa,ArrayList<String> storedDates) throws SerenaException	{
		return storedDates.contains(targa);
	}


	
	/**
	 * load stored data into arraylist of dates (as string)
	 * @param request
	 * @param storedDates
	 * @throws SerenaException
	 */
	@SuppressWarnings("unchecked")
	private void loadExistingData(HttpServletRequest request, ArrayList<String> storedDates) throws SerenaException	{
		String[] messages={"",""};
		int  res;
		Element data = null;
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET,Car4uFleetClass);
		Document resultData=ApplicationLibrary.getData(currentElement.getDocument(), request);
		data = ApplicationLibrary.prepareDataForPresentation(resultData);
		res = ConstantsXSerena.getXserenaRequestResult(data, messages,Car4uFleetClass);
		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			return;
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			logger.debug("start");
			List<Element> instances= data.selectNodes(".//"+Car4uFleetClass);
			for (Element anInstance: instances)	{
				storedDates.add(anInstance.elementText(Car4uFleetTarga));
			}
			logger.debug("stop");
		}
				
	}
	
	
}

