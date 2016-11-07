package eu.anastasis.it.tullinidms.modules;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;

import eu.anastasis.serena.application.index.util.ApplicationConfiguration;
import eu.anastasis.serena.application.index.util.ApplicationLibrary;
import eu.anastasis.serena.application.index.util.MailHandler;
import eu.anastasis.serena.common.XSerenaLibrary;
import eu.anastasis.serena.dataManager.DataManagerException;
import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.query.SelectQuery;

public class Targa {

	private static final String BeanName = "Car4uFleet";
	String targa;
	String cliente;

	Map<String, String> clienti = new HashMap<String, String>() {
		{
			put("Roma Fiumicino Airport", "756");
			put("Bologna", "757");
			put("Roma Ciampino Airport", "790"); 
		}
	};

	public String clienteFromDescriptionInFileFlotta(String desc) {
		String cli = clienti.get(desc);
		if (cli != null) {
			return cli;
		} else {
			for (String k : clienti.keySet()) {
				if (desc.contains(k)) {
					return clienti.get(k);
				}
			}
		}
		return null;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	private static final Logger logger = Logger.getLogger(Targa.class);

	// si tratta di doc di car4you?
	public static Targa workoutCar4Unumberplate(String filename, String tipo)
			throws SerenaException {
		if ("MULTA".equals(tipo)) {
			// formato a: AX789ER data
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]{7,}\\s.*?");
			Matcher matcher = pattern.matcher(filename);
			while (matcher.find()) {
				String matched = filename.substring(matcher.start(),
						matcher.start() + 7);
				logger.debug("Testing matching 'any7chars-numberplate *')"
						+ matched);
				Targa t = new Targa();
				t.setTarga(matched);
				return t;
			}
			// formato b: AY456ED_data
			pattern = Pattern.compile("[a-zA-Z0-9]{7,}_.*?");
			matcher = pattern.matcher(filename);
			while (matcher.find()) {
				String matched = filename.substring(matcher.start(),
						matcher.start() + 7);
				logger.debug("Testing matching 'any7chars-numberplate_*')"
						+ matched);
				Targa t = new Targa();
				t.setTarga(matched);
				return t;
			}

			// formato c: data_AY456ED
			pattern = Pattern.compile(".*?_[a-zA-Z0-9]{7,}");
			matcher = pattern.matcher(filename);
			while (matcher.find()) {
				String matched = filename.substring(matcher.end() - 7,
						matcher.end());
				logger.debug("Testing matching '*_any7chars-numberplate')"
						+ matched);
				Targa t = new Targa();
				t.setTarga(matched);
				return t;
			}

			logger.fatal("Attenzione: caricato documento MULTA, di cui non riesco a desumere il cliente dal nome file "
					+ filename);
		}
		// nothing has been found ... returns null which means document belongs
		// to no client
		return null;
	}

	protected void notifySegreteriaTargaNonRiconosiuta(String testoMail) {
		String[] bcc = null;
		String[] cc = null;
		String content_type = MailHandler.CONTENT_TYPE_PLAIN_TEXT;
		String from = "";
		try {
			from = ApplicationConfiguration.GetInstance().getParametro(
					ApplicationConfiguration.MAIL_SYSTEM_ADDRESS);
		} catch (SerenaException e) {
			logger.fatal("Cannot send mail as no system address has been specified in config_application");

		}

		String msg = "";
		MailHandler mailHandler = new MailHandler();
		String oggetto = "[CAT4U] - Problrma su una targa";

		String[] recipients = { "contabilita@contabilis.it" };

		try {
			mailHandler.sendAndReturnMsg(from, null, recipients, cc, bcc,
					oggetto, testoMail, content_type, new String[0]);
		} catch (Exception e) {
			msg += "Errore nell'invio della mail " + oggetto;
			logger.error(msg);
		}
	}

	public void loadCliente(HttpServletRequest request) throws SerenaException {
		SelectQuery query = new SelectQuery(BeanName);
		query.addCondition("targa", targa);
		query.getRootClassElement().addElement("cliente");
		try {
			Document result = ApplicationLibrary.getData(query, request);
			final int rdim = XSerenaLibrary.selectResultDimension(result);
			if (rdim == 1) {
				String clienteDaFile = result.selectSingleNode(".//cliente")
						.getText();
				cliente = clienteFromDescriptionInFileFlotta(clienteDaFile);
				// cliente= clienti.get(cliente); // name to ID
				if (cliente == null) {
					String err = "Targa "
							+ targa
							+ " riconosciuta ma cliente relativo (da file) non censito ("+ clienteDaFile +"): documento caricato ma targa non riconosciuta. Lanciare WF a mano!";
					logger.fatal(err);
					notifySegreteriaTargaNonRiconosiuta(err);
					// throw new SerenaException(err);
				}
			} else {
				String err = "Multa per targa " + targa
						+ ": documento caricato ma targa non riconosciuta. Lanciare WF a mano!";
				logger.fatal(err);
				notifySegreteriaTargaNonRiconosiuta(err);
				// throw new SerenaException(err);
			}
		} catch (DataManagerException e) {
			String msg = "Cannot perform query on Car4uFleet: "
					+ e.getMessage();
			logger.error(msg);
			throw new SerenaException(msg);
		}
	}
}
