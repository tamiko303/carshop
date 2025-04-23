<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/cart" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="orderLink" value="/order"/>

<common:page pageTitle="Cart" showMenu="false" showSearch="false">

    <div class="row w-25 justify-content-lg-center">
        <h2>Cart</h2>
    </div>
    <div class="row mb-3">
        <common:back/>
    </div>
    <table class="table table-striped" id="updateCart">
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
            <tr>
                <td></td><td></td><td></td><td></td><td></td>
                <td class="row justify-content-center">
                    <form>
                        <button  type="submit" class="btn btn-light btn-upd border-dark">
                            Update
                        </button>
                    </form>
                </td>
                <td>
                    <form action="${orderLink}" method="get">
                        <input type="hidden" name="cart" value="${cart}"/>
                        <button type="submit" class="btn btn-light border-dark">
                            Order
                        </button>
                    </form>
                </td>
            </tr>
        </tbody>
    </table>
</common:page>