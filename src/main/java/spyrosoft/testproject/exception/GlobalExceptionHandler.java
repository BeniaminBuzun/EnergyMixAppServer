package spyrosoft.testproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CarbonIntensityApiException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public String handleCarbonIntensityApiException(CarbonIntensityApiException ex) {
		return ex.getMessage();
	}

}
