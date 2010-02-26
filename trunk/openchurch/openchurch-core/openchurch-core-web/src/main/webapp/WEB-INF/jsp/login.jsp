<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Open Church</title>
</head>
<body>
 <c:if test="${errorText}">
 	<p>
 		${errorText }
 	</p>
 </c:if>	
 <form method="post" action="/openchurch/Login">
 	username: <input type="text" name="username" /><br />
 	password: <input type="password" name="passsword" /> <br />
 	<input type="submit" name="Login" />
 </form>
</body>
</html>