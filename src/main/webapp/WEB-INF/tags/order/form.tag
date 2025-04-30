<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<input form="order" name="data" type="hidden" value="${order.content}">
<table class="table w-25 border p-3">
    <tbody>
        <tr>
            <td>
                <label for="validationUser01" class="form-label">First Name</label>
            </td>
            <td>
                <input form="order" name="firstName" type="text" class="form-control" id="validationUser01" required>
                <div class="invalid-feedback">
                    Please provide a first name.
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="validationUser02" class="form-label">Last Name</label>
            </td>
            <td>
                <input form="order" name="lastName" type="text" class="form-control" id="validationUser02" required>
                <div class="invalid-feedback">
                    Please provide a last name.
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="validationUser03" class="form-label">Adress</label>
            </td>
            <td>
                <input form="order" name="adress" type="text" class="form-control" id="validationUser03" required>
                <div class="invalid-feedback">
                    Please provide a valid adress.
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="validationUser04" class="form-label">Phone</label>
            </td>
            <td>
                <input form="order" name="phone" type="text"  class="form-control" id="validationUser04" required>
                <div class="invalid-feedback">
                    Please provide a valid phone.
                </div>
            </td>
        </tr>
        <tr>
            <td colspan = "2">
                <textarea form="order" name="description" rows = "5" cols = "40" placeholder="Additional information">
                </textarea>
            </td>
        </tr>
        <tr>
            <td>
                <form id="order" action="order/placeOrder" method="post" class="needs-validation">
                    <button type="submit"
                            id="btn-user_data"
                            class="btn btn-light btn-sm btn-sm">
                        Place Order
                    </button>
                </form>
            </td>
            <td></td>
        </tr>
    </tbody>
</table>