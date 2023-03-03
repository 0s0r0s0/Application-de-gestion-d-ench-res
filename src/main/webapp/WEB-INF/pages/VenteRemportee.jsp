<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html"%>
<title>Détail </title>
</head>
<body>
<%@include file="Header.jsp"%>



<div class="row justify-content-md-center">
				
	<div class="row g-3 align-items-center">
	
		<c:choose>
			<c:when test="${sessionScope.user.noUtilisateur == article.utilisateur.noUtilisateur}"> <!-- Si User connecté et proprio de l'article -->
				<!-- toto à remporté l'enchère -->
				<div class="card-body">
			   		<h3 class=" d-flex justify-content-center mb-4">${utilisateur.pseudo} a remporté la vente</h3>
			    	<h5 class="card-title">${article.nomArticle}</h5>
			    	<p>Description : ${article.description}</p>
			    	<p>Meilleur offre : ${enchere.montantEnchere} pts par <a href="<c:out value="${pageContext.servletContext.contextPath}"/>/profil/<c:out value="${utilisateur.pseudo}" />"><c:out value ="${utilisateur.pseudo}"/></a></p>
			    	<p>Mise à prix : ${article.prixInitial } points</p>
			    	<p>Fin de l'enchère : 
			    		<fmt:parseDate value="${article.dateFinEncheres}" pattern="yyyy-MM-dd" var="dateFin" type="date"/>
						<fmt:formatDate pattern="dd/MM/yyyy" value="${dateFin}"/>
			    	</p>
			    	<p>Retrait : ${article.utilisateur.rue}	${article.utilisateur.codePostal} ${article.utilisateur.ville}</p>
			    	<p>Vendeur : ${article.utilisateur.pseudo}</p>
			    	<p>Téléphone : ${article.utilisateur.telephone}</p>
			    	
			    	<a href="<%=request.getContextPath()%>/" class="btn btn-primary btn-block mt-5" type="button" role="button">Retrait effectué</a>
		  		</div>
			
			</c:when>
			<c:otherwise>
			
			<div class="card-body">
		   		<h3 class=" d-flex justify-content-center mb-4">Vous avez remporté la vente</h3>
		    	<h5 class="card-title">${article.nomArticle}</h5>
		    	<p>Description : ${article.description}</p>
		    	<p>Meilleur offre : ${enchere.montantEnchere} pts</p>
		    	<p>Mise à prix : ${article.prixInitial} points</p>
		    	<p>Retrait : ${article.utilisateur.rue}	${article.utilisateur.codePostal} ${article.utilisateur.ville}</p>
		    	<p>Vendeur : ${article.utilisateur.pseudo}</p>
		    	<p>Téléphone : ${article.utilisateur.telephone}</p>
		    	
		    	<a href="<%=request.getContextPath()%>/" class="btn btn-primary btn-block mt-5" type="button" role="button">Retour</a>
		  	</div>
			
			</c:otherwise>
		</c:choose>

	  	
	</div>
		

</div>


</body>
</html>