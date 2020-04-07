package com.phantom;

public class Bone {
    private Player owner;
    private Half firstHalf;
    private Half secondHalf;

    public Half getFirstHalf() {
        return firstHalf;
    }

    public void setFirstHalf(Half firstHalf) {
        this.firstHalf = firstHalf;
    }

    public Half getSecondHalf() {
        return secondHalf;
    }

    public void setSecondHalf(Half secondHalf) {
        this.secondHalf = secondHalf;
    }

    public boolean attach(Bone other) {
        if (firstHalf.getLink() == null) {
            if (other.firstHalf.getLink() == null && other.firstHalf.getNumber() == firstHalf.getNumber()) {
                other.firstHalf.setLink(this);
                firstHalf.setLink(other);
                return true;
            }
            if (other.secondHalf.getLink() == null && other.secondHalf.getNumber() == firstHalf.getNumber()) {
                other.secondHalf.setLink(this);
                firstHalf.setLink(other);
                return true;
            }
        }
        if (secondHalf.getLink() == null) {
            if (other.firstHalf.getLink() == null && other.firstHalf.getNumber() == secondHalf.getNumber()) {
                other.firstHalf.setLink(this);
                secondHalf.setLink(other);
                return true;
            }
            if (other.secondHalf.getLink() == null && other.secondHalf.getNumber() == secondHalf.getNumber()) {
                other.secondHalf.setLink(this);
                secondHalf.setLink(other);
                return true;
            }
        }
        return false;
    }

    public Bone(Half firstHalf, Half secondHalf) {
        this.firstHalf = firstHalf;
        this.secondHalf = secondHalf;
    }

    @Override
    public String toString() {
        return String.format("[%s | %s]", firstHalf, secondHalf);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    public static int compare(Bone b1, Bone b2) {
        if (b1.getFirstHalf().getNumber() + b1.getSecondHalf().getNumber() > b2.getFirstHalf().getNumber() + b2.getSecondHalf().getNumber())
            return 1;
        return -1;
    }

    
}