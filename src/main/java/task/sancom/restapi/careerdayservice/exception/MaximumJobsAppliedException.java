package task.sancom.restapi.careerdayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MaximumJobsAppliedException  extends RuntimeException{
    final static String message = "Limit reached.A maximum of 3 interviews is allowed per day";

    public MaximumJobsAppliedException(){
        super(message);
    }



    public MaximumJobsAppliedException (Throwable err) {
        super(message, err);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
