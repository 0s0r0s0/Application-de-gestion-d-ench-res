<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Inscription</title>
<%@include file="InclusionDeBootstrap.html"%>
</head>
<body>
	<%@include file="Header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Inscription</h1>
			</div>
		</div>

		<c:if test="${connected == true}">
			<div class="alert alert-success" role="alert">Inscription
				réussie !</div>
		</c:if>

		<c:if test="${not empty erreurs}">
			<div class="row">
				<div class="col">
					<div class="alert alert-danger" role="alert">
						<p>
							Erreurs<br>
							<c:forEach var="erreur" items="${erreurs}">
								<c:out value="${erreur}"></c:out>
								<br />

							</c:forEach>
						</p>
					</div>
				</div>
			</div>
		</c:if>


		<form method="post"
			action="<c:out value="${pageContext.servletContext.contextPath}"/>/inscription">
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label for="pseudo">Pseudo</label> <input type="text"
							class="form-control" id="pseudo" name="pseudo" value="${utilisateur.pseudo}" required>
					</div>
					<div class="form-group">
						<label for="nom">Nom</label> <input type="text"
							class="form-control" id="nom" name="nom" value="${utilisateur.nom}" required>
					</div>
					<div class="form-group">
						<label for="prenom">Prénom</label> <input type="text"
							class="form-control" id="prenom" name="prenom" value="${utilisateur.prenom}" required>
					</div>
					<div class="form-group">
						<label for="email">Email</label> <input type="email"
							class="form-control" id="email" name="email" value="${utilisateur.email}" required>
					</div>
					<div class="form-group">
						<label for="password">Mot de passe</label> <input type="password"
							class="form-control" id="password" name="password" required>
					</div>


				</div>
				<div class="col">
					<div class="form-group">
						<label for="telephone">Téléphone</label> <input type="tel"
							class="form-control" id="telephone" name="telephone" value="${utilisateur.telephone}" required>
					</div>
					<div class="form-group">
						<label for="rue">Rue</label> <input type="text"
							class="form-control" id="rue" name="rue" value="${utilisateur.rue}" required>
					</div>
					<div class="form-group">
						<label for="code_postal">Code Postal</label> <input type="text"
							class="form-control" id="code_postal" name="code_postal" value="${utilisateur.codePostal}" required>
					</div>
					<div class="form-group">
						<label for="ville">Ville</label> <input type="text"
							class="form-control" id="ville" name="ville" value="${utilisateur.ville}" required>
					</div>
					<div class="form-group">
						<label for="verification_password">Vérification mot de passe</label> <input type="password"
							class="form-control" id="verification_password" name="verification_password" required>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" class="btn btn-primary float-right">Créer</button>
				</div>
				<div class="col">
					<a href="${sessionScope}" class="btn btn-outline-primary">Annuler</a>
				</div>

			</div>
		</form>
	</div>





</body>
</html>