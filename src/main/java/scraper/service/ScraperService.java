package scraper.service;


//import com.sun.tools.jdeprscan.Pretty;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sk.PrettyTable;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scraper.SeleniumApplication;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScraperService {
    private static final String URL = "https://sportsbook.draftkings.com/leagues/basketball/atp";
    private static final String URL2 = "https://www.betus.com.pa/sportsbook/";
    private static final String URL3 = "https://www.bovada.lv/sports/tennis";
    private static final String URL4 = "https://nitrobetting.eu/sportsbook/tennis/";

    //public PrettyTable table;

    //private  ChromiumDriver driver;
    public List<WebElement> words;
    public List<WebElement> teams;
    public List<WebElement> odds;

    public List<WebElement> teams2;
    public List<WebElement> odds2;

    public List<WebElement> teams3;
    public List<WebElement> odds3;

    public List<WebElement> teams4;
    public List<WebElement> odds4;

    public List<String> oddsText;
    public List<String> teamsText;
    public List<String> odds2Text;

    public static class PrettyTable {
        private List<String> headers = new ArrayList<>();
        private List<List<String>> data = new ArrayList<>();

        public PrettyTable(String... headers) {
            this.headers.addAll(Arrays.asList(headers));
        }

        public void addRow(String... row) {
            data.add(Arrays.asList(row));
        }

        private int getMaxSize(int column) {
            int maxSize = headers.get(column).length();
            for (List<String> row : data) {
                if (row.get(column).length() > maxSize)
                    maxSize = row.get(column).length();
            }
            return maxSize;
        }

        private String formatRow(List<String> row) {
            StringBuilder result = new StringBuilder();
            result.append("|");
            for (int i = 0; i < row.size(); i++) {
                result.append(StringUtils.center(row.get(i), getMaxSize(i) + 2));
                result.append("|");
            }
            result.append("\n");
            return result.toString();
        }

        private String formatRule() {
            StringBuilder result = new StringBuilder();
            result.append("+");
            for (int i = 0; i < headers.size(); i++) {
                for (int j = 0; j < getMaxSize(i) + 2; j++) {
                    result.append("-");
                }
                result.append("+");
            }
            result.append("\n");
            return result.toString();
        }

        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append(formatRule());
            result.append(formatRow(headers));
            result.append(formatRule());
            for (List<String> row : data) {
                result.append(formatRow(row));
            }
            result.append(formatRule());
            return result.toString();
        }

    }

    @PostConstruct
    void PostConstruct() throws InterruptedException, IOException {


        //PrettyTable table = new PrettyTable("Teams", "Spread", "Total", "Moneyline");
        //PrettyTable table = new PrettyTable("Teams", "Spread", "Total", "Moneyline");

       /*CompletableFuture<Void> a = CompletableFuture.runAsync(this::scrape);
       CompletableFuture<Void> b = CompletableFuture.runAsync(this::scrape2);

       boolean c = false;
       while(!c) {
           c = a.isDone() && b.isDone();
       }
*/
        long startTime = System.nanoTime();
        scrape2();
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/1000000000;
        System.out.println("TIME RUN: " + totalTime + " seconds");

        startTime = System.nanoTime();
        scrape3();
        endTime = System.nanoTime();
        totalTime = (endTime - startTime)/1000000000;
        System.out.println("TIME RUN: " + totalTime + " seconds");
        //scrape4();


        //odds.removeIf(Objects::isNull);

/*
        for(int i = 0; i < Math.min(teams.size(), 0.333333333*odds.size()-2); i++) {
           // System.out.println("QQQQ");

            try {
                if(i==0)
                        table.addRow(teams.get(i).getText().trim(), oddsText.get(0).replaceAll("\\s", ""), oddsText.get(1).replaceAll("\\s", ""), oddsText.get(2).replaceAll("\\s", ""));
else
                    table.addRow(teams.get(i).getText().trim(), oddsText.get(3*i).replaceAll("\\s", ""), oddsText.get(3*i+1).replaceAll("\\s", ""), oddsText.get(3*i+2).replaceAll("\\s", ""));

            } catch(StaleElementReferenceException ignored) {
//System.out.println("ZZZZZ");
            }
        }

 */
          /*  int k = 0;
            Set<String> duplicateSet = new HashSet<String>();
            //duplicateSet.clear();
            System.out.println(odds2.size() + " " + teams2.size());
            for (int j = 0; j < odds2.size(); j += 3) {
                try {
                    if (j + 2 >= odds2.size() || k >= teams2.size())
                        break;
                    if (!duplicateSet.contains(teams2.get(k).getAttribute("textContent").trim())) {
                        duplicateSet.add(teams2.get(k).getAttribute("textContent").trim());
                        table.addRow(teams2.get(k).getAttribute("textContent").trim(), odds2.get(j).getAttribute("textContent").replaceAll("\\s", ""), odds2.get(j + 2).getAttribute("textContent").replaceAll("\\s", ""), odds2.get(j + 1).getAttribute("textContent").replaceAll("\\s", ""));
                    }
                    k += 1;
                } catch (StaleElementReferenceException ignored) {
                    System.out.println("STALE!!");
                }
            }

            int h = 0;
            Set<String> duplicateSet2 = new HashSet<String>();
            //duplicateSet2.clear();
            System.out.println(teams3.size() + " " + odds3.size());
            for (int j = 0; j < odds3.size(); j++) {

                if (j % 2 == 0 && j != 0)
                    j += 4;

                if (j + 4 >= odds3.size() || h >= teams3.size()) {
                    break;
                }
                try {
                    if(!duplicateSet2.contains(teams3.get(h).getAttribute("textContent").trim())) {
                        table.addRow(teams3.get(h).getAttribute("textContent").trim(), odds3.get(j).getAttribute("textContent").replaceAll("\\s", ""), odds3.get(j + 4).getAttribute("textContent").replaceAll("\\s", ""), odds3.get(j + 2).getAttribute("textContent").replaceAll("\\s", ""));
                        duplicateSet2.add(teams3.get(h).getAttribute("textContent").trim());
                        System.out.println("ding!!");
                   }
                } catch (StaleElementReferenceException ignored) {
                    System.out.println("h!!");

                }
                h += 1;
            }

            int q = 0;
            System.out.println(teams4.size() + " " + odds4.size());
            for (int j = 0; j < odds4.size(); j++) {

                if (j % 2 == 0 && j != 0)
                    j += 4;

                if (j + 4 >= odds4.size() || q >= teams4.size()) {
                    break;
                }
                try {
                    table.addRow(teams4.get(q).getAttribute("textContent").trim(), odds4.get(j + 2).getAttribute("textContent").replaceAll("\\s", ""), odds4.get(j + 4).getAttribute("textContent").replaceAll("\\s", ""), odds4.get(j).getAttribute("textContent").replaceAll("\\s", ""));
                    //System.out.println("ding2!!");
                } catch (StaleElementReferenceException ignored) {
                    System.out.println("h2!!");

                }
                q += 1;
            }
            */

        //System.out.println("DEBUG!!!!");
        //System.out.print(table);
        ArbFinder(teams, odds);
        //scrape2();
    }



      /*  public void scrape() {

            driver.get(URL);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
           System.out.println("--------h------");

            final WebElement words = driver.findElement(By.xpath("//*[@class='sportsbook-table__body']"));
            final List<WebElement> wordList = words.findElements(By.xpath( ".//td[contains(@class,'sportsbook-table__column-row')]"));

            //final List<WebElement> filter = words.findElements(By.tagName("td"));

            teams.addAll(words.findElements(By.xpath("//*[@class='event-cell__name-text']")));
            odds.addAll(words.findElements(By.xpath("//div[contains(@role, 'button') or contains(@class, 'sportsbook-empty-cell')]")));
            odds.removeAll(odds.subList(0,4));


            int test = 0;
            WebElement lastElement = odds.get(0);
            for (WebElement odd: odds) {
                try {
                    boolean detect = (odd.findElement(By.xpath("./..")).equals(lastElement));
                    if (!Objects.equals(odd.getText(), "")) {
                        oddsText.add(odd.getText());
                        test = 0;
                    } else if(!detect) {
                        oddsText.add("");
                        test++;
                    } else {
                        //oddsText.add("IGNORE");
                    }
                    lastElement = odd;
                } catch(StaleElementReferenceException e) {
oddsText.add("DEBUG");
                }
            }



       }
*/


    public void scrape2() {

        ChromiumDriver driver2 = new ChromeDriver();
        driver2.get(URL2);

        driver2.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver2.get(URL2 + "atp");
        //driver2.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //  teams2.clear();
        //odds2.clear();
        int a = odds.size();
        int b = teams.size();
        Set<String> duplicateSet = new HashSet<>();
        int j = 0;
        for(int i = 0; i < driver2.findElements(By.xpath("//*[@class='team t-desc col-10']")).size()-1; i++) {
            String add ="(//*[@class='team t-desc col-10'])" + "[" + (i+1) + "]";
            if(!duplicateSet.contains(driver2.findElement(By.xpath(add)).getAttribute("textContent").trim())) {
                teams.add(driver2.findElement(By.xpath(add)));
                System.out.println(teams.get(j).findElement(By.xpath("./../..")).findElement(By.xpath(".//*[@class='g-ln col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='g-ln col-3 p-0 col-lg-2 border-top-0 line-container border-bottom-0' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 line-container'][2]")).getAttribute("textContent").trim());
                odds.add(teams.get(j).findElement(By.xpath("./../..")).findElement(By.xpath(".//*[@class='g-ln col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='g-ln col-3 p-0 col-lg-2 border-top-0 line-container border-bottom-0' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 line-container'][2]")));
                j++;
                duplicateSet.add(driver2.findElement(By.xpath(add)).getAttribute("textContent").trim());
            }
        }
        /*
        //teams.addAll(driver2.findElements(By.xpath("//*[@class='team t-desc col-10']")));
        odds.addAll(driver2.findElements(By.xpath("//*[@class='g-ln col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='col-3 p-0 col-lg-2 line-container border-bottom-0' or @class='g-ln col-3 p-0 col-lg-2 border-top-0 line-container border-bottom-0' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 border-left-0 line-container' or @class='g-ln col-3 col-lg-2 p-0 line-container']")));

int k = 0;

        for(int i = b; i < teams.size(); i++) {
            try {
                if (duplicateSet.contains(teams.get(i).getAttribute("textContent").trim())) {
                    int finalI = i;
                    odds.removeIf(odd -> odd.findElement(By.xpath("./../..")).equals(teams.get(finalI).findElement(By.xpath("./../.."))));
                    teams.remove(i);
                } else
                    duplicateSet.add(teams.get(i).getAttribute("textContent").trim());
            } catch (StaleElementReferenceException e) {
                System.out.println("300");
            }
        }

        for (int j = a; j < odds.size(); j += 3) {
            try {


                //persons.removeIf(p -> !namesAlreadySeen.add(p.getName()));

                if (j + 2 >= odds.size() || k >= teams.size())
                    break;
Collections.swap(odds, j+2, j+1);
                k += 1;
            } catch (StaleElementReferenceException ignored) {
                System.out.println("STALE!!");
            }
        }
*/
        //driver2.quit();
        System.out.println("YIPPEE!!!");
    }

    public void scrape3() {

        ChromiumDriver driver3 = new ChromeDriver();
        driver3.get(URL3);


        //driver3.get(URL3 + "college-basketball");
        //WebDriverWait wait = new WebDriverWait(driver3, Duration.ofSeconds(5));
        // wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='bet-btn']")));
        driver3.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //teams3.clear();
        // odds3.clear();
        int a = odds.size();
        int j = teams.size();
        System.out.println(j);
        int i2 = 0;
        Set<String> duplicateSet2 = new HashSet<>();
        for(int i = 0; i < driver3.findElements(By.xpath("//*[@class='name']")).size(); i++) {
            String add ="(//*[@class='name'])" + "[" + (i+1) + "]";
            try { if(!duplicateSet2.contains(driver3.findElement(By.xpath(add)).getAttribute("textContent").trim())) {
                System.out.println(driver3.findElement(By.xpath(add)).getText().trim());
                String add2;
                if(i%2 == 0)
                    add2 = "(.//*[@class='bet-btn' or @class='empty-bet' or @class='bet-btn suspended' or @class='bet-btn-price-increased' or @class='bet-btn-price-decreased'])[3]";
                else
                    add2 = "(.//*[@class='bet-btn' or @class='empty-bet' or @class='bet-btn suspended' or @class='bet-btn-price-increased' or @class='bet-btn-price-decreased'])[4]";
                //i2++;
                //System.out.println(add2);
                System.out.println(driver3.findElement(By.xpath(add)).findElement(By.xpath("./../../../../../..")).findElement(By.xpath(add2)).getAttribute("textContent").trim());

                //System.out.println(teams.size());
                teams.add(driver3.findElement(By.xpath(add)));
                odds.add(driver3.findElement(By.xpath(add)).findElement(By.xpath("./../../../../../..")).findElement(By.xpath(add2)));
                //System.out.println(driver3.findElement(By.xpath(add)).findElement(By.xpath("./../../../../../..")).findElement(By.xpath(add2)));

                //j++;
                duplicateSet2.add(driver3.findElement(By.xpath(add)).getAttribute("textContent").trim());
            }
            } catch(StaleElementReferenceException e) {
                System.out.println("STALE##!!!");
                //odds.add(driver3.findElement(By.xpath("//*[@class='bet-btn' or @class='empty-bet' or @class='bet-btn suspended' or @class='bet-btn-price-increased' or @class='bet-btn-price-decreased']")));
            }
        }
        /*
        teams.addAll(driver3.findElements(By.xpath("//*[@class='name']")));
        odds.addAll(driver3.findElements(By.xpath("//*[@class='bet-btn' or @class='empty-bet' or @class='bet-btn suspended']")));

        int k = 0;

        Set<String> duplicateSet = new HashSet<>();
        for(int i = b; i < teams.size(); i++) {
            try {
                if (duplicateSet.contains(teams.get(i).getAttribute("textContent").trim())) {
                    int finalI = i;
                    odds.removeIf(odd -> odd.findElement(By.xpath("./../../../../../..")).equals(teams.get(finalI).findElement(By.xpath("./../../../../../.."))));
                    teams.remove(i);
                } else
                    duplicateSet.add(teams.get(i).getAttribute("textContent").trim());
            } catch (StaleElementReferenceException e) {
                System.out.println("500");
            }
            }


        for (int j = a; j < odds.size(); j++) {
            try {
                if (j % 2 == 0 && j != 0)
                    j += 4;

                if (j + 4 >= odds.size() || k >= teams.size())
                    break;
                Collections.swap(odds, j+1, j+4);
                k++;
            } catch (StaleElementReferenceException ignored) {
                System.out.println("STALE!!");
            }
        }
      */
        //driver3.quit();

    }

    public void scrape4() {

        ChromiumDriver driver4 = new ChromeDriver();
        driver4.get(URL4);

        driver4.switchTo().frame("iframe-sports");
        //driver3.get(URL3 + "college-basketball");
        WebDriverWait wait = new WebDriverWait(driver4, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='vjymC wNMdb']")));
        driver4.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        teams.addAll(driver4.findElements(By.xpath("//*[@class='SQE5u Nx10j']")));
        odds.addAll(driver4.findElements(By.xpath("//*[@class='vjymC wNMdb']")));
      /*  Predicate<WebElement> suspended = odd -> odd.findElement(By.xpath("./../../..")).getAttribute("class").contains("suspended");
       try {
           odds3.removeIf(suspended);
       } catch(StaleElementReferenceException ignored) {
           System.out.println("scrape3 debug");
       }
       */
        //driver3.quit();

    }

    public void ArbFinder(List<WebElement> teams, List<WebElement> odds) throws IOException {
        //PrettyTable table2 = new PrettyTable("Team", "Spread", "Total", "Moneyline", "Spread2", "Total2", "Moneyline2");
        String[][] Duplicates = new String[teams.size()][3];
        int count = 0;
        int k = 0;
        int count2 = 0;
        String[] teamsText = new String[teams.size()];
        String[] oddsText = new String[odds.size()];


        for(int q = 0; q < teams.size(); q++) {
            try {
                teamsText[q] = teams.get(q).getAttribute("textContent").trim();
            } catch (StaleElementReferenceException e) {
                teamsText[q] = "N/A";
                System.out.println("ignored1");
            }
        }

        for(int q = 0; q < odds.size(); q++) {
            try {
                oddsText[q] = odds.get(q).getAttribute("textContent").replaceAll("\\s", "");
            } catch (StaleElementReferenceException e) {
                oddsText[q] = "-100000";
                System.out.println("ignored2");
            }
        }




        for (int i = 0; i < teamsText.length; i++) {

            for (int j = i + 1; j < teamsText.length; j++) {
                boolean tru = Objects.equals(teamsText[i], teamsText[j]) && !Objects.equals(teamsText[j], "N/A");
                if (tru) {
                    //System.out.println(teams.get(j).getAttribute("textContent").trim());
                    Duplicates[count][0] = teamsText[j];
                    //teams.remove(i);
                    //Duplicates[count+1][0] = teams.get(j);

                    if (count2 % 2 == 0 && count2 != 0)
                        count2 += 4;


                    Duplicates[count][1] = oddsText[i];
                    Duplicates[count][2] = oddsText[j];

                    System.out.println(Duplicates[count][0] +" " + Duplicates[count][1] + " " +Duplicates[count][2] );
                    count++;

                    //table2.addRow(table.data.get(i).get(0), table.data.get(i).get(1), table.data.get(i).get(2), table.data.get(i).get(3), table.data.get(j).get(1), table.data.get(j).get(2), table.data.get(j).get(3));
                    int n = i / 2;
                    if (n == (i + 1) / 2) {
                        //table2.addRow(table.data.get(i + 1).get(0), table.data.get(i + 1).get(1), table.data.get(i + 1).get(2), table.data.get(i + 1).get(3), table.data.get(j + 1).get(1), table.data.get(j + 1).get(2), table.data.get(j + 1).get(3));
                        //table.data.remove(j + 1);
                        Duplicates[count][0] = teamsText[i+1];
                        Duplicates[count][1] = oddsText[i+1];
                        Duplicates[count][2] = oddsText[j+1];
                        teamsText[i+1] = null;
                        oddsText[i+1] = null;
                        oddsText[j+1] = null;

                        //teams.remove(i+1);
                        //odds.remove(i+1);
                        //odds.remove(j+1);
                        count++;
                    }

                    else {
                        Duplicates[count][0] = teamsText[i-1];
                        Duplicates[count][1] = oddsText[i-1];
                        Duplicates[count][2] = oddsText[j-1];
                        teamsText[i-1] = null;
                        oddsText[i-1] = null;
                        oddsText[j-1] = null;

                        //teams.remove(i-1);
                        //odds.remove(i-1);
                        //odds.remove(j-1);
                        //i--;
                        count++;
                    }




                    count2++;   //table2.addRow(table.data.get(i - 1).get(0), table.data.get(i - 1).get(1), table.data.get(i - 1).get(2), table.data.get(i - 1).get(3), table.data.get(j - 1).get(1), table.data.get(j - 1).get(2), table.data.get(j - 1).get(3));

                }
            }
            k+=3;
        }

        String[][] Arbitrages = new String[Duplicates.length][3];


        //System.out.println(table2);
        System.out.println(Arbitrages.length);
        int counter = 0;
        int a = Duplicates.length-1;
        for(int i = 0; i < Duplicates.length; i++) {
            if(Objects.equals(Duplicates[i][0], null)) {
                a = i;
                break;
            }
        }


        for (int i = 0; i < a; i += 2) {
            double impliedProb = 0;
            double impliedProb2 = 0;
            double probA = 1;
            double probB = 1;
            double probC = 1;
            double probD = 1;
            boolean b;

            int oddA;
            int oddC;
            int oddB;
            int oddD;
            String moneyline1;
            String moneyline2;
            moneyline1 = Duplicates[i][1];
            moneyline2 = Duplicates[i][2];



            if (moneyline1.contains("+")) {
                oddA = Integer.parseInt(moneyline1.substring(1));
                probA = (double) 100 / (100 + (oddA));
            } else if (moneyline1.contains("-")) {
                oddA = Integer.parseInt(moneyline1.substring(1));
                probA = (double) oddA / (100 + (oddA));
            }
            else {
                probA = 0.5;
            }
            if (moneyline2.contains("+")) {
                oddB = Integer.parseInt(moneyline2.substring(1));
                probB = (double) 100 / (100 + (oddB));
            } else if (moneyline2.contains("-")) {
                oddB = Integer.parseInt(moneyline2.substring(1));
                probB = (double) oddB / (100 + (oddB));
            } else
                probB = 0.5;

            impliedProb += probA;
            impliedProb2 += probB;


            moneyline1 = Duplicates[i+1][1];
            moneyline2 = Duplicates[i+1][2];

            if (moneyline1.contains("+")) {
                oddC = Integer.parseInt(moneyline1.substring(1));
                probA = (double) 100 / (100 + (oddC));
                //  System.out.println(probA);

            } else if (moneyline1.contains("-")) {
                oddC = Integer.parseInt(moneyline1.substring(1));
                probA = (double) oddC / (100 + (oddC));
                // System.out.println(probA);
            }
            else
                probA = 0.5;


            if (moneyline2.contains("+")) {
                oddD = Integer.parseInt(moneyline2.substring(1));
                probB = (double) 100 / (100 + (oddD));
                // System.out.println(probB);
            } else if (moneyline2.contains("-")) {
                oddD = Integer.parseInt(moneyline2.substring(1));
                probB = (double) oddD / (100 + (oddD));
                // System.out.println(probB);
            } else
                probB = 0.5;

            impliedProb += probB;
            impliedProb2 += probA;
            double q = 100 * ((1 / Math.min(impliedProb, impliedProb2)) - 1);
            double netIncome = (double) Math.round(q * 100) / 100;



            if (impliedProb < 1 || impliedProb2 < 1) {
                System.out.print("Arbitrage Found | " + Duplicates[i][0] + " v. " + Duplicates[i+1][0] + " | Profit: " + netIncome + "%");
                Arbitrages[counter][0] = Duplicates[i][0];
                Arbitrages[counter][1] = Duplicates[i+1][0];
                Arbitrages[counter][2] = Double.toString(netIncome);
                counter++;
            } else {
                System.out.print("No | " + Duplicates[i][0] + " v. " + Duplicates[i+1][0] + " | Loss: " + netIncome + "%");
                Arbitrages[counter][0] = Duplicates[i][0];
                Arbitrages[counter][1] = Duplicates[i+1][0];
                Arbitrages[counter][2] = Double.toString(netIncome);
                counter++;
            }
            if (impliedProb < impliedProb2)
                System.out.print("  |  [" + Duplicates[i][1] + " / " + Duplicates[i+1][2] + "]\n");
            else
                System.out.print("  |  [" + Duplicates[i][2] + " / " + Duplicates[i+1][1] + "]\n");




        }
        writeToExcel(Arbitrages);
    }
    public void writeToExcel(String[][] arbitrageTable) throws IOException {
        String[] header = {"Team 1", "Team 2", "Total Return", "Time"};

        FileInputStream input = null;
        try {
            input = new FileInputStream("test.xlsx");
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        XSSFSheet spreadsheet;
        XSSFWorkbook workbook = new XSSFWorkbook(input);

        if(workbook.getNumberOfSheets() == 0)
            spreadsheet = workbook.createSheet("Arbitrages");
        else
            spreadsheet = workbook.getSheet("Arbitrages");

        XSSFRow row;

        Row headerRow = spreadsheet.createRow(0);
        for(int a = 0; a < header.length; a++) {
            Cell cell = headerRow.createCell(a);
            cell.setCellValue(header[a]);
        }
        int lastRow = spreadsheet.getLastRowNum();
        for(int i = 1; i < (arbitrageTable.length/2)+1; i++) {
            row = spreadsheet.createRow(lastRow+i);
            for(int j = 0; j < 3; j++) {
                Cell cell = row.createCell(j);

                cell.setCellValue(arbitrageTable[i-1][j]);
            }
            Cell cell = row.createCell(3);
            cell.setCellValue(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        }

        try {
            FileOutputStream out = new FileOutputStream(new File("test.xlsx"));
            workbook.write(out);
            out.close();
        } catch(FileNotFoundException ignored) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
