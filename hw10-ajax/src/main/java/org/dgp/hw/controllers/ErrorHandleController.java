package org.dgp.hw.controllers;

import org.dgp.hw.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//@ControllerAdvice
public class ErrorHandleController {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(NotFoundException exc) {
        var modelAndView = new ModelAndView("err404");
        modelAndView.addObject("message", exc.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(NotFoundException exc) {
        var modelAndView = new ModelAndView("err500");
        modelAndView.addObject("message", exc.getMessage());

        return modelAndView;
    }
}
