package com.artocons.carshop.persistence.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.artocons.carshop.persistence.jsonviews.Views;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
