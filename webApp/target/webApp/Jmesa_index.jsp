<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>	
		<title>我的第一个jmesa测试示例</title>	
		<link rel="STYLESHEET" type="text/css" href="css/jmesa.css" />
		<link rel="STYLESHEET" type="text/css" href="css/jmesa-pdf.css" />
		<script type="text/javascript" src="js/jquery-1.3.min.js" /></script>
		<script type="text/javascript" src="js/jmesa.js" /></script>
		<script type="text/javascript" src="js/jquery.jmesa.js" /></script>		
		<script type="text/javascript">
			function onInvokeAction(id) {
			    setExportToLimit(id, '');
			    createHiddenInputFieldsForLimitAndSubmit(id);
			}
			function onInvokeExportAction(id) {       
			    var parameterString = createParameterStringForLimit(id);  			   
			    location.href = "<%= request.getContextPath() %>/ExportServlet?" + parameterString;       
			}   
			
		</script>
</head>
<body>
<form name="personForm" action="<%= request.getContextPath() %>/ExportServlet" method="post">
<table  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td  align="center" width="100%">${presidents}</td></tr>
</table>	
</form>
</body>
</html>
