<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html" %>
<link rel="stylesheet" href="../css/style.css" type="text/css"/>
<title>Accueil</title>
</head>
<body>
<%@include file="Header.jsp" %>



<div class="container">
	<h3 class="text-center">Liste des enchères</h3>	
	<h4>Filtres :</h4>

		<!-- Barre de recherche --> 
	      <section>   
		    <form action="" method="post">
				<div class="row">
			 		<div class="col">
			 		 	<input class="form-control" type="text" placeholder="Rechercher un article" style="margin-top: 3px;" name="recherche">	
				    		 <label>Catégories : </label>
					    		<select class="form-control form-control-sm" style="margin-top: 3px;" name="categorie">
					  				 <option value ="0" >Toutes</option>
					  				 <c:forEach var="cat" items="${listeCategories}">
					  				 	<option value ="<c:out value ="${cat.noCategorie}"/>"><c:out value ="${cat.libelle}"/></option>
					  				 </c:forEach>
								</select> 
					</div>
					<div class="col-4">
						<button type="submit" class="btn btn-primary" style="margin-top: 3px;">Recherche</button>	
			    	</div>
				</div>
		    </form>	
		</section> 
	 
	<!-- Afficher article -->				
		<div class="row">
				<c:forEach var ="article" items="${listeArticles}">
					<div class="col-4">
						<div class="card" style="width: 18rem; margin-top: 4rem;">
						  <div class="card-body">
						  	<h5 class="card-title"> Article :  ${article.nomArticle}</h5>
						    <h6 class="card-subtitle mb-2 text-muted">Prix : 
						    	<c:choose>
						    		<c:when test="${article.prixVente == 0}">${article.prixInitial}</c:when>
						    		<c:otherwise>${article.prixVente}</c:otherwise>
						    	</c:choose>
						    </h6>
						    <h6 class="card-subtitle mb-4 text-muted">Date fin enchère : 
						    	<fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd" var="dateFin" type="date"/>
								<fmt:formatDate pattern="dd/MM/yyyy" value="${dateFin}"/>
						    </h6>
						    <h6 class="card-subtitle mb-4 text-muted">Vendeur : ${article.utilisateur.pseudo}</h6>
						    <p class="card-text">Description : ${article.description}</p>
						  </div>
						</div>
						
					</div>
					
				</c:forEach> 
			</div>

 </div>

</body>
</html>