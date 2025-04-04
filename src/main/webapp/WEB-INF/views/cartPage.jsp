<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="Cart" showMenu="false" showSearch="false">

    <div class="row justify-content-center">
        <h2>Cart</h2>
    </div>
    <div class="row mb-3">
        <common:back/>
    </div>
    <table class="table table-striped" id="addToCart">
        <thead>
        <tr>
            <th scope="col">Brand </th>
            <th scope="col">Model </th>
            <th scope="col">Color </th>
            <th scope="col">Production Year </th>
            <th scope="col">Price </th>
            <th scope="col">Quantity </th>
            <th scope="col">Action </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cart" items="${cart.content}">
            <cart:tile cart="${cart}"/>
        </c:forEach>
        </tbody>
    </table>

</common:page>