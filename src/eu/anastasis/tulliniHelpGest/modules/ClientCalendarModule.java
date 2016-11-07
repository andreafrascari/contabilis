/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.modules;

import org.apache.log4j.Logger;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.it.tullinidms.modules.Cliente;
import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.common.SerenaDate;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.constants.ConstantsBaseLibrary;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.templates.ActiveTemplate;
import eu.anastasis.serena.presentation.templates.TemplateException;

/**
 * Ridefinito per caricare oggetti notifiche per il solo cliente in sessione
 * Non legge più i paramteri da db tanto e tutto statico
 */
public class ClientCalendarModule extends DefaultModule 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClientCalendarModule.class);
	
	private static final String EventClass ="NotificaScadenza"; 
	private static final String EventDateAttribute ="data";
	private static final String EventModule = "object";
	private static final String EventClientAttribute = "ID_Cliente_avvisi_scadenze";
	private static final String LinkTpl = "?q=" + EventModule +"/list&amp;p=" + EventClass + "/_a_" + EventDateAttribute +"/_v_@DATA@" + "/_a_" + EventClientAttribute + "/_v_@CLIENTE@";
	private static final String LinkAll = "?q=" + EventModule +"/list&amp;p=" + EventClass + "/_a_" +  EventClientAttribute + "/_v_@CLIENTE@";
	
	private static final String MODULE_NAME = "clientCalendar";

    private static final String FAKE_DAY="15";
    private static final  String CHANGE_MONTH_LINK = "?q=clientCalendar/month&amp;c=@MONTH@/@YEAR@";
   
    private Calendar cal = null;
	private boolean hilightToday=true;

	
	@Override
	protected void setUpMethods()
	{
		// Non ci sono moduli
	}

	@Override
	public String doMethod(String method, HttpServletRequest request, HttpServletResponse response) throws SerenaException
	{
		try
		{
			initDate(request);
			return this.getCalendar(request);
		} catch (TemplateException e)	
		{
			e.printStackTrace();
			return null;
		}
	}
	
 
	
	
	private void setChangeMonthLinks(HttpServletRequest request,ActiveTemplate template,int month,int year)	{
	// next:
		int nextMonth = month+2; // java counts from 0!
		int nextMonthsYear= year;
		if (nextMonth==13)	{
			nextMonth=1;
			nextMonthsYear++;
		}
		// previous
		int previousMonth = month;
		int previousMonthsYear= year;
		if (previousMonth==0)	{
			previousMonth=12;
			previousMonthsYear--;
		}
		template.replaceTag("LINK_NEXT_MONTH",CHANGE_MONTH_LINK.replace("@MONTH@",new Integer(nextMonth).toString()).replace("@YEAR@",new Integer(nextMonthsYear).toString()));
		template.replaceTag("LINK_PREVIOUS_MONTH",CHANGE_MONTH_LINK.replace("@MONTH@",new Integer(previousMonth).toString()).replace("@YEAR@",new Integer(previousMonthsYear).toString()));
	}

	/**
	 * month and year can be passed through query string. Otherwise set current date 
	 * @param request
	 */
	private void initDate(HttpServletRequest request)	{
		cal = Calendar.getInstance(Locale.ITALIAN);
		String pPar = request.getParameter("c");
		if (pPar!=null)	{
			String[] params = pPar.split(ConstantsBaseLibrary.SERENA_QS_PARAM_SEPARATOR);
			SimpleDateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				hilightToday = false; 
				Date myDate = myDateFormat.parse(FAKE_DAY+"/"+params[0]+"/"+params[1]);
				cal.setTime(myDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cal.setTime(new Date());
			}	
		}else	{
			// set time to today
			cal.setTime(new Date());
		}

	}
	
	private String  getCalendar(HttpServletRequest request) throws SerenaException {
		final int NROWS = 6;
	    final int NDAYS = 7;
	    
		ActiveTemplate template= ActiveTemplate.load("calendar", "clientCalendar");
		String rowTpl = template.getBlockContent("ROW");
		String emptyDayTpl = template.getBlockContent("EMPTY_DAY");
		String dayTpl = template.getBlockContent("DAY");
		String todayTpl = template.getBlockContent("TODAY");
		String dayNoEvTpl = template.getBlockContent("DAY_NO_EVENT");
		String todayNoEvTpl = template.getBlockContent("TODAY_NO_EVENT");
	    
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.ITALIAN);
		//String[] weekdays = dfs.getWeekdays();
		String[] months = dfs.getMonths();				
		String[][]table = new String[NROWS][NDAYS];
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year= cal.get(Calendar.YEAR);
		// fetch events for current period
		ArrayList<String> monthEvents = new ArrayList<String>();
		String clientID = new Cliente().getClientIDFromSessionAccount(request);
		if (clientID==null)
			return "";
		fetchEvents(clientID,month+1,year,monthEvents,request);
		// fill int days
		int y = 0;
		template.replaceTag("MONTH", months[month]);
		template.replaceTag("YEAR", ""+year);
		this.setChangeMonthLinks(request,template,month,year);
		String strMonth = new Integer(month+1).toString();
		cal.setFirstDayOfWeek(2);	// starts on monday
		if (strMonth.length()==1)
			strMonth = "0"+strMonth;
		for (int i = 1; i <= 31; i++) {
			String dayCell=null;
			String eventTester = ""+i+"/"+strMonth+"/"+year;
			if (i<10)
				eventTester = "0"+eventTester;
			String tplToUse="";
			boolean dayHasEvents = monthEvents.contains(eventTester);
			if (day==i && hilightToday)
				if (dayHasEvents)
					tplToUse = todayTpl;
				else
					tplToUse = todayNoEvTpl;
			else
				if (dayHasEvents)
					tplToUse = dayTpl;
				else
					tplToUse = dayNoEvTpl;
			dayCell = tplToUse.replace("@DAY@", new Integer(i).toString());
			String sDay = new Integer(i).toString();
			String sDayAlt = sDay+"/"+new Integer(month+1).toString();
			String sDayLink = sDay+"-"+new Integer(month+1).toString()+"-"+new Integer(year).toString();
			
			dayCell = dayCell.replace("@DAY@", sDay);
			dayCell = dayCell.replace("@DAY_ALT@", sDayAlt);
			dayCell = dayCell.replace("@DAY_LINK@", LinkTpl.replace("@DATA@", sDayLink));
			dayCell = dayCell.replace("@CLIENTE@", clientID);
			
		    cal.set(Calendar.DAY_OF_MONTH, i);
		    if (month != cal.get(Calendar.MONTH))
		    	break; 
		    int x = cal.get(Calendar.DAY_OF_WEEK); // sunday still is one: 1 has to become 7 -> x= x+6%7
		    x=((x+5) % 7);
		    y = cal.get(Calendar.WEEK_OF_MONTH);
//		    System.out.print("giorno "+i+ " on ["+y+","+x+"]");
		    table[y][x]=dayCell;
		}
		// surplus rows into vector
		Vector<Integer> surplusRows = new Vector<Integer>();
		for (int i = NROWS-1; i > y; i--) {
			surplusRows.add(new Integer(i));
		}
		// scan table to produce html
		String content="";
		boolean stillEmpty=true;
		for (int i = 0; i < NROWS; i++) 
	    	if (!surplusRows.contains(new Integer(i)))	{
	    		String row="";
	    		for (int j = 0; j < NDAYS; j++)	
	    			if (table[i][j]==null || table[i][j].equals(""))
	    				row+=emptyDayTpl;
	    			else	{
	    				row+=table[i][j];
	    				stillEmpty=false;
	    			}
	    		if (!stillEmpty)
	    			content+=rowTpl.replace("@ROW@", row);
	    }
		
		template.replaceTag("CALENDAR", content); 
		template.replaceTag("LINK_ALL", LinkAll);
		String linkToday = CHANGE_MONTH_LINK.replace("@MONTH@", ""+new SerenaDate().getMonth());
		linkToday = linkToday .replace("@YEAR@", ""+new SerenaDate().getYear());
		template.replaceTag("LINK_TODAY", linkToday);
		template.replaceTag("CLIENTE", clientID);
		template.publish(); 
		return template.getContenuto();
	}
	
	/**
	 * Reperisce gli eventi del mese, o meglio i giorni che hanno eventi, e riempie l'array list monthEvents con tali date
	 * @param month
	 * @param year
	 * @param monthEvents
	 * @param request
	 * @return
	 */
	protected boolean fetchEvents(String id_cliente,int month,int year,ArrayList<String>monthEvents,HttpServletRequest request)	{
		try 
		{
			String firstOfMonth = "1/" + month+"/"+year;
			String lastOfMonth = "31/" + month+"/"+year;
	        Element currentElement = ObjectUtils.getXserenaRequestStandardHeader(request.getSession().getId(),ConstantsXSerena.ACTION_GET,EventClass);
	        currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,ConstantsXSerena.VAL_SELECT);
	        currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_SPECIFIED);
	        currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "0");
			Element condition = currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
			Element leaf = condition.addElement(EventDateAttribute);
			leaf.addAttribute(ConstantsXSerena.ATTR_OPERATOR,">=");
			leaf.setText(firstOfMonth);
			leaf = condition.addElement(EventDateAttribute);
			leaf.addAttribute(ConstantsXSerena.ATTR_OPERATOR,"<=");
			leaf.setText(lastOfMonth);
			leaf = condition.addElement(EventClientAttribute);
			leaf.setText(id_cliente);
			currentElement.addElement(EventDateAttribute);			
			Document query = currentElement.getDocument();
			Document xmlResponse = ApplicationLibrary.getData(query, request);
			String[] messages={"",""};
			if (ConstantsXSerena.getXserenaRequestResult(xmlResponse, messages,EventClass)==ConstantsXSerena.XSERENA_RESULT_SUCCESS)	
			{	
				Element data = XSerenaLibrary.selectServiceElement(xmlResponse);
				@SuppressWarnings("unchecked")
				List<Element> events = data.elements(EventClass);
				for (Element anEvent: events) 
					monthEvents.add(anEvent.elementText(EventDateAttribute));
			}
			return true;
		} catch(Exception e)		
		{
			logger.error("Error in fetching month events: ", e);
			return false;
		}
	}
	
	 
	
	@Override
	public String getDefaultName() 
	{
		return MODULE_NAME;
	}
}
