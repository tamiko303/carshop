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
        $( "#addToCart" ).on( "submit", function( event ) {

            enableAddButton(false);
            event.preventDefault();

            url = $form.attr( "action" )

            $.ajax({
                type : "POST",
                url : "${home}${url}",
                data : {
                   quantity: $("#quantity").val(),
                },
                success : function(data) {
                    console.log("SUCCESS: ", data);
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
        } );

        function enableAddButton(flag) {
            $("#btn-add").prop("disabled", flag);
        }


        // function callController(product, quantity) {
        //     // Get the value you want to send
        //     let quantity = document.getElementById("q").value;
        //
        //     // Make an AJAX request to the controller method
        //     fetch('/cart/add', {
        //         method: 'POST',
        //         headers: {
        //             'Content-Type': 'application/json; charset=utf-8',
        //             'Data-Type': 'json',
        //         },
        //         body: 'quantity=' + encodeURIComponent(quantity)
        //     })
        //         .then(response => response.text())
        //         .then(data => {
        //             // Handle the response from the controller
        //             console.log(data);
        //         });
        // }
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