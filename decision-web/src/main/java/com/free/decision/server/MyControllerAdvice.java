package com.free.decision.server;

import com.free.decision.server.enums.ResultCodeEnum;
import com.free.decision.server.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 * @author Xingyf
 */
@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class MyControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public void errorHandler(Exception ex) {
        logger.error("程序发生异常", ex);
        //ModelAndView modelAndView = new ModelAndView("error");
        //modelAndView.addObject("errMsg", ex.getMessage());
        //return modelAndView;
        throw new RuntimeException(ex.getMessage());
    }

    /**
     * Validator数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getValidBindingResultMap(e.getBindingResult());
    }

    private Result getValidBindingResultMap(BindingResult bindingResult) {
        FieldError error = bindingResult.getFieldError();
        String field = error.getField();
        String message = error.getDefaultMessage();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(field, message);
        logger.error(message);
        return new Result(false, ResultCodeEnum.EXCEPTION.getId(), "参数异常", errorMap);
    }
}
