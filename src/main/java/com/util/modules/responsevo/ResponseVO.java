package com.util.modules.responsevo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseVO {
    private String result;

    private String message;

    public ResponseVO(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseVO() {
    }
}
