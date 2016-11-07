INSERT INTO _system_module_parameter ( ID, ID__system_module_sm_parameters, smp_name, smp_value, activation_flag, deletion_flag, creation_user, creation_date, last_modification_user, last_modification_date ) VALUES( 26, "20", "REGISTER_DOCUMENT_FOR_NON_CLIENT", "NO", "1", "0", "fabio", "2015-11-25", "fabio", "2015-11-25" );

drop view if exists VW_TROPPE_REVISIONI;
create view VW_TROPPE_REVISIONI as
SELECT `ID_Documento_revisioni` , count(`ID_Documento_revisioni`) AS occorrenze
FROM `RevisioneDocumento`
GROUP BY `ID_Documento_revisioni`
HAVING occorrenze >10
ORDER BY occorrenze DESC ;

/* for each instance ID di documento: contyrolsre il nome file RADICE+INDICE*/

delete FROM `_system_attachment` WHERE `sa_filename` like 'RADICE%' and `sa_filename` <> 'RADICEINDICE';
delete FROM `RevisioneDocumento` WHERE `ID_Documento_revisioni` = 'ID';
delete fisica in upload



UPDATE `tullini`.`_system_module_parameter` SET `smp_value` = 'Contabilis - Attivazione area riservata ns. Sistema DMS' WHERE `_system_module_parameter`.`ID` =2;
UPDATE `tullini`.`_system_module_parameter` SET `smp_value` = '<p>Inclusi inoltre nel forfait:</p>

<div style="text-align: justify;">
<ul>
	<li>Compilazione certificazioni di ritenuta d&#39;acconto</li>
	<li>Compilazione Mod. F24 IVA/ritenute/imposte ed invio al Vs. indirizzo e-mail</li>
	<li>Servizio circolari informative via e-mail/web</li>
	<li>Attivazione Vs. area riservata su ns. sito web (ns. servizio DMS)</li>
</ul>

<p>*&egrave; esclusa la gestione del personale dipendente ed assimilato (es. consulenza, elaborazione paghe, modelli F24, quadri specifici della dichiarazione dei sostituti d&#39;imposta), per la quale restiamo a disposizione per fornire separato apposito preventivo.</p>

<p>Le prestazioni di cui sopra saranno fornite da Contabilis Srl (c.f./p.iva 01802150381 Polizza Responsabilit&agrave; Civile 2032/122/56764981 c/o Unipol Assicurazioni).<br />
<br />
Tutti gli importi dovranno essere maggiorati di <strong>IVA </strong>come per legge; il pagamento sar&agrave; richiesto mediante presentazione di proforma al termine di ciascun trimestre solare.<br />
Rimessa diretta vista proforma. L&#39;importo del forfait verr&agrave; rivalutato annualmente sulla base degli indici ISTAT.<br />
<br />
In caso di richieste relative a prestazioni diverse da quelle sopra indicate, le stesse saranno fornite dal ns. Studio o da Contabilis Srl e formeranno oggetto di separato addebito.<br />
<br />
Nel ringraziare per l&#39;attenzione restiamo a disposizione per ogni eventuale chiarimento su quanto sopra e, in attesa di un Vs. gradito riscontro, porgiamo i pi&ugrave; cordiali saluti.</p>
</div>

<p><br />
Contabilis Srl</p>
' WHERE `_system_module_parameter`.`ID` = 14;



+------------------------+------------+
| ID_Documento_revisioni | occorrenze |
+------------------------+------------+
|                  12653 |      10010 |
|                  12654 |      10010 |
|                  12655 |      10010 |
|                  12656 |      10010 |
|                  12657 |      10009 |
|                  12672 |       9848 |
|                  12673 |       9848 |
|                  12674 |       9848 |
|                  12675 |       9848 |
|                  12676 |       9848 |
|                  12677 |       9847 |
|                  12678 |       9847 |
|                  12679 |       9847 |
|                  12680 |       9847 |
|                  12682 |       9846 |
|                  12683 |       9846 |
|                  12684 |       9846 |
|                  12685 |       9846 |
|                  12681 |       9846 |
|                  12686 |       9844 |
|                  12687 |       9844 |
|                  12688 |       9844 |
|                  12804 |       8420 |
|                  12805 |       8420 |
|                  12806 |       8420 |
|                  12807 |       8420 |
|                  12797 |       8420 |
|                  12798 |       8420 |
|                  12799 |       8420 |
|                  12800 |       8420 |
|                  12801 |       8420 |
|                  12802 |       8420 |
|                  12803 |       8420 |
|                  12963 |       7484 |
|                  13058 |       6902 |
|                  13061 |       6670 |
|                  13065 |       6662 |
|                  13062 |       6582 |
|                  13072 |       6579 |
|                  13073 |       6453 |
|                  12484 |       3105 |
|                  12486 |       3105 |
|                  13863 |       2558 |
|                  13999 |       2557 |
|                  14017 |       2557 |
|                  14019 |       2557 |
|                  14041 |       2557 |
|                  13862 |       2557 |
|                  14195 |       2479 |
|                    508 |        814 |
|                  13311 |        566 |
|                  14399 |        496 |
|                  13327 |        196 |
|                  12952 |        167 |
|                   1330 |        125 |
