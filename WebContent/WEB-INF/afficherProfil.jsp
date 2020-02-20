<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="fr.eni.projet.bo.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mon Profil | TROC-ENCHERES</title>
<%@include file="style.jsp"%>
</head>
<%
	session.getAttribute("utilisateurCourant");
%>


<!-- Header -->
<jsp:include page="headerLogoConnect.jsp">
	<jsp:param name="header" value="" />
</jsp:include>

<body>
	<div class="container">

		<div class="row">
			<div class="col-sm-12">
				<h2>Mon Profil</h2>
			</div>

		</div>



		<div class="row">
			<div class="col-sm-12">
				<c:if test="${!empty listeCodesErreur}">
					<div class="alert alert-danger" role="alert">
						<strong>Erreur!</strong>
						<ul>
							<c:forEach var="code" items="${listeCodesErreur}">
								<li>${LecteurMessage.getMessageErreur(code)}</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>

			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">


				<!-- Prendre le pseudo de l'utilisateur en session et l'afficher dans la page  -->
				<label for="pseudo">Pseudo :</label> ${utilisateurCourant.pseudo} <br>

				<!-- Prendre le nom de l'utilsateur en session et l'afficher dans la page  -->
				<label for="nom">Nom :</label> ${utilisateurCourant.nom} <br>
				<p>
					<!-- Prendre le prénom de l'utilisateur en session et l'afficher dans la page  -->
					<label for="prenom">Prénom :</label> ${utilisateurCourant.prenom}
				</p>
				<br>
				<p>
					<!-- Prendre le mail de l'utilsateur en session et l'afficher dans la page  -->
					<label for="email">Email :</label> ${utilisateurCourant.email}
				</p>
				<p>
					<!-- Prendre le téléphone de l'utiiisateur en session et l'afficher dans la page  -->
					<label for="tel">Téléphone :</label>
					${utilisateurCourant.telephone}
				</p>
				<p>
					<!-- Prendre la rue de l'utiklisateur en session  session et l'afficher dans la page  -->
					<label for="rue">Rue :</label> ${utilisateurCourant.rue}
				</p>
				<p>
					<label for="cp">Code Postal :</label>
					${utilisateurCourant.codePostal}
				</p>
				<p>
					<!-- Prendre le code postal de l'utilisateur en session et l'afficher dans la page  -->
					<label for="ville">Ville :</label> ${utilisateurCourant.ville}
				</p>

			</div>
		</div>
		<!-- Après pression du bouton, rediriger vers la méthode get de la servletProfil  -->
		<div class="row" id="modifier">
			<div class="col-md-12 text-center">
				<form method="get"
					action="${pageContext.request.contextPath}/ServletProfil">
					<input type="submit" name="modifier" id="modifierButton"
						class="btn btn-dark" value="Modifier">
				</form>
			</div>

		</div>

	</div>
	<div align="right">
		<form action="<%=request.getContextPath()%>/ServletSaxoGuyGandalf">
			<input type="submit" class="boutonMagique" />
		</form>

	</div>
</body>
</html>