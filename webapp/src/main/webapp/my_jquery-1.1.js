$(document).ready(function(){
	$.ajax({url: "rest/service/version", success: function(result){
      $("#div1").html(result);
    }});
	
	
	setTimeout(function(){
		location.reload();
		}, 60000);
});