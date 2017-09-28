function addOrgSelected(){
	//var nodes = Ext.getCmp('tree').getSelectionModel().getSelectedNode();
	var nodes = $.fn.zTree.getZTreeObj("orgDptTree").getSelectedNodes();
	if(nodes != null){//部门
		/*if(nodes.attributes.id != 0) {
			var options = $("#deptLists option");
	    	if(!isListContains(options,'D'+nodes.attributes.id)){
				  $("#deptLists").append("<option value='D"+nodes.attributes.id+"' title='"+nodes.text+"' style='color:blue;font-weight:bold;'>"+nodes.text+"</option>");　　
			};
		}*/
		if(nodes.id != 0) {
			var options = $("#deptLists option");
			for(var i=0;i<nodes.length;i++){
				if(!isListContains(options,'D'+nodes[i].id)){
	    			$("#deptLists").append("<option value='D"+nodes[i].id+"' title='"+nodes[i].text+"' style='color:blue;font-weight:bold;'>"+nodes[i].text+"</option>");
	    		}
			}
		}
	}
};
function isListContains(options,value){
	var result = false;
	options.each(function(){		
		if($(this).val()==value){
			result = true;
			return false;
		}
	});
	return result;
};
function removeOrgSelected(){
　　$("#deptLists option:selected").each(function(){
　　　　$(this).remove();　
　　});
};
function addDeptInfo(){
	var idstr = '';
	var nameStr = '';
	var length = $("#deptLists option").size();
	$("#deptLists option").each(function(){
		idstr+=$(this).val()+",";  
		nameStr += $(this).attr('title')+";";
	});
	if(idstr==''){
		alert('请选择部门到列表！');
		return;
	}
	$("#trackstr").val(idstr.substr(idstr,idstr.length-1));
	$("#nameStr").val(nameStr.substr(nameStr,nameStr.length-1));
	$("#winclose").click();
};
