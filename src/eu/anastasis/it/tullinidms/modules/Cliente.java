package eu.anastasis.it.tullinidms.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import eu.anastasis.serena.application.core.tasks.CronPersistenceHome;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.modules.object.ObjectMethod;
import eu.anastasis.serena.application.modules.object.ObjectUtils;
import eu.anastasis.serena.auth.User;
import eu.anastasis.serena.constants.ConstantsXSerena;
import eu.anastasis.serena.exception.SerenaException;

public class Cliente {

	private static final Logger logger = Logger.getLogger(Cliente.class);
	/**
	 * i clienti hanno in realtà 2 codici identificativi: 1) di 6 lettere
	 * (vecchio) 2) di 3 lettere (nuovo) I due codici provengono da kerio in un
	 * unico campo, e sono separati da ....
	 */
	private static final String COD_CLI_SEPARATOR = ",";
	public static final String INSTANCE_NAME = "Cliente";

	private String ID = null;
	private String nome = null;
	private String nickname = null;
	private String email = null;
	private String email2 = null;
	private String email3 = null;
	private String fax = null;
	private String cellulare = null;
	private String tipo_sollecito = null;
	private String codicecliente = null;
	private String codicefiscale = null;

	private static HashMap<String, String> CF_CACHE = null;
	private static HashMap<String, String> PI_CACHE = null;
	private static HashMap<String, String> CODCLI_6_CACHE = null;
	private static HashMap<String, String> CODCLI_3_CACHE = null;
	private static HashMap<String, String> USER_CLIENT_CACHE = null;
	private static HashMap<String, Cliente> MYCACHE = null;
	private static HashMap<String, String> EMAIL_CACHE = null;

	public static HashMap<String, String> getCF_CACHE() throws SerenaException {
		if (CF_CACHE == null)
			new Cliente().loadCache();
		return CF_CACHE;
	}

	public static HashMap<String, String> getPI_CACHE() throws SerenaException {
		if (PI_CACHE == null)
			new Cliente().loadCache();
		return PI_CACHE;
	}

	public static HashMap<String, String> getCODCLI_6_CACHE()
			throws SerenaException {
		if (CODCLI_6_CACHE == null)
			new Cliente().loadCache();
		return CODCLI_6_CACHE;
	}

	public static HashMap<String, String> getCODCLI_3_CACHE()
			throws SerenaException {
		if (CODCLI_3_CACHE == null)
			new Cliente().loadCache();
		return CODCLI_3_CACHE;
	}

	public static HashMap<String, String> getEMAIL_CACHE()
			throws SerenaException {
		if (EMAIL_CACHE == null)
			new Cliente().loadCache();
		return EMAIL_CACHE;
	}

	public static HashMap<String, String> getUSER_CLIENT_CACHE()
			throws SerenaException {
		if (USER_CLIENT_CACHE == null)
			new Cliente().loadCache();
		return USER_CLIENT_CACHE;
	}

	public static final String SOLLECITO_MAIL = "mail";
	public static final String SOLLECITO_SMS = "sms";
	public static final String SOLLECITO_FAX = "fax";

	public static final String MY_CLASS = "Cliente";

	public String getTipo_sollecito() {
		return tipo_sollecito;
	}

	public void setTipo_sollecito(String tipo_sollecito) {
		this.tipo_sollecito = tipo_sollecito;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNickname() {
		if (nickname == null)
			return nome;
		else
			return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	/*
	 * public String[] getEmails() { return (email!=null)?email.replace(" ",
	 * "").split(","):null; }
	 */
	public Vector<String> getEmails() {
		Vector<String> res = new Vector<String>();
		if (this.getEmail() != null && !this.getEmail().isEmpty())
			res.add(this.getEmail());
		if (this.getEmail2() != null && !this.getEmail2().isEmpty())
			res.add(this.getEmail2());
		if (this.getEmail3() != null && !this.getEmail3().isEmpty())
			res.add(this.getEmail3());
		return res;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellulare() {
		return cellulare;
	}

	public String[] getCellulares() {
		return (cellulare != null) ? cellulare.replace(" ", "").split(",")
				: null;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getCodicecliente() {
		return codicecliente;
	}

	public String getCodicecliente_clean() {
		String cc = codicecliente;
		if (cc.contains(COD_CLI_SEPARATOR)) {
			cc = cc.split(COD_CLI_SEPARATOR)[0];
		}
		return cc;
	}

	public void setCodicecliente(String codicecliente) {
		this.codicecliente = codicecliente;
	}

	public String getCodicefiscale() {
		return codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	// il default è via mail!!!!!
	public boolean notificheViaMail() {
		return SOLLECITO_MAIL.equals(this.tipo_sollecito);
	}

	public boolean notificheViaSms() {
		return SOLLECITO_SMS.equals(this.tipo_sollecito);
	}

	public boolean notificheViaFax() {
		return SOLLECITO_FAX.equals(this.tipo_sollecito);
	}

	public Cliente getInstance(String ID) throws SerenaException {
		if (MYCACHE == null)
			loadCache();
		return MYCACHE.get(ID);
	}

	/**
	 * Identifica il cliente dalla stringa che trovi dopo "_" o "(", che
	 * potrebbe essere il cf o il codice cliente
	 * 
	 * @param anAttach
	 * @return
	 */
	public static String workoutClient(String filename) throws SerenaException {
		Pattern pattern = Pattern.compile("\\(.*?\\)");
		Matcher matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 1,
					matcher.end() - 1);
			logger.trace("Testing matching '(cf/pi/codcli6)'" + matched);
			if (getCF_CACHE().get(matched) != null) {
				return getCF_CACHE().get(matched);
			} else if (getCODCLI_6_CACHE().get(matched) != null) {
				return getCODCLI_6_CACHE().get(matched);
			} else if (getPI_CACHE().get(matched) != null) {
				return getPI_CACHE().get(matched);
			}
		}

		pattern = Pattern.compile("\\_.*?\\.");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 1,
					matcher.end() - 1);
			logger.trace("Testing matching '_cf/codcli6.')" + matched);
			if (getCF_CACHE().get(matched) != null)
				return getCF_CACHE().get(matched);
			else if (getCODCLI_6_CACHE().get(matched) != null)
				return getCODCLI_6_CACHE().get(matched);
		}

		pattern = Pattern.compile("_.*?\\s");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 1,
					matcher.end() - 1);
			logger.trace("Testing matching '_cf/codcli6 ')" + matched);
			if (getCF_CACHE().get(matched) != null)
				return getCF_CACHE().get(matched);
			else if (getCODCLI_6_CACHE().get(matched) != null)
				return getCODCLI_6_CACHE().get(matched);
		}

		pattern = Pattern.compile("F24\\s[a-zA-Z0-9]{3,}\\s.*?\\s");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 8,
					matcher.end() - 1);
			logger.trace("Testing matching 'F24 any3chars codcli3 ')" + matched);
			if (getCODCLI_3_CACHE().get(matched) != null)
				return getCODCLI_3_CACHE().get(matched);
		}

		pattern = Pattern.compile("F24\\s[a-zA-Z0-9]{3,}\\s.*?\\-");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 8,
					matcher.end() - 1);
			logger.trace("Testing matching 'F24 any3chars codcli3-')" + matched);
			if (getCODCLI_3_CACHE().get(matched) != null)
				return getCODCLI_3_CACHE().get(matched);
		}

		pattern = Pattern
				.compile("F24\\s[a-zA-Z0-9]{3,}\\s[a-zA-Z0-9]{4,}\\s.*?\\s");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 13,
					matcher.end() - 1);
			logger.trace("Testing matching 'F24 any3chars any4chars codcli3 ')"
					+ matched);
			if (getCODCLI_3_CACHE().get(matched) != null)
				return getCODCLI_3_CACHE().get(matched);
		}

		pattern = Pattern
				.compile("F24\\s[a-zA-Z0-9]{3,}\\s[a-zA-Z0-9]{4,}\\s.*?\\-");
		matcher = pattern.matcher(filename);
		while (matcher.find()) {
			String matched = filename.substring(matcher.start() + 13,
					matcher.end() - 1);
			logger.trace("Testing matching 'F24 any3chars any4chars codcli3-')"
					+ matched);
			if (getCODCLI_3_CACHE().get(matched) != null)
				return getCODCLI_3_CACHE().get(matched);
		}

		logger.trace("Cannot identify any client from " + filename);
		// nothing has been found ... returns null which means document belongs
		// to no client
		return null;
	}

	public void reasetCache() throws SerenaException {
		loadCache();
	}

	/**
	 * Carica cache clienti ATTIVI (non quelli con cessata assistenza)
	 * 
	 * @throws SerenaException
	 */
	public void loadCache() throws SerenaException {
		// TODO Auto-generated method stub
		try {
			MYCACHE = new HashMap<String, Cliente>();

			logger.trace("Loading cache clienti...");
			Element currentElement = ObjectUtils
					.getXserenaRequestStandardHeader("clientcache",
							ConstantsXSerena.ACTION_GET, Cliente.MY_CLASS);
			currentElement.addAttribute(ConstantsXSerena.ATTR_OPERATION,
					ConstantsXSerena.VAL_SELECT);
			currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET,
					ConstantsXSerena.TARGET_SPECIFIED);
			// currentElement.addAttribute(ConstantsXSerena.ATTR_TARGET_LEVELS,
			// "0");
			Element currentCond = currentElement
					.addElement(ConstantsXSerena.TAG_CONDITION);
			currentCond = currentElement.addElement("cessata_assistenza_il");
			currentCond.setText(ConstantsXSerena.VAL_NULL);
			currentElement.addElement("ID");
			currentElement.addElement("codice_cliente");
			currentElement.addElement("codice_fiscale");
			currentElement.addElement("partita_iva");
			currentElement.addElement("tipo_sollecito");
			currentElement.addElement("nickname");
			currentElement.addElement("cellulare");
			currentElement.addElement("email");
			currentElement.addElement("email2");
			currentElement.addElement("email3");
			currentElement.addElement("fax");
			currentElement.addElement("cliente");
			Element item = currentElement.addElement("account");
			item = item.addElement("_system_user");
			item.addElement("ID");

			String[] messages = { "", "" };
			Document resultData = new CronPersistenceHome().get(currentElement
					.getDocument());
			Element data = ApplicationLibrary
					.prepareDataForPresentation(resultData);
			int res = ConstantsXSerena.getXserenaRequestResult(data, messages,
					Cliente.MY_CLASS);

			if (res == ConstantsXSerena.XSERENA_RESULT_ERROR) {
				logger.error("Cannot startup ClientCache for upload agent client recognition ... which consequently WILL NOT WORK"
						+ messages[0]);
				throw new SerenaException(messages[0], "generic_sql_error");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_EMPTY) {
				logger.error("Cannot startup ClientCache for upload agent client recognition ... which consequently WILL NOT WORK: NO CLIENT FOUND");
				throw new SerenaException("nessun cliente");
			} else if (res == ConstantsXSerena.XSERENA_RESULT_SUCCESS) {
				List<Element> iClienti = data.selectNodes(".//Cliente");
				CF_CACHE = new HashMap<String, String>();
				PI_CACHE = new HashMap<String, String>();
				CODCLI_6_CACHE = new HashMap<String, String>();
				CODCLI_3_CACHE = new HashMap<String, String>();
				EMAIL_CACHE = new HashMap<String, String>();
				USER_CLIENT_CACHE = new HashMap<String, String>();
				for (Element unCliente : iClienti) {
					Cliente unClienteObj = new Cliente();
					String theID = unCliente.elementText("ID");
					String cf = unCliente.elementText("codice_fiscale");
					String pi = unCliente.elementText("partita_iva");
					String codcli = unCliente.elementText("codice_cliente");
					if (codcli != null) {
						if (codcli.contains(COD_CLI_SEPARATOR)) {
							// sono presenti entrambi i codici!!!
							String[] both = codcli.split(COD_CLI_SEPARATOR);
							if (both[0].length() == 6) {
								// logger.trace("CODCLI CACHE: adding " +
								// both[0] + " to CACHE6");
								CODCLI_6_CACHE.put(both[0], theID);
							} else {
								CODCLI_3_CACHE.put(both[0], theID);
								// logger.trace("CODCLI CACHE: adding " +
								// both[0] + " to CACHE3");
							}
							if (both[1].length() == 6) {
								CODCLI_6_CACHE.put(both[1], theID);
								// logger.trace("CODCLI CACHE: adding " +
								// both[1] + " to CACHE6");
							} else {
								CODCLI_3_CACHE.put(both[1], theID);
								// logger.trace("CODCLI CACHE: adding " +
								// both[1] + " to CACHE3");
							}
						} else {
							CODCLI_6_CACHE.put(codcli, theID);
							// logger.trace("CODCLI CACHE: adding " + codcli +
							// " to CACHE6");
						}
					}
					Element user = (Element) unCliente
							.selectSingleNode(".//_system_user");
					CF_CACHE.put(cf, theID);
					if (pi!=null && !pi.isEmpty())	{
						PI_CACHE.put(pi, theID);
					}
					if (user != null)	{
						USER_CLIENT_CACHE.put(user.elementText("ID"), theID);
					}
					unClienteObj.setID(theID);
					unClienteObj.setCodicecliente(codcli);
					unClienteObj.setCodicefiscale(cf);
					unClienteObj.setNome(unCliente.elementText("cliente"));
					if (unCliente.element("tipo_sollecito") != null)
						unClienteObj.setTipo_sollecito(unCliente
								.elementText("tipo_sollecito"));
					if (unCliente.element("nickname") != null)
						unClienteObj.setNickname(unCliente
								.elementText("nickname"));
					unClienteObj.setNome(unCliente.elementText("cliente"));
					if (unCliente.element("email") != null) {
						unClienteObj.setEmail(unCliente.elementText("email"));
						EMAIL_CACHE.put(unClienteObj.getEmail(), theID);
					}
					if (unCliente.element("email2") != null)
						unClienteObj.setEmail2(unCliente.elementText("email2"));
					if (unCliente.element("email3") != null)
						unClienteObj.setEmail3(unCliente.elementText("email3"));
					if (unCliente.element("fax") != null)
						unClienteObj.setFax(unCliente.elementText("fax"));
					if (unCliente.element("cellulare") != null)
						unClienteObj.setCellulare(unCliente
								.elementText("cellulare"));

					MYCACHE.put(theID, unClienteObj);
					logger.trace("CODCLI CACHE: done! CODCLI_6_CACHE has now size "
							+ CODCLI_6_CACHE.size()
							+ "; CODCLI_3_CACHE has now size "
							+ CODCLI_3_CACHE.size());
				}

			}
			logger.trace("DONE!");
		} catch (Exception any) {
			String msg = "Could not load client cache: " + any.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}

	public String getClientIDFromSessionAccount(HttpServletRequest request)
			throws SerenaException {
		User loggedUser = ApplicationLibrary.retrieveCurrentUser(request);
		return Cliente.getUSER_CLIENT_CACHE().get(loggedUser.getId());

	}

}
