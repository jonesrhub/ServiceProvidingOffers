package com.worldpay.offers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Class resresenting an Error Response object
 */
public class ErrorResponse {

    private HttpStatus httpStatus;
    private int httpCode;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorResponse.Error> errors;

    public ErrorResponse(){

    }
    public ErrorResponse(HttpStatus httpStatus, int httpCode, String description, List<ErrorResponse.Error> errors) {
        this.httpStatus = httpStatus;
        this.httpCode = httpCode;
        this.description = description;
        this.errors = errors;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public List<ErrorResponse.Error> getErrors() {
        return errors;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }


    public static class Error {
        public String detail;
        public String value;
        public String field;

        public Error() {
        }

        public String getvalue() {
            return value;
        }

        public void setvalue(String value) {
            this.value = value;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }

}
