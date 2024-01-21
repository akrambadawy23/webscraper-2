package scraper.configuration;


import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration {
    @PostConstruct
    void PostConstruct() {

        System.setProperty("webdriver.chrome.driver", "C:/Users/bigma/Downloads/chrome2/chromedriver.exe");
    }
@Bean
public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
return new ChromeDriver(chromeOptions);
}

}