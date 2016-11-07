package eu.anastasis.tulliniHelpGest.serenabeans.tullinihelpgest;

public class MySpesaAnticipata extends SpesaAnticipata {
	
	public void init(){
		this.importo= new Float(0);
		this.oggetto="";
		this.note="";
	}
	
	public void addImporto(float val){
		this.importo+=val;
	}
	
	public void appendOggetto(String val){
		this.oggetto+=val;
	}

}
