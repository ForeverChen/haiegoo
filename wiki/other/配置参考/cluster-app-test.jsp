<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>   
<html>
<head><title>Cluster App Test</title></head>   
<body>   
	Server Info:    
	<%    
		out.println(request.getLocalAddr() + " : " + request.getLocalPort()+"<br>");%>   
	<%
		out.println("<br> ID " + session.getId()+"<br>");     
		String dataName = request.getParameter("dataName");    
   		     
		if (dataName != null && dataName.length() > 0) {    
		   String dataValue = request.getParameter("dataValue");    
		   session.setAttribute(dataName, dataValue);    
		}      
   		     
   		out.print("<p><b>Session 列表：</b></p>");      
   		    
   		Enumeration e = session.getAttributeNames();    
   		     
   		while (e.hasMoreElements()) {
   			String name = (String) e.nextElement();
   			String value = session.getAttribute(name).toString();
   			out.println(name + " = " + value + "<br>");
   		}
   	%>
	<form action="test.jsp" method="POST">
		<p>
		 名称:<input type=text size=20 name="dataName">   
	  	<br>   
		 值　:<input type=text size=20 name="dataValue">   
	  	<br>
	  	<br>
		<input type=submit>
		</p>   
	</form>   
</body>   
</html> 