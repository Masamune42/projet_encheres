<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil | TROC-ENCHERES</title>

<jsp:include page='style.jsp'>
	<jsp:param name="style" value="" />
</jsp:include>
</head>
<!-- Header -->
<jsp:include page="header.jsp">
	<jsp:param name="header" value="" />
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





	<div class="container">

		<div class="row">
			<div class="col-md-12">
				<h2>Liste des enchères</h2>
			</div>
		</div>


		<!-- Section Filtre avec Recherche par nom ou par Catégorie -->

		<form method="post" action="/ServletArticles">
			<div class="row">
				<section class="col-md-4">
					<div class="row">
						<div class="col-md-6">
							<h3>Filtres:</h3>


							<input type="search" name="search" id="search"
								placeholder="Rechercher">
						</div>
						<div class="col-md-12" id="categorie">
							<label id="cat">Catégorie : </label> <select name="cat" id="cat"
								class="btn btn-info dropdown-toggle">
								<option value="Toutes" selected>Toutes</option>
								<c:forEach var="listeCat" items="${listeCategoriesCourante}">
									<option><c:out value="${listeCat.libelle}"></c:out></option>
								</c:forEach>
							</select>
						</div>
					</div>
				</section>
				<div class="col-md-8">
					<input type="submit" name="search" id="recherche"
						class="btn btn-dark" value="Rechercher">
				</div>
			</div>
		</form>

		<div class="row">
			<div class="col-sm-12">
				<p>Enchères en cours :</p>
			</div>
		</div>

		<div class="container pt-5">
			<div class="row">
				<div class="col-12 inline-block">
					<c:choose>
						<c:when test="${listeEncheresCourante.size()>0}">
							<c:forEach var="liste" items="${listeEncheresCourante}">
								<div class="row d-sm-inline-block">
									<div
										class="col-md-10 py-2 card h-100 text-black bg-white m-3 border border-secondary">
										<div class="card-header bg-secondary text-white">${liste.nomArticle}</div>
										<div class="card-body text-left">
											<img id="photoArticle" alt="photoArticle"
												src="<%=request.getContextPath()%>/ServletAffichageImage?nomImage=${liste.urlImage}&idArticle=${liste.id}&categorie=${liste.categorie.id}">
											<c:choose>
												<c:when test="${liste.prixVente > liste.prixInitial}">
													<p>${liste.prixVente}crédits</p>
												</c:when>
												<c:otherwise>
													<p>${liste.prixInitial}crédits</p>
												</c:otherwise>
											</c:choose>
											<p>
												<b>Début de l'enchère:</b>
											</p>
											<input type="datetime-local"
												value="${liste.dateDebutEncheres}" readonly
												style="border: 0">
											<p>
												<b>Fin de l'enchère:</b>
											</p>
											<input type="datetime-local" value="${liste.dateFinEncheres}"
												readonly style="border: 0">
											<p>
												<b>Vendeur:</b> ${liste.utilisateur.pseudo}
											</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-md-12">
									<p>Il n'y a pas de liste d'enchères pour le moment.</p>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
<%-- <%@include file="footer.jsp"%> --%>
</html>