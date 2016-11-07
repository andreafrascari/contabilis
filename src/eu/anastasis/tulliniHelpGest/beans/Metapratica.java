package eu.anastasis.tulliniHelpGest.beans;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectMethod;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.constants.ConstantsEntityBean;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class Metapratica {
	
	private static final Logger logger = Logger.getLogger(ObjectMethod.class);
	
	private String ID=null;
	private String titolo=null;
	private String titolo_per_fattura=null;
	private String prezzo=null;
	private String tipo=null;
	private String[] lista_attivita=null;
	private String descrizione=null;
	
		
	public static final String MY_CLASS= "Metapratica";
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getTitolo_per_fattura() {
		return titolo_per_fattura;
	}
	public void setTitolo_per_fattura(String titolo_per_fattura) {
		this.titolo_per_fattura = titolo_per_fattura;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String[] getLista_attivita() {
		return lista_attivita;
	}
	public void setLista_attivita(String[] lista_attivita) {
		this.lista_attivita = lista_attivita;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Metapratica getInstance(String ID) throws SerenaException {
	try	{
		Metapratica lametapraticaObj = new Metapratica();
		Element currentElement = ObjectUtils.getXserenaRequestStandardHeader("Metapratica", ConstantsXSerena.ACTION_GET, MY_CLASS);
		currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION, ConstantsXSerena.VAL_SELECT);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET, ConstantsXSerena.TARGET_ALL);
		currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS, "3");
		currentElement=currentElement.addElement(ConstantsXSerena.TAG_CONDITION);
		currentElement=currentElement.addElement(ConstantsEntityBean.ID);
		currentElement.setText(ID);
		String[] messages={"",""};
		Document resultData=new CronPersistenceHome().get(currentElement.getDocument());
		Element data = ApplicationLibrary.prepareDataForPresentation(resultData);
		int res = ConstantsXSerena.getXserenaRequestResult(data, messages,Metapratica.MY_CLASS);

		if (res==ConstantsXSerena.XSERENA_RESULT_ERROR)
		{
			logger.error("Cannot load metapratica "+messages[0] );
			throw new SerenaException(messages[0],"generic_sql_error");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_EMPTY)
		{
			logger.error("Cannot load metapratica (NOT FOUND)");
			throw new SerenaException("nessuna metapratica");
		}
		else if (res==ConstantsXSerena.XSERENA_RESULT_SUCCESS)
		{
			Element lametapratica = (Element)data.selectSingleNode(".//"+MY_CLASS);
			// must be there
			String theID = lametapratica.elementText("ID");
			lametapraticaObj.setID(theID);
			lametapraticaObj.setTitolo(lametapratica.elementText("titolo"));
			lametapraticaObj.setTipo(lametapratica.elementText("tipo"));
			lametapraticaObj.setTitolo(lametapratica.elementText("titolo"));
			if (lametapratica.elementText("descrizione")!=null)
				lametapraticaObj.setDescrizione(lametapratica.elementText("descrizione"));
			if (lametapratica.elementText("titolo_per_fattura")!=null)
				lametapraticaObj.setTitolo_per_fattura(lametapratica.elementText("titolo_per_fattura"));
			if (lametapratica.elementText("prezzo")!=null)
				lametapraticaObj.setPrezzo(lametapratica.elementText("prezzo"));
			List<Element>attivita = lametapratica.selectNodes(".//Metaattivita");
			if (!attivita.isEmpty())
				lametapraticaObj.setLista_attivita(new String[attivita.size()]);
			int i=0;
			for (Element unaattivita:attivita){
				lametapraticaObj.getLista_attivita()[i++]=unaattivita.elementText("nome");
			}
		}
		return lametapraticaObj;
	} catch  (Exception any)	{
		String msg = "Could not load operator cache: "+any.getMessage();
		logger.error(msg);
		throw new SerenaException(msg);
		}
	}
	
	 
}
