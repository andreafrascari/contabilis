function workoutCliente()	{
	var theURl = "ModuleIndex?q=ticket/clientFromContactInfo";
	var thePhone = $("#INP_numero_remoto").val();
	if (thePhone)	{
		$.ajax({
			type: "GET",
			url: theURl,
			data: {info: thePhone},
	        async: false,        
	        cache: false,
	        dataType: "json",
	        success: function(data){ console.log("SUCCESS "+data); $("#cliente-riconosciuto").show(); $("#nonRiconosciuto").hide(); $("#nome-cliente-riconosciuto").text(data.cliente); $("#INP_daCliente").val(data.id);},
	        error: function(data){ $("#cliente-riconosciuto").hide(); $("#nonRiconosciuto").show(); },
	        });
	}
}

function workoutChiamata()	{
	var theURl = "ModuleIndex?q=ticket/ultimaTelefonata";
	$.ajax({
		type: "GET",
		url: theURl,
		data: {},
        async: false,        
        cache: false,
        dataType: "json",
        success: function(data){ $("#INP_numero_remoto").val(data.numero_remoto);  $("#INP_numero_locale").val(data.numero_locale); workoutCliente();},
        error: function(data){ },
        });
}


function prenotaTassametro(ticket)	{
					var theURl="ModuleIndex?q=helpgest/activitycache/ticket="+ticket;
					var rep = $.ajax({
						type: "GET",
						url: theURl,
				        async: false,        
				        cache: false}).responseText;		
					try
					{
						var xmlrep = Anastasis.Utils.parseXML(rep);
						anastasis.logger.info("res = " + rep);
						var msg = Anastasis.XMessage.buildXMessage(xmlrep);
						if(msg.isErrorMessage())
						{
							$("#tassametro-no").show();
							$("#tassametro-no").text("Il sistema non riesce a prenotare il tassametro: il ticket e' assegnato ad una pratica?");
						} else {
				        	var mess=msg.selectSingleNode("result/message");
							$("#tassametro-yes").show();
							$("#tassametro-yes").text(Anastasis.Ajax.getText(mess));
						}
					}
						catch(e){alert("errore: "+e);}
				}
			

function deleteTicket(id)	{
			if (!confirm("Confermare eliminazione ticket:"))	{
				return;
			}
			var theURl = "ModuleIndex?q=ticket/ticketAjax";
			$.ajax({
				type: "GET",
				url: theURl,
				data: {action: 'deleteTicket', id: id},
		        async: false,        
		        cache: false,
		        dataType: "json",
		        success: function(data){ console.log("SUCCESS "+data); /*alert("Ticket cancellato!");*/ location.reload();},
		        error: function(data){  alert("Errore in cancellazione ticket");},
		        });
	}

function openTicket(id)	{
	var theURl = "ModuleIndex?q=ticket/ticketAjax";
	$.ajax({
		type: "GET",
		url: theURl,
		data: {action: 'openTicket', id: id},
        async: false,        
        cache: false,
        dataType: "json",
        success: function(data){ console.log("SUCCESS "+data); /*alert("Ticket aperto!");*/ location.reload();},
        error: function(data){  alert("Errore in apertura ticket");},
        });
}

function demoteTicket(id)	{
	var theURl = "ModuleIndex?q=ticket/ticketAjax";
	$.ajax({
		type: "GET",
		url: theURl,
		data: {action: 'demoteTicket', id: id},
        async: false,        
        cache: false,
        dataType: "json",
        success: function(data){ console.log("SUCCESS "+data); /*alert("Ticket con priorita normale!");*/ },
        error: function(data){  alert("Errore in set priorita normale ticket");},
        });
}
	
	