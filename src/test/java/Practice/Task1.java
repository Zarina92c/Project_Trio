package Practice;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utility.ConfigurationReader;
import utility.DB_Utility;
import utility.Driver;

public class Task1 {

/*
Use your selenium to navigate to browser
Capture the number on the screen and save it as actual result
Use your utility to run query and save expected result
Assert actual result is same as expected result.
 */

     static String usersCount;
     static String booksCount;
     static String borrowedBooksCount;
     static String usersCountSTR = "";


    @Test
    public static void captureTheNumber() {

        Driver.getDriver().get(utility.ConfigurationReader.getProperty("url"));
        String username = ConfigurationReader.getProperty("librarian.username");
        String password = ConfigurationReader.getProperty("librarian.password");


       WebElement usernameInput = Driver.getDriver().findElement(By.id("inputEmail"));
       usernameInput.sendKeys(username);

       WebElement passwordInput  = Driver.getDriver().findElement(By.id("inputPassword"));
       passwordInput.sendKeys(password+ Keys.ENTER);


        WebElement userCount = Driver.getDriver().findElement(By.id("user_count"));
        WebElement bookCount = Driver.getDriver().findElement(By.id("book_count"));
        WebElement borrowedCount = Driver.getDriver().findElement(By.id("borrowed_books"));

        usersCount= userCount.getText();
        booksCount=bookCount.getText();
        borrowedBooksCount=borrowedCount.getText();


        int countUsers = Integer.parseInt(usersCount);
        int countBooks = Integer.parseInt(booksCount);
        int countBorrowed = Integer.parseInt(borrowedBooksCount);



        String url= ConfigurationReader.getProperty("library2.database.url");
        String userName= ConfigurationReader.getProperty("library2.database.username");
        String passWord= ConfigurationReader.getProperty("library2.database.password");
        DB_Utility.createConnection(url, userName, passWord);

        String query = "select * from users";


        DB_Utility.runQuery(query);
        int expectedUsersCount =  DB_Utility.getRowCount();
        System.out.println("Users Count: "+ expectedUsersCount);







    }





}
