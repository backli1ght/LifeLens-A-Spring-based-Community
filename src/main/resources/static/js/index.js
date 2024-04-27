$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	// Get the title and content of the post
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	// Send the post request
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title":title, "content":content},
		function(data){
			data = $.parseJSON(data);
			// Show the hint message
			$("#hintBody").text(data.msg);
			// Show the hint modal
			$("#hintModal").modal("show");
			// Hide the hint modal after 2 seconds
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// Refresh the page
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	)

	$("#hintModal").modal("show");
	setTimeout(function(){
		$("#hintModal").modal("hide");
	}, 2000);
}