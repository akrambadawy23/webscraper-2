package scraper.service;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScraperService {
private static final String URL = "https://relatedwords.org/relatedto/";
private final ChromeDriver driver;


@PostConstruct
void PostConstruct() {
scrape("quarter");
}
public void scrape(final String value) {
    driver.get(URL + value);
    final WebElement words = driver.findElement(By.className("words"));
    final List<WebElement> wordList = words.findElements(By.tagName("a"));
    wordList.forEach(word -> System.out.println(word.getText()));
    driver.quit();
}
}
