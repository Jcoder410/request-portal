package com.jcoder.request.common.exception;

import com.jcoder.request.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller异常统一处理类
 *
 * @author Qiu
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);
    @Value("${spring.profiles.active:dev}")
    private String env;

    @Autowired
    private MessageSource messageSource;

    public BaseExceptionHandler() {
    }

    private Message buildMessage(String code, Object[] args) {
        Message message = new Message();
        message.setCode(code);
        message.setType("error");
        message.setDesc(messageSource.getMessage(code, args, Locale.SIMPLIFIED_CHINESE));
        return message;
    }

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<ExceptionResponse> process(HttpServletRequest request, HandlerMethod method, CommonException exception) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn(this.exceptionMessage("Common exception", request, method), exception);
        }

        ExceptionResponse er = new ExceptionResponse(buildMessage(exception.getCode(), exception.getParameters()));
        this.setDevException(er, exception);
        return new ResponseEntity(er, HttpStatus.OK);
    }

    private String exceptionMessage(String message, HttpServletRequest request, HandlerMethod method) {
        return String.format(message + ", Request: {URI=%s, method=%s}, User: %s",
                request.getRequestURI(),
                Optional.ofNullable(method).map(HandlerMethod::toString).orElse("NullMethod"), "null");
    }

    private void setDevException(ExceptionResponse er, Exception ex) {
        if ("dev".equals(this.env)) {
            er.setException(ex.getMessage());
            er.setTrace(ex.getStackTrace());
            Throwable cause = ex.getCause();
            if (cause != null) {
                er.setThrowable(cause.getMessage(), cause.getStackTrace());
            }
        }

    }

}
