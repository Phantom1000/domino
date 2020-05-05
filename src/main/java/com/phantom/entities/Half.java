package com.phantom.entities;

public class Half {
    private Points points;
    private Bone link;

    public int getPoints() {
        return points2Number();
    }

    public void setPoints(Points points) {
        this.points = points;
    }

    public Bone getLink() {
        return link;
    }

    public void setLink(Bone link) {
        this.link = link;
    }

    public Half(Points points, Bone link) {
        this.points = points;
        this.link = link;
    }

    public Half(int points) {
        this.points = number2Points(points);
        link = null;
    }

    private int points2Number() {
        int number = 0;
        switch (points) {
            case zero: number = 0; break;
            case one: number = 1; break;
            case two: number = 2; break;
            case three: number = 3; break;
            case four: number = 4; break;
            case five: number = 5; break;
            case six: number = 6;
        }
        return number;
    }

    private Points number2Points(int i) {
        Points points = null;
        switch (i) {
            case 0: points = Points.zero; break;
            case 1: points = Points.one; break;
            case 2: points = Points.two; break;
            case 3: points = Points.three; break;
            case 4: points = Points.four; break;
            case 5: points = Points.five; break;
            case 6: points = Points.six;
        }
        return points;
    }

    @Override
    public String toString() {
        return String.valueOf(points2Number());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + points2Number();
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
        if (points != other.points)
            return false;
        return true;
    }

    
    
}