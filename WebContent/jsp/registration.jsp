<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.registration" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <title><fmt:message key="title.register" bundle="${rb}"/></title>
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
		<c:when test = "${registerError != null}">    
		<%@ include file="common/errorBlock.jsp"%>        
        </c:when>
        <c:when test = "${loginError != null}">  
        <%@ include file="common/errorBlock.jsp"%>            
        </c:when>
        <c:when test = "${requestError != null}">  
        <%@ include file="common/errorBlock.jsp"%>            
        </c:when>        
    </c:choose>    

    <!-- Registration -->
	<div class="box">
  <div class="col-lg-12 text-center">
    <hr>
    <h2 class="intro-text text-center"><fmt:message key="register.register" bundle="${rb}"/></h2>      
    <hr>
    <div class="container">
      <form method="POST" action="${pageContext.request.contextPath}/controller?command=register">
        <div class="form-group row">
          <label for="userEmail" class="col-sm-2 col-form-label"><fmt:message key="register.email" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <!-- EMAIL -->
          <div class="col-sm-6">
            <div>
              <input type="email" name="email" class="form-control" id="userEmail" placeholder="<fmt:message key="register.email" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="email_error_set_email" class="error_message"><fmt:message key="register.set_email" bundle="${rb}"/></span>
              <span id="email_error_wrong_email" class="error_message"><fmt:message key="register.set_correct_email" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- PASSWORD -->
        <div class="form-group row">
          <label for="userPassword" class="col-sm-2 col-form-label"><fmt:message key="register.password" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="password" name="password" class="form-control" id="userPassword" placeholder="<fmt:message key="register.password" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="password_error_set_password" class="error_message"><fmt:message key="register.set_password" bundle="${rb}"/></span>
              <span id="password_error_wrong_password" class="error_message"><fmt:message key="register.set_correct_password" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- PASSWORD 2 -->
        <div class="form-group row">
          <label for="userPassword2" class="col-sm-2 col-form-label"><fmt:message key="register.confirm_password" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="password" name="password2" class="form-control" id="userPassword2" placeholder=<fmt:message key="register.confirm_password" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="password2_error_set_password" class="error_message"><fmt:message key="register.confirm_password" bundle="${rb}"/></span>
              <span id="password2_error_not_equals" class="error_message"><fmt:message key="register.passwords_not_match" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- PHONE -->
        <div class="form-group row">
          <label for="userPhone" class="col-sm-2 col-form-label"><fmt:message key="register.phone" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="phone" class="form-control" id="userPhone" placeholder="<fmt:message key="register.phone" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="phone_error_set_phone" class="error_message"><fmt:message key="register.set_phone" bundle="${rb}"/></span>
              <span id="phone_error_wrong_phone" class="error_message"><fmt:message key="register.set_correct_phone" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- NAME -->
        <div class="form-group row">
          <label for="userName" class="col-sm-2 col-form-label"><fmt:message key="register.fname" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="fname" class="form-control" id="userName" placeholder="<fmt:message key="register.fname" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="name_error_set_name" class="error_message"><fmt:message key="register.set_fname" bundle="${rb}"/></span>
              <span id="name_error_wrong_name" class="error_message"><fmt:message key="register.set_correct_fname" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- SURNAME -->
        <div class="form-group row">
          <label for="userSurname" class="col-sm-2 col-form-label"><fmt:message key="register.lname" bundle="${rb}"/><fmt:message key="register.star" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="text" name="lname" class="form-control" id="userSurname" placeholder="<fmt:message key="register.lname" bundle="${rb}"/>" oninput="validate(this.form)">
              <span id="surname_error_set_surname" class="error_message"><fmt:message key="register.set_lname" bundle="${rb}"/></span>
              <span id="surname_error_wrong_surname" class="error_message"><fmt:message key="register.set_correct_lname" bundle="${rb}"/></span>
            </div>
          </div>
        </div>
        <!-- BIRTH DATE -->
        <div class="form-group row">
          <label for="userBirthDate" class="col-sm-2 col-form-label"><fmt:message key="register.birth_date" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <div>
              <input type="date" name="birth_date" class="form-control" id="userBirthDate" placeholder="<fmt:message key="register.birth_date_example" bundle="${rb}"/>">
            </div>
          </div>
        </div>
        <!-- BUTTON -->
        <div class="form-group row">
          <label for="registration_button" class="col-sm-2 col-form-label"><fmt:message key="register.required_fields" bundle="${rb}"/></label>
          <div class="col-sm-6">
            <button type="submit" class="btn btn-primary btn-block" id="registration_button" disabled="disabled"><fmt:message key="register.register_button" bundle="${rb}"/></button>
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
  <!--Validate register form -->
  <script type="text/javascript" async="" src="${pageContext.request.contextPath}/js/validate_register_form.js"></script>
</body>

</html>