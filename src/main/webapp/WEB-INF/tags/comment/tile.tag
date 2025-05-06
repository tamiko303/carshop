<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ attribute name="comment" required="true" type="com.artocons.carshop.persistence.model.Rating" %>

<tr class="data-form">
    <td>
       <strong><c:out value="${comment.userName}"/></strong>
        <c:out value="${comment.ratingDate}"/>
    </td>
</tr>
<tr class="data-form">
    <td>
        <strong>rating:</strong> <c:out value="${comment.rating}"/><br/>
        <strong>comment:</strong> <c:out value="${comment.comment}"/>
    </td>
</tr>