package com.artocons.carshop.persistence.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.artocons.carshop.persistence.jsonviews.Views;

public class AjaxResponse {
    @JsonView(Views.Public.class)
    String msg;

    @JsonView(Views.Public.class)
    String code;

    @JsonView(Views.Public.class)
    ResultData result;

    public AjaxResponse() {};

    public AjaxResponse(String msg, String code, ResultData result) {
        this.msg = msg;
        this.code = code;
        this.result = result;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public ResultData getResult() {
        return result;
    }
    public void setResult(ResultData result) {
        this.result = result;
    }
}
