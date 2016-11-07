/**
 * 
 */
package eu.anastasis.it.tullinidms;

/**
 * Costanti generiche per il DMS dello Studio Tullini
 * @author mcarnazzo
 */
public class ConstantsTulliniDMS
{
	public static final String DOCUMENT_DESCRIPTION_TYPE_SLOT = "tipologia_documento";
	
	public static final String ID_OPERATORE_SEGRETERIA= "100";
	
	public static final String DandLK_USERPAR = "u";	// id cliente
	public static final String DandLK_FORCELOGONPAR = "f"; // la richiesta impone logon da email
	public static final String DandLK_DOCPAR = "d";	 // id documento
	public static final String DandLK_ATTACHPAR = "a";	// id attachment
	public static final String DandLK_EMAILPAR = "e";	// la richiesta proveniente da email
	public static final String DandLK_WORKFLOWPAR = "w";	// l'id del workflow relativo (da aggiornare)

	public static final String DOWNLOAD_SERVLET="DandL?q=get";
	public static final String AGENT_SERVLET="AgentServer";
	public static final String BASE_DOWNLOAD_SHARED_LINK = "@BASE_DOWNLOAD_LINK@&amp;"+DandLK_DOCPAR+"=@DOCID@&amp;"+DandLK_ATTACHPAR+"=@ATTACHID@&amp;"+DandLK_EMAILPAR+"=@MAINEMAIL@&amp;"+DandLK_WORKFLOWPAR+"=@WORKFLOWID@";
	public static final String BASE_DOWNLOAD_USER_LINK_SKIPLOGON = BASE_DOWNLOAD_SHARED_LINK+"&amp;"+DandLK_USERPAR+"=@USERID@";
	public static final String BASE_DOWNLOAD_USER_LINK_FORCELOGON = BASE_DOWNLOAD_USER_LINK_SKIPLOGON+"&amp;"+DandLK_FORCELOGONPAR+"=1";
	
	public static final String AZIONE_MAIL_NOTIFICA = "AZIONE A";
	public static final String AZIONE_MAIL_NOTIFICA_LINK = "AZIONE B";
	public static final String AZIONE_MAIL_NOTIFICA_LINK_LOGON = "AZIONE C";
	public static final String AZIONE_COMM_INTERNA_SEGRETERIA = "AZIONE D";
	public static final String AZIONE_COMM_INTERNA_STUDIO = "AZIONE E";
	public static final String AZIONE_NOTIFICA_MAILING_LIST = "AZIONE F";
	public static final String AZIONE_CARICAMENTO_SILENZIOSO_CLIENTE= "AZIONE G";
	
	public static final String VISIBILITA_INT_USER_ADMIN = "documento interno visibile user e admin";
	public static final String VISIBILITA_INT_ADMIN = "documento interno admin";
	public static final String VISIBILITA_CLI_CLI_USER_ADMIN = "documento cliente visibile cliente user e admin";
	public static final String VISIBILITA_CLI_ADMIN = "documento cliente visibile solo admin";
	public static final String VISIBILITA_CLI_USER_ADMIN = "documento cliente visibile solo user e admin";

	public static final String[] AZIONI_TRIGGERING_WORKFLOW = {AZIONE_MAIL_NOTIFICA,AZIONE_MAIL_NOTIFICA_LINK,AZIONE_MAIL_NOTIFICA_LINK_LOGON,AZIONE_CARICAMENTO_SILENZIOSO_CLIENTE};
	
	/** l'azione passata triggera il workflow?
	 * 
	 * @param anAction
	 * @return
	 */
	public static boolean actionTriggersWorkflow(String anAction){
		for (int i=0; i< AZIONI_TRIGGERING_WORKFLOW.length; i++)	{
			if (anAction.contains(AZIONI_TRIGGERING_WORKFLOW[i]))
				return true;
		}
		return false;
	}
	
}
