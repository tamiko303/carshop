<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer" %>
<%--<div th:if="${totalPages > 1}">--%>
<%--    <div class="row col-sm-10">--%>
<%--&lt;%&ndash;        <div class="col-sm-2">&ndash;%&gt;--%>
<%--&lt;%&ndash;            Total Rows: [[${totalItems}]]&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>
<%--        <div class="col-sm-1">--%>
<%--            <span c:each="i: ${numbers.sequence(1, totalPages)}">--%>
<%--              <a c:if="${currentPage != i}" c:href="@{'/page/' + ${i}}">[[${i}]]</a>--%>
<%--              <span c:unless="${currentPage != i}">[[${i}]]</span> &nbsp; &nbsp;--%>
<%--            </span>--%>
<%--        </div>--%>
<%--        <div class="col-sm-1">--%>
<%--            <a c:if="${currentPage < totalPages}" c:href="@{'/page/' + ${currentPage + 1}}">Next</a>--%>
<%--            <span c:unless="${currentPage < totalPages}">Next</span>--%>
<%--        </div>--%>

<%--        <div class="col-sm-1">--%>
<%--            <a c:if="${currentPage < totalPages}" c:href="@{'/page/' + ${totalPages}}">Last</a>--%>
<%--            <span c:unless="${currentPage < totalPages}">Last</span>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>






<div class="d-flex flex-row align-items-center">
    <c:if test="${currentPage != 1}">
        <td><a href="@{'/page/' + ${currentPage - 1}}">Previous</a></td>
    </c:if>

    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="cars/page/${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
    <c:if test="${currentPage lt totalPages}">
        <td><a href="@{'/page/' + ${currentPage + 1}}">Next</a></td>
    </c:if>
</div>

