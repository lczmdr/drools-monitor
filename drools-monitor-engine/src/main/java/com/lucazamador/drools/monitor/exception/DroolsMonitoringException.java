package com.lucazamador.drools.monitor.exception;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class DroolsMonitoringException extends Exception {

    private static final long serialVersionUID = 1L;

    public DroolsMonitoringException() {
    }

    public DroolsMonitoringException(String message) {
        super(message);
    }

    public DroolsMonitoringException(String message, Throwable cause) {
        super(message, cause);
    }

    public DroolsMonitoringException(Throwable cause) {
        super(cause);
    }

}
