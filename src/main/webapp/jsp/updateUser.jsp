<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.update_user" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.update_user" bundle="${rb}"/></title>
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

    <!-- ADMIN UPDATE USER -->
    <div class="box">
  	<div class="col-lg-12 text-center">
    <hr>
    <h2 class="intro-text text-center"><fmt:message key="admin.update.update" bundle="${rb}"/></h2>      
    <hr>
    <div class="container">
      <form method="POST" action="${pageContext.request.contextPath}/controller?command=update_user&id=${foundUser.id}">
        <div class="form-group row">
          <label for="userEmail" class="col-sm-4 col-form-label"><fmt:message key="register.email" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.email}</label>
          <!-- EMAIL -->
          <div class="col-sm-6">
            <div>
              <input type="email" name="email" class="form-control" id="userEmail" placeholder="<fmt:message key="register.email" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="email_error_wrong_email"><fmt:message key="register.set_correct_email" bundle="${rb}"/></span>
              </div>
          </div>
        </div>
        <!-- PASSWORD -->
        <div class="form-group row">
          <label for="userPassword" class="col-sm-4 col-form-label"><fmt:message key="register.password" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="password" name="password" class="form-control" id="userPassword" placeholder="<fmt:message key="register.password" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="password_error_wrong_password"><fmt:message key="register.set_correct_password" bundle="${rb}"/></span>
              </div>
          </div>
        </div>       
        <!-- PHONE -->
        <div class="form-group row">
          <label for="userPhone" class="col-sm-4 col-form-label"><fmt:message key="register.phone" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.phone}</label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="phone" class="form-control" id="userPhone" placeholder="<fmt:message key="register.phone" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="phone_error_wrong_phone"><fmt:message key="register.set_correct_phone" bundle="${rb}"/></span>
              </div>
          </div>
        </div>
        <!-- NAME -->
        <div class="form-group row">
          <label for="userName" class="col-sm-4 col-form-label"><fmt:message key="register.fname" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.firstName}</label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="fname" class="form-control" id="userName" placeholder="<fmt:message key="register.fname" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="name_error_wrong_name"><fmt:message key="register.set_correct_fname" bundle="${rb}"/></span>
              </div>
          </div>
        </div>
        <!-- SURNAME -->
        <div class="form-group row">
          <label for="userSurname" class="col-sm-4 col-form-label"><fmt:message key="register.lname" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.lastName}</label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="lname" class="form-control" id="userSurname" placeholder="<fmt:message key="register.lname" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="surname_error_wrong_surname"><fmt:message key="register.set_correct_lname" bundle="${rb}"/></span>
              </div>
          </div>
        </div>
        <!-- BIRTH DATE -->
        <div class="form-group row">
          <label for="userBirthDate" class="col-sm-4 col-form-label"><fmt:message key="register.birth_date" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.birthDate}</label>
          <div class="col-sm-6">
            <div>
              <input type="date" name="birth_date" class="form-control" id="userBirthDate" placeholder="<fmt:message key="register.birth_date_example" bundle="${rb}"/>">              
            </div>
          </div>
        </div>
        <!-- DISCOUNT -->
        <div class="form-group row">
          <label for="userBirthDate" class="col-sm-4 col-form-label"><fmt:message key="admin.discount" bundle="${rb}"/><fmt:message key="admin.update.was" bundle="${rb}"/>${foundUser.discount}%</label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="discount" class="form-control" id="discount" placeholder="${foundUser.discount}">              
            </div>
          </div>
        </div>
        <!-- BUTTON -->
        <div class="form-group row">
          <label for="registration_button" class="col-sm-4 col-form-label"><fmt:message key="admin.update.set_one_field" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <button type="submit" class="btn btn-primary btn-block" id="registration_button"><fmt:message key="admin.update.button" bundle="${rb}"/></button>
          </div>
        </div>
      </form>
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