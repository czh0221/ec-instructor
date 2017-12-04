package com.ec.instructor.core.exceptions;

/**
 * @author Colin.Z.Chen
 * @description unknown exception when system runtime;
 * @time 2017/12/3.
 */
public class SystemException extends RuntimeException {
    public SystemException(String message) {
        super(message);
    }
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
    public SystemException(Throwable cause) {
        super(cause);
    }
}
