<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<input form="rating" name="data" type="hidden" value="${ratingData}">
<table class="table w-25 border p-3">
    <tbody>
    <tr>
        <td>
            <label for="validationUser01" class="form-label">Username</label>
        </td>
        <td>
            <input form="rating" name="userName" type="text" class="form-control" id="validationUser01" required>
            <div class="invalid-feedback">
                Please provide a username.
            </div>
        </td>
    </tr>

    <tr>
        <td>
            <label for="validationRating" class="form-label">Rating</label>
        </td>
        <td>
            <select class="form-select" form="rating" name="rating" aria-label="Select rating" id="validationRating">
                <option value="5">"5"</option>
                <option value="4">"4"</option>
                <option value="3">"3"</option>
                <option value="2">"1"</option>
                <option value="1">"1"</option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan = "2">
                <textarea form="rating" name="comment" rows = "5" cols = "40">
                </textarea>
        </td>
    </tr>
    <tr>
        <td>
            <form id="rating" action="/product/${carItem.id}/addRating" method="post" class="needs-validation">
                <input type="hidden" value="" name="ratingDate" class="form-control">
                <button type="submit"
                        id="btn-user_data"
                        class="btn btn-light btn-sm btn-sm">
                    Add new comment
                </button>
            </form>
        </td>
        <td></td>
    </tr>
    </tbody>
</table>
