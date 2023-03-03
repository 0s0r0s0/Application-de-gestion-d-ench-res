<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="InclusionDeBootstrap.html" %>
<title>Connexion</title>
</head>
<body>
<%@include file="Header.jsp" %>
<c:if test="${ !empty pseudo }">
<p> <c:out value="Pseudo : ${pseudo}"></c:out></p>
<p> <c:out value="le prénom est : ${prenom}"></c:out></p>
<p> <c:out value="Le nom est : ${nom}"></c:out></p>
</c:if>

<div class="row justify-content-md-center">
	<form method="post" action="connexion">
	<div class="row g-3 align-items-center">
	  	<div class="col-auto">
   			 <label for="identifiant" class="col-form-label">Identifiant :</label>
  		</div>
		<div class="col-auto ml-4">
	    <input type="text" class="form-control" id="identifiant" name="identifiant" required>
	  </div>
  	</div>
  	<div class="row g-3 align-items-center mt-4 mb-4">
	  <div class="col-auto">
	    <label for="motDePasse" class="col-form-label">Mot de passe :</label>
	  </div>
	  <div class="col-auto">
	   	<input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
	  </div> 
  	</div>
  	<div class="row g-3 align-items-center mt-4 mb-4">
	  <div class="col-auto">
	    <button type="submit" class="btn btn-success btn-lg mr-5">Connexion</button>
	    <c:if test="${ !empty messErreur }">
			<p class="text-danger font-weight-bold"> <c:out value="${messErreur}"></c:out></p>
		</c:if>
	  </div>
	  <div class="col-auto">
	   	<div><input type="checkbox" class="form-check-input" id="SeSouvenirDeMoi">
	    <label class="form-check-label" for="seSouvenirDeMoi">Se souvenir de moi</label></div>
	    <div><a href="#">Mot de passe oublié</a></div>
	  </div> 
  	</div>
  	<div class="row g-3 align-items-center">
  		<a href="<%=request.getContextPath()%>/inscription" class="btn btn-primary btn-lg btn-block mt-5" type="button" role="button">Créer un compte</a>
	
	</div>
	</form>

</div>



</body>
</html>