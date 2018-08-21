package com.oocode;

import java.util.Date;

public class Review {
    private String text;
    private Date date;

    public Review(String text, Date date){

        this.text  = text;
        this.date = date;
    }

    public String getText()
    {

        return this.text;
    }

    public Date getDate()
    {
        return this.date;
    }

    //TODO: does it make sense to override toString? Its possibly not needed right now.
}
