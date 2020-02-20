<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fin de l'enchère</title>
<%@include file="style.jsp"%>
</head>

<%
	request.getAttribute("articleEnchere");
%>
<%
	request.getAttribute("encherisseur");
%>



<!-- Header -->
<jsp:include page='headerLogoConnect.jsp'>
	<jsp:param name="headerLogo" value="" />
</jsp:include>

<body>
	<%!DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime maintenant = LocalDateTime.now();
	String date = maintenant.format(formatter);
	LocalDateTime dateN = LocalDateTime.parse(date, formatter);%>

	<div>
	<!-- User remporte l'enchère -->
		<p>Vous avez remporté l'enchère</p>
	<!-- Meilleur enchérisseur remporte l'enchère -->
		<p>${encherisseur.pseudo} a remporté l'enchère<p><br>
		
		<form method="POST"
			action="${pageContext.request.contextPath}/ServletFinEnchere">

			<!-- Photo de l'article -->
			<div>
				<img id="photoArticle" alt="photoArticle"
					src="<%=request.getContextPath()%>/img/babyoda.jpg">
			</div>

			<!-- Nom de l'article -->
			<div>
				<input type="text" name="nomArticle"
					value="${articleEnchere.nomArticle}" readonly> <input
					type="hidden" name="idArticle" value="${articleEnchere.id}">
			</div>

			<!-- Description de l'article -->
			<div>
				<label>Description : </label>
				<textarea id="textarea" rows="5" cols="30" name="description"
					readonly> ${articleEnchere.description}</textarea>
			</div>

			<!-- Catégorie de l'article -->
			<div>
				<label>Catégorie : </label> <input type="text" name="categorie"
					value="${articleEnchere.categorie.libelle}" readonly>
			</div>

			<!-- Meilleure enchère pour l'article + nom dernier enchérisseur TODO: Selon qui a remporté l'enchère-->
			<div>
				<c:if
					test="${articleEnchere.prixVente > articleEnchere.prixInitial}">
					<!-- récupérer montant enchere -->
					<p>Meilleure offre: ${articleEnchere.prixVente} crédits par ${encherisseur.pseudo}</p>
					<input type="hidden" name="meilleureOffre"
						value="${articleEnchere.prixVente}">
				</c:if>


			</div>

			<!-- Prix initial de l'enchère -->
			<div>
				<p>Mise à prix : ${articleEnchere.prixInitial}</p>
				<input type="hidden" name="prixInitial"
					value="${articleEnchere.prixInitial}">
			</div>

			<!-- Date de fin de l'enchère -->
			<div>
				<p>Fin de l'enchère: ${articleEnchere.dateFinEncheres}</p>
				<input type="hidden" name="finEnchere"
					value="${articleEnchere.dateFinEncheres}">
			</div>
			<!-- 		<label>Fin de l'enchère: </label> <input type=datetime-local -->
			<!-- 			name="finEnchere" id="finEnchere"> <input type="submit" -->
			<%-- 			id="encherir" value="${articleEnchere.dateFinEncheres}"> --%>

			<!-- Adresse pour le retrait de l'article -->
			<div>
				<p>Retrait: ${articleEnchere.retrait.rue}
					${articleEnchere.retrait.codePostal}
					${articleEnchere.retrait.ville}</p>
			</div>

			<!-- Nom du vendeur de l'article -->
			<div>
				<p>Vendeur: ${articleEnchere.utilisateur.pseudo}</p>
				<input type="hidden" name="pseudoVendeur"
					value="${articleEnchere.utilisateur.pseudo}">
			</div>

			<!-- Enchérir -->
			<!-- Cas où l'utilisateur n'est pas le vendeur -->
			<c:if
				test="${articleEnchere.utilisateur.pseudo != utilisateurCourant.pseudo}">
				<input type="hidden" name="idUser" value="${utilisateurCourant.id}">
				<label>Ma proposition: </label>
				<input type="number" name="prop" id="prop"
					min="${articleEnchere.prixVente + 1}">
				<input type="submit" id="encherir" value="Enchérir">
			</c:if>
			<!-- Annuler et retourner à l'Accueil connecté -->
			<a href="<%=request.getContextPath()%>/ServletAccueilConnect"> <input
				type="button" value="Retourner à l'accueil"></a>
		</form>

	<!-- Cas où l'utilisateur est le vendeur -->
		<c:if
			test="${articleEnchere.utilisateur.pseudo == utilisateurCourant.pseudo}">
			<a href=""><input type="submit" id="modifierVente"
				value="Modifier la vente"></a>
			<a href=""><input type="submit" id="annulerVenter"
				value="Annuler la vente"></a>
		</c:if>





		</div>

</body>
</html>






