var  i=80;


function  addWorkExperience(){
	i=i+1;
	var lengths=$("#allWorkExperience>div").length;
	
	if(lengths>=3){	
		alert("工作经验请选择最近的三个填写");	
		return false;
	}else{
	var myDate = new Date();
	nowYear=myDate.getFullYear();
	
	
	html='	<div class="mui-input-group">'
	html+=		'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=				'<label>入职时间：</label>'
	html+=		'	<button id-name="'+"entryTime"+i
	html+=		'"data-options={"type":"date","beginYear":1900,"endYear":'+nowYear+'}'
	html+=		' class="btn mui-btn mui-btn-block"选择日期 ...</button>'
	html+=			'	<input id="'+"entryTime"+i
	html+=			'" name="entryTime" class="ui-alert" placeholder="请选择入职时间" />'
	html+=			'</div>'
	html+=			'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=			'<label>离职时间：</label>'
	html+=			'	<button id-name="'+"leaveTime"+i
	html += 		'"data-options={"type":"date","beginYear":1900,"endYear":'+nowYear+'}'
	html+=	        ' class="btn mui-btn mui-btn-block"选择日期 ...</button>'
	html+=			'<input id="'+"leaveTime"+i
	html+=          '"name="leaveTime"  class="ui-alert" placeholder="请选择离职时间"/>'
	html+=			'</div>'
	html+=			'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=			'	<label>公司名称：</label>'
	html+=			'	<input type="text" class="mui-input-clear" name="companyName" placeholder="请填写公司" >'
	html+=			'</div>'
	html+=			'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=			'<label>公司职位：</label>'
	html+=			'<input type="text" class="mui-input-clear" name="position" placeholder="请填写职位" >'
	html+=			'</div>'
	html+=			'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=			'	<label>公司地址：</label>'
	html+=			'<input type="text" class="mui-input-clear" name="companyAddress" placeholder="请填写地址" >'
	html+=			'</div>'
	html+=		'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=			'<label>个人薪资：</label>'
	html+=			'<input type="text" class="mui-input-clear" name="salary" placeholder="请填写薪资" >'
	html+=		'</div>'
	html+=		'<div class="mui-input-row tw-input-row marg-box xd-box">'
	html+=		'<label>证明人：</label>'
	html+=		'<input type="text" class="mui-input-clear" name="provePerson" placeholder="请填写证明人" >'
	html+=	'</div>'
	html+='</div>';

	$("#allWorkExperience").append(html);
	

	}
	
}


function reduceWorkExperience(){
	
	var lengths=$("#allWorkExperience>div").length;
	if(lengths<=1){
		
		alert("请至少填写一个");	
		return false;
		
	}else{
	if(confirm("确认是否要删除"))
	$("#allWorkExperience>div").eq(lengths-1).remove();
	
	}
}




function storeWorkExperience(){
	$("#subButt").attr("disabled","disabled");
	var  arr=new Array();
	var  doType=$("#doType").val();
	    $("#allWorkExperience>div").each(function(i,n){
			
	    	var entryTime=$(n).find("input[name='entryTime']").val();
	    	var leaveTime=$(n).find("input[name='leaveTime']").val();
	    	var companyName=$(n).find("input[name='companyName']").val();
	    	var position=$(n).find("input[name='position']").val();
	    	var companyAddress=$(n).find("input[name='companyAddress']").val();
	    	var salary=$(n).find("input[name='salary']").val();
	    	var provePerson=$(n).find("input[name='provePerson']").val();
	    	var resumeId=$("#resumeId").val();
	    	
	    	
	    	
			var workExperiences=new  workExperience(entryTime,leaveTime,companyName,position,companyAddress,salary,provePerson,resumeId);
			arr.push(workExperiences);
		 // alert($(this).find("div").find("input[type='text']").val());	
		});
	  
   
	 $.ajax({
      type :"POST",
      dataType: "json",
      url : "storeWorkExperience",
      data : {
         "jsonData":JSON.stringify(arr),
         "doType":doType,
            	},
        success :function(datas){   
     	 if(datas.data=="success"){
     		 alert("保存成功");
     		 window.location.href = "positionRequest";
     		$("#subButt").removeAttr("disabled");
     	 } else{

     		 alert("保存失败");
     		$("#subButt").removeAttr("disabled");
     	 }
     	   
       }
  });
	 
}



function   workExperience(entryTime,leaveTime,companyName,position,companyAddress,salary,provePerson,resumeId){
	
	this.entryTime=entryTime;
	this.leaveTime=leaveTime;
	this.companyName=companyName;
	this.position=position;
	this.companyAddress=companyAddress;
	this.salary=salary;
	this.provePerson=provePerson;	
	this.resumeId=resumeId;

	
}



	
	
	
	





