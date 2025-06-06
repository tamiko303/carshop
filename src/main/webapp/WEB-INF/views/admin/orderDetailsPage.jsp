<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="order" isAdmin="${isAdmin}" showMenu="true" showSearch="false" >
    <div class="row">
        <div class="col-md-4">
            <h5>Order number: <strong>"${orderId}"</strong></h5>
        </div>
        <div class="col-md-2 col-md-offset-6">
            <h5>Status: <strong id="status">${orderStatus}</strong></h5>
        </div>
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
            <order:tile orderi="${order}"/>
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
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-8 col-md-offset-1">
            <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                <div class="btn-group role="group" aria-label="First group">
                    <common:back url="/admin/orders/goBack"  title="go Back"/>
                </div>
                <div class="btn-group role="group" aria-label="Second group">
                    <form id="delivered" data-id="${orderId}">
                        <button type="submit" class="btn btn-light btn-sm">Delivered</button>
                    </form>
                </div>
                <div class="btn-group role="group" aria-label="Third group">
                    <form id="rejected" data-id="${orderId}">
                        <button type="submit" class="btn btn-light btn-sm">Rejected</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</common:page>
