package com.phantom.entities;

import java.util.List;

public class Bone {
    private List<Half> halfs;
    private Player owner;

    public Bone(List<Half> halfs) {
        this.halfs = halfs;
    }

    @Override
    public String toString() {
        StringBuffer res = new StringBuffer("[");
        for (Half h : halfs) {
            res.append(h.getPoints() + "|");
        }
        res.append("]");
        return res.toString();
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

}