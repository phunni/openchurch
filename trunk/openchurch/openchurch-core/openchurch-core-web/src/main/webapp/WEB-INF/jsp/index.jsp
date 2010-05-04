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
<c:if test="${currentUser.username == 'guest'}">
	Please <a href="/openchurch/Login">login</a>.
	<hr />
</c:if>
 Welcome ${currentUser.preferredNames } ${currentUser.surname }!
 <div class="menubar">
 	<c:forEach items="${model.layout}" var="currentMenu">
 		${currentMenu.name}
 		<div class="menuItems">
 			<c:forEach items="${currentMenu.items}" var="currentMenuItem">
 			  <a href="/openchurch/?page=${currentMenuItem.id}" title="${currentMenuItem.title}">
 			  	${currentMenuItem.title}
 			  </a>
 			</c:forEach>
 		</div>
 	</c:forEach>
 </div>
 <c:if test="${not empty model.page}"> 
 <div class="page">
 	${model.page.name}
 </div>
 </c:if>
</body>
</html>