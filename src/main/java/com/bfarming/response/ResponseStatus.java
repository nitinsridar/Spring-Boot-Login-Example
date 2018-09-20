package com.bfarming.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.bfarming.constants.AppCodes;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;


/**
 * @author sachin
 *
 */
public class ResponseStatus {


    private Boolean isSuccess;
    private Integer code;
    private String message;
    private List<ApiSubError> subErrors;

    ResponseStatus() {
    }

    public ResponseStatus(Boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.subErrors = null;
    }

    public ResponseStatus(Boolean isSuccess, Integer code, String message, List<ApiSubError> errors) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.subErrors = errors;
    }

    ResponseStatus(HttpStatus status, Throwable ex) {
        this();
        this.code = status.value();
        this.message = ex.getLocalizedMessage();
        this.isSuccess = false;
        this.subErrors = null;
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiValidationError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when
     * a @Validated validation fails.
     *
     * @param cv
     *            the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<ApiSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public static ResponseStatus getInvalidResponseStatus(HttpStatus httpStatus, String msg) {
        return new ResponseStatus(false, httpStatus.value(), msg);
    }

    public static ResponseStatus getInvalidResponseStatus(AppCodes code, String msg) {
        return new ResponseStatus(false, code.getCode() , msg);
    }

    public static ResponseStatus getValidResponseStatus(HttpStatus httpStatus) {
        return new ResponseStatus(true, httpStatus.value(), "");
    }

    public static ResponseStatus getValidResponseStatusWithMesssage(HttpStatus httpStatus, String message) {
        return new ResponseStatus(true, httpStatus.value(), message);
    }

    public static ResponseStatus getValidResponseStatus() {
        return new ResponseStatus(true, HttpStatus.OK.value() , "");
    }



}

