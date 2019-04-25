package task.sancom.restapi.careerdayservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class TimeFormatterComponent {



    public Date date(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");


        Date date = formatter.parse(dateInString);

        return date;


    }
    public  Date time (String time) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = formatter.parse(time);
        return date;



    }


}
