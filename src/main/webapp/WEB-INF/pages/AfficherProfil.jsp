<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html"%>
<title>Profil de <c:out value="${profil.pseudo}" />
</title>
</head>
<body>
	<%@include file="Header.jsp"%>

	<div class="container">
		<div class="row">
			<div class="col">
					<c:choose>
						<c:when test="${sessionScope.user.pseudo == profil.pseudo}">
					<h1>Mon profil</h1>
					<p>Vous avez ${sessionScope.user.credit} crédits</p>
				</c:when>
						<c:otherwise>
					<h1>Profil de
					<c:out value="${profil.pseudo}" /></h1>
						</c:otherwise>
					</c:choose>

			</div>
		</div>
		<div class="row">
			<div class="col">
				<p>Pseudo :</p>
				<p>Nom :</p>
				<p>Prénom :</p>
				<p>Email :</p>
				<p>Téléphone :</p>
				<p>Rue :</p>
				<p>Code Postal :</p>
				<p>Ville</p>
			</div>
			<div class="col">
				<p>
					<c:out value="${profil.pseudo}" />
				</p>
				<p>
					<c:out value="${profil.nom}" />
				</p>
				<p>
					<c:out value="${profil.prenom}" />
				</p>
				<p>
					<c:out value="${profil.email}" />
				</p>
				<p>
					<c:out value="${profil.telephone}" />
				</p>
				<p>
					<c:out value="${profil.rue}" />
				</p>
				<p>
					<c:out value="${profil.codePostal}" />
				</p>
				<p>
					<c:out value="${profil.ville}" />
				</p>
			</div>

		</div>
		<div class="row">
			<div class="col">
				<c:if test="${sessionScope.user.pseudo == profil.pseudo}">
					<a class="btn btn-outline-primary"
						href="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/editer">Modifier</a>
				</c:if>
					<a class="btn btn-outline-primary"
						href="<c:out value="${pageContext.servletContext.contextPath}"/>/">Retour</a>
			</div>
		</div>




	</div>

</body>
</html>