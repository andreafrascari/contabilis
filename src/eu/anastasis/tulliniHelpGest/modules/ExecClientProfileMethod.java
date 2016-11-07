package eu.anastasis.tulliniHelpGest.modules;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.IndexQueryException;
import eu.anastasis.serena.application.modules.object.ListMethod;
import eu.anastasis.serena.application.modules.object.ObjectModule;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.application.modules.object.components.ObjectListComponentCreator;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.presentation.components.ComponentCreator;
import eu.anastasis.serena.presentation.components.ComponentLibrary;
import eu.anastasis.serena.presentation.templates.DefaultTemplate;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.ProfiloDinamicoClienti;
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.TulliniHelpGestBeanDataManager;

 
public class ExecClientProfileMethod extends ListMethod 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExecClientProfileMethod.class);


	private static final String METHOD_NAME = "lanciaprofiloclienti";
	private static final String PROFILE_PARAM = "profile";

	public ExecClientProfileMethod(DefaultModule parentModule,String[] defaultParameters)
	{
		super(parentModule, defaultParameters);
	}

	@Override
	protected String getName()
	{
		return METHOD_NAME;
	}

	@Override
	public ComponentCreator createComponentCreator(String doParam) {
		return new ObjectListComponentCreator(ObjectModule.MODULE_NAME, getParentModule().getName(), ListMethod.METHOD_NAME, getCurrentClass(), doParam);
	}
	
	@Override
	protected Document buildQueryGetFromHttpGet(HttpServletRequest request, DefaultTemplate template) throws IndexQueryException
	{
		final String session = request.getSession().getId();
		final String theClass = request.getParameter(ConstantsBaseLibrary.QUERY_STRING_PARAMETERS_PARAM);
		String profile = request.getParameter(PROFILE_PARAM);
		if (profile==null)	{
			String message = "Parametro profile non presente";
			logger.error(message);
			throw new IndexQueryException(message);
		}
		try	{
			setTargetAttributes(request);
			setUpCurrentClass(request);
			setCurrentPage(1); 
			// fetch profile 
			TulliniHelpGestBeanDataManager tdm = new TulliniHelpGestBeanDataManager();
			ProfiloDinamicoClienti p = tdm.getProfiloDinamicoClienti(request, 0, profile);
			this.setCurrentClass(theClass);
			final Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(session, ConstantsXSerena.ACTION_GET, theClass);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "2");
			// appending profile condition
			Element condition = (Element) DocumentHelper.parseText(p.getQuery()).getRootElement().clone();
			currentElement.add(condition);
			Document theQuery = currentElement.getDocument();
			// gestione parametro pubblishing mode
			processPublishParameter(request, theQuery);
			request.getSession().setAttribute(getCurrentQuerySessionIdentifier(request), theQuery);
			final String order = getParam(request, ConstantsXSerena.ATTR_ORDER_BY, null);
			if (order != null)
			{
				((Element) theQuery.selectSingleNode("/serena/service/" + getCurrentClass())).addAttribute(ConstantsXSerena.ATTR_ORDER_BY, order);
			}
			/*
			final int pageDimension = retrievePageDimension(request, getInterfaceRoot());
			final Element classElem = theQuery.getRootElement().element(ConstantsXSerena.TAG_COMMAND).element(getCurrentClass());
			classElem.addAttribute(ConstantsXSerena.ATTR_FETCH_FROM, ((getCurrentPage() - 1) * pageDimension) + "");
			classElem.addAttribute(ConstantsXSerena.ATTR_FETCH_TO, (getCurrentPage() * pageDimension) + "");
			*/
			return currentElement.getDocument();
		} catch (Exception e) {
			String message = "Errore in reperimento profilo e esecuzione query del profilo";
			logger.error(message,e);
			throw new IndexQueryException(message);
		}
	}
	 

	@SuppressWarnings("unchecked")
	@Override
	protected String setTargetAttributes(HttpServletRequest request) throws IndexQueryException
	{
		String parList = "";
		final Collection<Element> elementItems = getInterfaceRoot().element(ConstantsEntityBean.ENT_ATTRIBUTI).elements(ConstantsEntityBean.ENT_ITEM);
		for (final Element anItem : elementItems)
		{
			if (anItem.element(ComponentLibrary.LIST_PARAM) != null)
			{
				parList += anItem.element(ConstantsEntityBean.ENT_NOME).getText() + ",";
			}
		}
		try
		{
			parList = parList.substring(0, parList.length() - 1);
			// aggiungo sempre ID
			if (parList.indexOf(ConstantsEntityBean.ID) < 0)
			{
				parList += "," + ConstantsEntityBean.ID;
			}
			// storing current target list (necessary to reconstruct header):
			request.getSession().setAttribute(SESSION_CURRENT_TARGET + getParentModule().getName() + getCurrentClass(), parList);
		} catch (final IndexOutOfBoundsException e)
		{
			final String message = "Impossibile leggere i parametri. Controllare che l'interface abbia almeno un attributo list";
			logger.error(message);
			throw new IndexQueryException(message);
		}
		return parList;
	}

	protected String getCurrentQuerySessionIdentifier(HttpServletRequest request)
	{
		final String candidateP = request.getParameter(ConstantsBaseLibrary.QUERY_STRING_PARAMETERS_PARAM);
		if (candidateP != null)
		{
			return SESSION_CURRENT_LIST + getParentModule().getName() + candidateP;
		} else
		{
			return SESSION_CURRENT_LIST + getParentModule().getName() + getCurrentClass();
		}
	}

}

