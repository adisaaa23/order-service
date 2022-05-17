package id.saputra.adi.orderservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = -5474045229894655529L;
    private final String responseKey;
    private final HttpStatus httpStatus;

    public ApplicationException(String responseKey, HttpStatus httpStatus){
        super();
        this.responseKey = responseKey;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage(){
        return String.format("Application got an error %s with http status code %s", responseKey, httpStatus);
    }

}
