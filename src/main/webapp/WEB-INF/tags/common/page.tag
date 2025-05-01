<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String" %>
<%@ attribute name="showSearch" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showMenu" required="true" type="java.lang.Boolean" %>
<%@ attribute name="isAdmin" required="true" type="java.lang.Boolean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/carshop.css"/>"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            $( "#addToCart .btn-add" ).click( function( event ) {
                // debugger;
                enableAddButton(false);
                event.preventDefault();
                let $form = $(this).closest('form');
                let product = $(this).closest('tr').data('id');
                $('[data-id=' + 's' + product + ']').text('');
                let qty = 1;

                addToCart(product, qty, $form.serialize());
            });
        });

        $(document).ready(function() {
            $( "#updateCart .btn-upd" ).click( function( event ) {
                enableAddButton(false);
                event.preventDefault();

                $('#updateCart tbody tr.data-form ').each(function() {
                    // debugger;
                    let product = $(this).closest('tr').data('id');
                    $('[data-id=' + 's' + product + ']').text('');
                    let quantity = $(this).closest('tr').find('input.qty').val();
                    let qtyOld = $(this).closest('tr').data('qty');
                    let qty = +quantity - qtyOld;

                    if (qty != 0) {
                        addToCart(product, +quantity ,$.param({ quantity: qty }) )
                    }
                });
            });
        });

        $(document).ready(function() {
            debugger;
            $( "#delivered .btn-sm" ).click( function( event ) {
                enableAddButton(false);
                event.preventDefault();

                let $order = $(this).closest('form').data('id');
                setStatus($order ,$.param({ status: 'DELIVERED' }) )
            });
        });

        $(document).ready(function() {
            debugger;
            $( "#rejected .btn-sm" ).click( function( event ) {
                enableAddButton(false);
                event.preventDefault();

                let $order = $(this).closest('form').data('id');
                setStatus($order ,$.param({ status: 'REJECTED' }) )
            });
        });

        $(document).ready(function() {
            // debugger;
            $( "#btn-user_data" ).click( function( event ) {
                let $forms = $(this).closest('form.needs-validation');

                Array.prototype.slice.call($forms)
                    .forEach(function (form) {
                        form.addEventListener('submit', function (event) {
                            if (!form.checkValidity()) {
                                event.preventDefault()
                                event.stopPropagation()
                            }

                            form.classList.add('was-validated')
                        }, false)
                    })

            });
        });

        function enableAddButton(flag) {
            $("#btn-add").prop("disabled", flag);
        }

        function addToCart(product, qty, form) {
            $.ajax({
                type : "POST",
                url : "${home}" + "/cars/"  + product + "/addToCart",
                data: form,
                success : function(response) {
                    $('#count').text(response.data.count);
                    $('#total').text(response.data.total);
                    $('[data-id=' + product + ']').data('qty', qty);
                },
                error : function(e) {
                    if (e.responseJSON.code == "422") {
                        $('[data-id=' + 's' + product + ']').text(e.responseJSON.msg);
                    }

                },
                done : function() {
                    console.log("DONE");
                    enableAddButton(true);
                }
            });
        }

        function setStatus(order, form) {
            $.ajax({
                type : "POST",
                url : "${home}" + "/admin/orders/"  + order + "/setStatus",
                data: form,
                success : function(response) {
                    $('#status').text(response.data.status);
                },
                error : function(e) {
                    if (e.responseJSON.code == "422") {
                        $('#error').text(e.responseJSON.msg);
                    }

                },
                done : function() {
                    console.log("DONE");
                    enableAddButton(true);
                }
            });
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
        <c:choose>
            <c:when test="${isOrders}">
                <common:adminMenu isAdmin="${isAdmin}"/>
            </c:when>
            <c:when test="${showMenu}">
                <common:menu isAdmin="${isAdmin}"/>
            </c:when>
        </c:choose>
        <c:if test="${showSearch}">
            <common:search currentPage="${currentPage}" sortField="${sortField}" sortDir="${sortDir}"/>
        </c:if>
        <jsp:doBody/>
    </div>
</body>
</html>