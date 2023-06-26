package com.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * アプリケーション内で処理されなかった例外をここでキャッチし、
 * エラーページへ遷移させます.
 * 
 * @author igamasayuki
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public String handleConnectionError(Throwable e) {
         
    	logger.error(e.getMessage(), e);
         
        return "error/500";
    }
    
}