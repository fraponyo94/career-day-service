package task.sancom.restapi.careerdayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JobConflictException extends RuntimeException {
    final static String message ="Job interviews Conflict. You have already enrolled for another job interview which lies within the same interview date and time selected";

    public JobConflictException(){
        super(message);
    }

    public  JobConflictException(Throwable err){
        super(message,err);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }



}
