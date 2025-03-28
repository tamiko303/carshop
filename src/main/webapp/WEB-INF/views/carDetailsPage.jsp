<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Car details" showMenu="false" showSearch="false">
    <div class="container">
        <div class="row align-items-start">
            <div class="col">
                <div class="row mb-3">
                    <common:back/>
                </div>
                <h2><c:out value="${carItem.brand}"/></h2>
                <img src="<c:url value="https://raw.githubusercontent.com/Alex-Job/img/refs/heads/main/car.png"/>" width="100" height="100" alt="Car image">
                <div><c:out value="${carItem.description}"/></div>
                <br>
                <table class="table center-block border border-success w-50 p-3 border-opacity-25" id="addToCart">
                    <tbody>
                    <tr>
                        <td>
                            <b>Price: </b><c:out value="${carItem.price}"/>
                        </td>
                    </tr>
                    <tr data-id="${carItem.id}" class="data-form" >
                        <td>
                            <form id="${carItem.id}">
                                <input form="${carItem.id}" type="number" name="quantity" min="1" required value="1">
                                <br/><span data-id="s${carItem.id}" style="color: red" ></span>
                                <button
                                        type="submit"
                                        class="btn btn-danger btn-sm">
                                    Add to Cart
                                </button>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col">
                <div><strong>Details:</strong></div>
                <table class="table table-bordered w-75 p-3">
                    <tbody>
                    <tr>
                        <td>Year</td>
                        <td><c:out value="${carItem.productionYear}"/></td>
                    </tr>
                    <tr>
                        <td>Mileage</td>
                        <td><c:out value="${carItem.mileage}"/></td>
                    </tr>
                    <tr>
                        <td>Body type</td>
                        <td><c:out value="${carItem.bodyType}"/></td>
                    </tr>
                    </tbody>
                </table>
                <div><strong>Engine:</strong></div>
                <table class="table table-bordered w-75 p-3">
                    <tbody>
                    <tr>
                        <td>Type</td>
                        <td><c:out value="${carItem.engineType}"/></td>
                    </tr>
                    <tr>
                        <td>Capacity</td>
                        <td><c:out value="${carItem.engineCapacity}"/></td>
                    </tr>
                    </tbody>
                </table>
                <div><strong>Other:</strong></div>
                <table class="table table-bordered w-75 p-3">
                    <tbody>
                    <tr>
                        <td>Colors</td>
                        <td><c:out value="${carItem.colors}"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</common:page>
