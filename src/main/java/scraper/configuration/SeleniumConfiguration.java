package scraper.configuration;


import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.sk.PrettyTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SeleniumConfiguration {
    @PostConstruct
    void PostConstruct() {

        System.setProperty("webdriver.chrome.driver", "C:/Users/bigma/Downloads/chrome2/chromedriver4.exe");
    }
@Bean
public ChromeDriver driver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--headless");
    //chromeOptions.addArguments("window-size=1920,1080");
return new ChromeDriver(chromeOptions);
}

@Bean
    public PrettyTable table() {
        final String[] Headers = {"Teams", "Spread", "Total", "Moneyline"};
        return new PrettyTable(Headers);
}

}