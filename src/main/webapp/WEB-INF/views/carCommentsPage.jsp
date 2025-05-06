<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ taglib prefix="comment" tagdir="/WEB-INF/tags/comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<common:page pageTitle="Cart" isAdmin="${isAdmin}" showMenu="true" showSearch="false" >
    <div class="row w-25 justify-content-lg-center">
        <common:back url="/product/goBack" title="Back to product list"/>
        <br/>
    </div>
    <div class="row mb-3">
        <div class="col">
            <h5>&nbsp;&nbsp;All comments</h5>
            <table class="table table-striped border w-75" >
                <thead>
                <tr>
                    <th scope="col">
                        <h5>
                            <c:out value="${carItem.brand}"/>&nbsp;&nbsp;&nbsp;
                            <span style="color: red">
                            <c:out value="${ratingStar}"/>&nbsp;&nbsp;
                            <c:out value="${averageRating}"/>
                        </span>
                            (<c:out value="${ratingCount}"/>)
                        </h5>
                        <p>
                            <c:out value="${carItem.brand}"/>,&nbsp;
                            <c:out value="${carItem.model}"/>,&nbsp;
                            <c:out value="${carItem.productionYear}"/>,&nbsp;
                            <c:out value="${carItem.colors}"/>,&nbsp;
                            <c:out value="${carItem.engineType}"/>,&nbsp;
                            <c:out value="${carItem.engineCapacity}"/>,&nbsp;
                            <c:out value="${carItem.bodyType}"/>,&nbsp;
                            <c:out value="${carItem.mileage}"/>,&nbsp;
                        </p>
                    </th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${ratingDetailItem}">
                        <comment:tile comment="${comment}"/>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col"><h5>New comment</h5>
            <comment:form/>
        </div>
    </div>
</common:page>