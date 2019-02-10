package info.campersites.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import info.campersites.bo.Errors;

@ControllerAdvice
public class ValidationHandler {
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException exception) {
        Errors errors = new Errors();
        for (FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
            errors.addErrorInfo(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<Errors>(errors, HttpStatus.BAD_REQUEST);
    }

}
