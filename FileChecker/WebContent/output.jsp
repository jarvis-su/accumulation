<%
  String state = (String)request.getAttribute("STATE");
  String date = (String)request.getAttribute("DATE");
  String output = (String)request.getAttribute("OUTPUT");
  String errOnly = request.getParameter("errOnly");

  if(output.equals("")) {
	  out.print("<html>Invalid Request</html>");
  }
  else {
	  String s;
		if(errOnly != null && errOnly.equals("true")) {
			s = output.replaceAll(",[^,]*,[^,]*,[^,]*,[^,]*,\n","");
		}
		else {
			s = output;
		}
	  date = date.replaceAll("/","_");
	  if(state.equals(null)) {
		  out.print("Please check with a valid state and date");
	  }
	  else {

		if(s.length() == 42) {
			out.print("no errors");
		}
		else {
	  		response.setContentType("text/csv");
			response.setHeader("Content-Disposition","filename=\"" + state + "_" + date + ".csv\"");
			out.print(s);
		}
	  }
  }
%>
