<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="loginLink" value="/auth/login"/>

<common:page pageTitle="Login" isAdmin="false" showMenu="false" showSearch="false">
    <div class="row mb-3">
        <common:back url="/product/goBack"  title="Back to shopping"/>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <form id="login" action="${loginLink}" method="post">
                        <div class="form-group row">
                            <label for="username" class="col-sm-3 col-form-label">
                                Username:
                            </label>
                            <div class="col-sm-8">
                                <input type="text" id="username" form="login" name="username" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="password" class="col-sm-3 col-form-label">
                                Password:
                            </label>
                            <div class="col-sm-8">
                                <input type="password" id="password" form="login" name="password" class="form-control"/>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</common:page>