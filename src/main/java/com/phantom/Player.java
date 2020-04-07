package com.phantom;
import java.util.Set;

public class Player {

    private Set<Bone> bones;
    private String name;
    private final BoneService boneService;

    public Set<Bone> getBones() {
        return bones;
    }

    public void setBones(Set<Bone> bones) {
        this.bones = bones;
    }

    public void go(Game game) {
        if (bones.isEmpty()) {
            game.over = true;
            return;
        }

        boolean b = false;

        for (Bone bone : bones) {
            if (boneService.attach(bone, game.getStart())) {
                game.setStart(bone);
                bones.remove(bone);
                b = true;
                break;
            }
            if (boneService.attach(bone, game.getEnd())) {
                game.setEnd(bone);
                bones.remove(bone);
                b = true;
                break;
            }
        }

        while (!b) {
            Bone rand = game.getRandomBone();
            if (rand != null) {
                System.out.println(name + " get random bone - " + rand);
                if (boneService.attach(rand, game.getStart())) {
                    b = true;
                    game.setStart(rand);
                    break;
                }
                if (boneService.attach(rand, game.getEnd())) {
                    b = true;
                    game.setEnd(rand);
                    break;
                }
                if (!b) {
                    bones.add(rand);
                }
            }

        }

    }

    public void info() {
        System.out.println(name);
        bones.stream().forEach(bone -> System.out.println(bone));
        System.out.println("----");
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
        boneService = BoneService.getInstance();
    }

}