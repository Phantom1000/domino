package com.phantom.entities;

import java.util.List;
import java.util.stream.Collectors;

//@JSONAutoDetect
public class Bone {
    private List<Half> halfs;
    private Player owner;

    public Bone() {
    }

    public Bone(List<Half> halfs) {
        this.halfs = halfs;
    }

    @Override
    public String toString() {
        return halfs.stream().map(x -> String.valueOf(x.getPoints())).collect(Collectors.joining("|", "[", "]"));
    }

    public List<Half> getHalfs() {
        return halfs;
    }

    public void setHalfs(List<Half> halfs) {
        this.halfs = halfs;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getSum() {
        int sum = 0;
        for (Half h: halfs) {
            sum += h.getPoints();
        }
        return sum;
    }

}