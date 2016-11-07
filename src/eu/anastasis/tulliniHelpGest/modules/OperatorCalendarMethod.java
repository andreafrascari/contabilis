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

import eu.anastasis.serena.application.core.modules.DefaultMethod;
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
import eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest.MyOperatore;

/**
 * Nel caso si dovesse trasformare in metodo .... e' quasi fatto!!!
 */
public class OperatorCalendarMethod extends DefaultMethod
{
	public OperatorCalendarMethod(DefaultModule parentModule,
			String[] defaultParameters) {
		super(parentModule, defaultParameters);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OperatorCalendarMethod.class);

	protected static final String EventClass ="NotificaScadenza"; 
	protected static final String EventDateAttribute ="data";
	protected static final String EventModule = "object";
	protected static final String EventOperatorAttribute = "ID_Operatore_notifiche_scadenze";
	protected static final String LinkTpl = "?q=" + EventModule +"/list&amp;p=" + EventClass + "/_a_" + EventDateAttribute +"/_v_@DATA@" + "/_a_" + EventOperatorAttribute + "/_v_@OPERATORE@";
	protected static final String LinkAll = "?q=" + EventModule +"/list&amp;p=" + EventClass + "/_a_" +  EventOperatorAttribute + "/_v_@OPERATORE@";	
	
	private static final String MODULE_NAME = "operatorCalendar";

	protected static final String FAKE_DAY="15";
	protected static final  String CHANGE_MONTH_LINK = "?q=@MODULE@/month&amp;c=@MONTH@/@YEAR@";
   
	protected Calendar cal = null;
	protected boolean hilightToday=true;

	private static final String METHOD_NAME = "operatorCalendar";


	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return METHOD_NAME;
	}

	@Override
	public String doMethod(HttpServletRequest request,
			HttpServletResponse response) throws SerenaException {
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
	
 	protected void setChangeMonthLinks(HttpServletRequest request,ActiveTemplate template,int month,int year)	{
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
		String cml = getChangeMonthLink();
		template.replaceTag("LINK_NEXT_MONTH",cml.replace("@MONTH@",new Integer(nextMonth).toString()).replace("@YEAR@",new Integer(nextMonthsYear).toString()));
		template.replaceTag("LINK_PREVIOUS_MONTH",cml.replace("@MONTH@",new Integer(previousMonth).toString()).replace("@YEAR@",new Integer(previousMonthsYear).toString()));
	}

	protected String getChangeMonthLink()
	{
		return CHANGE_MONTH_LINK.replace("@MODULE@", getName());
	}

	/**
	 * month and year can be passed through query string. Otherwise set current date 
	 * @param request
	 */
	protected void initDate(HttpServletRequest request)	{
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
	
	protected String  getCalendar(HttpServletRequest request) throws SerenaException {
		final int NROWS = 6;
	    final int NDAYS = 7;
	    
	    
		ActiveTemplate template= ActiveTemplate.load("calendar", getCalendarName());
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
		String idOperatore = "";
		try	{
			idOperatore = getIdOperatore(request);
		} catch (Exception e) {
			logger.error("Impossibile reperire operatore in sessione: rendering calendario fallito");
			return "";
		}
		fetchEvents(idOperatore,month+1,year,monthEvents,request);
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
			dayCell = dayCell.replace("@OPERATORE@", idOperatore);

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
		String linkToday = CHANGE_MONTH_LINK.replace("@MONTH@", ""+new SerenaDate().getMonth());
		linkToday = linkToday .replace("@YEAR@", ""+new SerenaDate().getYear());
		template.replaceTag("LINK_TODAY", linkToday);
		template.replaceTag("LINK_ALL", LinkAll);
		template.replaceTag("OPERATORE", idOperatore);
		template.publish(); 
		return template.getContenuto();
	}
	
	protected String getIdOperatore(HttpServletRequest request) throws SerenaException
	{
		MyOperatore currentOp = new MyOperatore().getInstanceSessionAccount(request);
		return currentOp.getId();
	}

	protected String getCalendarName() 
	{
		return "operatorCalendar";
	}

	/**
	 * Reperisce gli eventi del mese, o meglio i giorni che hanno eventi, e riempie l'array list monthEvents con tali date
	 * @param month
	 * @param year
	 * @param monthEvents
	 * @param request
	 * @return
	 */
	protected boolean fetchEvents(String id_operatore,int month,int year,ArrayList<String>monthEvents,HttpServletRequest request)	{
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
			leaf = condition.addElement(EventOperatorAttribute);
			leaf.setText(id_operatore);
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
	
}
