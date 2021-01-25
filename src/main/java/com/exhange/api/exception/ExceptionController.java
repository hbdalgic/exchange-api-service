package com.exhange.api.exception;

import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.model.BaseResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DomainException.class)
    @ResponseBody
    public ResponseEntity<BaseResponseModel> exceptionResponse(DomainException e){
        return new ResponseEntity<>(new BaseResponseModel(e.getCode(),e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseResponseModel> exception(Exception e){
        return new ResponseEntity<>(new BaseResponseModel(ErrorEnum.ERROR.getCode(), e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseModel> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        BaseResponseModel response = new BaseResponseModel();
        if(bindingResult != null && !CollectionUtils.isEmpty(bindingResult.getAllErrors())) {
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            response.setErrorMessage(ErrorEnum.INVALID_REQUEST_MODEL.getMessage() +" -- " + objectError.getDefaultMessage());
        } else {
            response.setErrorMessage(ErrorEnum.INVALID_REQUEST_MODEL.getMessage() +" -- " + exception.getMessage());
        }
        response.setErrorCode(ErrorEnum.INVALID_REQUEST_MODEL.getCode());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
