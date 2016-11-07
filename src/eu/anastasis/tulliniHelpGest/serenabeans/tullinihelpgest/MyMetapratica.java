package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class MyMetapratica extends Metapratica {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyMetapratica.class);
	
	public static Metapratica getInstance(String ID, HttpServletRequest request) throws SerenaException {
		try	{
			Metapratica metapratica =null;
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(), ConstantsXSerena.ACTION_GET, INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			currentElement = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			currentElement = currentElement.addElement("ID");
			currentElement.setText(ID);
			
			String[] messages={"",""};
			Document resultData=new CronPersistenceHome().get(currentElement.getDocument());
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,INSTANCE_NAME);

			if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				logger.error("Cannot load metapratica "+messages[0]);
				throw new SerenaException(messages[0],"generic_sql_error");
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				logger.error("Cannot load metapratica: not found");
				throw new SerenaException(messages[0],"generic_sql_error");		
			}
			else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				List<Element> listaMetapratuche = data.selectNodes(".//"+INSTANCE_NAME);
				metapratica =new Metapratica();
				if (listaMetapratuche.size()>1){
					logger.error("Cannot load metapratica: more than one instance!");
					throw new SerenaException("Cannot load metapratica: more than one instance!");		
				}
				for (Element aMeta: listaMetapratuche){
					metapratica.setTitolo(aMeta.elementText(SLOT_TITOLO));
					metapratica.setTipo(aMeta.elementText(SLOT_TIPO));
					if (aMeta.elementText(SLOT_DESCRIZIONE)!=null)
						metapratica.setDescrizione(aMeta.elementText(SLOT_DESCRIZIONE));
					if (aMeta.elementText(SLOT_TITOLO_PER_FATTURA)!=null)
						metapratica.setTitolo_per_fattura(aMeta.elementText(SLOT_TITOLO_PER_FATTURA));
					String prezzo =aMeta.elementText(SLOT_PREZZO);
					if (prezzo!=null && !prezzo.isEmpty())	{
						metapratica.setPrezzo(new Float(aMeta.elementText(SLOT_PREZZO)));				
					}
					List<Element> attivita = aMeta.selectNodes(".//"+Metaattivita.INSTANCE_NAME);
					if (attivita.size()>0){
						ArrayList<Metaattivita> attlist= new ArrayList<Metaattivita>();
						for (Element ametaatt:attivita)	{
							Metaattivita metaatt = new Metaattivita();
							metaatt.setId(ametaatt.elementText("ID"));
							metaatt.setNome(ametaatt.elementText(Metaattivita.SLOT_NOME));
							attlist.add(metaatt);
						}
						metapratica.setLista_attivita(attlist);					
					}
					return metapratica;
				}
			}
			return metapratica; // should never reach this
		} catch  (Exception any)	{
			String msg = "Could not load metapratica: "+any.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

}
