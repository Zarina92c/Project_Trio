package Practice;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
        System.out.println("countUsers = " + countUsers);
        int countBooks = Integer.parseInt(booksCount);
        System.out.println("countBooks = " + countBooks);
        int countBorrowed = Integer.parseInt(borrowedBooksCount);
        System.out.println("countBorrowed = " + countBorrowed);



        String url= ConfigurationReader.getProperty("library2.database.url");
        String userName= ConfigurationReader.getProperty("library2.database.username");
        String passWord= ConfigurationReader.getProperty("library2.database.password");
        DB_Utility.createConnection(url, userName, passWord);

        String query1 = "select * from users";
        String query2= "select * from books";
        String query3 = "select * from book_borrow where is_returned=0";


        DB_Utility.runQuery(query1);
        int expectedUsersCount =  DB_Utility.getRowCount();
        System.out.println("Users Count: "+ expectedUsersCount);

        DB_Utility.runQuery(query2);
        int actualNumberOfBooks = DB_Utility.getRowCount();
        System.out.println("Books Count: "+ actualNumberOfBooks);

        DB_Utility.runQuery(query3);
        int actualBorrowedBooksCount = DB_Utility.getRowCount();
        System.out.println("Borrows books count: "+actualBorrowedBooksCount);


        Assert.assertEquals(expectedUsersCount, countUsers);
        Assert.assertEquals(actualBorrowedBooksCount, countBorrowed);
        Assert.assertEquals(actualNumberOfBooks, countBooks);








    }





}
