package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.utilities.BrowserUtils;
import com.library.utilities.DBUtils;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.Map;

public class BookTableStepDefs {
    BooksPage booksPage = new BooksPage();

    @Then("book information must match the database for {string}")
    public void book_information_must_match_the_database_for(String bookName) {

        BrowserUtils.waitFor(4);
        // get the info from UI

        String actualName = booksPage.bookName.getAttribute("value");
        String actualISBN = booksPage.isbn.getAttribute("value");
        String actualYear = booksPage.year.getAttribute("value");
        String actualAuthor = booksPage.author.getAttribute("value");
        String description = booksPage.description.getText();

        System.out.println("actualName = " + actualName);
        System.out.println("actualISBN = " + actualISBN);
        System.out.println("actualYear = " + actualYear);
        System.out.println("actualAuthor = " + actualAuthor);
        System.out.println("description = " + description);


        // get info from Database

        String query= "select name, isbn, year, author, description\n" +
                "from books\n" +
                "where name = '"+bookName+"'";


        //get the data in java collection

        Map<String, Object> dbData = DBUtils.getRowMap(query);

        System.out.println(dbData.toString());

        String expectedname = (String) dbData.get("name");
        String expectedisbn = (String) dbData.get("isbn");
        String expectedyear = (String) dbData.get("year");
        String expectedauthor = (String) dbData.get("author");
        String expecteddescription = (String) dbData.get("description");


        //close connection

        DBUtils.destroy();

        // assertion, compare UI vs DATABASE info

        Assert.assertEquals(expectedname, actualName);
        Assert.assertEquals(expectedisbn, actualISBN);
        Assert.assertEquals(expectedyear, actualYear);
        Assert.assertEquals(expectedauthor, actualAuthor);
        Assert.assertEquals(expecteddescription, description);

    }


}
