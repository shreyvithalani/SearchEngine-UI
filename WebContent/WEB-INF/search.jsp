<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="search" method="post">

		<div style="text-align: center;">
			<h1>Noogle</h1>
			<input type='text' name='test' />
		</div>
		<div style="text-align: center;">
			<input type='submit' value='Search' />
		</div>
	</form>
	<div align="center">
		<c:forEach items="${docnames}" var="item">
			<a href="file://${item}" target="_blank">${item}</a>
			<br />
		</c:forEach>
	</div>
</body>
</html>