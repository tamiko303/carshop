<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>
<%@ attribute name="showMenu" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showSearch" required="true" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/carshop.css"/>"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $( "#addToCart button" ).click( function( event ) {
                debugger;
                enableAddButton(false);
                event.preventDefault();
                let $form = $(this).closest('form');
                console.log($form.serialize());
                let product = $(this).closest('tr').data('id');
                console.log(product);
                console.log("${home}" + "/" + product + "/addToCart");

                $.ajax({
                    type : "POST",
                    url : "${home}" + "/cars/"  + product + "/addToCart",
                    data: $form.serialize(),
                    success : function(response) {
                        console.log("SUCCESS: ", response);
                        $('#count').text(response.count);
                        $('#total').text(response.total);
                    },
                    error : function(e) {
                        console.log("ERROR: ", e);
                        $('#quantityError').text(e);
                    },
                    done : function(e) {
                        console.log("DONE");
                        enableAddButton(true);
                    }
                });
            });
        });

        function enableAddButton(flag) {
            $("#btn-add").prop("disabled", flag);
        }

    </script>

    <meta charset="utf-8">
    <title><c:out value="${pageTitle}"/></title>
</head>
<body>
    <div class="container-fluid">
        <header>
            <div class="row justify-content-center">
                <h1>Car Shop Application</h1>
            </div>
        </header>
        <c:if test="${showMenu}">
            <common:menu/>
        </c:if>
        <common:myCart/>
        <c:if test="${showSearch}">
            <common:search currentPage="${currentPage}" sortField="${sortField}" sortDir="${sortDir}"/>
        </c:if>
        <jsp:doBody/>
    </div>
</body>
</html>