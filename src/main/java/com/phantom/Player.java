package com.phantom;

import java.util.Random;
import java.util.Set;

public class Player {

    private Set<Bone> bones;
    private boolean played = false;
    private String name;

    public Set<Bone> getBones() {
        return bones;
    }

    public void setBones(Set<Bone> bones) {
        this.bones = bones;
    }

    public void go() {
        if (bones.isEmpty()) {
            Game.over = true;
        }
        for (Bone bone : bones) {
            if (bone.attach(Game.getStart())) {
                played = true;
                Game.setStart(bone);
                bones.remove(bone);
                break;
            }
            if (bone.attach(Game.getEnd())) {
                played = true;
                Game.setEnd(bone);
                bones.remove(bone);
                break;
            }
        }

        while (!played) {
            Bone rand = Game.getRandomBone();
            System.out.println(name + " get random bone - " + rand);
            if (rand.attach(Game.getStart())) {
                played = true;
                Game.setStart(rand);
                break;
            }
            if (rand.attach(Game.getEnd())) {
                played = true;
                Game.setEnd(rand);
                break;
            }
            if (!played) {
                bones.add(rand);
            }
        }

    }

    public void info() {
        System.out.println(name);
        bones.stream().forEach(bone -> System.out.println(bone));
        System.out.println("----");
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
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