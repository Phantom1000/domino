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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Half other = (Half) obj;
        if (number != other.number)
            return false;
        return true;
    }

    
    
}