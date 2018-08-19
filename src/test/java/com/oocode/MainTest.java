package com.oocode;

import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MainTest {

    IsClassicResolver isClassicResolver;

    @Before
    public void Setup()
    {
        this.isClassicResolver = mock(IsClassicResolver.class);
        when(this.isClassicResolver.isClassic("This is a very weird book")).thenReturn(true);
        when(this.isClassicResolver.isClassic("Hello World")).thenReturn(true);
    }

    @Test
    public void Should_SearchingForTitleAndReturnOrderedMatchingBookTitles_WhenSearchStringIsLowerCase() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
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
    public void Should_SearchForInitialsAndReturnOrderedMatchingBookTitles_WhenSearchStringIsUpperCase() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Hello Wally", "Hello World"),
                bookClub.search("HW"));
    }

    @Test
    public void Should_SearchForInitialsAndReturnOrderedMatching_WhenSearchStringIsUpperCaseWithThreeAddedReviews() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
        bookClub.addReview("Hello", "boring");
        bookClub.addReview("Hi", "boring");
        bookClub.addReview("Halloha", "boring");

        List<String> expectedResult = asList("Halloha", "Hello", "Hi");
        List<String> realResult = bookClub.search("H");

        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials " +
                        "and it should return an ordered list",
                expectedResult,
                realResult);
    }

    @Test
    public void Should_ReturnOrderedMatchingBookTitles_WhenSearchByTitleOrByInitials() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
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
    public void Should_ReturnReviewsForABook_WhenReviewsAreRequestedForABook() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello World", "the best book ever");

        assertEquals(asList("great", "the best book ever"),
                bookClub.reviewsFor("Hello World"));
    }

    @Test
    public void Should_ReturnOnlyClassicBookTitles_WhenSearchByTitleOrByInitials() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals(singletonList("Hello World"),
                bookClub.classics("HW"));
        assertEquals(singletonList("Hello World"),
                bookClub.classics("He"));
    }


    @Test
    public void Should_ReturnOnlyClassicBookTitles_WhenSearchByTitleOrByInitialsForAWeirdBook() {
        BookClub bookClub = new BookClub(this.isClassicResolver);
        bookClub.addReview("This is a very weird book", "great");

        assertEquals(singletonList("This is a very weird book"),
                bookClub.classics("T"));
        assertEquals(singletonList("This is a very weird book"),
                bookClub.classics("This"));

        verify(isClassicResolver, times(2)).isClassic("This is a very weird book");
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_WhenSearchStringIsEmpty() {
        BookClub bookClub = new BookClub();

        bookClub.search("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_WhenSearchStringIsNull() {
        BookClub bookClub = new BookClub();

        bookClub.search(null);
    }
}
