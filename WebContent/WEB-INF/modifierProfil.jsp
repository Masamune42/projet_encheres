<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modifier Profil | TROC-ENCHERES</title>
<!-- Inclure la page de style avec une balise jsp:include pour permettre la transformationn et compilation automatique de la jsp -->
<jsp:include page="style.jsp"></jsp:include>
</head>



<!-- Header -->
<jsp:include page="headerLogoConnect.jsp">
	<jsp:param name="header" value="" />
</jsp:include>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h2>Modifier Mon Profil</h2>
				<c:if test="${Mot==1}">
					<div class="alert alert-danger" role="alert">
						<strong>Le nouveau mot de passe et le mot de passe de
							confirmation ne sont pas identiques</strong>
					</div>
				</c:if>
			</div>
		</div>

		<form method="post"
			action="${pageContext.request.contextPath}/ServletProfil">
			<div class="info_perso">
				<div>
					<p>

						<label for="saisiePseudo">Pseudo :</label><input type="text"
							id="saisiePseudo" name="pseudo"
							value="${utilisateurCourant.pseudo}" autofocus required pattern="[a-zA-Z0-9]{4,10}$">

					</p>
					<p>
						<label for="saisiePrenom">Prénom :</label><input type="text"
							id="saisiePrenom" name="prenom"
							value="${utilisateurCourant.prenom}" required>
					</p>
					<p>

						<label for="saisieTel">Téléphone :</label><input type="tel"
							id="saisieTel" name="tel" value="${utilisateurCourant.telephone}">

					</p>
					<p>
						<label for="saisieCp">CodePostal :</label><input type="text"
							id="saisieCp" name="cp" value="${utilisateurCourant.codePostal}"
							required>
					</p>
					<p>
						<label for="mdp">Mot de passe actuel :</label><input
							type="password" id="mdp" name="mdp" required>
					</p>
					<p>
						<label for="nvMdp">Nouveau Mot de passe :</label><input
							type="password" id="nvMdp" name="nvMdp" required>
					</p>

					<p>

						<label for="credit">Crédit(s) :
							${utilisateurCourant.credit}</label>
					</p>

					<p>
						<input type="submit" name="enregistrer" class="btn btn-dark"
							id="recordbutton" value="Enregistrer">
					</p>

				</div>

				<div>
					<p class="text-center">
						<label for="saisieNom">Nom :</label><input type="text"
							id="saisieNom" name="nom" value="${utilisateurCourant.nom}"
							required>
					</p>


					<p>
						<label for="saisieEmail">Email :</label><input type="email"
							id="saisieEmail" name="email" value="${utilisateurCourant.email}"
							required>
					</p>

					<p>
						<label for="saisieRue">Rue :</label><input type="text"
							id="saisieRue" name="rue" value="${utilisateurCourant.rue}">
					</p>

					<p>

						<label for="saisieVille">Ville :</label><input type="text"
							id="saisieVille" name="ville" value="${utilisateurCourant.ville}"
							required>
					</p>

					<p></p>
					<p>
						<label for="confMdp">Confirmation :</label><input type="password"
							id="confMdp" name="confMdp" required>
					</p>
				</div>




			</div>

		</form>

		<form method="post"
			action="${pageContext.request.contextPath}/ServletDeconnexion">
			<div class="row">
				<div class="col-sm-12 text-left">

					<div>
						<label for="mdp"></label><input type="hidden" id="mdp" name="mdp">
					</div>
					<div>
						<button type="submit" name="supp" class="btn btn-dark"
							id="deletebutton"
							onclick="return confirm('Etes vous sur de vouloir supprimer :${utilisateurCourant.pseudo}')">
							Supprimer mon compte</button>
					</div>

				</div>
			</div>
		</form>


	</div>
</body>
</html>