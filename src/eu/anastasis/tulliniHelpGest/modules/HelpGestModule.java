package eu.anastasis.tulliniHelpGest.modules;

import eu.anastasis.serena.application.core.modules.DefaultModule;
import eu.anastasis.tulliniHelpGest.utils.ClientChartGraphicsMethod;
import eu.anastasis.tulliniHelpGest.utils.ClientChartReportMethod;
import eu.anastasis.tulliniHelpGest.utils.ClienteFromSysUserFunction;
import eu.anastasis.tulliniHelpGest.utils.Export2BPFunction;
import eu.anastasis.tulliniHelpGest.utils.NextFatturaFunction;
import eu.anastasis.tulliniHelpGest.utils.NextProFormaFunction;
import eu.anastasis.tulliniHelpGest.utils.OperatoreCorrenteFunction;


public class HelpGestModule extends DefaultModule {
	
	public static final String DEFAULT_MODULE_NAME= "helpgest";
	
	@Override
	public String getDefaultName()
	{
		// TODO Auto-generated method stub
		return DEFAULT_MODULE_NAME;
	}
	
	@Override
	protected void setUpMethods()
	{
		addMethod(new MetapraticaAjaxMethod(this,getDefaultParameters()));
		addMethod(new NextNumberAjaxMethod(this,getDefaultParameters()));
		addMethod(new Listino2PraticheMethod(this,getDefaultParameters()));
		addMethod(new GeneraProformaMethod(this,getDefaultParameters()));
		addMethod(new ActivityCacheAjaxMethod(this,getDefaultParameters()));
		addMethod(new ResetOperatorsCacheMethod(this,getDefaultParameters()));
		addMethod(new ResetIndicizzazioneCacheMethod(this,getDefaultParameters()));
		addMethod(new Proforma2FatturaMethod(this,getDefaultParameters()));
		addMethod(new RefreshInvoicingCalendarMethod(this,getDefaultParameters()));
		addMethod(new BirtReport2DocumentoMethod(this,getDefaultParameters()));		
		addMethod(new BirtReportProforma2DocumentoMethod(this,getDefaultParameters()));
		addMethod(new Listino2PreventivoMethod(this,getDefaultParameters()));
		addMethod(new Proforma2SollecitoMethod(this,getDefaultParameters()));
		addMethod(new SendSollecitoMethod(this,getDefaultParameters()));
		addMethod(new UploadAndSendProfiledMethod(this,getDefaultParameters()));
		addMethod(new DeleteVoceFatturaMethod(this,getDefaultParameters()));
		addMethod(new ResetInvoicingCalendarMethod4Client(this,getDefaultParameters()));
		addMethod(new AdjustInvoiceNumerationAjaxMethod(this,getDefaultParameters()));
		addMethod(new CacheCleanAjaxMethod(this,getDefaultParameters()));
		addMethod(new SendAndRegisterSmsMethod(this,getDefaultParameters()));
		addMethod(new SmsNotificationMethod(this,getDefaultParameters()));
		addMethod(new ActivityNotificationAjaxMethod(this,getDefaultParameters()));
		addMethod(new DeleteAccountClienteAjaxMethod(this,getDefaultParameters()));
		addMethod(new ProcessMetascadenzaMethod(this,getDefaultParameters()));
		addMethod(new RegisterClientProfileMethod(this,getDefaultParameters()));
		addMethod(new ExecClientProfileMethod(this,getDefaultParameters()));
		addMethod(new Pratica2ListinoMethod(this,getDefaultParameters()));
		addMethod(new MigraPratichePendentiMethod(this,getDefaultParameters()));
		addMethod(new InsertPraticaMethod(this,getDefaultParameters()));
		addMethod(new Merge2SinglePdfMethod(this,getDefaultParameters()));
		
		addMethod(new ClientChartReportMethod(this,getDefaultParameters()));
		addMethod(new ClientChartGraphicsMethod(this,getDefaultParameters()));
		
		addFunctionToPostparse(new NextFatturaFunction());
		addFunctionToPostparse(new NextProFormaFunction());
		addFunctionToPostparse(new Export2BPFunction());
		addFunctionToPostparse(new ClienteFromSysUserFunction());
		addFunctionToPreparse(new OperatoreCorrenteFunction());
	}
	
}
