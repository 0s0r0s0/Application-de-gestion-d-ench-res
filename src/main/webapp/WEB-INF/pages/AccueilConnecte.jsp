<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html" %>
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
						<div class="row" style="margin-left: 3px">
								<div class="form-check">
								  <input class="form-check-input" type="radio" name="radioOptions" id="achats" value="achats" checked onclick="achatsSelected()">
								  <label class="form-check-label" for="inlineRadio1">Achats</label>
								  	<div class="form-check">
									  <input class="form-check-input" type="checkbox" value="encheresOuvertes" id="encheresOuvertes" name="encheresOuvertes" checked>
									  <label class="form-check-label" for="encheresOuvertes">Enchères ouvertes</label>
									</div>
									<div class="form-check">
									  <input class="form-check-input" type="checkbox" value="mesEncheresEnCours" id="mesEncheresEnCours" name="mesEncheresEnCours" >
									  <label class="form-check-label" for="mesEncheresEnCours">Mes enchères en cours</label>
									</div>
									<div class="form-check">
									  <input class="form-check-input" type="checkbox" value="mesEncheresRemportees" id="mesEncheresRemportees" name="mesEncheresRemportees">
									  <label class="form-check-label" for="mesEncheresRemportees">Mes enchères remportées</label>
									</div>
								</div>
								<div class="form-check ">
								  <input class="form-check-input" type="radio" name="radioOptions" id="ventes" value="ventes" onclick="ventesSelected()">
								  <label class="form-check-label" for="inlineRadio2">Mes Ventes</label>
								  <div class="form-check">
									  <input class="form-check-input" type="checkbox" value="mesVentes" id="mesVentes" name="mesVentes" disabled >
									  <label class="form-check-label" for="mesVentes">Mes ventes en cours</label>
									</div>
									<div class="form-check">
									  <input class="form-check-input" type="checkbox" value="ventesNonDebutees" id="ventesNonDebutees" name="ventesNonDebutees" disabled>
									  <label class="form-check-label" for="ventesNonDebutees">Ventes non débutées</label>
									</div>
									<div class="form-check">
									  <input class="form-check-input" type="checkbox" value="ventesTerminee" id="ventesTermine" name="ventesTermine" disabled>
									  <label class="form-check-label" for="ventesTermine">Ventes terminées</label>
									</div>
								</div>
								<div class="col">
									<button type="submit" class="btn btn-primary" style="margin-top: 4rem; float: right ">Recherche</button>	
								</div>
						</div>
								
					    	
						</div>
				</div>
				
		    </form>	
		</section> 
	 </div>
	<!-- Afficher article -->
	<div class="container">				
		<div class="row">
			<c:if test="${empty listeArticles}">
				<c:if test="${empty listeEncheres}">
					<div class="alert alert-warning" role="alert">Aucun résultat ! Merci d'effectuer une autre recherche</div>
				</c:if>
			</c:if>
			<c:if test="${!empty listeArticles }">
				<c:forEach var ="article" items="${listeArticles}">
					<div class="col-4">
						<div class="card" style="width: 18rem; margin-top: 4rem;">
						  <div class="card-body">
						  	<h5 class="card-title"> Article :  <a href="<c:out value="${pageContext.servletContext.contextPath}"/>/detail?id=${article.noArticle }">${article.nomArticle}</a></h5>
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
						    <h6 class="card-subtitle mb-4 text-muted">Vendeur : <a href="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/<c:out value="${article.utilisateur.pseudo }" />"><c:out value ="${article.utilisateur.pseudo}"/></a></h6>
						    <p class="card-text">Description : ${article.description}</p>
						  </div>
						</div>
						
					</div>
					
				</c:forEach> 
				</c:if>
				<c:if test="${!empty listeEncheres }">
				<c:forEach var ="enchere" items="${listeEncheres}">
					<div class="col-4">
						<div class="card" style="width: 18rem; margin-top: 4rem;">
						  <div class="card-body">
						  	<h5 class="card-title"> Article :  <a href="<c:out value="${pageContext.servletContext.contextPath}"/>/detail?id=${enchere.article.noArticle}">${enchere.article.nomArticle}</a></h5>
						    <h6 class="card-subtitle mb-2 text-muted">Prix : ${enchere.article.prixVente}</h6>
						    <h6 class="card-subtitle mb-4 text-muted">Date fin enchère : 
						    		<fmt:parseDate value="${enchere.article.dateFinEncheres}" pattern="yyyy-MM-dd" var="dateFin" type="date"/>
									<fmt:formatDate pattern="dd/MM/yyyy" value="${dateFin}"/>
						    </h6>
						    <h6 class="card-subtitle mb-4 text-muted">Vendeur : <a href="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/<c:out value="${enchere.article.utilisateur.pseudo }" />"><c:out value ="${enchere.article.utilisateur.pseudo}"/></a></h6>
						    <p class="card-text">Description : ${enchere.article.description}</p>
						  </div>
						</div>
						
					</div>
					
				</c:forEach> 
				</c:if>
			</div>

 </div>

</body>
<script type="text/javascript">



function ventesSelected(){
	document.getElementById("mesVentes").removeAttribute("disabled");
	document.getElementById("ventesNonDebutees").removeAttribute("disabled");
	document.getElementById("ventesTermine").removeAttribute("disabled");
	document.getElementById("encheresOuvertes").checked = false;
	document.getElementById("mesEncheresEnCours").checked = false;
	document.getElementById("mesEncheresRemportees").checked = false;
	document.getElementById("encheresOuvertes").setAttribute("disabled", "disabled");
	document.getElementById("mesEncheresEnCours").setAttribute("disabled", "disabled");
	document.getElementById("mesEncheresRemportees").setAttribute("disabled", "disabled");
}

function achatsSelected(){
	document.getElementById("encheresOuvertes").removeAttribute("disabled");
	document.getElementById("mesEncheresEnCours").removeAttribute("disabled");
	document.getElementById("mesEncheresRemportees").removeAttribute("disabled");
	document.getElementById("mesVentes").checked = false;
	document.getElementById("ventesNonDebutees").checked = false;
	document.getElementById("ventesTermine").checked = false;
	document.getElementById("mesVentes").setAttribute("disabled", "disabled");
	document.getElementById("ventesNonDebutees").setAttribute("disabled", "disabled");
	document.getElementById("ventesTermine").setAttribute("disabled", "disabled");
}


</script>
</html>
