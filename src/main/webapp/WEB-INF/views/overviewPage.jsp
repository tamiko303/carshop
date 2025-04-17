<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="order" showMenu="false" showSearch="false" showCart="false">
    <div class="row w-25 justify-content-lg-center">
        <h4>Thank you for your order</h4>
    </div>
    <div class="row w-25 justify-content-lg-center">
        <h4>Order number: "${orderId}"</h4>
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
            <c:forEach var="order" items="${order}">
                <order:tile order="${order}"/>
            </c:forEach>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td><strong>SubTotal</strong></td>
                <td><strong>${subTotal}</strong></td>
            </tr>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td><strong>Delivery</strong></td>
                <td><strong>${delivery}</strong></td>
            </tr>
            <tr>
                <td></td><td></td><td></td><td></td>
                <td><strong>Total</strong></td>
                <td><strong>${total}</strong></td>
            </tr>
        </tbody>
    </table>
    <div class="container w-30">
        <div class="row px-4">
            <table class="table w-25 border p-3">
                <tbody>
                    <tr>
                        <td>First Name:</td>
                        <td>${userName}</td>
                    </tr>
                    <tr>
                        <td>Last Name:</td>
                        <td>${userSuName}</td>
                    </tr>
                    <tr>
                        <td>Adress:</td>
                        <td><${userAdress}</td>
                    </tr>
                    <tr>
                        <td>Phone:</td>
                        <td>${userPhone}</td>
                    </tr>
                    <tr>
                        <td colspan = "2">
                            <h5>Description:</h5>
                            ${userDescription}
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row mb-3">
        <common:back url="/product/goBack"  title="Back to shopping"/>
    </div>
</common:page>
