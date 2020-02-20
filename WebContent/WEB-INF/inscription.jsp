<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inscription | TROC-ENCHERES</title>
<%@include file="style.jsp"%>
</head>
<body>
	<jsp:include page='headerLogo.jsp'>
		<jsp:param name="headerLogo" value="" />
	</jsp:include>

	<!-- FORMULAIRE INSCRIPTION -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h2>Mon profil</h2>
			</div>
		</div>


				<form method="post"
					action="${pageContext.request.contextPath}/Creer">

					<div class="info_perso">
						<div>
							<p>
								<label for="pseudo"> Pseudo : </label> <input type="text"
									name="pseudo" id="pseudo" autofocus required
									pattern="^[a-zA-Z0-9_]*$">

							</p>
							<p>
								<label for="prenom"> Prénom : </label> <input type="text"
									name="prenom" id="prenom" required>
							</p>

							<p>
								<label for="tel"> Téléphone : </label> <input type="tel"
									name="tel" id="tel">
							</p>
							<p>
								<label for="cp"> Code postal : </label> <input type="text"
									name="cp" id="cp" required>
							</p>
							<p>
								<label for="mdp"> Mot de passe : </label> <input type="password"
									name="mdp" id="mdp" required>
							</p>
						</div>
						<div>

							<p>
								<label for="nom"> Nom : </label> <input type="text" name="nom"
									id="nom" required>
							</p>

							<p>
								<label for="email"> Email : </label> <input type="email"
									name="email" id="email" required>
							</p>

							<p>
								<label for="rue"> Rue : </label> <input type="text" name="rue"
									id="rue">
							</p>

							<p>
								<label for="ville"> Ville : </label> <input type="text"
									name="ville" id="ville" required>
							</p>

							<p>
								<label for="mdpconf"> Confirmation : </label> <input
									type="password" name="mdpconf" id="mdpconf" required>
							</p>

						</div>
					</div>
					<div id="create">
						<div>
							<p>
								<input type="submit" class="btn btn-dark" id="createbutton"
									value="Créer">
							</p>
						</div>
						<div>
							<p>
								<a href="${pageContext.request.contextPath}/ServletAccueil"><input
									type="button" class="btn btn-dark" id="createbutton"
									value="Annuler"></a>
							</p>
						</div>

					</div>
				</form>

			</div>

		<%-- 	<form method="get" action="${pageContext.request.contextPath}/ServletAccueil"> --%>
		<!-- <input type="submit" class="btn btn-success"  value="Annuler"> -->
		<!-- </form> -->

</body>
</html>