package com.oocode;

import java.util.Calendar;
import java.util.Date;

public class ReviewUtil {
    static public Review getNewReview()
    {
        return new Review("great", new Date());
    }
    static public Review getOlderThanOneYearReview()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -2);
        Date date =  cal.getTime();
        return new Review("great", date);
    }
}
