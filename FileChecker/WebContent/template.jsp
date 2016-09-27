<html>
<head>
  <title> FileChecker </title>
  <link rel=stylesheet type="text/css" href="images/style.css">
  <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
  <script type="text/javascript">
	  $(document).ready(function(){
		  $("img#config").click(function(){
		  	$("p").toggle();
		  });
	  });
  </script>
</head>
<body bottommargin="0" leftmargin="0" marginheight="0" marginwidth="0" rightmargin="0" topmargin="0" background="images/background.jpg" text="#EAF5FF">
<table width="100%" height="94" cellpadding="0" cellspacing="0" border="0">
	<tr valign="top">
		<td width="246" height="94"><img src="images/acs_logo.jpg" width="246" height="94" border="0" alt=""></td>
		<td width="368" height="94"><img src="images/topbar1.jpg" width="368" height="94" border="0" alt=""></td>
		<td width="100%" height="94" background="images/topbar1bg.jpg">
		<H1>
		<% String state = (String)request.getAttribute("STATE");
		if(state == null) {
			out.print("FileChecker");
		}
		else {
			out.print(state.toUpperCase());
		}
		%></H1>
		</td>
	</tr>
</table>
<table width="100%" height="91" cellpadding="0" cellspacing="0" border="0">
	<tr valign="top">
		<td width="613" height="91"><img src="images/2maincolorarea.jpg" width="613" height="91" border="0" alt=""></td>
		<td width="100%" height="91" background="images/2maincolorarea_bg.jpg">&nbsp;</td>
	</tr>
</table>
<table width="100%" height="33" cellpadding="0" cellspacing="0" border="0">
	<tr valign="top">
		<td width="246" height="33"><a href="/FileChecker"><img src="images/buttons/home.jpg" width="207" height="33" border="0" alt=""></a></td>
		<td width="368" height="33"><img src="images/3buttonarea.jpg" width="427" height="33" border="0" alt=""></td>
		<td width="100%" height="33" background="images/3buttonareabg.jpg">&nbsp;</td>
	</tr>
		<tr>
		<td width="246" height="33"><img id="config" src="images/buttons/config.jpg" width="207" height="33" border="0" alt="">
			<div id="item" style=font-size:16px;color="#007FFF">
				<a style="text-decoration:none" href="/FileCheckerWeb/ConfigStateManagment?config=ture">
					<p style="display:none;"><b>&nbsp;&nbsp;&nbsp;&nbsp;State Managment</b></p>
				</a>
				<a style="text-decoration:none" href="/FileCheckerWeb/ConfigFileManagment?config=ture">
					<p style="display:none;"><b>&nbsp;&nbsp;&nbsp;&nbsp;File Managment</b></p>
				</a>		
			</div>
		</td>
	</tr>
</table>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr valign="top">

	<td width="207">
		<table class=sidePanel cellpadding="0" cellspacing="1" border="0">
		<% out.print(request.getAttribute("SIDEPANEL")); %>
	  	</table>
	</td>
	<td width="20">&nbsp;&nbsp;&nbsp;</td>
	<td width="100%">