<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html" %>
<title>Modifier votre profil</title>
</head>
<body>
<%@include file="Header.jsp" %>

<div class="container">
		<div class="row">
			<div class="col">
				<h1>Mon profil</h1>
			</div>
		</div>

		<form method="post" action="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/editer">
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label for="pseudo">Pseudo</label> <input type="text"
							class="form-control" id="pseudo" name="pseudo" value="${sessionScope.user.getPseudo() }" required>
					</div>
					<div class="form-group">
						<label for="nom">Nom</label> <input type="text"
							class="form-control" id="nom" name="nom" value="${sessionScope.user.getNom() }" required>
					</div>
					<div class="form-group">
						<label for="prenom">Prénom</label> <input type="text"
							class="form-control" id="prenom" name="prenom" value="${sessionScope.user.getPrenom() }" required>
					</div>
					<div class="form-group">
						<label for="email">Email</label> <input type="email"
							class="form-control" id="email" name="email" value="${sessionScope.user.getEmail() }" required>
					</div>
					<div class="form-group">
						<label for="password">Mot de passe actuel</label> <input type="password"
							class="form-control" id="password" name="password" required>
						<c:if test="${ !empty messErreurPass }">
							<p class="text-danger font-weight-bold"> <c:out value="${messErreurPass}"></c:out></p>
						</c:if>
					</div>
					<div class="form-group">
						<label for="password">Nouveau mot de passe</label> <input type="password"
							class="form-control" id="passwordNew" name="passwordNew">
							<c:if test="${ !empty messErreurNewPass }">
							<p class="text-danger font-weight-bold"> <c:out value="${messErreurNewPass}"></c:out></p>
						</c:if>
					</div>

				</div>
				<div class="col" >
					<div class="form-group">
						<label for="telephone">Téléphone</label> <input type="tel"
							class="form-control" id="telephone" name="telephone" value="${sessionScope.user.getTelephone() }" required>
					</div>
					<div class="form-group">
						<label for="rue">Rue</label> <input type="text"
							class="form-control" id="rue" name="rue" value="${sessionScope.user.getRue() }" required>
					</div>
					<div class="form-group">
						<label for="code_postal">Code Postal</label> <input type="text"
							class="form-control" id="code_postal" name="code_postal" value="${sessionScope.user.getCodePostal() }" required>
					</div>
					<div class="form-group">
						<label for="ville">Ville</label> <input type="text"
							class="form-control" id="ville" name="ville" value="${sessionScope.user.getVille() }" required>
					</div>

					<div class="form-group" style="padding-top: 86px">
						<label for="passwordConfirm">Confirmation</label> <input type="password"
							class="form-control" id="passwordConfirm" name="passwordConfirm">
					</div>
				</div>
				
			</div>
			Crédit : ${sessionScope.user.getCredit() } 
			<div class="row mt-4">
				<div class="col">
					<button type="submit" class="btn btn-lg btn-primary ml-5">Enregistrer</button>
					
				</div>

			</div>
		</form>
		<form method="post" action="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/supprimer">
			<button type="submit" class="btn btn-lg btn-danger ml-5 mt-3">Supprimer</button>
		</form>
	</div>


</body>
</html>