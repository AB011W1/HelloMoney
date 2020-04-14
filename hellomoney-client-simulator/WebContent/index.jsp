<%@ page contentType="text/html" language="java" %>


<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<head>
<title>USSD CLIENT</title>
<script src="js/jquery-1.4.4.min.js"></script>
<script language="javaScript">

<%
	StringBuffer requestURL = request.getRequestURL();
	String context = request.getContextPath().toString();

	int strIndex = requestURL.indexOf(context);
	String contextPath = requestURL.substring(0, strIndex + context.length());
%>

    var simulatorURL = "<%= contextPath %>/hellomoneyclient";
    var environment_list=[];

	var ussd_hosts = [
	             ["local","localhost"                       , "http" , "8080" , "hellomoney" , "ALL"],
	             ["PCT"  ,"widd.wload.global"               , "https", "52288", "hellomoney" , "ALL"],
	             ["SIT"  ,"cs8t.wload.barclays.co.uk"       , "https", "45032", "hellomoney", "ALL"],
	             ["NFT"  ,"ubhm-tznft.barclays.intranet"    , "https", ""     , "hellomoney" , "TZBRB"],
	             ["NFT"  ,"ubhm-ugnft.barclays.intranet"    , "https", ""     , "hellomoney" , "UGBRB"],
	             ["NFT"  ,"ubhm-bwnft.barclays.intranet"    , "https", ""     , "hellomoney" , "BWBRB"],
	             ["NFT"  ,"ubhm-zmnft.barclays.intranet"    , "https", ""     , "hellomoney" , "ZMBRB"],
	             ["NFT"  ,"ubhm-zwnft.barclays.intranet"    , "https", ""     , "hellomoney" , "ZWBRB"],
	             ["NFT"  ,"ubhm-ghnft.barclays.intranet"    , "https", ""     , "hellomoney" , "GHBRB"],
	             ["NFT"  ,"ubhm-tznbcnft.barclays.intranet" , "https", ""     , "hellomoney" , "TZNBCBRB"],
	             ["NFT"  ,"ubhm-mznft.barclays.intranet"    , "https", ""     , "hellomoney" , "MZBRB"],
	             ["NFT"  ,"ubhm-kenft.barclays.intranet"    , "https", ""     , "hellomoney" , "KEBRB"],
		         ["PROD" ,"ubhm-tz-c2live.barclays.intranet", "https", ""     , "hellomoney" , "TZBRB"],
	             ["PROD" ,"ubhm-ug-c2live.barclays.intranet", "https", ""     , "hellomoney" , "UGBRB"]
	             ];

	function onLoad() {
		var len = ussd_hosts.length;
		var newlist = "";
		for(var i = 0; i < len; i++) {
        	var tempHost = ussd_hosts[i];
            var environ = {
				env 		: tempHost[0],
			    url		  	: tempHost[1],
			    protocol    : tempHost[2],
			    port  		: tempHost[3],
			    context		: tempHost[4],
			    business	: tempHost[5]
			};
            environment_list[i] = environ;
		}
		populateFields();
	}

	function populateFields() {
		var len = environment_list.length;
		var envList=[];
		var urlList=[];
		var protocolList=[];
		var portList=[];
		var contextList=[];
		var businessList=[];
		for(var i = 0; i < len; i++) {
			envList[i]=environment_list[i].env;
			urlList[i]=environment_list[i].url;
			protocolList[i]=environment_list[i].protocol;
			portList[i]=environment_list[i].port;
			contextList[i]=environment_list[i].context;
			businessList[i]=environment_list[i].business;
		}
		populateHostDropDown(envList);

	}

	function populateHostDropDown(envList){
		var uniqueEnvList = envList.unique();
		var len = uniqueEnvList.length;
		for(var i = 0; i < len; i++) {
			document.getElementById('env').options[i+1]= new Option(uniqueEnvList[i], uniqueEnvList[i]);
		}
	}

	Array.prototype.contains = function(v) {
	    for(var i = 0; i < this.length; i++) {
	        if(this[i] === v) return true;
	    }
	    return false;
	};

	Array.prototype.unique = function() {
	    var arr = [];
	    for(var i = 0; i < this.length; i++) {
	        if(!arr.contains(this[i])) {
	            arr.push(this[i]);
	        }
	    }
	    return arr;
	}
	window.onload=onLoad;

    function updateHost(hostName){
    	var j =0;
    	var selectedEnv = hostName.value;
    	var selectedBusiness =document.getElementById('bsnssId').value;
		var inputHost = hostName.value;
        document.getElementById('domain').value="";
        document.getElementById('port').value="";
        document.getElementById('protocol').value="";
        document.getElementById('context').value="";

        for(j =0; j< environment_list.length; j++){
        	if(environment_list[j].env==selectedEnv && environment_list[j].business==selectedBusiness){
        		break;
        	}
        }
        //if not found
        if(environment_list.length==j){
        	for(j =0; j< environment_list.length; j++){
            	if(environment_list[j].env==selectedEnv && environment_list[j].business=="ALL"){
            		break;
            	}
            }
        }

        if(environment_list.length==j){
        	document.getElementById('domain').value="Not Available!"
    	    //document.getElementById('protocol').value="Not Available!"
			document.getElementById('port').value="Not Available!"
        	document.getElementById('context').value="Not Available!"
        }else{
        	var selectedEnv= environment_list[j];
			document.getElementById('domain').value=environment_list[j].url;
    	    document.getElementById('protocol').value=environment_list[j].protocol;
			document.getElementById('port').value=environment_list[j].port;
        	document.getElementById('context').value=environment_list[j].context;
        }
	}

    function updateDomain(){
    	var j =0;
    	var selectedEnv = document.getElementById('env').value;;
    	var selectedBusiness =document.getElementById('bsnssId').value;

    	for(j =0; j< environment_list.length; j++){
        	if(environment_list[j].env==selectedEnv && environment_list[j].business==selectedBusiness){
        		document.getElementById('domain').value=environment_list[j].url;
        		document.getElementById('port').value=environment_list[j].port;
        		document.getElementById('context').value=environment_list[j].context;
        		break;
        	}
        }
    	//if not found
    	if(environment_list.length==j){
        	for(j =0; j< environment_list.length; j++){
            	if(environment_list[j].env==selectedEnv && environment_list[j].business=="ALL"){
            		document.getElementById('domain').value=environment_list[j].url;
            		document.getElementById('port').value=environment_list[j].port;
            		document.getElementById('context').value=environment_list[j].context;
            		break;
            	}
            }
        }

        if(environment_list.length==j){
        	document.getElementById('domain').value="Not Available!";
    		document.getElementById('port').value="Not Available!";
    		document.getElementById('context').value="Not Available!";
        }

    }

		function populatePhone(buttonValue)	{
			if(document.getElementById('responseFlag').value =="1")
			{
				document.getElementById('phoneInput').value="";
				document.getElementById('responseFlag').value ="0";
			}
			var currentValue= document.getElementById('phoneInput').value;
			document.getElementById('phoneInput').value= currentValue+buttonValue;
		}

		function disconnect()		{
			document.getElementById('phoneInput').value="";
			enableKeyPad();
			msisdnChange();
		}

		function trim(obj){
			return obj.replace(/^\s+|\s+$/g, '');
		}

		function isNull(obj){
			if(obj == null) {
				return true;
			}
			return false;
		}

		function postRequest(inputMode)
		{
			//alert("createURL");

			//var nonce = getCookie("jsessionid");
			//alert("nonce="+nonce);

			var protocol = document.getElementById('protocol').value;
			var domain = document.getElementById('domain').value;
			var port = document.getElementById('port').value;
			var context = document.getElementById('context').value;
			//var path = document.getElementById('path').value;
			var e = document.getElementById("mobNo");
			var msisdn = e.options[e.selectedIndex].value;
			if(document.getElementById('msisdn').value!=""){
				msisdn = document.getElementById('msisdn').value;
			}

			var input = document.getElementById('phoneInput').value;
			var templevel= document.getElementById('templevel').value;
			var currentlevel= document.getElementById('currentlevel').value;
			var extra= document.getElementById('extra').value;

			var e = document.getElementById("bsnssId");
			var businessId = e.options[e.selectedIndex].value;

			var aggregator = document.getElementById("aggregator").value;

			var jSessionId = document.getElementById('SESSIONID').value;
			var nonceValue = document.getElementById('NV').value;

            if(domain == "") {
                alert("Please select the USSD Browser application host.");
                return;
            }
			//alert("phoneInput="+ document.getElementById('phoneInput').value);

			if(trim(document.getElementById('phoneInput').value) == "*150*20*1#") {
				input=null;
			} else if(trim(document.getElementById('phoneInput').value) == "*150*20*2#") {
				input=null;
				context="hellomoney";
			}
			else if (inputMode == "manual") {
				input=document.getElementById('tempInputURL').value;
			} else	{
				input=document.getElementById('phoneInput').value;
			}

			if(isNull(templevel)) {
				templevel="1";
			}
			if(isNull(currentlevel)) {
				currentlevel="200";
			}
			if(isNull(extra)) {
				extra="null";
			}
			if(isNull(input)) {
				input="null";
			}
			try {
				//alert("tempInputURL="+ document.getElementById('tempInputURL').value);
				//alert("manualInput="+ document.getElementById('manualInput').value);
				//

					//if(isNull(document.getElementById('tempInputURL').value)) {
					//	input=document.getElementById('tempInputURL').value;
					//	document.getElementById('tempInputURL').value ="";
					//}
                    //alert ("aggregator="+aggregator);

					document.getElementById('xmlResponseText').value= "";

                    var targetURL = protocol + "://"+ domain + (port==""?"":":" + port) + "/" +context+"/"+  aggregator;
                    //alert ("targetURL="+targetURL);
					var displayTargetURL = targetURL+"&MSISDN=" + msisdn + "&INPUT=" + encodeURIComponent(input) + "&TEMPLEVEL=" + templevel + "&CURRENTLEVEL=" + currentlevel + "&EXTRA=" + extra + "&BUSINESS=" + businessId;
					displayTargetURL = displayTargetURL + "&SESSIONID=" + jSessionId+ "&NV=" + nonceValue;
					var action="";
					if(input == "null"){
						action = 'LOGIN';
					}
					var TEST="<REQUEST><HEADER><BUSINESSID>"+ businessId+"</BUSINESSID><MNO>"+aggregator+"</MNO><MSISDN>"+msisdn+"</MSISDN><IMEI></IMEI><IMSI></IMSI><SESSIONID>"+jSessionId+"</SESSIONID><NONCE>" + nonceValue + "</NONCE></HEADER><BODY><ACTION>"+action+"</ACTION><USERINPUT>"+encodeURIComponent(input)+"</USERINPUT><EXTRA></EXTRA></BODY></REQUEST>";
					document.getElementById('xmlRequestText').value = TEST;

					var requestURL = simulatorURL + "?targetURL="+encodeURIComponent(targetURL)+"&MSISDN=" + msisdn + "&INPUT=" + encodeURIComponent(input) + "&TEMPLEVEL=" + templevel + "&CURRENTLEVEL=" + currentlevel + "&EXTRA=" + extra + "&BUSINESS=" + businessId;
					requestURL = requestURL + "&SESSIONID=" + jSessionId+ "&NV=" + nonceValue + "&formMethod=XML-POST"; //+'&TEST='+TEST;
					//alert("requestURL =[" + requestURL + "]");
					document.getElementById("targetURL").value = targetURL;
					//document.getElementById('inputURL').value="";
					//document.getElementById('inputURL').value = displayTargetURL;

					//alert("getResponse");
					responseData = getResponse(requestURL);

					//alert("RESPONSE =" + responseData);
					// window.open(url,"outputPage");
					//document.getElementById('phoneInput').value=responseData;
			} catch (e) {
                alert("Error - " + e);
            }

		}

		function getCookie(c_name) {
			var c_value = document.cookie;
		        //alert(c_value);
			var c_start = c_value.indexOf(" " + c_name + "=");
			if (c_start == -1) {
			  c_start = c_value.indexOf(c_name + "=");
			 }
			if (c_start == -1) {
			  c_value = null;
			}
			else {
			  c_start = c_value.indexOf("=", c_start) + 1;
			  var c_end = c_value.indexOf(";", c_start);
			  if (c_end == -1)  {
				c_end = c_value.length;
			  }
			  c_value = unescape(c_value.substring(c_start,c_end));
			}
    			return c_value;
		}

		function disableKeyPad() {
			document.getElementById('buttonKeyPad1').disabled=true;
			document.getElementById('buttonKeyPad2').disabled=true;
			document.getElementById('buttonKeyPad3').disabled=true;
			document.getElementById('buttonKeyPad4').disabled=true;
			document.getElementById('buttonKeyPad5').disabled=true;
			document.getElementById('buttonKeyPad6').disabled=true;
			document.getElementById('buttonKeyPad7').disabled=true;
			document.getElementById('buttonKeyPad8').disabled=true;
			document.getElementById('buttonKeyPad9').disabled=true;
			document.getElementById('buttonKeyPad0').disabled=true;
			document.getElementById('buttonKeyPadStar').disabled=true;
			document.getElementById('buttonKeyPadHash').disabled=true;



		}

		function enableKeyPad()	{
			document.getElementById('buttonKeyPad1').disabled=false;
			document.getElementById('buttonKeyPad2').disabled=false;
			document.getElementById('buttonKeyPad3').disabled=false;
			document.getElementById('buttonKeyPad4').disabled=false;
			document.getElementById('buttonKeyPad5').disabled=false;
			document.getElementById('buttonKeyPad6').disabled=false;
			document.getElementById('buttonKeyPad7').disabled=false;
			document.getElementById('buttonKeyPad8').disabled=false;
			document.getElementById('buttonKeyPad9').disabled=false;
			document.getElementById('buttonKeyPad0').disabled=false;
			document.getElementById('buttonKeyPadStar').disabled=false;
			document.getElementById('buttonKeyPadHash').disabled=false;

		}


		  var tzPctMobNo=[["255000184486","4914"],["255000162880","4912"]];
	        var tzSitMobNo=[["255659800308","4912"],["255789783842","4912"],["255763210278","4912"]];
	        //var ugMobNo=[["256782060209","4912"],["256703834644","4915"],["256757596771","4912"],["256752779552","4912"], ["256776655443","4912"]];
	        var ugMobNo=[["256714020103","4912"],["256753354981","4915"],["256714020125","0125"]];
			var keMobNo=[["745123457","3457"],["745123458","3458"]];
			var ghMobNo=[["233121117534","4912"]];
	        function updateMobNo() {
	        	cleanMobNo();
	        	var mobNo = document.getElementById("mobNo");
	            var mobileNumbers = tzPctMobNo;
	            var e = document.getElementById("bsnssId");
	            var bznzID = e.options[e.selectedIndex].value;
	            if(bznzID=='UGBRB'){
	            	mobileNumbers = ugMobNo;
	            }else if(bznzID=='TZBRB'){
	                var domainVal = document.getElementById('domain').value;
	                if(domainVal!='BDSPUKD00003809' && domainVal!='widd.wload.global'){
	                	mobileNumbers = tzSitMobNo;
	                }else{
	                	mobileNumbers = tzPctMobNo;
	                }
	            }else if(bznzID=='KEBRB'){
	            	mobileNumbers = keMobNo;
	            }
	            else if(bznzID=='GHBRB'){
	            	mobileNumbers = ghMobNo;
	            }
	            var len = mobileNumbers.length;
		   		for(var i = 0; i < len; i++) {
	                 var tempmobNo = mobileNumbers[i];
	                 var text="PIN: "+tempmobNo[1];
	                 (mobNo.options[mobNo.options.length] = new Option(tempmobNo[0], tempmobNo[0])).setAttribute("title",text);
	    	 	 }
	    	}

			function cleanMobNo(){
		        var mobOptions=document.getElementById("mobNo");
		        var mobOptionsLength = mobOptions.options.length -1;
		        for( var ind=mobOptionsLength;ind>=0;ind--){
		        	mobOptions.remove(ind);
		        }
			}


			function updatePort()
			{
				var e = document.getElementById("env");
	            var host = e.options[e.selectedIndex].innerHTML;
	            var businessId = document.getElementById("bsnssId");
	            var busId = businessId.options[businessId.selectedIndex].value;

				if(host =='local')
				{
		      			document.getElementById("port").value="8080";
				}
			}

			function updateAgg(bznsId){
	            var businessIDSelected = bznsId.value;
	            var agg = document.getElementById("aggregator");
	            var aggregatorLen = agg.length;
	            if(businessIDSelected == 'UGBRB'){
	               for(var i = 0; i < aggregatorLen; i++) {
	         	   if(agg.options[i].value=='trueafrican'){
	         		   agg.options[i].selected='selected';
	         		   break;
	         	   }
	            }
	            }else if(businessIDSelected == 'TZBRB'){
	               for(var tzI = 0; tzI < aggregatorLen; tzI++) {
	             	   if(agg.options[tzI].value=='selcom'){
	             		   agg.options[tzI].selected='selected';
	             		  break;
	             	   }
	                }
	        	}else if(businessIDSelected == 'KEBRB' || businessIDSelected == 'GHBRB'|| businessIDSelected == 'ZWBRB'|| businessIDSelected == 'ZMBRB'|| businessIDSelected == 'BWBRB'){
	        		   for(var keI = 0; keI < aggregatorLen; keI++) {
		             	   if(agg.options[keI].value=='cellulant'){
		             		   agg.options[keI].selected='selected';
		             		  break;
		             	   }
		                }
		        }
	        }

			function updateCountry(){
	        	//cleanMobNo();

	            var e = document.getElementById("env");
	            var host = e.options[e.selectedIndex].innerHTML;
	            var agg = document.getElementById("aggregator");
	            var businessId = document.getElementById("bsnssId");
				if(host =='SITTZ')
				{
		            for(var i = 0; i < businessId.length; i++) {
		            	if(businessId.options[i].value=='TZBRB'){
		            		businessId.options[i].selected='selected';
		            		break;
			      		}
		            }
		            for(i = 0; i < agg.length; i++) {
		            	if(agg.options[i].value=='selcom'){
		            		agg.options[i].selected='selected';
	         		   		break;
			      		}
		            }
				}else if(host =='SITUG')
				{
		            for(var j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='UGBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='trueafrican'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				else if(host =='SITKE')
				{
		            for(j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='KEBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='Cellulant'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				else if(host =='SITGH')
				{
		            for(j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='GHBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='Cellulant'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				else if(host =='SITZW')
				{
		            for(j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='ZWBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='Cellulant'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				else if(host =='SITZM')
				{
		            for(j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='ZMBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='Cellulant'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				else if(host =='SITBW')
				{
		            for(j = 0; j < businessId.length; j++) {
		            	if(businessId.options[j].value=='BWBRB'){
		            		businessId.options[j].selected='selected';
		            		break;
			      		}
		            }
		            for(j = 0; j < agg.length; j++) {
		            	if(agg.options[j].value=='Cellulant'){
		            		agg.options[j].selected='selected';
	         		   		break;
			      		}
		            }
				}
				updateAgg(businessId);
	    	}

		function getResponse(serverUrl)	{
			var retVal;
			$().ready(function(){
				$.get(serverUrl,function(data){
		            var responseDataParts = data.split("|");
					var totalLength = data.length;
					var sessionIdLength = responseDataParts[6].length;
					var nonceLength = responseDataParts[7].length;
					var lenToDec = totalLength- sessionIdLength - nonceLength - 2;
					var displayText = data.substring(0,  lenToDec);
					document.getElementById('phoneInput').value = (responseDataParts[1]).replace(/\\n/g,"\r\n");
					document.getElementById('responseFlag').value="1";
					document.getElementById('templevel').value = responseDataParts[0];
					//alert('after templevel');
					retVal = document.getElementById('phoneInput').value;
					document.getElementById('respLen').value= (document.getElementById('phoneInput').value).length;
					if(responseDataParts[4] == 'end'){
						// alert("status is end!!!!!");
						disableKeyPad();
					}if(document.getElementById('respLen').value > 180){
						document.getElementById('phoneInput').style.backgroundColor="red";
					}else{
						document.getElementById('phoneInput').style.backgroundColor="lightGray";
					}

					document.getElementById('SESSIONID').value=responseDataParts[6];
					document.getElementById('NV').value=responseDataParts[7];

					if (responseDataParts.length >= 8) {
						document.getElementById('xmlResponseText').value = responseDataParts[8];
					}



				});
			});

			return retVal;
		}
		function msisdnChange()
		{
			document.getElementById('phoneInput').value="*150*20*1#";
			var mn = document.getElementById("mobNo");
			console.log("mobile number " + mn);
			var msisdnPIN = mn.options[mn.selectedIndex].title;
			document.getElementById('tempInputURL').value = msisdnPIN.split(": ")[1];

		}


		function clearPhIn(){
			var screenText =document.getElementById('phoneInput').value;
			var len = screenText.length;
			document.getElementById('phoneInput').value=screenText.substr(0,len);

		}


/*function getResponse(targetUrl){
	var remote;
    $.ajax({
        type: "GET",
        url: targetUrl,
        async: false,
        success : function(data) {
            remote = data;
            alert("Remote Data"+ data);
        }
    });
    return remote;
    */

</script>

<style type="text/css">
body {
	font-family: verdana, arial, sans-serif;
	font-size: 9pt;
	margin: 10px;
}

td {
	text-align: center;
}

input.keys {
	width: 50px !important;
	background-color: silver;
}

input.callkeys {
	width: 80px !important;
}

input.callkeysManual {
	width: 80px !important;
	style: "visibility:hidden;"
}
</style>
</head>

<body bgcolor="#F5F5DC">
<center>
<h2>Strategic Hello Money - Mobile Simulator</h2>
</center>
<form>
<table bgcolor="lightgray" width="100%">
    <tr>
        <table width="100%" bgcolor="lightgray" align="center">
            <tr>
                <td colspan=12 style="text-align: center"><B>Hello Money - Host Machine Details</B></td>
            </tr>
            <tr>
                <td>Env:<select id="env" name="host" onchange="updateHost(this);updateMobNo();updatePort();updateCountry();">
                    <option value="" selected>Select a host</option>
                </select></td>

                <td>Country:</td>
                <td><select id="bsnssId" onChange='updateAgg(this);updateMobNo();updatePort();updateDomain();'>
                    <!-- style="background-color: lightBlue" -->
                    <option value="TZBRB" selected="selected">Tanzania</option>
                    <option value="UGBRB">Uganda</option>
                   <option value="KEBRB">Kenya</option>
                   <option value="GHBRB">Ghana</option>
                   <option value="ZWBRB">Zimbabwe</option>
                   <option value="ZMBRB">Zambia</option>
                    <option value="BWBRB">Botswana</option>
                </select></td>
                <td>Aggregator:</td>
                <td><select id="aggregator">
                    <!-- style="background-color: lightBlue" -->
                    <option value="selcom" selected="selected">Selcom</option>
                    <option value="trueafrican">TrueAfrican</option>
                    <option value="cellulant">Cellulant</option>
                </select></td>

                <td>Host Name:</td>
                <td><input id="domain" name="domain" type="text" value="" style="background-color: lightBlue" /></td>
                <td>Port:</td>
                <td><input id="port" name="port" type="text" value="" style="background-color: lightBlue" /></td>
                <td>Context:</td>
                <td><input id="context" name="context" type="text" value="" style="background-color: lightBlue" /></td>
            </tr>
        </table>
    </tr>
</table>

<BR />
<center>URL : <input id="targetURL" name="targetURL" type="text" size="100" readonly="true" style="background-color: lightGray; font-size: 9pt;" /> <input
    type="hidden" id="templevel" name="templevel" /> <input type="hidden" id="currentlevel" name="currentlevel" /> <input type="hidden" id="extra"
    name="extra" /> <input type="hidden" id="responseFlag" name="responseFlag" value="0" /> <input type="hidden" id="protocol" name="protocol"
    type="text" value="http" /> <br>


<table>
    <tr style="text-align: center; font-family: sans-serif">
        <td style="text-align: left; width: 150px">Enter Mobile No.</td>
        <td><input style="display: inline-block; width: 120px;" maxlength="12" id="msisdn" name="msisdn" type="text" onChange="msisdnChange()"
            value="" /></td>
        <td>or Select <select id="mobNo" style="background-color: lightBlue" onChange="msisdnChange()">
            <option value="0000184486" selected="selected" title="PIN: 4922">0000184486</option>
            <option value="000162880" title="PIN: 4120">000162880</option>
            <option value="255763210370" title="PIN: 1628">763210370</option>
            <option value="255659800308" title="PIN: 4912">659800308</option>
            <option value="255789783842" title="PIN: 4912">789783842</option>
            <option value="255763210278" title="PIN: 4912">763210278</option>
             <option value="782060209" title="UGANDA PIN: 4912">782060209</option>
            <option value="703834644" title="UGANDA PIN: 4912">703834644</option>
            <option value="757596771" title="UGANDA PIN: 4912">757596771</option>
            <option value="752779552" title="UGANDA PIN: 4912">752779552</option>
            <option value="776655443" title="KENYA PIN: 4925">776655443</option>
        </select></td>

    </tr>
</table>
<div style="width: 225px; margin: auto;"><!-- <h3 align="center">USSD SIMULATOR</h3>-->

<table align="center" border="0" cellpadding="0" cellspacing="0" width="60" frame="void" >
    <tr>
        <td rowspan="7"><textarea value="" id="phoneInput" style="text-align: left; background-color: #CCCCCC" id="phoneInput" name="phoneInput"
            readonly="true" rows="13" cols="25">*150*20*1#</textarea></td>
    </tr>
    <tr>
	<td> &nbsp; </td>
    </tr>
    <tr>

	    <td> <input id="SESSIONID" name="SESSIONID" type="hidden" size="25" readonly="true"	style="background-color: lightGray; font-size: 8pt;" />   </tr>

    <tr>
 	   <td> <input id="NV" name="NV" type="hidden" size="25" readonly="true" style="background-color: lightGray; font-size: 8pt;" />
    </tr>
    <tr>
	<td> <input type="hidden" id="respLen" name="respLen" size="25" readonly="true" style="background-color: lightGray; font-size: 8pt;" /> </td>
    </tr>
    <tr>
	<td> &nbsp; </td>
    </tr>
     <tr>
	<td> &nbsp; </td>
    </tr>


    <tr>
        <td bgcolor="gray"><input type="button" class="callkeys" value="Answer"
            style="background-image: url(./images/answer.bmp); background-position: center; background-size: cover;" onClick="postRequest('auto');" />
        <input type="button" class="callkeys" value="Decline"
            style="background-image: url(./images/decline.bmp); background-position: center; background-size: cover;" onClick="disconnect()" /> <input
            type="button" style="text-align: left; width: 40px" value="&lt;--" title="clear" onClick="clearPhIn()" /></td>
        <td></td>

    </tr>
    <tr>
        <td align="center">
        <table bgcolor="gray" id="keypad" name="keypad" width="100%">
            <tr>
                <td><input id="buttonKeyPad1" name="buttonKeyPad1" type="button" class="keys" value="1" onClick="populatePhone('1')" /></td>
                <td><input id="buttonKeyPad2" name="buttonKeyPad2" type="button" class="keys" value="2 abc" onClick="populatePhone('2')" /></td>
                <td><input id="buttonKeyPad3" name="buttonKeyPad3" type="button" class="keys" value="3 def" onClick="populatePhone('3')" /></td>
            </tr>
            <tr>
                <td><input id="buttonKeyPad4" name="buttonKeyPad4" type="button" class="keys" value="4 ghi" onClick="populatePhone('4')" /></td>
                <td><input id="buttonKeyPad5" name="buttonKeyPad5" type="button" class="keys" value="5 jkl" onClick="populatePhone('5')" /></td>
                <td><input id="buttonKeyPad6" name="buttonKeyPad6" type="button" class="keys" value="6 mno" onClick="populatePhone('6')" /></td>
            </tr>
            <tr>
                <td><input id="buttonKeyPad7" name="buttonKeyPad7" type="button" class="keys" value="7 pqrs" onClick="populatePhone('7')" /></td>
                <td><input id="buttonKeyPad8" name="buttonKeyPad8" type="button" class="keys" value="8 tuv" onClick="populatePhone('8')" /></td>
                <td><input id="buttonKeyPad9" name="buttonKeyPad9" type="button" class="keys" value="9 wxyz" onClick="populatePhone('9')" /></td>
            </tr>
            <tr>
                <td><input id="buttonKeyPadStar" name="buttonKeyPadStar" type="button" class="keys" value="*" onClick="populatePhone('*')" /></td>
                <td><input id="buttonKeyPad0" name="buttonKeyPad0" type="button" class="keys" value="0" onClick="populatePhone('0')" /></td>
                <td><input id="buttonKeyPadHash" name="buttonKeyPadHash" type="button" class="keys" value="#" onClick="populatePhone('#')" /></td>
            </tr>
        </table>
        </td>

    </tr>
    <tr>
    <td colspan="2" style="text-align: left;">

<input type="text" id="tempInputURL" name="tempInputURL" type="text" size="18" value="password" />
<input id="manualInput" type="button" class="callkeysManual" value="Answer"  style="background-image: url(images/answer.bmp); background-position: center; background-size: cover;"
    onClick="postRequest('manual');" />

    </td>

    </tr>
</table>

</div>

<!--<h4>INPUT URL: </h4>
<input type="hidden" id="inputURL" name="inputURL" type="text" size="150" />
-->

<!--<h4>OUTPUT URL: </h4> <input type="hidden" id="outputURL" name="outputURL" type="text" size="150" disabled />
-->

<BR>
<BR>


<table align="center" width="100%">
    <tr>
	<td style="text-align:right"> XML Request Text: </td>
        <td colspan="10"><textarea rows="2" cols="120" id="xmlRequestText" name="xmlRequestText" readonly="readonly"
            style="background-color: lightGray; align: center" value=""></textarea></td>
    </tr>
    <tr>
	<td style="text-align:right"> XML Response Text: </td>
        <td colspan="10"><textarea rows="4" cols="120" id="xmlResponseText" name="xmlResponseText" readonly="readonly"
            style="background-color: lightGray; align: center" value=""></textarea></td>
    </tr>
    <!--  <tr>
	<td style="text-align:right"> Simulator Response Text: </td>
        <td colspan="10"><textarea rows="2" cols="120" id="responseText" name="responseText" readonly="readonly"
            style="background-color: lightGray; align: center" value="" hidden="true"></textarea></td>
    </tr>-->
    </form>

</center>

</body>
</html>













