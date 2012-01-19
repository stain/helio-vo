<%@ 
page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HELIO Community Interaction Service</title>
</head>
<body>
<img src="images/helio-logo.jpg"/> 
<BR>HELIO Community Interaction Service<BR> 
<BR>
If you are already registered in HELIO, enter your details below
<BR>
<BR>
<FORM METHOD=POST ACTION="actions.jsp">
Enter your name     : <INPUT TYPE=TEXT     NAME=userName SIZE=20><BR>
Enter your password : <INPUT TYPE=PASSWORD NAME=userPwd  SIZE=20><BR>
<P><INPUT TYPE=SUBMIT>
<BR>
<BR>
If you are not a registered user, please follow this link to <a href="create.jsp">Create an account</a>
</body>
</html>