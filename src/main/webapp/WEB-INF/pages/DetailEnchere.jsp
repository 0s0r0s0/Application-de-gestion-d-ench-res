<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html"%>
<title>Détail</title>
</head>
<body>
	<%@include file="Header.jsp"%>

	<c:if test="${enchere_reussie == true}">
		<div class="alert alert-success" role="alert">Enchère réussie !</div>
	</c:if>
	<c:if test="${created == true}">
		<div class="alert alert-success" role="alert">Article créé</div>
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

	<div class="row justify-content-md-center">

		<div class="col-5 g-3 align-items-center">

			<div class="card-body">
				<h3 class=" d-flex justify-content-center mb-4">Détail vente</h3>
				<h5 class="card-title">${article.nomArticle}</h5>
				<p>Description : ${article.description}</p>
				<p>Catégorie : ${article.categorie.libelle }</p>
				<p>
					Meilleure offre :
					<c:choose>
						<c:when test="${not empty enchere.montantEnchere}">${enchere.montantEnchere}</c:when>
						<c:otherwise>Pas encore d'offres</c:otherwise>
					</c:choose>
				</p>
				<p>Mise à prix : ${article.prixInitial } points</p>
				<p>Fin de l'enchère : 
					<fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd" var="patientDob" type="date"/>
					<fmt:formatDate pattern="dd/MM/yyyy" value="${patientDob}"/>
				</p>
				<p>Retrait : <c:out value="${sessionScope.user.rue}"/>, <c:out value="${sessionScope.user.codePostal}"/> <c:out value="${sessionScope.user.ville}"/> </p>
				<p>Vendeur : ${article.utilisateur.pseudo}</p>
				<c:if
					test="${sessionScope.user.noUtilisateur != article.utilisateur.noUtilisateur}">
					<p>Ma proposition :
					<form method="post"
						action="<c:out value="${pageContext.servletContext.contextPath}" />/encherir">
						<input type="hidden" name="id_article"
							value="${article.noArticle}"><input type="number"
							value="${miseMini}" name="nouveau_prix">
						<button type="submit" class="btn btn-outline-info btn-sm ml-4"
							style="width: 15rem;">Enchérir</button>
					</form>
					</p>
				</c:if>
				<c:if
					test="${sessionScope.user.noUtilisateur == article.utilisateur.noUtilisateur}">
					<p>
						<a
							href="<c:out value="${pageContext.servletContext.contextPath}" />/modifier?id=
							${article.noArticle}" class="btn btn-outline-primary btn-block mt-5">Modifier</a>
					</p>
				</c:if>
				<a href="<c:out value="${pageContext.servletContext.contextPath}" />"
					class="btn btn-primary mt-5" type="button" role="button">Retour</a>
			</div>
		</div>

</div>


</body>
</html>