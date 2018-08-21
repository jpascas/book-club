package com.oocode;

public class Review {
    private String text;

    public Review(String text){
        this.text  = text;
    }

    public String getText()
    {
        return this.text;
    }

    //TODO: does it make sense to override toString? Its possibly not needed right now.
}
