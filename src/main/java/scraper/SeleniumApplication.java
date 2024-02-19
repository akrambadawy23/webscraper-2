package scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.openqa.selenium.chromium.ChromiumDriver;

@SpringBootApplication
@EnableAsync
public class SeleniumApplication {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        SpringApplication.run(SeleniumApplication.class, args);
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        System.out.println("TIME RUN: " + totalTime + " seconds");
    }

}
