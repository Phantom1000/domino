package com.phantom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {

    private List<Bone> reserve;
    private Set<Player> goPlayers;
    private Bone start;
    private Bone end;
    private final int COUNT_PLAYERS = 2;
    private static Random rnd = new Random();
    public boolean over = false;
    private final BoneService boneService;

    public Game() {
        boneService = BoneService.getInstance();
        goPlayers = new HashSet<>();

        fillReserve();

        String[] names = { "Tom", "Bob" };
        for (int i = 0; i < COUNT_PLAYERS; i++) {
            goPlayers.add(createPlayer(names[i]));
        }

        Set<Bone> duplicates = new HashSet<>();
        goPlayers.stream().forEach(p -> {
            p.info();
            Bone dup = dublicate(p.getBones());
            if (dup != null) {
                duplicates.add(dup);
            }
        });

        Bone max = null;
        if (!duplicates.isEmpty())
            max = duplicates.stream().max(new BoneComparator()).get();
        if (max != null) {
            max.getOwner().getBones().remove(max);
            start = max;
            end = max;
        } else {
            Set<Bone> maxs = new HashSet<>();
            for (Player p : goPlayers) {
                maxs.add(p.getBones().stream().max(new BoneComparator()).get());
            }
            max = maxs.stream().max(new BoneComparator()).get();
            max.getOwner().getBones().remove(max);
            start = max;
            end = max;
        }


        display(start, null);
        while (!over) {
            for (Player p : goPlayers) {
                System.out.println("Ходит " + p.getName());
                p.go(this);
                display(start, null);                
            }
        }

        System.out.println("Game over");
        for (Player p : goPlayers) {
            p.info();
        }

    }

    private void display(Bone b, Bone except) {
        if (b != null) {
            System.out.print(b + "  ");
            for (Half h : b.getHalfs()) {
                Bone link = h.getLink();
                if (link != except) {
                    display(link, b);
                }
            }
        }
    }

    private void fillReserve() {
        reserve = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                List<Half> halfs = new ArrayList<>();
                halfs.add(new Half(i));
                halfs.add(new Half(j));
                reserve.add(new Bone(halfs));
            }
        }
    }

    public Bone getRandomBone() {
        if (reserve.size() > 0) {
            int rand = rnd.nextInt(reserve.size());
            Bone res = reserve.get(rand);
            reserve.remove(res);
            return res;
        } else {
            over = true;
            return null;
        }
    }

    private Player createPlayer(String name) {
        Set<Bone> bones = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            int rand = rnd.nextInt(reserve.size());
            bones.add(reserve.get(rand));
            reserve.remove(rand);
        }
        Player res = new Player(bones, name);
        bones.stream().forEach(bone -> {
            bone.setOwner(res);
        });
        return res;
    }

    public Bone dublicate(Set<Bone> bones) {
        Set<Bone> res = new HashSet<>();
        for (Bone bone : bones) {
            for (int i = 6; i > 0; i--) {
                if (boneService.isDuplicate(bone, i)) {
                    res.add(bone);
                }
            }
        }

        return res.stream().max(new BoneComparator()).orElse(null);
    }

    public Bone getStart() {
        return start;
    }

    public void setStart(Bone start) {
        this.start = start;
    }

    public List<Bone> getReserve() {
        return reserve;
    }

    public void setReserve(List<Bone> reserve) {
        this.reserve = reserve;
    }

    public Bone getEnd() {
        return end;
    }

    public void setEnd(Bone end) {
        this.end = end;
    }

    public Set<Player> getGoPlayers() {
        return goPlayers;
    }

    public void setGoPlayers(Set<Player> goPlayers) {
        this.goPlayers = goPlayers;
    }

}