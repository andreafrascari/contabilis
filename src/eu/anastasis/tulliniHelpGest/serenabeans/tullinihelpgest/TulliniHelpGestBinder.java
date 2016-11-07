package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import org.dom4j.Document;
import org.dom4j.Element;
import eu.anastasis.serena.beans.Binder;
import eu.anastasis.serena.exception.BindingException;

public class TulliniHelpGestBinder extends Binder
{
	private static final java.lang.String PROJECT_NAME = "tullinihelpgest";
	private static final java.lang.String PACKAGE_PREFIX = "eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest";
	private static final java.lang.String BINDING_PREFIX = PROJECT_NAME;
	private static final java.lang.String PACKAGE_POSTFIX = "";




	public TulliniHelpGestBinder()
	{
		super(PACKAGE_PREFIX);
	}


	public Attivita createAttivita(Element element)
	{
		return (Attivita) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Attivita obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Attivita obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public CalendarioFatturazione createCalendarioFatturazione(Element element)
	{
		return (CalendarioFatturazione) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(CalendarioFatturazione obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(CalendarioFatturazione obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Cliente createCliente(Element element)
	{
		return (Cliente) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Cliente obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Cliente obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public ClienteCandidato createClienteCandidato(Element element)
	{
		return (ClienteCandidato) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(ClienteCandidato obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(ClienteCandidato obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public DatiCRM createDatiCRM(Element element)
	{
		return (DatiCRM) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(DatiCRM obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(DatiCRM obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public DatiContabili createDatiContabili(Element element)
	{
		return (DatiContabili) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(DatiContabili obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(DatiContabili obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public DatiFatturazione createDatiFatturazione(Element element)
	{
		return (DatiFatturazione) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(DatiFatturazione obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(DatiFatturazione obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public DescrizioneDocumento createDescrizioneDocumento(Element element)
	{
		return (DescrizioneDocumento) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(DescrizioneDocumento obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(DescrizioneDocumento obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Documento createDocumento(Element element)
	{
		return (Documento) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Documento obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Documento obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public DocumentoCliente createDocumentoCliente(Element element)
	{
		return (DocumentoCliente) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(DocumentoCliente obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(DocumentoCliente obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Fattura createFattura(Element element)
	{
		return (Fattura) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Fattura obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Fattura obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public HDChiamataTelefonica createHDChiamataTelefonica(Element element)
	{
		return (HDChiamataTelefonica) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(HDChiamataTelefonica obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(HDChiamataTelefonica obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public HDGruppoAssistenza createHDGruppoAssistenza(Element element)
	{
		return (HDGruppoAssistenza) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(HDGruppoAssistenza obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(HDGruppoAssistenza obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public HDRispostaStandard createHDRispostaStandard(Element element)
	{
		return (HDRispostaStandard) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(HDRispostaStandard obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(HDRispostaStandard obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public HDThreadStep createHDThreadStep(Element element)
	{
		return (HDThreadStep) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(HDThreadStep obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(HDThreadStep obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public HDTicket createHDTicket(Element element)
	{
		return (HDTicket) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(HDTicket obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(HDTicket obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public IndicizzazioneListino createIndicizzazioneListino(Element element)
	{
		return (IndicizzazioneListino) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(IndicizzazioneListino obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(IndicizzazioneListino obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public ItemListino createItemListino(Element element)
	{
		return (ItemListino) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(ItemListino obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(ItemListino obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public LavoroSuAttivita createLavoroSuAttivita(Element element)
	{
		return (LavoroSuAttivita) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(LavoroSuAttivita obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(LavoroSuAttivita obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Metaattivita createMetaattivita(Element element)
	{
		return (Metaattivita) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Metaattivita obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Metaattivita obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Metapratica createMetapratica(Element element)
	{
		return (Metapratica) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Metapratica obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Metapratica obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Metasollecito createMetasollecito(Element element)
	{
		return (Metasollecito) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Metasollecito obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Metasollecito obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public NotificaScadenza createNotificaScadenza(Element element)
	{
		return (NotificaScadenza) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(NotificaScadenza obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(NotificaScadenza obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Operatore createOperatore(Element element)
	{
		return (Operatore) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Operatore obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Operatore obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Pratica createPratica(Element element)
	{
		return (Pratica) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Pratica obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Pratica obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public ProForma createProForma(Element element)
	{
		return (ProForma) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(ProForma obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(ProForma obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public ProfiloDinamicoClienti createProfiloDinamicoClienti(Element element)
	{
		return (ProfiloDinamicoClienti) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(ProfiloDinamicoClienti obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(ProfiloDinamicoClienti obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public RegimePrevidenziale createRegimePrevidenziale(Element element)
	{
		return (RegimePrevidenziale) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(RegimePrevidenziale obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(RegimePrevidenziale obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public RevisioneDocumento createRevisioneDocumento(Element element)
	{
		return (RevisioneDocumento) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(RevisioneDocumento obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(RevisioneDocumento obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Scadenza createScadenza(Element element)
	{
		return (Scadenza) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Scadenza obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Scadenza obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public Sms createSms(Element element)
	{
		return (Sms) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(Sms obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(Sms obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public SollecitoPagamento createSollecitoPagamento(Element element)
	{
		return (SollecitoPagamento) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(SollecitoPagamento obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(SollecitoPagamento obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public SpesaAnticipata createSpesaAnticipata(Element element)
	{
		return (SpesaAnticipata) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(SpesaAnticipata obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(SpesaAnticipata obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public StoriaDocumento createStoriaDocumento(Element element)
	{
		return (StoriaDocumento) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(StoriaDocumento obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(StoriaDocumento obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public StoricoInvii createStoricoInvii(Element element)
	{
		return (StoricoInvii) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(StoricoInvii obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(StoricoInvii obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public StoricoModifiche createStoricoModifiche(Element element)
	{
		return (StoricoModifiche) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(StoricoModifiche obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(StoricoModifiche obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

	public VoceFattura createVoceFattura(Element element)
	{
		return (VoceFattura) createObject(BINDING_PREFIX, PACKAGE_POSTFIX, element);
	}

	public Document createDocument(VoceFattura obj)
	throws BindingException
	{
		return createDocument(obj, BINDING_PREFIX);
	}

	public Element createElement(VoceFattura obj)
	throws BindingException
	{
		return createElement(obj, BINDING_PREFIX);
	}

}