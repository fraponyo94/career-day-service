package task.sancom.restapi.careerdayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JobHasMaximumParticipantsException extends RuntimeException {
    final static String message ="Maximum number of participants reached.Please try again later";

    public JobHasMaximumParticipantsException(){
        super(message);
    }

    public JobHasMaximumParticipantsException(Throwable err){
        super(message,err);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

