<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil | TROC-ENCHERES</title>
<jsp:include page='style.jsp'>
    <jsp:param name="style" value=""/>
</jsp:include>

</head>

<% session.getAttribute("utilisateurCourant");
request.getAttribute("listeCategoriesCourante"); 
 %>
 
 <!-- Header --> 
<jsp:include page="headerLogoConnect.jsp">
    <jsp:param name="header" value=""/>
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
		
		
<header>

<div class = "welcomeUser">
<% if(session.getAttribute("utilisateurCourant") !=null) {
 %> 
<p>Bonjour, ${utilisateurCourant.pseudo} !</p>
<p>Vous avez : ${utilisateurCourant.credit} crédits.</p>
<%} %>
</div>

<h2>Liste des enchères</h2>
</header>


	<!-- Zone de recherche par catégorie -->
	<div class="filtres col-md-12 text-center">

		<form method="post"
			action="<%=request.getContextPath()%>/ServletArticles">
			<label>Filtres:</label> <input type="search" name="search"
				placeholder="Rechercher"> <label>Catégorie</label> <select
				name="cat" id="cat" class="btn btn-info dropdown-toggle">
				<option value="Toutes" selected>Toutes</option>
				<c:forEach var="listeCat" items="${listeCategoriesCourante}">
					<option><c:out value="${listeCat.libelle}"></c:out></option>
				</c:forEach>
			</select>
			<div class="col-md-12">
				<input type="submit" name="rechercher" id="recherche"
					class="btn btn-dark" value="Rechercher">

			</div>
			<!-- Zone Achats / Ventes -->
			<div class="container pt-5">
				<div class="row">
					<div class="col-md-5 m-3 text-center">
						<input type="radio" name="radio" id="radioAchat"
							onclick="disabledVentes()" checked> <b>Achats</b> <br>
						<input type="checkbox" name="enchereOuverte" id="enchereOuverte"
							onclick="disabledVentes()"> Enchères ouvertes <br> <input
							type="checkbox" name="enchereEnCours" id="enchereEnCours"
							onclick="disabledVentes()"> Mes enchères en cours <br>
						<input type="checkbox" name="enchereRemportee"
							id="enchereRemportee" onclick="disabledVentes()"> Mes
						enchères remportées <br>
					</div>

					<div class="col-md-5 m-3 text-center">
						<input type="radio" name="radio" id="radioVente"
							onclick="disabledAchat()"><b> Mes Ventes</b><br> <input
							type="checkbox" name="venteEnCours" id="venteEnCours"
							onclick="disabledAchat()"> Mes ventes en cours <br>
						<input type="checkbox" name="venteNonDebutees"
							id="venteNonDebutees" onclick="disabledAchat()"> Mes
						ventes non débutées <br> <input type="checkbox"
							name="venteTerminees" id="venteTerminees"
							onclick="disabledAchat()"> Ventes terminées <br>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- Zone de la liste des enchères présentant une liste d'articles -->
	<h2>Enchères en cours :</h2>
	<div class="container pt-5">
		<div class="row">
			<div class="col-12 inline-block">
				<c:choose>
					<c:when test="${listeEncheresCourante.size()>0}">
						<ul>
							<c:forEach var="liste" items="${listeEncheresCourante}">
								<div class="row d-sm-inline-block">
									<div
										class="col-md-10 py-2 card h-100 text-black bg-white m-3 border border-secondary">
										<div class="card-header bg-secondary text-white">${liste.nomArticle}</div>
										<div class="card-body text-left">
											<img id="photoArticle" alt="photoArticle"
												src="<%=request.getContextPath()%>/ServletAffichageImage?nomImage=${liste.urlImage}&idArticle=${liste.id}&categorie=${liste.categorie.id}">

											<p>
												<a
													href="<%=request.getContextPath()%>/ServletDetailsVente?idArticle=${liste.id}">${liste.nomArticle}</a>
											</p>
											<c:choose>
												<c:when test="${liste.prixVente > liste.prixInitial}">
													<p>${liste.prixVente}crédits</p>
												</c:when>
												<c:otherwise>
													<p>${liste.prixInitial}crédits</p>
												</c:otherwise>
											</c:choose>



											<p>
												Début de l'enchère: <br> <input type="datetime-local"
													value="${liste.dateDebutEncheres}" readonly
													style="border: 0">
											</p>
											<p>
												Fin de l'enchère: <br> <input type="datetime-local"
													value="${liste.dateFinEncheres}" readonly style="border: 0">
												<!-- TO DO: changer le nom de la Servlet Profil -->
											</p>
											<p>
												<a
													href="<%=request.getContextPath()%>/ServletUtilisateur?pseudo=${liste.utilisateur.pseudo}">Vendeur:
													${liste.utilisateur.pseudo}</a>
											</p>
											<!-- TO DO = Créer une Servlet Profil qui détermine si le profil est vendeur ou utilisateur -->

										</div>
									</div>
								</div>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<p>Il n'y a pas de liste d'enchères pour le moment.</p>
					</c:otherwise>

				</c:choose>
			</div>
		</div>
	</div>


</body>


<script>
	function disabledVentes() {
		document.getElementById("radioAchat").checked = true;
		document.getElementById("radioVente").checked = false;

		document.getElementById("venteEnCours").disabled = true;
		document.getElementById("venteNonDebutees").disabled = true;
		document.getElementById("venteTerminees").disabled = true;

		document.getElementById("venteEnCours").checked = false;
		document.getElementById("venteNonDebutees").checked = false;
		document.getElementById("venteTerminees").checked = false;

		document.getElementById("enchereOuverte").disabled = false;
		document.getElementById("enchereEnCours").disabled = false;
		document.getElementById("enchereRemportee").disabled = false;

	}

	function disabledAchat() {
		document.getElementById("radioAchat").checked = false;
		document.getElementById("radioVente").checked = true;

		document.getElementById("enchereOuverte").disabled = true;
		document.getElementById("enchereEnCours").disabled = true;
		document.getElementById("enchereRemportee").disabled = true;

		document.getElementById("enchereOuverte").checked = false;
		document.getElementById("enchereEnCours").checked = false;
		document.getElementById("enchereRemportee").checked = false;

		document.getElementById("venteEnCours").disabled = false;
		document.getElementById("venteNonDebutees").disabled = false;
		document.getElementById("venteTerminees").disabled = false;

	}
</script>
</html>