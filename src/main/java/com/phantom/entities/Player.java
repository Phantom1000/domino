package com.phantom.entities;

import java.util.Set;

public class Player {

    private Set<Bone> bones;
    private String name;

    public Set<Bone> getBones() {
        return bones;
    }

    public void setBones(Set<Bone> bones) {
        this.bones = bones;
    }

    public String toString() {
        String res = name;
        for (Bone b : bones) { //*
            res += b;
        }
        res += "----";
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(Set<Bone> bones, String name) {
        this.bones = bones;
        this.name = name;
    }

}