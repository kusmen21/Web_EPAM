<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="page" value="page.request" scope="session"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">   
  <title><fmt:message key="title.request" bundle="${rb}"/></title>
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
        <c:when test = "${requestError != null}">  
        <%@ include file="common/errorBlock.jsp"%>            
        </c:when>        
    </c:choose>  
  
	<!-- REQUEST CREATE -->
    <div class="box">
      <div class="col-lg-12 text-center">
        <hr>
        <h2 class="intro-text text-center"><fmt:message key="request.request_creating" bundle="${rb}"/></h2>
        <hr>
        <form method="POST" action="${pageContext.request.contextPath}/controller?command=continue_request">
          <!-- DATE FROM -->
          <div class="form-group row">
            <label for="dateFrom" class="col-sm-2 col-form-label"><fmt:message key="request.date_from" bundle="${rb}"/></label>
            <div class="col-lg-6">
              <div>
                <input type="date" name="dateFrom" class="form-control setting_date" id="dateFrom" placeholder="ex. 2017-06-25">
              </div>
            </div>
          </div>
          <!-- DATE TO -->
          <div class="form-group row">
            <label for="dateTo" class="col-sm-2 col-form-label"><fmt:message key="request.date_to" bundle="${rb}"/></label>
            <div class="col-lg-6">
              <div>
                <input type="date" name="dateTo" class="form-control setting_date" id="dateTo" placeholder="ex. 2017-06-25">
              </div>
            </div>            
          </div>
          	<!-- JS VALIDATION ERRORS -->
          	<span id="date_error_set_date" class="error_message"><fmt:message key="request.error_date_format" bundle="${rb}"/></span>
            <span id="date_error_from_to" class="error_message"><fmt:message key="request.error_start_date_bigger" bundle="${rb}"/></span>
            <span id="date_error_from_bigger" class="error_message"><fmt:message key="request.error_start_date_before_today" bundle="${rb}"/></span>
            <hr>
          <!-- TABLE -->	
          <table class="table table-bordered request_table">
            <thead>
            <tr>
              <th class="col-lg-6 text-center border_bottom" colspan="3" align="center"><fmt:message key="request.room_12" bundle="${rb}"/></th>
              <th class="border_left_right col-lg-2 text-center border_bottom"><fmt:message key="request.room_4" bundle="${rb}"/></th>
              <th  class="col-lg-2 text-center border_bottom"><fmt:message key="request.room_2" bundle="${rb}"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td id="t1">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n1" id="n1">
                  №1
                </label>
              </td>
              <td  id="t2">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n2" id="n2">
                  №2
                </label>
              </td>
              <td  id="t3">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n3" id="n3">
                  №3
                </label>
              </td>
              <td  id="t13" class="border_left_right">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n13" id="n13">
                  №13
                </label>
              </td>
              <td  id="t17">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n17" id="n17">
                  №17
                </label>
              </td>
            </tr>
            <tr>
              <td  id="t4">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n4" id="n4">
                  №4
                </label>
              </td>
              <td  id="t5">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n5" id="n5">
                  №5
                </label>
              </td>
              <td id="t6">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n6" id="n6">
                  №6
                </label>
              </td>
              <td  id="t14" class="border_left_right">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n14" id="n14">
                  №14
                </label>
              </td>
              <td  id="t18" class="border_bottom">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n18" id="n18">
                  №18
                </label>
              </td>
            </tr>
            <tr>
              <td  id="t7">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n7" id="n7">
                  №7
                </label>
              </td>
              <td  id="t8">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n8" id="n8">
                  №8
                </label>
              </td>
              <td  id="t9">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n9" id="n9">
                  №9
                </label>
              </td>
              <td  id="t15" class="border_left_right">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n15" id="n15">
                  №15
                </label>
              </td>
              <td  id="t19">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n19" id="n19">
                  №19
                </label>
              </td>
            </tr>
            <tr>
              <td  id="t10">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n10" id="n10">
                  №10
                </label>
              </td>
              <td  id="t11">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n11" id="n11">
                  №11
                </label>
              </td>
              <td  id="t12">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n12" id="n12">
                  №12
                </label>
              </td>
              <td  id="t16" class="border_left_right">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n16" id="n16">
                  №16
                </label>
              </td>
              <td  id="t20">
                <label class="checkbox">
                  <input type="checkbox" value="" name="n20" id="n20">
                  №20
                </label>
              </td>
            </tr>
            </tbody>
          </table>
          <hr>        
          <p><fmt:message key="request.description_1" bundle="${rb}"/></p>
          <p><fmt:message key="request.description_2" bundle="${rb}"/></p>
          <p><fmt:message key="request.description_3" bundle="${rb}"/></p>
          <p><fmt:message key="request.description_4" bundle="${rb}"/></p>
          <p><fmt:message key="request.description_5" bundle="${rb}"/></p>
          <hr>
          <!-- BUTTON -->
          <div class="col-lg-12">
            <button type="submit" class="btn btn-primary btn-block request_button text-center center-block" id="request_button"><fmt:message key="request.button_commit" bundle="${rb}"/></button>
          </div>
        </form>         
      </div>
    </div>	
  </div>
  
  <%@ include file="common/footer.jsp"%>

  <!-- jQuery -->
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!-- Bootstrap Core JavaScript -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>  
  <!--Validate request form -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/validate_request_form.js"></script>
 </body>