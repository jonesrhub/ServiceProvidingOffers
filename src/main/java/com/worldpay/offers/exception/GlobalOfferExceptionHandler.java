package com.worldpay.offers.exception;

import com.worldpay.offers.model.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class which allows consistency across the application for the defined exceptions
 */
@ControllerAdvice
public class GlobalOfferExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalOfferExceptionHandler.class);

    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleOfferNotFoundException(OfferNotFoundException ex) {

        String errorMessage = "An offer with the provided uniqueOfferId does not exist";

        //TODO pull into own method.
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setHttpCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setDescription(errorMessage);

        logErrorResponse(errorMessage);

        return buildResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleOfferAlreadyExistsException(OfferAlreadyExistsException ex) {

        String errorMessage = "Unable to create offer with a uniqueOfferId that already exists";
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.CONFLICT);
        errorResponse.setHttpCode(HttpStatus.CONFLICT.value());
        errorResponse.setDescription(errorMessage);

        logErrorResponse(errorMessage);

        return buildResponseEntity(errorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(OfferServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleOfferServiceException(OfferServiceException ex) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setDescription(ex.getMessage());

        logErrorResponse(ex.getMessage());

        return buildResponseEntity(errorResponse, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errorMessage = "Incorrect format or invalid data supplied";
        List<ErrorResponse.Error> validationErrors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            ErrorResponse.Error error1 = new ErrorResponse.Error();
            error1.setField(fieldError.getField().isEmpty() ? "null" : fieldError.getField());
            error1.setvalue(fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString());
            error1.setDetail(fieldError.getDefaultMessage());

            validationErrors.add(error1);
        }

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.value(),
            errorMessage,
            validationErrors
        );

        logErrorResponse(errorMessage);

        return buildResponseEntity(errorResponse, HttpStatus.CONFLICT);
    }


    private void logErrorResponse(String description) {
        LOGGER.info("An error occured due to: {}" , description);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse, HttpStatus httpStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }


}
