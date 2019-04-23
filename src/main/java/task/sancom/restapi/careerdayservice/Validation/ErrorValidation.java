package task.sancom.restapi.careerdayservice.Validation;

import task.sancom.restapi.careerdayservice.exception.SubException;

public class ErrorValidation extends SubException {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ErrorValidation() {
    }

    public ErrorValidation(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public ErrorValidation(String object, String field) {
        this.object = object;
        this.field = field;
    }


    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
