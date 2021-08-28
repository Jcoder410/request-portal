package com.jcoder.request.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jcoder.request.common.message.Message;
import org.apache.commons.lang3.ArrayUtils;

public class ExceptionResponse {

    public static final String FILED_FAILED = "failed";
    public static final String FILED_CODE = "code";
    public static final String FILED_MESSAGE = "message";
    public static final String FILED_TYPE = "type";
    private Boolean failed;
    private String code;
    private String message;
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String exception;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] trace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] throwable;

    public ExceptionResponse() {
        this.failed = true;
    }

    public ExceptionResponse(String code, Message message) {
        this.failed = true;
        this.code = code;
        this.message = message.getDesc();
        this.type = message.getType();
    }

    public ExceptionResponse(Message message) {
        this(true, message);
    }

    public ExceptionResponse(boolean failed, Message message) {
        this(failed, message.code(), message.desc(), message.type());
    }

    public ExceptionResponse(String code, String message) {
        this(true, code, message, Message.DEFAULT_TYPE.code());
    }

    public ExceptionResponse(String code, String message, String type) {
        this(true, code, message, type);
    }

    public ExceptionResponse(boolean failed, String code, String message, String type) {
        this.failed = failed;
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public ExceptionResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public ExceptionResponse setType(String type) {
        this.type = type;
        return this;
    }

    public boolean getFailed() {
        return this.failed;
    }

    public ExceptionResponse setFailed(boolean failed) {
        this.failed = failed;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public ExceptionResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getException() {
        return this.exception;
    }

    public ExceptionResponse setException(String exception) {
        this.exception = exception;
        return this;
    }

    public String[] getTrace() {
        return this.trace;
    }

    @JsonIgnore
    public ExceptionResponse setTrace(StackTraceElement[] trace) {
        this.trace = ArrayUtils.toStringArray(trace);
        return this;
    }

    public String[] getThrowable() {
        return this.throwable;
    }

    public ExceptionResponse setThrowable(String message, StackTraceElement[] trace) {
        this.throwable = (String[]) ArrayUtils.insert(0, ArrayUtils.toStringArray(trace), new String[]{message});
        return this;
    }

}
