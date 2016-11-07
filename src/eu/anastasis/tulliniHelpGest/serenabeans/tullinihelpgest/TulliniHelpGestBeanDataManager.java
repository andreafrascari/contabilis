package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Element;
import eu.anastasis.serena.beans.BeanCreator;
import eu.anastasis.serena.beans.BeanDataManager;
import eu.anastasis.serena.beans.ElementCreator;
import eu.anastasis.serena.beans.SerenaMarshallable;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.BindingException;

public class TulliniHelpGestBeanDataManager extends BeanDataManager
{


	private TulliniHelpGestBinder binder;


	public TulliniHelpGestBeanDataManager()
	{
		super();
		this.binder = new TulliniHelpGestBinder();
	}


	@SuppressWarnings("unchecked")
	public Attivita getAttivita(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Attivita) getObject(request, "Attivita", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Attivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Attivita newObject = binder.createAttivita(element);
					((List<Attivita>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Attivita> getAttivitaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Attivita>) getObjects(request, "Attivita", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Attivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Attivita newObject = binder.createAttivita(element);
					((List<Attivita>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertAttivita(HttpServletRequest request, Attivita object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Attivita) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public CalendarioFatturazione getCalendarioFatturazione(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (CalendarioFatturazione) getObject(request, "CalendarioFatturazione", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<CalendarioFatturazione>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					CalendarioFatturazione newObject = binder.createCalendarioFatturazione(element);
					((List<CalendarioFatturazione>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<CalendarioFatturazione> getCalendarioFatturazioneList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<CalendarioFatturazione>) getObjects(request, "CalendarioFatturazione", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<CalendarioFatturazione>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					CalendarioFatturazione newObject = binder.createCalendarioFatturazione(element);
					((List<CalendarioFatturazione>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertCalendarioFatturazione(HttpServletRequest request, CalendarioFatturazione object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((CalendarioFatturazione) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Cliente getCliente(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Cliente) getObject(request, "Cliente", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Cliente>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Cliente newObject = binder.createCliente(element);
					((List<Cliente>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getClienteList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Cliente>) getObjects(request, "Cliente", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Cliente>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Cliente newObject = binder.createCliente(element);
					((List<Cliente>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertCliente(HttpServletRequest request, Cliente object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Cliente) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public ClienteCandidato getClienteCandidato(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (ClienteCandidato) getObject(request, "ClienteCandidato", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ClienteCandidato>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ClienteCandidato newObject = binder.createClienteCandidato(element);
					((List<ClienteCandidato>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<ClienteCandidato> getClienteCandidatoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<ClienteCandidato>) getObjects(request, "ClienteCandidato", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ClienteCandidato>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ClienteCandidato newObject = binder.createClienteCandidato(element);
					((List<ClienteCandidato>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertClienteCandidato(HttpServletRequest request, ClienteCandidato object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((ClienteCandidato) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public DatiCRM getDatiCRM(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (DatiCRM) getObject(request, "DatiCRM", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiCRM>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiCRM newObject = binder.createDatiCRM(element);
					((List<DatiCRM>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<DatiCRM> getDatiCRMList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<DatiCRM>) getObjects(request, "DatiCRM", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiCRM>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiCRM newObject = binder.createDatiCRM(element);
					((List<DatiCRM>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDatiCRM(HttpServletRequest request, DatiCRM object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((DatiCRM) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public DatiContabili getDatiContabili(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (DatiContabili) getObject(request, "DatiContabili", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiContabili>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiContabili newObject = binder.createDatiContabili(element);
					((List<DatiContabili>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<DatiContabili> getDatiContabiliList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<DatiContabili>) getObjects(request, "DatiContabili", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiContabili>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiContabili newObject = binder.createDatiContabili(element);
					((List<DatiContabili>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDatiContabili(HttpServletRequest request, DatiContabili object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((DatiContabili) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public DatiFatturazione getDatiFatturazione(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (DatiFatturazione) getObject(request, "DatiFatturazione", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiFatturazione>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiFatturazione newObject = binder.createDatiFatturazione(element);
					((List<DatiFatturazione>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<DatiFatturazione> getDatiFatturazioneList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<DatiFatturazione>) getObjects(request, "DatiFatturazione", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DatiFatturazione>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DatiFatturazione newObject = binder.createDatiFatturazione(element);
					((List<DatiFatturazione>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDatiFatturazione(HttpServletRequest request, DatiFatturazione object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((DatiFatturazione) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public DescrizioneDocumento getDescrizioneDocumento(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (DescrizioneDocumento) getObject(request, "DescrizioneDocumento", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DescrizioneDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DescrizioneDocumento newObject = binder.createDescrizioneDocumento(element);
					((List<DescrizioneDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<DescrizioneDocumento> getDescrizioneDocumentoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<DescrizioneDocumento>) getObjects(request, "DescrizioneDocumento", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DescrizioneDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DescrizioneDocumento newObject = binder.createDescrizioneDocumento(element);
					((List<DescrizioneDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDescrizioneDocumento(HttpServletRequest request, DescrizioneDocumento object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((DescrizioneDocumento) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Documento getDocumento(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Documento) getObject(request, "Documento", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Documento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Documento newObject = binder.createDocumento(element);
					((List<Documento>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Documento> getDocumentoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Documento>) getObjects(request, "Documento", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Documento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Documento newObject = binder.createDocumento(element);
					((List<Documento>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDocumento(HttpServletRequest request, Documento object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Documento) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public DocumentoCliente getDocumentoCliente(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (DocumentoCliente) getObject(request, "DocumentoCliente", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DocumentoCliente>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DocumentoCliente newObject = binder.createDocumentoCliente(element);
					((List<DocumentoCliente>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoCliente> getDocumentoClienteList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<DocumentoCliente>) getObjects(request, "DocumentoCliente", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<DocumentoCliente>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					DocumentoCliente newObject = binder.createDocumentoCliente(element);
					((List<DocumentoCliente>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertDocumentoCliente(HttpServletRequest request, DocumentoCliente object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((DocumentoCliente) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Fattura getFattura(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Fattura) getObject(request, "Fattura", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Fattura>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Fattura newObject = binder.createFattura(element);
					((List<Fattura>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Fattura> getFatturaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Fattura>) getObjects(request, "Fattura", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Fattura>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Fattura newObject = binder.createFattura(element);
					((List<Fattura>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertFattura(HttpServletRequest request, Fattura object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Fattura) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public HDChiamataTelefonica getHDChiamataTelefonica(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (HDChiamataTelefonica) getObject(request, "HDChiamataTelefonica", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDChiamataTelefonica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDChiamataTelefonica newObject = binder.createHDChiamataTelefonica(element);
					((List<HDChiamataTelefonica>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<HDChiamataTelefonica> getHDChiamataTelefonicaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<HDChiamataTelefonica>) getObjects(request, "HDChiamataTelefonica", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDChiamataTelefonica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDChiamataTelefonica newObject = binder.createHDChiamataTelefonica(element);
					((List<HDChiamataTelefonica>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertHDChiamataTelefonica(HttpServletRequest request, HDChiamataTelefonica object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((HDChiamataTelefonica) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public HDGruppoAssistenza getHDGruppoAssistenza(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (HDGruppoAssistenza) getObject(request, "HDGruppoAssistenza", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDGruppoAssistenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDGruppoAssistenza newObject = binder.createHDGruppoAssistenza(element);
					((List<HDGruppoAssistenza>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<HDGruppoAssistenza> getHDGruppoAssistenzaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<HDGruppoAssistenza>) getObjects(request, "HDGruppoAssistenza", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDGruppoAssistenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDGruppoAssistenza newObject = binder.createHDGruppoAssistenza(element);
					((List<HDGruppoAssistenza>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertHDGruppoAssistenza(HttpServletRequest request, HDGruppoAssistenza object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((HDGruppoAssistenza) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public HDRispostaStandard getHDRispostaStandard(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (HDRispostaStandard) getObject(request, "HDRispostaStandard", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDRispostaStandard>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDRispostaStandard newObject = binder.createHDRispostaStandard(element);
					((List<HDRispostaStandard>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<HDRispostaStandard> getHDRispostaStandardList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<HDRispostaStandard>) getObjects(request, "HDRispostaStandard", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDRispostaStandard>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDRispostaStandard newObject = binder.createHDRispostaStandard(element);
					((List<HDRispostaStandard>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertHDRispostaStandard(HttpServletRequest request, HDRispostaStandard object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((HDRispostaStandard) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public HDThreadStep getHDThreadStep(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (HDThreadStep) getObject(request, "HDThreadStep", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDThreadStep>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDThreadStep newObject = binder.createHDThreadStep(element);
					((List<HDThreadStep>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<HDThreadStep> getHDThreadStepList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<HDThreadStep>) getObjects(request, "HDThreadStep", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDThreadStep>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDThreadStep newObject = binder.createHDThreadStep(element);
					((List<HDThreadStep>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertHDThreadStep(HttpServletRequest request, HDThreadStep object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((HDThreadStep) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public HDTicket getHDTicket(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (HDTicket) getObject(request, "HDTicket", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDTicket>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDTicket newObject = binder.createHDTicket(element);
					((List<HDTicket>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<HDTicket> getHDTicketList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<HDTicket>) getObjects(request, "HDTicket", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<HDTicket>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					HDTicket newObject = binder.createHDTicket(element);
					((List<HDTicket>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertHDTicket(HttpServletRequest request, HDTicket object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((HDTicket) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public IndicizzazioneListino getIndicizzazioneListino(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (IndicizzazioneListino) getObject(request, "IndicizzazioneListino", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<IndicizzazioneListino>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					IndicizzazioneListino newObject = binder.createIndicizzazioneListino(element);
					((List<IndicizzazioneListino>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<IndicizzazioneListino> getIndicizzazioneListinoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<IndicizzazioneListino>) getObjects(request, "IndicizzazioneListino", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<IndicizzazioneListino>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					IndicizzazioneListino newObject = binder.createIndicizzazioneListino(element);
					((List<IndicizzazioneListino>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertIndicizzazioneListino(HttpServletRequest request, IndicizzazioneListino object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((IndicizzazioneListino) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public ItemListino getItemListino(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (ItemListino) getObject(request, "ItemListino", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ItemListino>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ItemListino newObject = binder.createItemListino(element);
					((List<ItemListino>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<ItemListino> getItemListinoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<ItemListino>) getObjects(request, "ItemListino", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ItemListino>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ItemListino newObject = binder.createItemListino(element);
					((List<ItemListino>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertItemListino(HttpServletRequest request, ItemListino object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((ItemListino) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public LavoroSuAttivita getLavoroSuAttivita(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (LavoroSuAttivita) getObject(request, "LavoroSuAttivita", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<LavoroSuAttivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					LavoroSuAttivita newObject = binder.createLavoroSuAttivita(element);
					((List<LavoroSuAttivita>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<LavoroSuAttivita> getLavoroSuAttivitaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<LavoroSuAttivita>) getObjects(request, "LavoroSuAttivita", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<LavoroSuAttivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					LavoroSuAttivita newObject = binder.createLavoroSuAttivita(element);
					((List<LavoroSuAttivita>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertLavoroSuAttivita(HttpServletRequest request, LavoroSuAttivita object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((LavoroSuAttivita) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Metaattivita getMetaattivita(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Metaattivita) getObject(request, "Metaattivita", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metaattivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metaattivita newObject = binder.createMetaattivita(element);
					((List<Metaattivita>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Metaattivita> getMetaattivitaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Metaattivita>) getObjects(request, "Metaattivita", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metaattivita>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metaattivita newObject = binder.createMetaattivita(element);
					((List<Metaattivita>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertMetaattivita(HttpServletRequest request, Metaattivita object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Metaattivita) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Metapratica getMetapratica(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Metapratica) getObject(request, "Metapratica", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metapratica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metapratica newObject = binder.createMetapratica(element);
					((List<Metapratica>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Metapratica> getMetapraticaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Metapratica>) getObjects(request, "Metapratica", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metapratica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metapratica newObject = binder.createMetapratica(element);
					((List<Metapratica>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertMetapratica(HttpServletRequest request, Metapratica object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Metapratica) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Metasollecito getMetasollecito(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Metasollecito) getObject(request, "Metasollecito", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metasollecito>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metasollecito newObject = binder.createMetasollecito(element);
					((List<Metasollecito>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Metasollecito> getMetasollecitoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Metasollecito>) getObjects(request, "Metasollecito", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Metasollecito>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Metasollecito newObject = binder.createMetasollecito(element);
					((List<Metasollecito>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertMetasollecito(HttpServletRequest request, Metasollecito object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Metasollecito) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public NotificaScadenza getNotificaScadenza(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (NotificaScadenza) getObject(request, "NotificaScadenza", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<NotificaScadenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					NotificaScadenza newObject = binder.createNotificaScadenza(element);
					((List<NotificaScadenza>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<NotificaScadenza> getNotificaScadenzaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<NotificaScadenza>) getObjects(request, "NotificaScadenza", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<NotificaScadenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					NotificaScadenza newObject = binder.createNotificaScadenza(element);
					((List<NotificaScadenza>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertNotificaScadenza(HttpServletRequest request, NotificaScadenza object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((NotificaScadenza) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Operatore getOperatore(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Operatore) getObject(request, "Operatore", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Operatore>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Operatore newObject = binder.createOperatore(element);
					((List<Operatore>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Operatore> getOperatoreList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Operatore>) getObjects(request, "Operatore", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Operatore>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Operatore newObject = binder.createOperatore(element);
					((List<Operatore>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertOperatore(HttpServletRequest request, Operatore object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Operatore) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Pratica getPratica(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Pratica) getObject(request, "Pratica", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Pratica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Pratica newObject = binder.createPratica(element);
					((List<Pratica>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Pratica> getPraticaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Pratica>) getObjects(request, "Pratica", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Pratica>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Pratica newObject = binder.createPratica(element);
					((List<Pratica>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertPratica(HttpServletRequest request, Pratica object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Pratica) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public ProForma getProForma(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (ProForma) getObject(request, "ProForma", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ProForma>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ProForma newObject = binder.createProForma(element);
					((List<ProForma>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<ProForma> getProFormaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<ProForma>) getObjects(request, "ProForma", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ProForma>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ProForma newObject = binder.createProForma(element);
					((List<ProForma>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertProForma(HttpServletRequest request, ProForma object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((ProForma) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public ProfiloDinamicoClienti getProfiloDinamicoClienti(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (ProfiloDinamicoClienti) getObject(request, "ProfiloDinamicoClienti", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ProfiloDinamicoClienti>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ProfiloDinamicoClienti newObject = binder.createProfiloDinamicoClienti(element);
					((List<ProfiloDinamicoClienti>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<ProfiloDinamicoClienti> getProfiloDinamicoClientiList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<ProfiloDinamicoClienti>) getObjects(request, "ProfiloDinamicoClienti", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<ProfiloDinamicoClienti>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					ProfiloDinamicoClienti newObject = binder.createProfiloDinamicoClienti(element);
					((List<ProfiloDinamicoClienti>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertProfiloDinamicoClienti(HttpServletRequest request, ProfiloDinamicoClienti object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((ProfiloDinamicoClienti) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public RegimePrevidenziale getRegimePrevidenziale(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (RegimePrevidenziale) getObject(request, "RegimePrevidenziale", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<RegimePrevidenziale>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					RegimePrevidenziale newObject = binder.createRegimePrevidenziale(element);
					((List<RegimePrevidenziale>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<RegimePrevidenziale> getRegimePrevidenzialeList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<RegimePrevidenziale>) getObjects(request, "RegimePrevidenziale", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<RegimePrevidenziale>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					RegimePrevidenziale newObject = binder.createRegimePrevidenziale(element);
					((List<RegimePrevidenziale>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertRegimePrevidenziale(HttpServletRequest request, RegimePrevidenziale object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((RegimePrevidenziale) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public RevisioneDocumento getRevisioneDocumento(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (RevisioneDocumento) getObject(request, "RevisioneDocumento", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<RevisioneDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					RevisioneDocumento newObject = binder.createRevisioneDocumento(element);
					((List<RevisioneDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<RevisioneDocumento> getRevisioneDocumentoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<RevisioneDocumento>) getObjects(request, "RevisioneDocumento", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<RevisioneDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					RevisioneDocumento newObject = binder.createRevisioneDocumento(element);
					((List<RevisioneDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertRevisioneDocumento(HttpServletRequest request, RevisioneDocumento object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((RevisioneDocumento) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Scadenza getScadenza(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Scadenza) getObject(request, "Scadenza", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Scadenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Scadenza newObject = binder.createScadenza(element);
					((List<Scadenza>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Scadenza> getScadenzaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Scadenza>) getObjects(request, "Scadenza", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Scadenza>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Scadenza newObject = binder.createScadenza(element);
					((List<Scadenza>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertScadenza(HttpServletRequest request, Scadenza object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Scadenza) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public Sms getSms(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (Sms) getObject(request, "Sms", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Sms>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Sms newObject = binder.createSms(element);
					((List<Sms>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<Sms> getSmsList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<Sms>) getObjects(request, "Sms", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<Sms>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					Sms newObject = binder.createSms(element);
					((List<Sms>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertSms(HttpServletRequest request, Sms object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((Sms) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public SollecitoPagamento getSollecitoPagamento(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (SollecitoPagamento) getObject(request, "SollecitoPagamento", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<SollecitoPagamento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					SollecitoPagamento newObject = binder.createSollecitoPagamento(element);
					((List<SollecitoPagamento>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<SollecitoPagamento> getSollecitoPagamentoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<SollecitoPagamento>) getObjects(request, "SollecitoPagamento", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<SollecitoPagamento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					SollecitoPagamento newObject = binder.createSollecitoPagamento(element);
					((List<SollecitoPagamento>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertSollecitoPagamento(HttpServletRequest request, SollecitoPagamento object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((SollecitoPagamento) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public SpesaAnticipata getSpesaAnticipata(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (SpesaAnticipata) getObject(request, "SpesaAnticipata", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<SpesaAnticipata>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					SpesaAnticipata newObject = binder.createSpesaAnticipata(element);
					((List<SpesaAnticipata>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<SpesaAnticipata> getSpesaAnticipataList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<SpesaAnticipata>) getObjects(request, "SpesaAnticipata", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<SpesaAnticipata>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					SpesaAnticipata newObject = binder.createSpesaAnticipata(element);
					((List<SpesaAnticipata>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertSpesaAnticipata(HttpServletRequest request, SpesaAnticipata object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((SpesaAnticipata) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public StoriaDocumento getStoriaDocumento(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (StoriaDocumento) getObject(request, "StoriaDocumento", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoriaDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoriaDocumento newObject = binder.createStoriaDocumento(element);
					((List<StoriaDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<StoriaDocumento> getStoriaDocumentoList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<StoriaDocumento>) getObjects(request, "StoriaDocumento", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoriaDocumento>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoriaDocumento newObject = binder.createStoriaDocumento(element);
					((List<StoriaDocumento>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertStoriaDocumento(HttpServletRequest request, StoriaDocumento object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((StoriaDocumento) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public StoricoInvii getStoricoInvii(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (StoricoInvii) getObject(request, "StoricoInvii", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoricoInvii>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoricoInvii newObject = binder.createStoricoInvii(element);
					((List<StoricoInvii>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<StoricoInvii> getStoricoInviiList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<StoricoInvii>) getObjects(request, "StoricoInvii", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoricoInvii>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoricoInvii newObject = binder.createStoricoInvii(element);
					((List<StoricoInvii>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertStoricoInvii(HttpServletRequest request, StoricoInvii object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((StoricoInvii) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public StoricoModifiche getStoricoModifiche(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (StoricoModifiche) getObject(request, "StoricoModifiche", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoricoModifiche>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoricoModifiche newObject = binder.createStoricoModifiche(element);
					((List<StoricoModifiche>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<StoricoModifiche> getStoricoModificheList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<StoricoModifiche>) getObjects(request, "StoricoModifiche", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<StoricoModifiche>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					StoricoModifiche newObject = binder.createStoricoModifiche(element);
					((List<StoricoModifiche>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertStoricoModifiche(HttpServletRequest request, StoricoModifiche object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((StoricoModifiche) object);
				}
			}
		);
	}
	@SuppressWarnings("unchecked")
	public VoceFattura getVoceFattura(HttpServletRequest request, int targetLevels, String instanceId)
		throws DataManagerException
	{
		return (VoceFattura) getObject(request, "VoceFattura", instanceId, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<VoceFattura>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					VoceFattura newObject = binder.createVoceFattura(element);
					((List<VoceFattura>) objectList).add(newObject);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public List<VoceFattura> getVoceFatturaList(HttpServletRequest request, Element condition, int targetLevels)
		throws DataManagerException
	{
		return (List<VoceFattura>) getObjects(request, "VoceFattura", condition, targetLevels, new BeanCreator()
			{
				public List<? extends SerenaMarshallable> createObjectEmptyList()
				{
					return new ArrayList<VoceFattura>();
				}

				public void addObjectToList(List<? extends SerenaMarshallable> objectList, Element element)
				{
					VoceFattura newObject = binder.createVoceFattura(element);
					((List<VoceFattura>) objectList).add(newObject);
				}
			}
		);
	}

	public String insertVoceFattura(HttpServletRequest request, VoceFattura object)
		throws DataManagerException
	{
		return insertObject(request, object, new ElementCreator()
			{
				public Element createElement(SerenaMarshallable object) throws BindingException
				{
					return binder.createElement((VoceFattura) object);
				}
			}
		);
	}
}