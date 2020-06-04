package com.phantom.entities;

import java.util.Set;
//@JSONAutoDetect
public class Player {

    //private Set<Bone> bones;
    private String name;
    private String color;

    /*public Set<Bone> getBones() {
        return bones;
    }*/

    /*public void setBones(Set<Bone> bones) {
        this.bones = bones;
    }*/

    /*public String toString() {
        String res = name;
        for (Bone b : bones) { //*
            res += b;
        }
        res += "----";
        return res;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player() {
    }

    public Player(String name, String color) {
        //this.bones = bones;
        this.name = name;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}