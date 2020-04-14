<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<title>SHM Simulator</title>
		<link rel="stylesheet" type="text/css" href="css/shm.css">
		<script>
			<%
				StringBuffer requestURL = request.getRequestURL();
				String context = request.getContextPath().toString();

				int strIndex = requestURL.indexOf(context);
				String contextPath = requestURL.substring(0, strIndex + context.length());
			%>
			var simulatorURL = "<%= contextPath %>/hellomoneyclient";
		</script>
		<script src="js/jquery-1.4.4.min.js"></script>
		<script language="javascript" type="text/javascript" src="js/shm_link.js"></script>
	</head>
	<body>
		<form>
			<input type="hidden" id="templevel" name="templevel" />
			<input type="hidden" id="currentlevel" name="currentlevel" />
			<!--<input type="hidden" id="extra" name="extra" />
    		--><input type="hidden" id="responseFlag" name="responseFlag" value="0" />
    		<input type="hidden" id="protocol" name="protocol"  type="text" value="http" />
    		<input type="hidden" id="targetURL" name="targetURL" />
    		<input type="hidden" id="SESSIONID" name="SESSIONID" />
    		<input type="hidden" id="NV" name="NV" />
    		<input type="hidden" id="respLen" name="respLen" />
    		<input type="hidden" disabled class="time" id="requestTime" name="requestTime" >
			<input type="hidden" disabled class="time" id="responseTime" name="responseTime" >
			<div class="barclaysRibbon">
				<img class="barclaysLogo" src="images/absa-logo-white@3x.png">
				<label class="simulatorTitle">Hello Money Simulator (USSD)</label>
			</div>
			<div class="scrollContent1">
				Request : <textarea class="request" rows="15" cols="28" id="xmlRequestText" name="xmlRequestText" readonly="readonly"></textarea>
			</div>
			<div class="scrollContent2">
				Response: <textarea class="response"rows="15" cols="28" id="xmlResponseText" name="xmlResponseText" readonly="readonly"></textarea>
			</div>
			<table>
				<tr>
					<td>
						<div class="filterSideBar">
							<label class="fieldLabel">Env</label>
							<select class="dropDown" id="env" name="host" onchange="updateHost(this);updateMobNo();updatePort();updateCountry();">
								<option value="" selected>Select a host</option>
								<!--<option>Local</option>
								<option>SIT</option>
								<option>UAT</option>-->
							</select>
							<br/>
							<label class="fieldLabel">Country</label>
							<select class="dropDown" id="bsnssId" onChange='updateAgg(this);listChange();updatePort();updateDomain();updateFreeDialUssd(this);'>
								<option value="TZBRB" selected="selected" title="PIN: 4912">Tanzania</option>
			                    <option value="UGBRB" title="PIN: 4912">Uganda</option>
								<option value="KEBRB" title="PIN: 3457">Kenya</option>
								<option value="GHBRB" title="PIN: 4912">Ghana</option>
								<!--<option value="ZWBRB">Zimbabwe</option>
								--><option value="ZMBRB" title="PIN: 4914">Zambia</option>
			                    <option value="BWBRB" title="PIN: 4914">Botswana</option>
                                <option value="MZBRB" title="PIN: 4912">Mozambique</option>
                                <option value="TZNBC" title="PIN: 4912">TZNBC</option>
							</select>
							<br/>
							<label class="fieldLabel">Aggregator</label>
							<select class="dropDown" id="aggregator">
								<option value="selcom" selected="selected">Selcom</option>
			                    <option value="trueafrican">TrueAfrican</option>
			                    <option value="cellulant">Cellulant</option>
							</select>
							<br/>
							<label class="fieldLabel">Host Name</label>
							<input type="text" class="filterInput" id="domain" name="domain">
							<br/>
							<label class="fieldLabel">Port</label>
							<input type="text" class="filterInput" id="port" name="port">
							<br/>
							<label class="fieldLabel">Context</label>
							<input type="text" class="filterInput" id="context" name="context">
							<br/>
<!--							<label class="fieldLabel">Mobile List</label>-->
<!--							<select class="dropDown" id="mobNo" onChange="msisdnChange()">-->
<!--								<option value="0000184486" selected="selected" title="PIN: 4922">0000184486</option>-->
<!--            					<option value="000162880" title="PIN: 4120">000162880</option>-->
<!--					            <option value="255763210370" title="PIN: 1628">763210370</option>-->
<!--					            <option value="255659800308" title="PIN: 4912">659800308</option>-->
<!--					            <option value="255789783842" title="PIN: 4912">789783842</option>-->
<!--					            <option value="255763210278" title="PIN: 4912">763210278</option>-->
<!--					            <option value="782060209" title="UGANDA PIN: 4912">782060209</option>-->
<!--					            <option value="703834644" title="UGANDA PIN: 4912">703834644</option>-->
<!--					            <option value="757596771" title="UGANDA PIN: 4912">757596771</option>-->
<!--					            <option value="752779552" title="UGANDA PIN: 4912">752779552</option>-->
<!--					            <option value="776655443" title="KENYA PIN: 4925">776655443</option>-->
<!--							</select>-->
<!--							<br/>-->
							<!-- FreeDialUSSD simulator changes -->
							<label class="fieldLabel" id="freeDialLabel" style="visibility:hidden">FreeDialUssd</label>
							<select class="dropDown"  id="extra" name="extra" style="visibility:hidden" disabled="disabled" onChange='showAmount(this);' >
								<!--<option value="" selected="selected">Default</option>
			                    <option value="FREE">FreeDailAirtime</option>
			                    <option value="FREEDIALMTN">FreeDailMWallet</option>
							--></select>
							<br/>
							<label class="fieldLabel">Mobile#</label>
							<input type="text" class="filterInput" maxlength="12" id="msisdn" name="msisdn" onChange="listChange();" value="">
							<br/>
							<label class="fieldLabel">User Input</label>
							<input type="text" class="filterInput" id="tempInputURL" name="tempInputURL">
							<br/>
							<label class="fieldLabel" id="topUpAmount" style="visibility:hidden">Amount</label>
                            <input type="text" class="filterInput" id="amount" style="visibility:hidden" >
                            <br/>
							<br/>
							<a class="alternateCallKey" href="#" onClick="postRequest('manual');" id="manualInput" name="manualInput">&nbsp;<a>
						</div>
						<div class="footer1">
				 		</div>
					</td>
					<td>
						<div class="handsetContainer">
							<textarea class="phoneScreen" rows="125" cols="158" disabled id="phoneInput" name="phoneInput">*150*20*1#
							</textarea>
							<br/>
							<div class="keypadContainer">
								<a class="callKey" href="#" onClick="postRequest('auto');">&nbsp;<a>
								<a class="clearKey" href="#" onClick="clearPhIn();">C<a>
								<a class="disconnectKey" href="#" onClick="disconnect();">&nbsp;</a>
								<br/>
								<div class="numKeys">
									<a class="numKey" href="#" id="buttonKeyPad1" name="buttonKeyPad1" onClick="populatePhone('1');">1<a>
									<a class="numKey" href="#" id="buttonKeyPad2" name="buttonKeyPad2" onClick="populatePhone('2');">2 abc<a>
									<a class="numKey" href="#" id="buttonKeyPad3" name="buttonKeyPad3" onClick="populatePhone('3');">3 def<a>
									<br/>
									<a class="numKey" href="#" id="buttonKeyPad4" name="buttonKeyPad4" onClick="populatePhone('4');">4 ghi<a>
									<a class="numKey" href="#" id="buttonKeyPad5" name="buttonKeyPad5" onClick="populatePhone('5');">5 jkl<a>
									<a class="numKey" href="#" id="buttonKeyPad6" name="buttonKeyPad6" onClick="populatePhone('6');">6 mno<a>
									<br/>
									<a class="numKey" href="#" id="buttonKeyPad7" name="buttonKeyPad7" onClick="populatePhone('7');">7 pqrs<a>
									<a class="numKey" href="#" id="buttonKeyPad8" name="buttonKeyPad8" onClick="populatePhone('8');">8 tuv<a>
									<a class="numKey" href="#" id="buttonKeyPad9" name="buttonKeyPad9" onClick="populatePhone('9');">9 wxyz<a>
									<br/>
									<a class="numKey" href="#" id="buttonKeyPadStar" name="buttonKeyPadStar" onClick="populatePhone('*');">* +<a>
									<a class="numKey" href="#" id="buttonKeyPad0" name="buttonKeyPad0" onClick="populatePhone('0');">0 _<a>
									<a class="numKey" href="#" id="buttonKeyPadHash" name="buttonKeyPadHash" onClick="populatePhone('#');">#<a>
								</div>
							</div>
							<br/>
						</div>
					</td>
					<td>
						<div class="navigationContainer">
							<ul>
								<li class="logNavigation"><a class="linkHeader" href="#" disabled>LOGS</a></li>
								<!--<li class="logNavigation"><a class="loglink" href="https://jhbdsr000000862.intranet.barcapint.com:45032/logs/jhbdsr000000862_wasadmin2-shm01-server01/helloMoneyLogs/" target="_blank">DEV</a></li>
								--><li class="logNavigation"><a class="loglink" href="https://zadsdcrapp1196.ocorp.dsarena.com:45032/DigitalLogger/digitallogger" target="_blank">LOGGER</a></li>
<!--								<li class="logNavigation"><a class="loglink" href="https://zadsdcrapp1445.corp.dsarena.com:9448/logview/listlogs.jsp?logfolder=HelloMoney&profile=roadigitaluat" target="_blank">UAT</a></li>-->
								<br/>
								<li class="logNavigation"><a class="linkHeader" href="#" disabled>BOC</a></li>
								<li class="logNavigation"><a class="loglink" href="https://federationuat.client.barclayscorp.com/idp/startSSO.ping?PartnerSpId=BOCSSAPCT" target="_blank">PCT</a></li>
								<li class="logNavigation"><a class="loglink" href="https://federationuat.client.barclayscorp.com/idp/startSSO.ping?PartnerSpId=SSABOCSIT" target="_blank">SIT</a></li>
								<li class="logNavigation"><a class="loglink" href="https://federationuat.client.barclayscorp.com/idp/startSSO.ping?PartnerSpId=SSABOCUAT" target="_blank">UAT</a></li>
								<li class="logNavigation"><a class="loglink" href="https://federationuat.client.barclayscorp.com/idp/startSSO.ping?PartnerSpId=SSABOCNFT" target="_blank">NFT</a></li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
				 	<td colspan="3">
				 		<div class="footer2">
				 			<span class="timer">Response time [min:sec:ms]</span><br/>
							<time><textarea class="time" id="stopWatch" name="stopWatch" rows="1" cols="25">00:00:000</textarea></time>
				 		</div>
				 		<div class="footer4">
				 			<div class="scrollContent3">
								<textarea class="url" id="url" name="url" rows="1" cols="100" readonly="readonly"></textarea>
							</div>
				 		</div>
						<div class="footer3">
							<span class="timer">Response length [chars]</span><br/>
							<time><textarea class="time" id="respLength" name="respLength" rows="1" cols="25">0</textarea></time>
						</div>
					</td>
				</tr>
				<tr>
				 	<td colspan="3">
				 		<div class="scrollPanel">Click here to show XML Request/Response
				 		</div>
				 	</td>
				</tr>
			</table>
		</form>
	</body>
</html>