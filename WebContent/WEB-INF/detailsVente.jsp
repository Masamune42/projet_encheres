<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Détails Vente</title>
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
	<div class="container">
		<%!DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime maintenant = LocalDateTime.now();
	String date = maintenant.format(formatter);
	LocalDateTime dateN = LocalDateTime.parse(date, formatter);%>

		<div>

			<div class="row">
				<div class="col-md-12">
					<h2>Détail vente</h2>
					<p>Pour rappel, vous avez : ${utilisateurCourant.credit}
						crédit(s).</p>
				</div>
			</div>


			<form method="POST"
				action="${pageContext.request.contextPath}/ServletDetailsVente">

				<div class="row">
					<!-- Photo de l'article -->
					<div class="col-md-4">
						<img id="photoArticle" alt="photoArticle"
							src="${pageContext.request.contextPath}/ServletAffichageImage?nomImage=${articleEnchere.urlImage}&idArticle=${articleEnchere.id}&categorie=${articleEnchere.categorie.id}">
					</div>

					<div class="col-md-8">
						<!-- Nom de l'article -->
						<div class="row">
							<div class="col-md-12 pb-2">
								<input class="form-control" type="text" name="nomArticle"
									value="${articleEnchere.nomArticle}" readonly> <input
									type="hidden" name="idArticle" value="${articleEnchere.id}">
							</div>

							<!-- Description de l'article -->
							<div class="col-md-12 pb-2">
								<label>Description : </label>
								<textarea class="form-control" id="textarea" rows="5" cols="30" name="description"
									readonly> ${articleEnchere.description}</textarea>
							</div>

							<!-- Catégorie de l'article -->
							<div class="col-md-12 pb-2">
								<label>Catégorie : </label> <input class="form-control" type="text" name="categorie"
									value="${articleEnchere.categorie.libelle}" readonly>
							</div>

							<!-- Meilleure enchère pour l'article + nom dernier enchérisseur -->
							<div class="col-md-12 pb-2">
								<c:if
									test="${articleEnchere.prixVente > articleEnchere.prixInitial}">
									<!-- récupérer montant enchere -->
									<p>Meilleure offre: ${articleEnchere.prixVente} crédits par
										${encherisseur.pseudo}</p>
									<input type="hidden" name="meilleureOffre"
										value="${articleEnchere.prixVente}">
										<input type="hidden" name="encherisseurId"
										value="${encherisseur.id}">
								</c:if>
							</div>

							<!-- Prix initial de l'enchère -->
							<div class="col-md-12 pb-2">
								<p>Mise à prix : ${articleEnchere.prixInitial} crédits</p>
								<input class="form-control" type="hidden" name="prixInitial"
									value="${articleEnchere.prixInitial}">
							</div>

							<!-- Date de fin de l'enchère -->
							<div class="col-md-12 pb-2">
								<p>Fin de l'enchère: ${articleEnchere.dateFinEncheres}</p>
								<input class="form-control" type="hidden" name="finEnchere"
									value="${articleEnchere.dateFinEncheres}">
							</div>
							<!-- 		<label>Fin de l'enchère: </label> <input type=datetime-local -->
							<!-- 			name="finEnchere" id="finEnchere"> <input type="submit" -->
							<%-- 			id="encherir" value="${articleEnchere.dateFinEncheres}"> --%>

							<!-- Adresse pour le retrait de l'article -->
							<div class="col-md-12 pb-2">
								<p>Retrait: ${articleEnchere.retrait.rue}
									${articleEnchere.retrait.codePostal}
									${articleEnchere.retrait.ville}</p>
							</div>

							<!-- Nom du vendeur de l'article -->
							<div class="col-md-12 pb-2">
								<p>Vendeur: ${articleEnchere.utilisateur.pseudo}</p>
								<input type="hidden" name="pseudoVendeur"
									value="${articleEnchere.utilisateur.pseudo}">
							</div>

							<!-- Enchérir -->
							<!-- Cas où l'utilisateur n'est pas le vendeur -->
							<div class="col-md-12 pb-2">
								<c:if
									test="${articleEnchere.utilisateur.pseudo != utilisateurCourant.pseudo}">
									<input type="hidden" name="idUser"
										value="${utilisateurCourant.id}">
									<label>Ma proposition: </label>
									<input class="form-control" type="number" name="prop" id="prop"
										min="${articleEnchere.prixVente + 1}"
										value="${articleEnchere.prixVente + 1}">
									<input class="btn btn-dark" type="submit" id="encherir" value="Enchérir">
								</c:if>
								<!-- Annuler et retourner à l'Accueil connecté -->
								<a href="${pageContext.request.contextPath}/ServletAccueilConnect">
									<input class="btn btn-dark" type="button" value="Retourner à l'accueil">
								</a>
							</div>

						</div>
					</div>
				</div>
			</form>

			<!-- Cas où l'utilisateur est le vendeur -->
			<div class="row">
				<div class="col-md-12">
					<c:if
						test="${articleEnchere.utilisateur.pseudo == utilisateurCourant.pseudo}">
						<a href=""><input type="button" class="btn btn-dark"
								role="button" id="createbutton" value="Modifier la vente" disabled></a>
						<a href=""><input type="button" class="btn btn-dark"
								role="button" id="createbutton" value="Annuler la
								vente" disabled></a>
					</c:if>
				</div>

			</div>

		</div>

	</div>
</body>
</html>