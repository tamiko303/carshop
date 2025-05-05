<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="Cart" isAdmin="${isAdmin}" showMenu="true" showSearch="false" >
    <div class="row w-25 justify-content-lg-center">
        <h2>Comments</h2>
    </div>
    <div class="row mb-3">
        <common:back url="/product/goBack" title="Back to product list"/>
        <table class="table table-striped" id="updateCart">
            <thead>
            <tr>
                <th scope="col">DETAIL DATA</th>
            </tr>
            </thead>
            <tbody>
<%--            <c:forEach var="comment" items="${ratingDetailItem}">--%>
<%--                <tr>--%>
<%--                    <td class="row justify-content-center">--%>
<%--                        <strong>${ratingDetailItem.username}</strong>--%>
<%--                        <strong>${ratingDetailItem.ratingDate}</strong>--%>
<%--                        <br>--%>
<%--                        <span>${ratingDetailItem.comment}</span>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            <tr>--%>
<%--                <td class="row justify-content-center">--%>
<%--                    <form>--%>
<%--                        <button  type="submit" class="btn btn-light btn-upd border-dark">--%>
<%--                            Update--%>
<%--                        </button>--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form action="${orderLink}" method="get">--%>
<%--                            &lt;%&ndash;                        <input type="hidden" name="cart" value="${cart}"/>&ndash;%&gt;--%>
<%--                        <button type="submit" class="btn btn-light border-dark">--%>
<%--                            Order--%>
<%--                        </button>--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--            </tr>--%>
            </tbody>
        </table>
    </div>
</common:page>