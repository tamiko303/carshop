<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="order" showMenu="false" showSearch="false">
    <div class="row w-25 justify-content-lg-center">
        <h2>Order</h2>
    </div>
    <div class="row mb-3">
        <common:back url="/order/goBack" title="Back to cart"/>
    </div>
    <table class="table">
        <thead class="table-light">
            <tr>
                <th scope="col">Brand </th>
                <th scope="col">Model </th>
                <th scope="col">Production Year </th>
                <th scope="col">Color </th>
                <th scope="col">Quantity </th>
                <th scope="col">Price </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${order.content}">
                <order:tile order="${order}"/>
            </c:forEach>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td>SubTotal</td>
                <td>${subTotal}</td>
            </tr>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td>Delivery</td>
                <td>${delivery}</td>
            </tr>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td>Total</td>
                <td>${total}</td>
            </tr>
        </tbody>
    </table>
    <div class="row mb-3">
        <common:back url="/order/goBack" title="Back to cart"/>
    </div>
</common:page>
