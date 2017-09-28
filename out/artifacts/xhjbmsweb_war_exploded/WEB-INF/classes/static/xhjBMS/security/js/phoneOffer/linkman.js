function  addLinkman(){
	
	var lengths=$("#allLinkMan>div").length;
	
	if(lengths>=3){	
		alert("填写数组最多不能超过三组");	
		return false;
	}else{
	$("#allLinkMan").append($("#linkManItemsTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,""));
	}

}


function reduceLinkman(){
	
	var lengths=$("#allLinkMan>div").length;
	if(lengths<=1){
		
		alert("请至少填写一组数据");	
		return false;
		
	}else{
	if(confirm("确认是否要删除"))
	$("#allLinkMan>div").eq(lengths-1).remove();
	}
	
}




function  storelinkman(){
	   var  arr=new Array();
	   var  storeType=$("#storeType").val();
	   var  doType=$("#doType").val();
	   var passed = true;
	    $("#allLinkMan>div").each(function(i,n){
			if($(n).find("input[name='name']").val()==""){
			alert("姓名不能为空 请填写第"+(i+1)+"组数据 姓名");
			   passed = false;
			   return false;
			}
			
			else if($(n).find("input[name='relation']").val()==""){
				alert("关系不能为空 请填写第"+(i+1)+"组数据 关系");
				passed=  false;
				 return false;
		    }
			
			else if($(n).find("input[name='phone']").val()==""){
				alert("联系电话不能为空 请填写第"+(i+1)+"组数据 联系电话");
				passed=  false;
				 return false;
		    }
			 
			var linkmans=new  linkman($(n).find("input[name='linkmanId']").val(),$(n).find("input[name='name']").val(),$(n).find("input[name='relation']").val(),$(n).find("input[name='profession']").val(),$(n).find("input[name='companyName']").val(),$(n).find("input[name='phone']").val(),$("#resumeId").val());
			arr.push(linkmans);
		 // alert($(this).find("div").find("input[type='text']").val());	
		});
	   if(!passed)
	   return passed;
	 
	   $("#subButt").attr("disabled","disabled");
	 $.ajax({
         type :"POST",
         dataType: "json",
         url : "storeLinkman",
         data : {
            "jsonData":JSON.stringify(arr),
            "storeType":storeType,
            "doType":doType,
               	},
           success :function(datas){   
        	 if(datas.data=="success"){
        		 
        		 alert("保存成功");
        		 $("#subButt").removeAttr("disabled");
        		 window.location.href = "positionRequest";
        	 } else{

        		 alert("保存失败");
        		 $("#subButt").removeAttr("disabled");
        	 }
        	   
          }
     });
	 
}


function linkman(id,name,relation,profession,companyName,phone,resumeId){
	this.id=id;
	this.name=name;
	this.relation=relation;
	this.profession=profession;
	this.companyName=companyName;
	this.phone=phone;
	this.resumeId=resumeId;

	
}



