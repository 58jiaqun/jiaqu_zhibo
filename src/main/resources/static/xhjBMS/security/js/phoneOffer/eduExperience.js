
$(document).ready(function(){


	//学历
	getHrSyscs(16,"eduId");

	
	//存数据
	$('#saveEduExperience').submit(function() {
	
		jQuery.ajax({
			url: "saveEduExperience",
			dataType: "json",
			data: $('#saveEduExperience').serialize(),
			type: "POST",
			beforeSend: function(){
				//提交验证
				return saveEduExperienceCheck();
				
			},
			success: function(result){
				if(result.data == "success") {
					
					alert("保存成功");
					window.location.href = "positionRequest";
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




function getHrSyscs(prevId, addId){
	$.ajax({
	    type :"POST",
	    dataType: "json",
	    async : false,
	    url : "hrSyscsParentJson",
	    data : {"previd" : prevId},
	    success :function(datas){
	    	$.each(datas, function (i, info) {
				$("#" + addId).append('<option value="'+info.sid+'">'+info.name+'</option>');
			});
	    }
	});
};


function  saveEduExperienceCheck(){
	//入学时间
	var entranceTime=$("#entranceTime").val().replace(/\s+/g,"");
	if(entranceTime == null || entranceTime == "") {
		alert("入学时间不能为空!");
		$("#entranceTime").focus();
		return false;
	}

	
	//毕业时间
	var graduationTime=$("#graduationTime").val().replace(/\s+/g,"");
	if(graduationTime == null || graduationTime == "") {
		alert("毕业时间不能为空!");
		$("#graduationTime").focus();
		return false;
	}
	
	//学校名字
	var schoolName=$("#schoolName").val().replace(/\s+/g,"");
	if(schoolName == null || schoolName == "") {
		alert("学校名字不能为空!");
		$("#schoolName").focus();
		return false;
	}
	
	//学历
	var eduId=$("#eduId").val().replace(/\s+/g,"");
	if(eduId == null || eduId == "") {
		alert("请选择教育程度!");
		$("#eduId").focus();
		return false;
	}
	
	
	
	//所学专业
	var major=$("#major").val().replace(/\s+/g,"");
	if(major == null || major == "") {
		alert("所学专业不能为空!");
		$("#major").focus();
		return false;
	}

	

	
	$("#subButt").attr("disabled","disabled");
	

}
