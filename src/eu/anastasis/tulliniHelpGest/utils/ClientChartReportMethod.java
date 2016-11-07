package eu.anastasis.tulliniHelpGest.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.serena.application.modules.BirtReport.GiveMethod;
import eu.anastasis.serena.application.modules.BirtReport.engine.ReportEngine;
import eu.anastasis.serena.exception.SerenaException;

// http://localhost:8080/TulliniHelpGest/ModuleIndex?q=helpgest/clientchartreport/CLS=Statistica/data_source=chart-clienti/FILE=ChartClienti/DOC=ChartClienti/TYPE=pdf
// http://localhost:8080/TulliniHelpGest/ModuleIndex?q=helpgest/clientchartreport/CLS=Statistica/data_source=chart-clienti/FILE=ChartClienti/DOC=ChartClienti/TYPE=xls


public class ClientChartReportMethod extends GiveMethod
{


		public static final String METHOD_NAME = "clientchartreport";

		public ClientChartReportMethod(DefaultModule  parentModule, String[] defaultParameters)
		{
			super(parentModule, defaultParameters);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected String getName()
		{http://localhost:8080/TulliniHelpGest/ModuleIndex?q=helpgest/clientchartgraphics/CLS=Statistica/data_source=chart-clienti-proforma/FILE=ChartClienti/TYPE=pdf/TPL=ClientChartGraphics
			return METHOD_NAME;
		}

		
		@Override
		protected Document retrieveData(HttpServletRequest request,
				ReportEngine engine, File designFile) throws SerenaException {

			// prelevo l'xml ritornato dalla query
			Document ret = super.retrieveData(request, engine, designFile);

			List<Element> instances = ret.selectNodes(".//Statistica");
			
			/* 
			 * p1: anno
			 * p2: cliente
			 * p3: studio/contabilis
			 * 
			 * p4: costo 
			 * p5: fatturato 
			 * p6: % fatturato su totale 
			 * p7: reddititivta totale 
			 * 
			 * .... il metodo aggiunge
			 * 
			 * p7: redditivita cliente
			 * p8: percentuale redditivita cliente su totale
			 * 
			 * */
			
			
			for (Element aStat: instances)	{
				Element incomeEl = aStat.element("p5");
				Element costEl = aStat.element("p4");
				if (incomeEl==null || incomeEl.getText().isEmpty())	{
					aStat.getParent().remove(aStat);
					continue;
				} else {
					if (costEl==null || costEl.getText().isEmpty())	{
						costEl.setText("0");
					}
					Float income = Float.parseFloat(incomeEl.getText());
					Float cost = Float.parseFloat(costEl.getText());
					float redditivitaTotale = 0;
					if (entryIsSignificant(income,cost))	{
						Float gain = income-cost;
						int intGain = gain.intValue(); 
						Element redTotEl = aStat.element("p7");
						redditivitaTotale = Float.parseFloat(redTotEl.getText());
						float thisReddPerc = intGain / redditivitaTotale * 100;
						DecimalFormat df = new DecimalFormat("#.##");  
						String nome = aStat.elementText("p2");
						if (nome.length()>32){
							aStat.element("p2").setText(nome.substring(0,32)+"...");
						}
						Element p7 = aStat.element("p7");
						Element p8 = aStat.element("p8");
						if (p7==null)	{
							p7 = aStat.addElement("p7");
						}
						if (p8==null)	{
							p8 = aStat.addElement("p8");
						}
						p7.setText(new Integer(intGain).toString()); 
						p8.setText(df.format(thisReddPerc));
						
					} else {
						aStat.getParent().remove(aStat);
					}
					System.out.println("Gain for "+aStat.elementText("p2") + " is "+aStat.elementText("p7")+ " that is "+aStat.elementText("p8")+" % on " +redditivitaTotale+" total gain");
				}
			}

		
			return ret;
		}

		protected boolean entryIsSignificant(Float income, Float cost)
		{
			return true;
			/*
			if (income < 1000 && income > cost)	{
				return false; // cliente non significativo
			}
			// altri cut-off?
			return true;
			*/
		}
		 
	}
