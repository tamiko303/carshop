package com.artocons.carshop.persistence.response;

import com.artocons.carshop.persistence.model.ResultData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse {
    String msg;
    String code;
    ResultData data;
}
