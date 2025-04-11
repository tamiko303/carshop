<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<table class="table w-25 border p-3">
    <tbody>
        <tr>
            <td>First Name</td>
            <td><input form="order" type="text"></td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input form="order" type="text"></td>
        </tr>
        <tr>
            <td>Adress</td>
            <td><input form="order"  type="text"></td>
        </tr>
        <tr>
            <td>Phone</td>
            <td><input form="order"  type="text"></td>
        </tr>
        <tr>
            <td colspan = "2">
                <textarea form="order"  rows = "5" cols = "40" placeholder="Additional information"> </textarea>
            </td>
        </tr>
        <tr>
            <td>
                <form id="order">
                    <button type="submit"
                            class="btn btn-light btn-sm">
                        Place Order
                    </button>
                </form>
            </td>
            <td></td>
        </tr>
    </tbody>
</table>