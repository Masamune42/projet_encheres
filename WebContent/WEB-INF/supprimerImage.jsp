<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Let's dance !</title>

<jsp:include page='style.jsp'>
	<jsp:param name="style" value="" />
</jsp:include>
</head>
<body>
	<div align="center">
		<iframe src="https://www.youtube.com/watch?v=G1IbRujko-A?autoplay=1">
		</iframe>
	</div>
	<div align="center">
		<a href="${pageContext.request.contextPath}/ServletAccueilConnect"
			class="button" style="color: black;"><img alt="birds"
			src="<%=request.getContextPath()%>/img/tenor.gif"></a>

	</div>
	
</body>
</html>