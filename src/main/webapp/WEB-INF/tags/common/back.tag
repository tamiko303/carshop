<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="url" required="true" type="java.lang.String" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<a href="<c:url value="${url}"/>">
    <button type="button" class="btn btn-link"><< ${title}</button>
</a>