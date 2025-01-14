<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<common:page pageTitle="Login" showMenu="false">
    <div class="row mb-3">
        <common:back/>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <form>
                        <div class="form-group row">
                            <label for="username" class="col-sm-3 col-form-label">
                                Username:
                            </label>
                            <div class="col-sm-8">
                                <input type="text" id="username" name="username" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="password" class="col-sm-3 col-form-label">
                                Password:
                            </label>
                            <div class="col-sm-8">
                                <input type="password" id="password" name="password" class="form-control"/>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                        <div class="row justify-content-center">
                            Login via GitHub >>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</common:page>