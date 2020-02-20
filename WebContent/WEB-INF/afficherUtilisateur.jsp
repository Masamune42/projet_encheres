<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profil | TROC-ENCHERES</title>
<!-- Inclure la page de style avec une balise jsp:include pour permettre la transformationn et compilation automatique de la jsp -->
<jsp:include page="style.jsp"></jsp:include>
</head>

<!-- Header -->
<jsp:include page='headerLogoConnect.jsp'>
	<jsp:param name="headerLogo" value="" />
</jsp:include>

<body>

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


	<p>
		<!-- Prendre le pseudo de l'utilisateur en session et l'afficher dans la page  -->
		<label for="pseudo">Pseudo :</label> ${utilisateurVendeur.pseudo}
	</p>
	<p>
		<!-- Prendre le nom de l'utilsateur en session et l'afficher dans la page  -->
		<label for="nom">Nom :</label> ${utilisateurVendeur.nom}
	</p>
	<p>
		<!-- Prendre le prénom de l'utilisateur en session et l'afficher dans la page  -->
		<label for="prenom">Prénom :</label> ${utilisateurVendeur.prenom}
	</p>
	<p>
		<!-- Prendre le mail de l'utilsateur en session et l'afficher dans la page  -->
		<label for="email">Email :</label> ${utilisateurVendeur.email}
	</p>
	<p>
		<!-- Prendre le téléphone de l'utiiisateur en session et l'afficher dans la page  -->
		<label for="tel">Téléphone :</label> ${utilisateurVendeur.telephone}
	</p>
	<p>
		<!-- Prendre la rue de l'utiklisateur en session  session et l'afficher dans la page  -->
		<label for="rue">Rue :</label> ${utilisateurVendeur.rue}
	</p>
	<p>
		<label for="cp">Code Postal :</label> ${utilisateurVendeur.codePostal}
	</p>
	<p>
		<!-- Prendre le code postal de l'utilisateur en session et l'afficher dans la page  -->
		<label for="ville">Ville :</label> ${utilisateurVendeur.ville}
	</p>

</body>
</html>