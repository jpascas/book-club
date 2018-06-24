package com.oocode;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void t1() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals("if search string is NOT ALL UPPER case " +
                        "then it means search matching start of title",
                asList("Hello Wally", "Hello World"),
                bookClub.search("Hel"));
        assertEquals(asList("Hello Wally", "Hello World"),
                bookClub.search("hel"));
    }

    @Test
    public void canSearchByInitials() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Hello Wally", "Hello World"),
                bookClub.search("HW"));
    }

    @Test
    public void t3() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("hello world", "great");
        bookClub.addReview("hello wally", "boring");

        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("Hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("HW"));
    }

    @Test
    public void t4() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello World", "the best book ever");

        assertEquals(asList("great", "the best book ever"),
                bookClub.reviewsFor("Hello World"));
    }

    @Test
    public void t5() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");
        // Should pass but it depends what the external service
        // determines is a "classic", which might change
        assertEquals(singletonList("Hello World"),
                bookClub.classics("HW"));
        assertEquals(singletonList("Hello World"),
                bookClub.classics("He"));
    }
}
