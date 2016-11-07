package eu.anastasis.tulliniHelpGest.modules;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;

import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.it.tullinidms.modules.UploadAndSendMethod;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.tulliniHelpGest.beans.SuperCliente;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProfiloDinamicoClienti;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBinder;

/**
 * Genera le pratiche dell'anno corrente di un cliente a partire dal listino
 * 
 * @author afrascari
 */
public class UploadAndSendProfiledMethod extends UploadAndSendMethod
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UploadAndSendProfiledMethod.class);

	private static final String METHOD_NAME = "uploadandsendprofiled";

	public UploadAndSendProfiledMethod(DefaultModule parentModule, String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	/**
	 * Torna array di clienti filtrati in base al profilo (cats)
	 * 
	 * @param cats
	 * @param request
	 * @return
	 * @throws SerenaException
	 */
	@Override
	protected Cliente[] getDestinatari(String[] cats, HttpServletRequest request) throws SerenaException
	{
		HashMap<String, Cliente> clienteArray = new HashMap<String, Cliente>();
		Cliente[] retClienteArray = null;
		try
		{
			Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("clientiprofilo", ConstantsXSerena.ACTION_GET, SuperCliente.INSTANCE_NAME);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
			currentElement.addElement("ID");
			currentElement.addElement("cellulare");
			currentElement.addElement("email");
			currentElement.addElement("email2");
			currentElement.addElement("email3");
			currentElement.addElement("fax");
			currentElement.addElement("tipo_sollecito");
			currentElement.addElement("cliente");
			Element rootCond = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element currentCond = rootCond.addElement(eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.Cliente.SLOT_CESSATA_ASSISTENZA_IL);
			currentCond.setText(ConstantsXSerena.VAL_NULL);
			currentCond = rootCond.addElement(ConstantsXSerena.TAG_OR);
			for (String aCond : cats)
			{
				ProfiloDinamicoClienti pdc = new TulliniHelpGestBeanDataManager().getProfiloDinamicoClienti(request, 2, aCond);
				Element aCondElem = DocumentHelper.parseText(pdc.getQuery()).getRootElement();
				List<Element> siblings = aCondElem.elements();
				for (Element sib : siblings)
				{
					currentCond.add((Element) sib.clone());
				}
			}
			/* ACCROCCHIO DEPLOREVOLE - PUNTO DEBOLE */
			// have to get rid of excess deletion/activation_flag (out of
			// persisntece bug)
			List<Element> dF = currentElement.selectNodes(".//deletion_flag");
			for (Element aDF : dF)
			{
				aDF.getParent().remove(aDF);
			}
			dF = currentElement.selectNodes(".//activation_flag");
			for (Element aDF : dF)
			{
				aDF.getParent().remove(aDF);
			}
			dF = currentElement.selectNodes(".//and");
			for (Element aDF : dF)
			{
				if (aDF.elements().isEmpty())
				{
					aDF.getParent().remove(aDF);
				}
			}
			dF = currentElement.selectNodes(".//or");
			for (Element aDF : dF)
			{
				if (aDF.elements().isEmpty())
				{
					aDF.getParent().remove(aDF);
				}
			}
			String[] messages =
			{ "", "" };
			Document resultData = new CronPersistenceHome().get(currentElement.getDocument());
			Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages, SuperCliente.INSTANCE_NAME);
			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR)
			{
				String msg = "Cannot load client from profile " + messages[0];
				logger.error(msg);
				throw new SerenaException(msg);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY)
			{
				String msg = "Nessun cliente identificato dal profilo richiesto";
				logger.warn(msg);
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS)
			{
				// processing all clients matching condition(s) in List
				List<Element> iClienti = data.selectNodes(".//" + SuperCliente.INSTANCE_NAME);
				int i = 0;
				for (Element unCliente : iClienti)
				{
					Cliente ilCliente = new Cliente();
					ilCliente.setID(unCliente.elementText("ID"));
					if (!clienteArray.containsKey(ilCliente.getID()))
					{
						ilCliente.setNome(unCliente.elementText("cliente"));
						ilCliente.setNickname(unCliente.elementText("nickname"));
						ilCliente.setEmail(unCliente.elementText("email"));
						ilCliente.setCellulare(unCliente.elementText("cellulare"));
						ilCliente.setTipo_sollecito(unCliente.elementText("tipo_sollecito"));
						clienteArray.put(ilCliente.getID(), ilCliente);
					}
				}
			}
		} catch (Exception any)
		{
			String msg = "Could not fetch clienti for selected categories ";
			any.printStackTrace();
			logger.error(msg + any.getMessage());
			throw new SerenaException(msg);
		}
		retClienteArray = new Cliente[clienteArray.size()];
		int i = 0;
		for (Cliente c : clienteArray.values())
		{
			retClienteArray[i++] = c;
		}
		return retClienteArray;
	}

}
