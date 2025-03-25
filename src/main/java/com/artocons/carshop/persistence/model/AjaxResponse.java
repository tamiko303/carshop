package com.artocons.carshop.persistence.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.artocons.carshop.persistence.jsonviews.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse {
    @JsonView(Views.Public.class)
    String msg;

    @JsonView(Views.Public.class)
    String code;

    @JsonView(Views.Public.class)
    ResultData data;
}
