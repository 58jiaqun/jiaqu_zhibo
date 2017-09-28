
$(document).ready(function(){


	//存数据
	$('#saveSocialRelation').submit(function() {
		 $("#subButt").attr("disabled","disabled");
		jQuery.ajax({
			url: "saveSocialRelation",
			dataType: "json",
			data: $('#saveSocialRelation').serialize(),
			type: "POST",
//			beforeSend: function(){
//				//提交验证
//				return savePersonInfoCheck();
//			},
			success: function(result){
				if(result.data == "success") {
					
					alert("保存成功");
					window.location.href = "positionRequest?resumeId="+result.resumeId;
					$("#subButt").removeAttr("disabled");
				} else {
					alert("保存失败" + result.data);
					$("#subButt").removeAttr("disabled");
					return false;
				}
			}
		});
		return false;
	});
});





