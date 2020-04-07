package com.phantom;

public class Half {
    private int number;
    private Bone link;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Bone getLink() {
        return link;
    }

    public void setLink(Bone link) {
        this.link = link;
    }

    public Half(int number, Bone link) {
        this.number = number;
        this.link = link;
    }

    public Half(int number) {
        this.number = number;
        link = null;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    
}