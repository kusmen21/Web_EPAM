<div class="row">
      <div class="box">
        <div class="col-lg-12">
          <hr>
          <h2 class="intro-text text-center"><fmt:message key="info.title" bundle="${rb}"/></h2>
          <hr>
          <div class="col-lg-12 text-center">    
          	 <!-- SHOW INFO -->          
             <span class="info_block_message"><c:out value="${registerInfo}"></c:out></span> 
             <span class="info_block_message"><c:out value="${loginInfo}"></c:out></span>  
             <span class="info_block_message"><c:out value="${requestInfo}"></c:out></span> 
             <span class="info_block_message"><c:out value="${adminInfo}"></c:out></span>  
             <!-- DELETING -->   
             <c:remove var="registerInfo"/>
             <c:remove var="loginInfo"/>
             <c:remove var="requestInfo"/>
             <c:remove var="adminInfo"/>
          </div>
        </div>
      </div>
    </div>   