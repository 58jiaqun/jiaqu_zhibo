
$(document).ready(function(){
	
	//民族
	getHrSyscs(1,"nation");
	
	//学历
	getHrSyscs(16,"eduId");
	getHrSyscs(4,"politicalStatus");
	
	var doType=$("#doType").val();
	if(doType!=null||doType!="")
	showPersonInfo()
	
	//存数据
	$('#savePersonInfo').submit(function() {
		jQuery.ajax({
			url: "savePersonInfo",
			dataType: "json",
			data: $('#savePersonInfo').serialize(),
			type: "POST",
			beforeSend: function(){
				//提交验证
				return savePersonInfoCheck();
				
			},
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


function  savePersonInfoCheck(){
	//姓名
	var name=$("#name").val().replace(/\s+/g,"");
	if(name == null || name == "") {
		alert("姓名不能为空!");
		$("#name").focus();
		return false;
	}
	//性别
	var sex=$('input:radio[name="sex"]:checked').val().replace(/\s+/g,"");
	if(sex == null || sex == "") {
		alert("性别不能为空!");
		$("#sex").focus();
		return false;
	}
	
	//民族
	var nation=$("#nation").val().replace(/\s+/g,"");
	if(nation == null || nation == "") {
		alert("民族不能为空!");
		$("#nation").focus();
		return false;
	}
	
	//应聘职位
	var applyForJobs=$("#applyForJobs").val().replace(/\s+/g,"");
	if(applyForJobs == null || applyForJobs == "") {
		alert("申请职位不能为空!");
		$("#applyForJobs").focus();
		return false;
	}
	
	//出生年月
	var birthday=$("#birthday").val().replace(/\s+/g,"");
	if(birthday == null || birthday == "") {
		alert("出生年月不能为空!");
		$("#birthday").focus();
		return false;
	}
	
	
	
	//手机号码
	var phone=$("#phone").val().replace(/\s+/g,"");
	if(phone == null || phone == "") {
		alert("手机号码不能为空!");
		$("#phone").focus();
		return false;
	}

	
	//身份证
	var idCard=$("#idCard").val().replace(/\s+/g,"");
	if(idCard == null || idCard == "") {
		alert("身份证号码不能为空!");
		$("#idCard").focus();
		return false;
	}
	
	//证件类别是身份证校验身份证
	if(!isIdCardNo(idCard)) {
		return false;
	}
	
	
	//最高学历degree
	var eduId=$("#eduId").val().replace(/\s+/g,"");
	if(eduId == null || eduId == "") {
		alert("最高学历不能为空!");
		$("#eduId").focus();
		return false;
	}
	
	//家庭住址familyAddress
	var familyAddress=$("#familyAddress").val().replace(/\s+/g,"");
	if(familyAddress == null || familyAddress == "") {
		alert("家庭住址不能为空!");
		$("#familyAddress").focus();
		return false;
	}
	
	
	//现在住址familyAddress
	var nowAddress=$("#nowAddress").val().replace(/\s+/g,"");
	if(nowAddress == null || nowAddress == "") {
		alert("现在住址不能为空!");
		$("#nowAddress").focus();
		return false;
	}
	
	
	 $("#subButt").attr("disabled","disabled");
}

function isIdCardNo(num){
	num = num.toUpperCase();
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
    {
        alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
        return false;
    }
    //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
    //下面分别分析出生日期和校验位
    var len, re;
    len = num.length;
    if (len == 15)
    {
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var arrSplit = num.match(re);
 
        //检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay)
        {
            alert('输入的身份证号里出生日期不对！');
            return false;
        }
        else
        {
                //将15位身份证转成18位
                //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
                var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
                var nTemp = 0, i;
                num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
                for(i = 0; i < 17; i ++)
                {
                    nTemp += num.substr(i, 1) * arrInt[i];
                }
                num += arrCh[nTemp % 11];
                return true;
        }
    }
    if (len == 18)
    {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
        var arrSplit = num.match(re);
 
        //检查生日日期是否正确
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
        var bGoodDay;
        bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay)
        {
            alert('输入的身份证号里出生日期不对！');
            return false;
        }
    else
    {
        //检验18位身份证的校验码是否正确。
        //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
        var valnum;
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        var nTemp = 0, i;
        for(i = 0; i < 17; i ++)
        {
            nTemp += num.substr(i, 1) * arrInt[i];
        }
        valnum = arrCh[nTemp % 11];
        if (valnum != num.substr(17, 1))
        {
            alert('18位身份证的校验码不合法,请确认！' );
            return false;
        }
        return true;
    }
    }
    return false;
 }

function setBirthDay(idNo){
var newIdNo = $("#idCard").val();
var flag = isIdCardNo(newIdNo);
if(idNo != newIdNo && flag) {
	var time = newIdNo.substr(6,8);
	var year = time.substr(0,4);
	var moth = time.substr(4,2);
	var day = time.substr(6);
	$("#birthday").val(year + "-" + moth + "-" + day);
}

}



