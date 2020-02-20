<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<jsp:include page="style.jsp"></jsp:include>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nouvelle vente</title>





<!-- Style permettant de dimensionner l'image de l'article -->

<style>
#preview {
	width: 200px;
	display: inline-block;
}

#preview img {
	width: 100%;
}
</style>



</head>

<!-- Header -->
<jsp:include page="headerLogoConnect.jsp">
	<jsp:param name="header" value="" />
</jsp:include>

<body>
	<div class="container">
		<%!DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	LocalDateTime maintenant = LocalDateTime.now();
	String date = maintenant.format(formatter);
	LocalDateTime dateN = LocalDateTime.parse(date, formatter);%>



		<div class="row">
			<div class="col-md-12">
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
			<div class="col-md-12">
				<h2>Nouvelle vente</h2>
			</div>
		</div>


		<form method="post"
			action="${pageContext.request.contextPath }/ServletNouvelleVente"
			name="test" enctype="multipart/form-data">

			<div class="row">
				<!-- Affichage de l'image -->
				<div class="col-md-4">
					<label for="upload"><span id="preview"></span></label>
				</div>
				
				<div class="col-md-8">
					<!-- Article -->
					<div class="row">
						<div class="col-md-12 pb-5">
							<label>Article: </label> <input class="form-control" type="text"
								placeholder="Entrer le nom de votre article" name="nomArticle"
								autofocus required>
						</div>



						<!-- Description -->

						<div class="col-md-12 pb-5">
							<label>Description: </label>
							<textarea class="form-control" rows="5" cols="30"
								placeholder="Entrer la description de votre article"
								name="descriptionArticle" ></textarea>
							<br>
						</div>

						<!-- Upload de la photo -->
						<div class="col-md-12 pb-5">
							<label>Photo de l'article</label> <input class="form-control" type="file"
								onchange="handleFiles(files)" id="upload" name="file"><br>
						</div>


						<!-- Catégorie -->
						<div class="col-md-12 pb-5">
							<label>Catégorie: </label> 
							<select class="form-control" name="categorieArticle"
								required>
								<c:forEach var="c" items="${listeCategoriesCourante}">
									<option>
										<c:out value="${c.libelle}"></c:out></option>
								</c:forEach>
							</select> 
						</div>


						<!-- Mise à prix -->
						<div class="col-md-12 pb-5">
							<label>Mise à prix</label> <input class="form-control" type="number"
								name="prixInitial" min="0" max="1000" step="1" value="1"
								required> 
						</div>

						<!-- Début enchère -->
						<div class="col-md-12 pb-5">
							<label>Début de l'enchère</label> <input class="form-control" type="datetime-local"
								name="dateDebutEnchere" id="dateDebutEnchere" value="<%=dateN%>"
								min="<%=dateN%>" required> 
								
								 <label>Fin de l'enchère</label> <input class="form-control" type="datetime-local" name="dateFinEnchere"
								id="dateFinEnchere" onfocus="get_value()" min="get_value()"
								required>
						</div>

						<!-- Encart retrait -->
						<div class="col-md-12 pb-5">
							<fieldset class="border p-2">
								<legend class="w-auto"> Adresse de retrait </legend>

								<label>Rue :</label> <input class="form-control" type="text"
									value="${utilisateurCourant.rue }" name="retraitRueArticle">
								<br> <label>Code postal :</label> <input class="form-control" type="text"
									value="${utilisateurCourant.codePostal }"
									name="retraitCodePostalArticle"> <br> <label>Ville
									:</label> <input class="form-control" type="text" value="${utilisateurCourant.ville }"
									name="retraitVilleArticle">
							</fieldset>
						</div>

						<div id="create">
							<div>
								<p>
									<input type="submit" name="enregistrerNouvelleVente"
										class="btn btn-dark" id="recordbutton"
										value="Enregistrer la vente">
								</p>
							</div>
							<div>
								<p>
									<a
										href="${pageContext.request.contextPath}/ServletAccueilConnect"><input
										type="button" class="btn btn-dark" id="recordbutton"
										value="Annuler"></a>
								</p>
							</div>

						</div>


					</div>

				</div>
			</div>
		</form>



		<script>
			function handleFiles(files) {
				var imageType = /^image\//;
				for (var i = 0; i < files.length; i++) {
					var file = files[i];
					if (!imageType.test(file.type)) {
						alert("veuillez sélectionner une image");
					} else {
						if (i == 0) {
							preview.innerHTML = '';
						}
						var img = document.createElement("img");
						img.classList.add("obj");
						img.file = file;
						preview.appendChild(img);
						var reader = new FileReader();
						reader.onload = (function(aImg) {
							return function(e) {
								aImg.src = e.target.result;
							};
						})(img);

						reader.readAsDataURL(file);
					}

				}
			}
		</script>


		<script>
			function get_value() {

				var inv_nrs;
				inv_nrs = document.getElementById('dateDebutEnchere').value;
				document.getElementById('dateFinEnchere').value = inv_nrs;
				document.getElementById('dateFinEnchere').min = inv_nrs;
			}
		</script>

	</div>
</body>
</html>

