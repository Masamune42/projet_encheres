<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ENI ENCHERES</title>
<jsp:include page='style.jsp'>
    <jsp:param name="style" value=""/>
</jsp:include>

</head>

<!-- Header --> 
<jsp:include page="headerLogo.jsp">
    <jsp:param name="header" value=""/>
</jsp:include>

<body>

<%
Integer utilisateurInexistant = (Integer) request.getAttribute("utilisateurInexistant");
%>

<div class="container">

		<div class="row">
			<div class="col-sm-12">
				<c:if test="${utilisateurInexistant == -1 }">
					<p>utilisateur ou mot de passe incorrecte</p>
				</c:if>
			</div>

		</div>
		<form method="post"
			action="${pageContext.request.contextPath}/ServletConnexion">
			<!-- Identifiant et Mdp-->
			<div class="row">
				<div class="col-sm-12 text-center" id="identifiant">

					<div class="row">

						<div class="col-sm-12">
							<label for="saisieId">Identifiant : </label> <input type="text"
								id="saisieId" name="pseudo" autofocus />
						</div>

						<div class="col-sm-12">
							<label for="saisieMdp">Mot de passe : </label> <input
								type="password" id="saisieMdp" name="mdp" />
						</div>

					</div>

				</div>
			</div>
			<!-- Connexion-->
			<div class="row">
				<div class="col-sm-6 text-right">
					<input type="submit" name="connexion" class="btn btn-info" id="connexion" value="Connexion">
				</div>
				<div class="col-sm-6">
					<div class="row">
						<div class="col-md-12">
							<input type="checkbox" id="souvenir" name="souvenir"> <label
								for="souvenir" id="souvenir">Se souvenir de moi</label>
						</div>
						<div class="col-md-12">
							<a href="" id="mdpOubli">Mot de passe oublié</a>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- Créer un compte -->
		<div class="row">
			<div class="col-md-12 text-center">
				<form method="get"
					action="${pageContext.request.contextPath}/ServletInscription">
					<input type="submit" name="compte" class="btn btn-dark" id="compte" value="Créer un compte">
				</form>
			</div>
		</div>

	</div>



</body>
</html>