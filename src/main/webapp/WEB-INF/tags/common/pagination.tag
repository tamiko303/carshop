<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="sortField" required="false" type="java.lang.String" %>
<%@ attribute name="sortDir" required="false" type="java.lang.String" %>

<c:url var="url" value="/cars/page"/>

<div class="row col-sm-10">
    <c:if test="${currentPage != 1}">
        <td><a href="${url}/${currentPage - 1}?sortField${sortField}&sortDir=${sortDir}">Previous</a></td>
    </c:if>

    <table>
        <tr>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${url}/${i}?sortField=${sortField}&sortDir=${sortDir}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>

    <c:if test="${currentPage lt totalPages}">
        <td><a href="${url}/${currentPage + 1}}?sortField${sortField}&sortDir=${sortDir}">Next</a></td>
    </c:if>
</div>

