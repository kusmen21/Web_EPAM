<div class="row">
      <div class="box">
        <div class="col-lg-12">
          <hr>
          <h2 class="intro-text text-center"><fmt:message key="error.title" bundle="${rb}"/></h2>
          <hr>
          <div class="col-lg-12 text-center">    
          	 <!-- SHOW ERROR -->          
             <span class="error_block_message"><c:out value="${registerError}"></c:out></span> 
             <span class="error_block_message"><c:out value="${loginError}"></c:out></span>  
             <span class="error_block_message"><c:out value="${requestError}"></c:out></span> 
             <span class="error_block_message"><c:out value="${adminError}"></c:out></span>  
             <!-- DELETING -->   
             <c:remove var="registerError"/>
             <c:remove var="loginError"/>
             <c:remove var="requestError"/>
             <c:remove var="adminError"/>
          </div>
        </div>
      </div>
    </div>   