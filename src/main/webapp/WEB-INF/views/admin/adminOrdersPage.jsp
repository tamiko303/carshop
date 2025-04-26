<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="Admin panel" showMenu="true" showSearch="false" isAdmin="${isAdmin}">
    <div class="container justify-content-start">
        <div class="row gx-5">
            <h3>Orders</h3>
        </div>
    </div>
    <table class="table table-striped border" id="addToCart">
        <thead>
        <tr>
            <th scope="col">Order number </th>
            <th scope="col">Customer </th>
            <th scope="col">Phone </th>
            <th scope="col">Adress </th>
            <th scope="col">Date </th>
            <th scope="col">Total price </th>
            <th scope="col">Status </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders.content}">
            <order:admint order="${order}"/>
        </c:forEach>
        </tbody>
    </table>
</common:page>

