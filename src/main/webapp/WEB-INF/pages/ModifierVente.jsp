<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html"%>
<title>Modifier article</title>
</head>
<body>
	<%@include file="Header.jsp"%>
	<div class="container">
		<div class=row>
			<div class="col">
				<h1>Modifier article</h1>
			</div>
		</div>
		<c:if test="${updated == true}">
			<div class="alert alert-success" role="alert">Article mis à jour</div>
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
			action="<c:out value="${pageContext.servletContext.contextPath}"/>/modifier">
			
			<input type="hidden" name="id_article" value="${article.noArticle}"/>

			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="nom_article">Article</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="nom_article"
						name="nom_article" value="${article.nomArticle}" required>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="description">Description</label>
				<div class="col-sm-10">
					<textarea class="form-control" id="description" name="description"
						rows="3" cols="5" required>${article.description}</textarea>
				</div>
			</div>

			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="categorie">Catégorie</label>
				<div class="col-sm-10">
					<select id="categorie" name="categorie" class="custom-select">
						<option value="0">Choisir une catégorie</option>
						<c:forEach var="categorie" items="${categories}">
							<option <c:if test="${categorie.noCategorie == article.categorie.noCategorie}">selected</c:if> value="<c:out value="${categorie.noCategorie}"/>"><c:out
									value="${categorie.libelle}" /></option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="mise_prix">Mise à prix</label>
				<div class="col-sm-10">
					<input type="number" class="form-control" id="mise_prix"
						name="mise_prix" value="150" value="${article.prixInitial}" disabled required>
						<input type="hidden" name="mise_prix" value="${article.prixInitial}" />
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="date_debut">Début de l'enchère</label>
				<div class="col-sm-10">
					<input type="date" class="form-control" id="date_debut"
						name="date_debut" value="${article.dateDebutEncheres}" disabled required/>
						<input type="hidden" name="date_debut" value="${article.dateDebutEncheres}" />
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="date_fin">Fin de l'enchère</label>
				<div class="col-sm-10">
					<input type="date" class="form-control" id="date_fin"
						name="date_fin" value="${article.dateFinEncheres}" required>
				</div>
			</div>
			
			<h3>Retrait</h2>
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="rue">Rue</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="rue"
						name="rue" value="<c:out value="${sessionScope.user.rue}" />" required>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="code_postal">Code postal</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="code_postal"
						name="code_postal" value="<c:out value="${sessionScope.user.codePostal}" />" required>
				</div>
			</div>
			
			<div class="form-group row">
				<label class="col-sm-2 col-form-label" for="ville">Ville</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="ville"
						name="ville" value="<c:out value="${sessionScope.user.ville}" />" required>
				</div>
			</div>
			
			<div class="form-group row">
				<div class="col">
					<button class="btn btn-primary" type="submit">Enregistrer</button>
				
					<a class="btn btn-warning" href="<c:out value="${pageContext.servletContext.contextPath}" />/detail?id=<c:out value="${article.noArticle}" />">Annuler</a>
				</div>
			</div>
			
		</form>

	</div>
</body>
</html>