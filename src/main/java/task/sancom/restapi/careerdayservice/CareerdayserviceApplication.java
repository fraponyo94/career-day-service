package task.sancom.restapi.careerdayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class CareerdayserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerdayserviceApplication.class, args);

        System.out.println(new Date().getTime());
        System.out.println(new Date());
    }


}
