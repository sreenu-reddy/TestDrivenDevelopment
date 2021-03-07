package com.sree.testDrivenDevelopment.controller;

import com.sree.testDrivenDevelopment.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception){
        log.error("Handling number format Exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400Error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleFileNotFound(Exception exception){

        log.error("Handling file not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404Error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ModelAndView handleNullPoint(Exception exception){
//
//        log.error("Handling Null point exception");
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("500Error");
//        modelAndView.addObject("exception",exception);
//        return modelAndView;
//    }


}
