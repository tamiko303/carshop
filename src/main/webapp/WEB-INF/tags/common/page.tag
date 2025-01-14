<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>
<%@ attribute name="showMenu" required="true" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/carshop.css"/>"/>
    <meta charset="utf-8">
    <title><c:out value="${pageTitle}"/></title>
</head>
<body>
<div class="container-fluid">
    <header>
        <div class="row justify-content-center">
            <h1>Car Shop Application</h1>
        </div>
    </header>
    <c:if test="${showMenu}">
        <common:menu/>
    </c:if>
    <jsp:doBody/>
</div>
</body>
</html>