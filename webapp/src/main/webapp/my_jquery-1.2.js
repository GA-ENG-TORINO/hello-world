$(document).ready(function(){
	$.ajax({url: "rest/service/version", success: function(result){
      $("#div1").html(result);
    }});
	
	
	setTimeout(function(){
		if(window.location.pathname.indexOf("/riavvio.html") >= 0)
			window.location.href = window.location.origin
		else
			location.reload();
		}, 60000);
});