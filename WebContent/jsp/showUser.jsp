<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.show_user" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.show_user" bundle="${rb}"/></title>
  <!-- Bootstrap Core CSS -->
  <link rel='stylesheet' href="${pageContext.request.contextPath}/css/bootstrap.min.css" type='text/css' media='all'>
  <!-- Custom CSS -->
  <link href="${pageContext.request.contextPath}/css/hostel.css" rel="stylesheet">
</head>

<body>	
  
  <%@ include file="common/navigation.jsp"%>

<div class="container">	

	<!-- ERROR BLOCK -->	
	<c:choose>
		<c:when test = "${adminError != null}">    
		<%@ include file="common/errorBlock.jsp"%>        
        </c:when>              
    </c:choose>  
    
     <!-- INFO BLOCK -->	
	<c:choose>
        <c:when test = "${adminInfo != null}">  
        <%@ include file="common/infoBlock.jsp"%>            
        </c:when> 
    </c:choose>  

    <!-- ADMIN SHOW FOUND USER -->
    <div class="box">
      <div class="col-lg-12">
        <hr>
        <h2 class="intro-text text-center">${foundUser.firstName} ${foundUser.lastName}</h2>
        <hr>
        <!-- TABLE -->
        <table class="table table-bordered user_data_table center-block">
          <thead>
          <tr>
            <th class="col-lg-8 text-center" colspan="2" align="center"><fmt:message key="admin.user_data" bundle="${rb}"/></th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="admin.id" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.id}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="register.fname" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.firstName}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="register.lname" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.lastName}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="register.email" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.email}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="register.phone" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.phone}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="register.birth_date" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.birthDate}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="admin.registration_date" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.registrationDate}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="admin.banned" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.banned}</p>
          </tr>
          <tr>
            <td class="col-lg-2">
              <p><fmt:message key="admin.discount" bundle="${rb}"/></p>
            </td>
            <td class="col-lg-4">
              <p>${foundUser.discount}%</p>
          </tr>
          </tbody>
        </table>
        <hr>
        <div class="form-group col-lg-3">
          <a href="${pageContext.request.contextPath}/adminMenu/updateUser"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.update_user" bundle="${rb}"/></button></a>
        </div>
        <div class="form-group col-lg-3">
          <a href="${pageContext.request.contextPath}/controller?command=ban_user&id=${foundUser.id}"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.ban_user" bundle="${rb}"/></button></a>
        </div>
        <div class="form-group col-lg-3">
          <a href="${pageContext.request.contextPath}/controller?command=show_user_requests_admin&id=${foundUser.id}"><button type="button" class="btn btn-default btn-block"><fmt:message key="admin.show_requests" bundle="${rb}"/></button></a>
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