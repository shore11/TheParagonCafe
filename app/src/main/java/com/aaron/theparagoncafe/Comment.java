package com.aaron.theparagoncafe;

/**
 * Comment Box
 * used to facilitate sending comments about the restaurant or food
 * Created by Seth on 6/8/2017.
 */

public class Comment {
    private String comment;

    // Constructor
    public Comment(String comment) {
        this.comment = comment;
    }

    // Getter and Setter
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // We can add code once we figure out how we will send comments
    public boolean sendComment() {
        return false;
    }
}
