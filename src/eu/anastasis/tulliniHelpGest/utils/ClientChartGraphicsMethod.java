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

//proforma:
//http://localhost:8080/TulliniHelpGest/ModuleIndex?q=helpgest/clientchartgraphics/CLS=Statistica/data_source=chart-clienti-proforma/FILE=ChartClienti/TYPE=pdf/TPL=ClientChartGraphics


public class ClientChartGraphicsMethod extends ClientChartReportMethod
{


		public static final String METHOD_NAME = "clientchartgraphics";

		public ClientChartGraphicsMethod(DefaultModule  parentModule, String[] defaultParameters)
		{
			super(parentModule, defaultParameters);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected String getName()
		{
			return METHOD_NAME;
		}

		 

		protected boolean entryIsSignificant(Float income, Float cost)
		{
			return true;
		}
		 
	}
