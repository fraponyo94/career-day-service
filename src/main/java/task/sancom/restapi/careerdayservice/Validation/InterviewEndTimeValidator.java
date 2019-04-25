//package task.sancom.restapi.careerdayservice.Validation;
//
//import task.sancom.restapi.careerdayservice.entity.Job;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.sql.Time;
//
//public class InterviewEndTimeValidator implements ConstraintValidator<PhoneNumberConstraint, String>  {
//
//    @Override
//    public void initialize(InterviewEndTimeConstraint constraint) {
//    }
//
//    @Override
//    public boolean isValid(Time interviewEndTime,
//                           ConstraintValidatorContext cxt) {
//        boolean ok = false;
//        try{
//            ok =interviewEndTime != null && interviewEndTime;
//        }catch (NoSuchFieldException e){
//
//        }
//        return ok;
//    }
//
//}
