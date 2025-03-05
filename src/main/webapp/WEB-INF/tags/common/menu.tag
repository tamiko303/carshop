<%--@elvariable id="query" type=""--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="sortField" required="false" type="java.lang.String" %>
<%@ attribute name="sortDir" required="false" type="java.lang.String" %>
<c:url var="url" value="/cars/page"/>
<div class="row">
    <div class="col-4 offset-9">
        <a href="<c:url value="/"/>">
            <button type="button" class="btn btn-primary">Login</button>
        </a>
    </div>

    <form action="${url}/${currentPage}?query=${query}&sortField=${sortField}&sortDir=${sortDir}" method="get" >
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search" name="query" value=${query}>
            <div class="input-group-btn">
                <button class="btn btn-default" type="submit">
                    <i class="glyphicon glyphicon-search"></i>
                </button>
            </div>
        </div>
    </form>
<%--    <div  class="container mt-5">--%>
<%--        <form c:action="/cars" method="get" class="col-4 offset-9">--%>
<%--            <div class="input-group">--%>
<%--                <input type="text" class="form-control" name="query" value=${query}>--%>
<%--                <button class="btn btn-outline-secondary" type="submit">Search</button>--%>
<%--            </div>--%>
<%--        </form>--%>
<%--    </div>--%>
</div>