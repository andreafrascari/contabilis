/**
 * Questo file appartiene al progetto Sere-na - www.sere-na.it
 * 
 * @copyright Anastasis Soc. Coop. - www.anastasis.it
 */
package eu.anastasis.tulliniHelpGest.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import eu.anastasis.serena.exception.SerenaException;
import eu.anastasis.serena.presentation.functions.DefaultFunction;

/*
 * Campo	Tipo	Formato	Lunghezza	Decimali	Posizione	Note
N. Fattura	Num		6	0	1	
Data Fattura	Alfa	AAAAMMGG	8		7	
Cliente	Alfa		6		15	Da Trascodificare
Imponibile aliquota 1	Num	9990.00	12	2	21	
Aliquota 1	Alfa		3		33	Fisso F04
IVA aliquota 1	Num	9990.00	6	2	36	Fisso 0
Imponibile aliquota 2	Num	9990.00	12	2	42	
Aliquota 2	Alfa		3		54	Fisso E10
IVA aliquota 2	Num	9990.00	6	2	57	Fisso 0
Imponibile aliquota 3	Num	9990.00	12	2	63	
Aliquota 3	Alfa		3		75	Fisso 4
IVA aliquota 3	Num	9990.00	6	2	78	
Imponibile aliquota 4	Num	9990.00	12	2	84	
Aliquota 4	Alfa		3		96	Fisso 10
IVA aliquota 4	Num	9990.00	6	2	99	
Imponibile aliquota 5	Num	9990.00	12	2	105	
Aliquota 5	Alfa		3		117	Fisso 21
IVA aliquota 5	Num	9990.00	6	2	120	

Totale imponibile	Num	9990.00	15	2	126	
Totale IVA	Num	9990.00	15	2	141	
Totale Spese Anticipate	Num	9990.00	12	2	156	
Totale fattura	Num	9990.00	15	2	168	
Fattura o Nota Accredito	Alfa		2		183	FE=Fattura - NE=Nota
Fine Record	Alfa		2		185	CR+LF

 */
public class Export2BPFunction extends DefaultFunction
{
 
	private final static String FUNCTION_NAME = "FUN_EXPORT_BP";
	private final static String Input_Param = "input";
	
	private final static String INPUT_SEP = ";";
	private final static String OUTPUT_SEP = "";

	@Override
	public String getFunctionName()
	{
		return FUNCTION_NAME;
	}

	// @tag_numero@;@tag_data@;@FUN_SUBSTRING(string=@tag_inverse_of_fatture.Cliente.codice_cliente@,start?0,end=6)@;@FUN_REPLACE(string=@tag_Totale_Imponibile_0@,search=.,replace=#COMMA#)@;F04;@FUN_REPLACE(string=@tag_Iva_0@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Totale_Imponibile_1@,search=.,replace=#COMMA#)@;E10;@FUN_REPLACE(string=@tag_Iva_1@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Totale_Imponibile_2@,search=.,replace=#COMMA#)@;4;@FUN_REPLACE(string=@tag_Iva_2@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Totale_Imponibile_3@,search=.,replace=#COMMA#)@;10;@FUN_REPLACE(string=@tag_Iva_3@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Totale_Imponibile_4@,search=.,replace=#COMMA#)@;21;@FUN_REPLACE(string=@tag_Iva_4@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@XPATH_FUN(number(Totale_Imponibile_0+Totale_Imponibile_1+Totale_Imponibile_2+Totale_Imponibile_3+Totale_Imponibile_4))@@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@XPATH_FUN(number(Iva_0+Iva_1+Iva_2+Iva_3+Iva_4))@@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Spese_Escluse_Da_Imponibile@,search=.,replace=#COMMA#)@;@FUN_REPLACE(string=@tag_Totale_Fattura@,search=.,replace=#COMMA#)@;FE;\n
	@Override
	public String doMethod(HttpServletRequest request, Map<String, String> params) throws SerenaException
	{
		StringBuffer res = new StringBuffer();
		final String input = retrieveParam(params, Input_Param , null);
		if (input==null)	{
			return "ERRORE COMPUTAZIONE TRACCIATO FISSO BP";
		}
		String[] preProc = input.split(INPUT_SEP);
		
		res.append(fill2(preProc[0], 6, "r"));	// N. Fattura	Num		6
		res.append(OUTPUT_SEP);
		res.append(fill2(toAAAAMMGG(preProc[1]), 8, "l"));	// Data Fattura	Alfa	AAAAMMGG	8
		res.append(OUTPUT_SEP);
		res.append(fill2(preProc[2], 6, "l"));	//
		res.append(OUTPUT_SEP);
		
		res.append(fill2(toImporto(preProc[3],2), 12, "r"));	// Imponibile aliquota 1	Num	9990.00	12	2
		res.append(OUTPUT_SEP);
		res.append("F04");										// Aliquota 1	Alfa		3
		res.append(OUTPUT_SEP);
		// res.append(fill2(toImporto(preProc[5],2), 6, "r"));	// IVA aliquota 1	Num	9990.00	6	2
		res.append(fill2("0", 8, "r"));	// IVA aliquota 1	Num	9990.00	6	2
		res.append(OUTPUT_SEP);
		
		res.append(fill2(toImporto(preProc[6],2), 12, "r"));	// Imponibile aliquota 2	Num	9990.00	12	2
		res.append(OUTPUT_SEP);
		res.append("E10");										// Aliquota 2	Alfa		3
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[8],2), 8, "r"));		// IVA aliquota 2	Num	9990.00	6	2
		res.append(OUTPUT_SEP);

		res.append(fill2(toImporto(preProc[9],2), 12, "r"));	// Imponibile aliquota 3	Num	9990.00	12	2
		res.append(OUTPUT_SEP);
		res.append(fill2(preProc[10],3, "l"));	// Aliquota 3	Alfa		3
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[11],2), 8, "r"));	// IVA aliquota 3	Num	9990.00	6	2
		res.append(OUTPUT_SEP);
		
		res.append(fill2(toImporto(preProc[12],2), 12, "r"));	// Imponibile aliquota 4	Num	9990.00	12	2
		res.append(OUTPUT_SEP);
		res.append(fill2(preProc[13], 3, "l"));	// Aliquota 5	Alfa		3
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[14],2), 8, "r"));	// IVA aliquota 4	Num	9990.00	6	2
		res.append(OUTPUT_SEP);

		res.append(fill2(toImporto(preProc[15],2), 12, "r"));	// Imponibile aliquota 5	Num	9990.00	12	2
		res.append(OUTPUT_SEP);
		res.append(fill2(preProc[16], 3, "l"));	// Aliquota 5	Alfa		3
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[17],2), 8, "r"));	// IVA aliquota 5	Num	9990.00	6	2
		res.append(OUTPUT_SEP);

		res.append(fill2(toImporto(preProc[18],2), 15, "r"));	// Totale imponibile	Num	9990.00	15	2	126
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[19],2), 15, "r"));	// Totale IVA	Num	9990.00	15	2	141
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[20],2), 12, "r"));	// Totale Spese Anticipate	Num	9990.00	12	2	156
		res.append(OUTPUT_SEP);
		res.append(fill2(toImporto(preProc[21],2), 15, "r"));	// Totale fattura	Num	9990.00	15	2	168
		res.append(OUTPUT_SEP);
		res.append(fill2(preProc[22], 2, "l"));		// Fattura o Nota Accredito	Alfa		2		183	FE=Fattura - NE=Nota
		
		return res.toString();
		
	}
	
	/** 
	 * versione con decimali separati da vorgola
	 * @param string
	 * @param decimali
	 * @return
	 */
	private String toImportoIT(String string, int decimali) {
		if (string.isEmpty() || string.equals("0.00"))	{
			return "0";
		} else {
			String tmp =  string.replace(".",",");
			int indexOfComma =  tmp.indexOf(",");
			if (indexOfComma<0)	{
				return tmp+",00";
			} else if (indexOfComma==tmp.length()-2)	{
				return tmp+"0";	// manca uno zero!!!!
			} else return tmp;
		}
	}
	
	/** 
	 * versione con decimali separati da punto
	 * @param string
	 * @param decimali
	 * @return
	 */
	private String toImporto(String string, int decimali) {
		if (string.isEmpty() || string.equals("0.00"))	{
			return "0";
		} else {
			String tmp =  string.replace(",","");
			int indexOfComma =  tmp.indexOf(".");
			if (indexOfComma<0)	{
				return tmp+".00";
			} else if (indexOfComma==tmp.length()-2)	{
				return tmp+"0";	// manca uno zero!!!!
			} else return tmp;
		}
	}


	/*
	 * from GG/MM/AAAAA to AAAAMMGG
	 */
	private String toAAAAMMGG(String string) {
		return string.substring(6,10)+string.substring(3,5)+string.substring(0,2);
	}

	private String fill2(String str, int num, String pos)	{
		if (str.length() == num)	{
			return str;
		} else if (str.length() > num)	{
			return str.substring(0,num);
		} else {
			String tmp = str;
			for (int i=str.length(); i<num; i++)	{
				if (pos.equals("l"))	{
					tmp = tmp+" ";
				} else {
					tmp = " "+tmp;
				}
			}
			return tmp;
		}
	}
}
