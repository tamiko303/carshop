<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="order" required="true" type="com.artocons.carshop.persistence.model.OrderHeader" %>
<c:url var="detailsLink" value="/admin/orders"/>

<tr class="data-form" >
    <td>
        <a href="${detailsLink}/${order.orderId}">
            <c:out value="${order.orderId}"/>
        </a>
    </td>
    <td><c:out value="${order.lastName} ${order.firstName}"/></td>
    <td><c:out value="${order.adress}"/></td>
    <td><c:out value="${order.phone}"/></td>
    <td><c:out value="${order.orderDate}"/></td>
    <td><c:out value="${order.total}"/></td>
    <td><c:out value="${order.orderStatus}"/></td>
</tr>