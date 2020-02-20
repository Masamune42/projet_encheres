<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil | TROC-ENCHERES</title>
</head>
<header class="container-fluid header"> 
	<div class="container-fluid ">
		<div class="logo-header">
			<a href="<%=request.getContextPath()%>/ServletAccueilConnect"><img src="img/gavel.jpg" class="logo"></a>
		</div>
		<div class="connect">
<a href="<%=request.getContextPath()%>/ServletAccueilConnect">Enchères</a>
<a href="<%=request.getContextPath()%>/ServletNouvelleVente">Vendre un article</a>
<a href="<%=request.getContextPath()%>/ServletAfficherProfil">Mon espace</a>
<a href="<%=request.getContextPath()%>/ServletDeconnexion">Déconnexion</a>
		</div>

	</div>
</header>