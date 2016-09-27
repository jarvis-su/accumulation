<html>
<head>
<script type="text/javascript">

<%

Map<String, String> eccStates = (Map<String, String>)session.getAttribute("eccStates");
Map<String, String> ebtStates = (Map<String, String>)session.getAttribute("ebtStates");
Map<String, String> epcStates = (Map<String, String>)session.getAttribute("epcStates");
Map<String, String> otherStates = (Map<String, String>)session.getAttribute("otherStates");

%>
function check(browser){
    var group = $('input[name="group1"]:checked').val();
    if("EPC"==group){
    	var epcStat = "<%=epcStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
    	$("#divState").empty();
    	for(var i=0;i<epcStat.length;i++){
    		var state = epcStat[i].split("=");
    		if(state[1]!="#"){
		    	$("#divState").append("<option value=" + state[0] + ">" + state[1] + "</option>");
    		}
    	}
    }else if("EBT"==group){
    	var ebtStat = "<%=ebtStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
    	$("#divState").empty();
    	for(var i=0;i<ebtStat.length;i++){
    		var state = ebtStat[i].split("=");
    		if(state[1]!="#"){
	    		$("#divState").append("<option value=" + state[0] + ">" + state[1] + "</option>");
    		}
    	}
    }else if("ECC"==group){
    	var eccStat = "<%=eccStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
    	$("#divState").empty();
    	for(var i=0;i<eccStat.length;i++){
    		var state = eccStat[i].split("=");
    		if(state[1]!="#"){
	    		$("#divState").append("<option value=" + state[0] + ">" + state[1] + "</option>");
    		}
    	}
    }else{
    	var otherStat = "<%=otherStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
    	$("#divState").empty();
    	for(var i=0;i<otherStat.length;i++){
    		var state = otherStat[i].split("=");
    		if(state[1]!="#"){
		    	$("#divState").append("<option value=" + state[0] + ">" + state[1] + "</option>");
    		}
    	}
    	
    }
    
}

function getStateByGroup(){
	 var group = $('input[name="group2"]:checked').val();
	 if("EPC"==group){
	    	var epcStat = "<%=epcStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
	    	alert(epcStat);
	    	$("#divState2").empty();
	    	for(var i=0;i<epcStat.length;i++){
	    		var state = epcStat[i].split("=");
	    		if(state[1]!="#"){
			    	$("#divState2").append("<option value=" + state[0] + ">" + state[1] + "</option>");
	    		}
	    	}
	    }else if("EBT"==group){
	    	var ebtStat = "<%=ebtStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
	    	$("#divState2").empty();
	    	for(var i=0;i<ebtStat.length;i++){
	    		var state = ebtStat[i].split("=");
	    		if(state[1]!="#"){
		    		$("#divState2").append("<option value=" + state[0] + ">" + state[1] + "</option>");
	    		}
	    	}
	    }else if("ECC"==group){
	    	var eccStat = "<%=eccStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
	    	$("#divState2").empty();
	    	for(var i=0;i<eccStat.length;i++){
	    		var state = eccStat[i].split("=");
	    		if(state[1]!="#"){
		    		$("#divState2").append("<option value=" + state[0] + ">" + state[1] + "</option>");
	    		}
	    	}
	    }else{
	    	var otherStat = "<%=otherStates%>".replace(/{/g, '').replace(/}/g, '').split(",");
	    	$("#divState2").empty();
	    	for(var i=0;i<otherStat.length;i++){
	    		var state = otherStat[i].split("=");
	    		if(state[1]!="#"){
			    	$("#divState2").append("<option value=" + state[0] + ">" + state[1] + "</option>");
	    		}
	    	}
	    	
	    }
}

function getPathByState(){
	var instanceID = document.getElementById("divState2").value;
	alert(instanceID);
	document.stateManagment.action="/FileCheckerWeb/FileCheckerConfig?instanceID="+instanceID;
	document.stateManagment.submit();
	alert("aaaaa");
}

function submitState(){
	var state = $("#stateName").val();
	var group = $('input[name="group1"]:checked').val();
	document.stateManagment.action="/FileCheckerWeb/FileChecker?stateName="+state+"&group="+group;
	document.stateManagment.submit();
}

function deleteState(){
	var group = $('input[name="group1"]:checked').val();
	var deleteState = $("#divState").find("option:selected").text();
	var deleteInstanceId = $("#divState").val();
 	document.stateManagment.action="/FileCheckerWeb/FileChecker?deleteInstanceId="+deleteInstanceId+"&deleteState="+deleteState+"&group="+group;
	document.stateManagment.submit(); 
}
</script>
<script type="text/javascript" src="time.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.3.min.js"></script>

</head>
<body>
<jsp:include page="template.jsp" flush="true" />
<%@page import="java.sql.*,java.util.*,java.io.*"%>
<%String state = (String)request.getAttribute("STATE");
  String date = (String) request.getAttribute("DATE");
  out.print(request.getAttribute("ALERT"));
  if(state != null) { 
    out.print("<center><a href=\"FileChecker?state=" + state + "&op=getCSV\">Export All to CSV</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp" + 
			"<a href = \"FileChecker?state=" + state + "&op=getCSV&errOnly=true\">Export Errors to CSV</a></center><br>");
  }
%>


<form method="post" action="FileChecker">
	<input type="hidden" name="checknow" value="true"/>
	<input type="hidden" name="state" value="<%=state%>"/>
</form>
<form name=stateManagment method="post" >

<h3>State Managment:</h3>
<table>
<tr>
	<td>
		<input type="radio" name="group1" onclick="check(this.value)" value="EPC" />EPC  
		<input type="radio" name="group1" onclick="check(this.value)" value="EBT" />EBT  
		<input type="radio" name="group1" onclick="check(this.value)" value="ECC" />ECC  
		<input type="radio" name="group1" onclick="check(this.value)" value="OTHER" />OTHER  &nbsp;&nbsp;
	</td>
	<td>

		<select id="divState" style="hidden" name="reportState1">  
		  <option value="0" selected="selected">--Select the state name--</option>
		</select>  &nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" value="Delete" onClick="deleteState()">Delete</button> &nbsp;&nbsp;
 	  
	</td>
</tr>
<tr>
	<td>
		<b>Input new state name: </b><input type="text" id="stateName" name="stateName" maxlength="7" size="7"/> &nbsp;&nbsp;
	</td>

</tr>
<tr>
	<td><br>
		<button type="button" value="Submit" onclick="submitState()">Submit</button> &nbsp;&nbsp;
		<button type="reset" value="Reset">Reset</button> &nbsp;&nbsp;
	</td>
</tr>
</table>
</form>

<hr/>
<form>
<h3>Update Report:</h3>
<table>
<tr>
	<td>
		<input type="radio" name="group2" onclick="getStateByGroup()" value="EPC" />EPC  
		<input type="radio" name="group2" onclick="getStateByGroup()" value="EBT" />EBT  
		<input type="radio" name="group2" onclick="getStateByGroup()" value="ECC" />ECC  
		<input type="radio" name="group2" onclick="getStateByGroup()" value="OTHER" />OTHER  &nbsp;&nbsp;

		<select id="divState2" style="hidden" name="reportState2" onchange="getPathByState()">  
		  <option value="0" selected="selected">--Select the state name--</option>
		</select>  &nbsp;&nbsp;&nbsp;&nbsp;


		<select name="reportPath2">  
		  <option value="0" selected="selected" > --Select the path-- </option>  
		  <option value="1">view</option>  
		  <option value="2">home</option>  
		  <option value="3">extfile</option>  
		  <option value="4">genfile</option>  
		  <option value="5">send</option>
		</select>
	</td>
</tr>

<tr>
	<td>
		<br><B>File name:</B><br>
		<select name="fileName">  
		  	<option value="0" selected="selected">--Select the file name--</option>  
      		<option value="1">CA01-CALWIN-(20140108(20|21|22|23)|20140109(00|01|02|03|04|05|06|07|08|09|10|11|12|13))[0-9]*-CSBEBENEMAINT.rej</option>  
      		<option value="2">CA02-CIV-20140109010525148-CSBEBENEMAINT.rej</option>  
      		<option value="3">admin_fin_transactions_summary_daily/07/P_CA_07_20140108-admin_fin_transactions_summary_daily.txt</option>  
      		<option value="4">benefit_drawdown_daily/36/P_CA_[0-9][0-9]_20140109-benefit_drawdown_daily.csv</option> 
		</select>
	</td>
	<td>
		<br><br>
		<button type="button" value="Delete">Delete</button>
	</td>
</tr>
<tr>
	<td>
		<br><B>Change the report frequency:</B><br>
		<select name="frequency">
			<option value="1"> 1st of month </option>
			<option value="2"> 2nd of month </option>
			<option value="3"> 3rd of month </option>
			<option value="4"> 4th of month </option>
			<option value="5"> 5th of month </option>
			<option value="6"> 6th of month </option>
			<option value="7"> 7th of month </option>
			<option value="8"> 8th of month </option>
			<option value="9"> 9th of month </option>
			<option value="10">	10th of month </option>
			<option value="11"> 11th of month </option>
			<option value="12">	12th of month </option>
			<option value="13">	13th of month </option>
			<option value="14">	14th of month </option>
			<option value="15">	15th of month </option>
			<option value="16">	16th of month </option>
			<option value="17">	17th of month </option>
			<option value="18">	18th of month </option>
			<option value="19">	19th of month </option>
			<option value="20">	20th of month </option>
			<option value="21">	21st of month </option>
			<option value="22">	22nd of month </option>
			<option value="23">	23rd of month </option>
			<option value="24">	24th of month </option>
			<option value="25">	25th of month </option>
			<option value="26">	26th of month </option>
			<option value="27">	27th of month </option>
			<option value="28">	28th of month </option>
			<option value="29">	29th of month </option>
			<option value="30">	30th of month </option>
			<option value="31">	31st of month </option>
			<option value="101"> Daily </option>
			<option value="102"> Monthly </option>
			<option value="103"> Weekly Sunday </option>
			<option value="104"> Weekly Monday </option>
			<option value="105"> Weekly Tuesday </option>
			<option value="106"> Weekly Wednesday </option>
			<option value="107"> Weekly Thursday </option>
			<option value="108"> Weekly Friday </option>
			<option value="109"> Weekly Saturday </option>
			<option value="110"> Quarter January </option>
			<option value="111"> Quarter April </option>
			<option value="112"> Quarter July </option>
			<option value="113"> Quarter October </option>
			<option value="114"> Quarter Jan third </option>
			<option value="115"> Quarter Apr third </option>
			<option value="116"> Quarter Jul third </option>
			<option value="117"> Quarter Oct third </option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp; <b>Check Time: </b><input onkeyDown="validate(this,2)" value="00:00:00" size="8">
		&nbsp;&nbsp;&nbsp;&nbsp; <b>Check Stop: </b><input onkeyDown="validate(this,2)" value="00:00:00" size="8">
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" name="disabled"> <b>Disabled: </b>
	</td>
</tr>
<tr>
	<td>
		<br><B>Change the file name:</B><br>
		<input type="text" name="path" style="color:#cccccc" onclick="this.value=''" value="Input new file name here." size="100"/>
	</td>
</tr>
<tr>
	<td>
		<button type="submit" value="Submit">Submit</button>&nbsp;&nbsp;
		<button type="reset" value="Reset">Reset</button>&nbsp;&nbsp;
	</td>	
</tr>
</table>
</form>
<hr/>
<form>
<h3>Add New Report:</h3>
<table>
<tr>
	<td>
		<input type="radio" name="group2" onclick="check(this.value)" value="EPC" />EPC  
		<input type="radio" name="group2" onclick="check(this.value)" value="EBT" />EBT  
		<input type="radio" name="group2" onclick="check(this.value)" value="ECC" />ECC  
		<input type="radio" name="group2" onclick="check(this.value)" value="OTHER" />OTHER  &nbsp;&nbsp;

		<select name="reportState2">  
		  <option value="0" selected="selected">--Select the state name--</option>  
		  <option value="1">CA</option>  
		  <option value="2">LA</option>  
		  <option value="3">NC</option>  
		</select>  &nbsp;&nbsp;&nbsp;&nbsp;

		<select name="reportPath2">  
		  <option value="0" selected="selected"> --Select the path-- </option>  
		  <option value="1">view</option>  
		  <option value="2">home</option>  
		  <option value="3">extfile</option>  
		  <option value="4">genfile</option>  
		  <option value="5">send</option>  
		</select>
	</td>
</tr>
<tr>
	<td>
		<br><B>Add report name:</B><br>
		<input type="text" name="path" style="color:#cccccc" onclick="this.value=''" value="Input new file name here." size="100"/>
	</td>
</tr>
<tr>
	<td>
		<select name="reportPath1">  
		  <option value="0" selected="selected">--Select the path--</option>  
		  <option value="1">view</option>  
		  <option value="2">home</option>  
		  <option value="3">extfile</option>  
		  <option value="4">genfile</option>  
		  <option value="5">others</option>  
		</select> &nbsp;&nbsp;
		<input type="text" name="path" style="color:#cccccc" onclick="this.value=''" value="Input the report path here." size="25"/>
	</td>
</tr>
<tr>
	<td>
		<button type="submit" value="Submit">Submit</button>&nbsp;&nbsp;
		<button type="reset" value="Reset">Reset</button>&nbsp;&nbsp;
	</td>	
</tr>
</table>
</form>
</body>
</html>
