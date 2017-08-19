<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.error" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title><fmt:message key="title.error" bundle="${rb}"/></title>
  <!-- Bootstrap Core CSS -->
  <link rel='stylesheet' href="${pageContext.request.contextPath}/css/bootstrap.min.css" type='text/css' media='all'>
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/hostel.css" rel="stylesheet">
</head>

<body>	
  
  <%@ include file="common/navigation.jsp"%>

<div class="container">	

	<div class="row">
      <div class="box">
        <div class="col-lg-12">
          <hr>
          <h2 class="intro-text text-center"><fmt:message key="error.title" bundle="${rb}"/></h2>
          <hr>
          <div class="col-lg-12 text-center"> 
          	 <c:choose>
        		<c:when test = "${customException != null}">  
        			<!-- SHOW ERROR -->          
             		<span class="error_block_message"><c:out value="${customException}"></c:out></span>              
             		<!-- DELETING -->   
             		<c:remove var="customException"/>             
        		</c:when> 
        		<c:otherwise>
        			<span class="error_block_message"><fmt:message key="error.unknown_error" bundle="${rb}"/></span>    
        		</c:otherwise>
   			 </c:choose>             	           
          </div>
        </div>
      </div>
    </div>   

</div>
  <!-- /.container -->

	<%@ include file="common/footer.jsp"%>

  <!-- jQuery -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!-- Bootstrap Core JavaScript -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>    
</body>

</html>