package net.cybermak.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
public class SpringRestApplication extends SpringBootServletInitializer {
    final static Logger logger = LoggerFactory.getLogger(SpringRestApplication.class);
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringRestApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
        logger.info("application started successfully");


    }

    @RequestMapping(value = "/hello")
    public String hello() {
        logger.info("Calling hello Method");
        return "Hello , Wafa";
    }

    public static class Main {
        public static void main(String[] args) {
            String[] addresses = new String[15];
            String finalString = "";
            Arrays.fill(addresses,"static");
            addresses[0] = "wafa";
            addresses[1] = "marwa";
            addresses[2] = "abdo";
            addresses[5] = "abdo2";
            addresses[5] = "abdo2";
            String temp = "";
            for (int i = 0; i < addresses.length; i++) {
                System.out.println("----------------------trial " + i + "----------------------");
                String index = addresses[i];
                if (index != "static") {
                    System.out.println(index);
                    System.out.println("single address: " + index);
                    temp = temp + ", " + index;
                    System.out.println("concatenated address: " + temp);
                }

            }
            System.out.println("line 26: " + temp.replaceFirst(", ", " "));
            finalString = temp.replaceFirst(", ", " ");
            System.out.println("line 28 :" + finalString);
            if (finalString.length() > 0) {
                finalString = finalString + ".";
            }
            System.out.println(finalString);


        }
    }
}
