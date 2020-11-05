package ro.ubb.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class SpecialError extends HttpServerErrorException {
    public SpecialError(String message, HttpStatus httpStatus){
        super(httpStatus,message);
    }
}
