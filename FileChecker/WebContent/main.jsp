<html>
<body>
<jsp:include page="template.jsp" flush="true" />
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
<table width="1000" cellspacing="0" cellpadding="0" border="1" style="margin: 0px">
    <tr>
      <td width=40%>
        State
      </td>
      <td width=20%>
        Last Checked
      </td>
      <td width=20%>
        Run Date
      </td>
      <td width=20%>
      </td>
    </tr>
    <tr>
      <td width=40%>
        <%=state%>
      </td>
      <td width=20%>
        <%=date%>
      </td>
      <td width=20%>
		<input type="text" name="dateFor" size="8" value=""/>
      </td>
      <td width=20%>
          <input type="submit" value="Check"/>
      </td>
    </tr>
  </table>
</form>
  <%= request.getAttribute("OUTPUT") %>
</body>
</html>
