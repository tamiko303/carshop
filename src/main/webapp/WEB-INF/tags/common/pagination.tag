<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="sortField" required="false" type="java.lang.String" %>
<%@ attribute name="sortDir" required="false" type="java.lang.String" %>
<%@ attribute name="query" required="false" type="java.lang.String" %>

<c:url var="url" value="/cars/page"/>

<%--<div class="row">--%>
<%--    <div class="col-4 offset-5">--%>
<%--        <c:if test="${currentPage != 1}">--%>
<%--            <td><a href="${url}/${currentPage - 1}?query=${query}&sortField=${sortField}&sortDir=${sortDir}">Previous</a></td>--%>
<%--        </c:if>--%>
<%--        <c:if test="${totalPages != 1}">--%>
<%--            <table>--%>
<%--                <tr>--%>
<%--                    <c:forEach begin="1" end="${totalPages}" var="i">--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${currentPage eq i}">--%>
<%--                                <td>${i}</td>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <td><a href="${url}/${i}?query=${query}&sortField=${sortField}&sortDir=${sortDir}">${i}</a></td>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                    </c:forEach>--%>
<%--                </tr>--%>
<%--            </table>--%>
<%--        </c:if>--%>
<%--        <c:if test="${currentPage != totalPages}">--%>
<%--            <td><a href="${url}/${currentPage + 1}?query=${query}&sortField=${sortField}&sortDir=${sortDir}">Next</a></td>--%>
<%--        </c:if>--%>
<%--    </div>--%>
<%--</div>--%>

<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage != 1}">
            <li class="page-item">
                <a class="page-link" href="${url}/${currentPage - 1}?query=${query}&sortField=${sortField}&sortDir=${sortDir}" tabindex="-1">Previous</a>
            </li>
        </c:if>

<%--        <li class="page-item"><a class="page-link" href="#">1</a></li>--%>
<%--        <li class="page-item"><a class="page-link" href="#">2</a></li>--%>
<%--        <li class="page-item"><a class="page-link" href="#">3</a></li>--%>
        <c:if test="${totalPages != 1}">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item">
                            <a href="#">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="${url}/${i}?query=${query}&sortField=${sortField}&sortDir=${sortDir}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
        <c:if test="${currentPage != totalPages}">
            <li class="page-item">
                <a class="page-link" href=""${url}/${currentPage + 1}?query=${query}&sortField=${sortField}&sortDir=${sortDir}"">Next</a>
            </li>
        </c:if>
    </ul>
</nav>

