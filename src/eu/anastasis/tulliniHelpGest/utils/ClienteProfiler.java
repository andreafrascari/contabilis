package eu.anastasis.tulliniHelpGest.utils;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.common.IoLibrary;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.exception.SerenaException;

/**
 * Singleton per la profilazione dei clienti in base all'xml client_profiles.xml
 * es: 	<Rule>	
			<condizione_profilo>tipo cliente: AP</condizione_profilo>
			<condition>
				<Cliente>	
					<tipo_cliente>AP</tipo_cliente>
				</Cliente>
			</condition>
		</Rule>
 * @author afrascari
 *
 */

public class ClienteProfiler {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ClienteProfiler.class);
	
	private static final String rulesFolder=ConstantsBaseLibrary.realpath + "/" + ConstantsBaseLibrary.TEMPLATE_DIR + "/rules/";	
	private static final String rulesFile="client_profiles.xml";
	
	private static ClienteProfiler theInstance=null;
	private Document rules=null;
	
	public static ClienteProfiler getInstance() throws SerenaException
	{
		if(theInstance==null)
		{
			theInstance=new ClienteProfiler();
		}
		return theInstance;
	}
	
	private ClienteProfiler() throws SerenaException {
		loadRules();
	}
	
	public void clearCache()
	{
		this.rules=null;
	}
	
	private void loadRules() throws SerenaException
	{
		
		try 
		{
			rules = DocumentHelper.parseText(IoLibrary.readTextFile(rulesFolder+"/"+rulesFile));
		} catch (DocumentException e) {
			throw new SerenaException("Unable to parse rules file",e);
		} catch (IOException e) {
			throw new SerenaException("Unable to read rules file",e);
		}
	}
	

	
	public Element  getConditionFor(String ruleId) throws SerenaException
	{

		Element rule = (Element)rules.selectSingleNode(".//Rule[condizione_profilo='"+ruleId+"']");
		if (rule==null)	{
			String msg = "Regola non trovata per costruzione profilo clienti +"+ruleId;
			logger.error(msg);
			throw new SerenaException(msg);
		}
		return rule.element("condition");
	}
					
	
	 
	
}
