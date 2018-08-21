package com.oocode;

import org.concordion.api.FullOGNL;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@FullOGNL
@RunWith(ConcordionRunner.class)
public class BookSearchExclusionBasedOnReviewAgeFixture {

    public boolean SearchForBook(String bookTitle,Boolean isClassic,Boolean lastReviewOlderThanOneYear, String searchTerm)
    {
        IsClassicResolver isClassicResolver;
        isClassicResolver = mock(IsClassicResolver.class);
        when(isClassicResolver.isClassic(bookTitle)).thenReturn(isClassic);
        BookClub bookClub = new BookClub(isClassicResolver);
        Review review;
        if (lastReviewOlderThanOneYear){
            review = ReviewUtil.getOlderThanOneYearReview();
        }else
        {
            review = ReviewUtil.getNewReview();
        }
        bookClub.addReview(bookTitle,  review);

        List<String> results = bookClub.search(searchTerm);
        if (results.size() == 0)
            return false;
        return bookClub.search(searchTerm).get(0).equalsIgnoreCase(bookTitle);
    }

}
