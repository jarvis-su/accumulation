<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/jmesa.tld" prefix="jmesa" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>	
		<title>我的第一个jmesa测试示例</title>	
		<link rel="STYLESHEET" type="text/css" href="css/jmesa.css" />
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
			    alert("parameterString: " + parameterString);  
			    location.href = "<%= request.getContextPath() %>/ExportServlet?" + parameterString;       
			}   
			function del(user,index){
				alert("del: " + user + ",INDEX: " + index);
			} 
		</script>
</head>
<body>
<form name="personForm" action="<%= request.getContextPath() %>/ExportServlet" method="post">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0" align="center">
<tr><td>
	<jmesa:tableModel id="presidents" items="${presidents}" var="bean" >   
        <jmesa:htmlTable width="600px" >                  
            <jmesa:htmlRow > 
                <jmesa:htmlColumn property="education"/>   
                <jmesa:htmlColumn property="politicalParty"/>  
                <jmesa:htmlColumn property="term"/>   
                <jmesa:htmlColumn property="career"/>  
                <jmesa:htmlColumn property="born"/>   
                <jmesa:htmlColumn property="id"/>   
            </jmesa:htmlRow>   
        </jmesa:htmlTable>    
</jmesa:tableModel>  
</td></tr>
</table>	
</form>
</body>
</html>
