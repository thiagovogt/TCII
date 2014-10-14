<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />

<title>VSLSystem - Access Report</title>
</head>
<body>
<center>
		<h1>VSLSystem</h1>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		${data}
		<br>
		<br>
		<div id="report" >
			<table style="width:335px;border-collapse: collapse;border:solid black 1px;" border="1">
				<caption><b>Relatório de acessos ao sistema</b></caption>
				<thead>
					<tr>
						<th>
							Acessos no dia
						</th>
						<th>
							Acessos no mês
						</th>
						<th>
							Acessos no ano
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							3
						</td>
						<td>
							414
						</td>
						<td>
							412123
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</center>
</body>
</html>