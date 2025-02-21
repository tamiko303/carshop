<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@ taglib prefix="car" tagdir="/WEB-INF/tags/car" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Car list" showMenu="true">
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Image</th>
            <th scope="col">Brand <util:sorting page="${currentPage}" query="${query}" field="brand"/></th>
            <th scope="col">Model <util:sorting page="${currentPage}" query="${query}" field="model"/></th>
            <th scope="col">Color </th>
            <th scope="col">Price <util:sorting page="${currentPage}" field="price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="car" items="${cars.content}">
            <car:tile car="${car}"/>
        </c:forEach>
        </tbody>
    </table>
    <common:pagination currentPage="${currentPage}" totalPages="${totalPages}" sortField="${sortField}" sortDir="${sortDir}"/>
</common:page>