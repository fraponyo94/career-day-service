package task.sancom.restapi.careerdayservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeFormatterComponent implements Converter<String, ZonedDateTime> {

      private final DateTimeFormatter formatter;


    public TimeFormatterComponent() {
            // set the zone in the formatter
            this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    }

    @Override
    public ZonedDateTime convert(String source) {
            // now the formatter has a zone set, so I can parse directly to ZonedDateTime
            return ZonedDateTime.parse(source, this.formatter);
            }
}
