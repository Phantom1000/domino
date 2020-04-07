package com.phantom;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {

    private static List<Bone> reserve;
    private List<Player> players = new ArrayList<>();
    private static Bone start;
    private static Bone end;
    private final int COUNT_PLAYERS = 2;
    private static Random rnd = new Random();
    public static boolean over = false;

    public Game() {
        fillReserve();

        String[] names = { "Tom", "Bob" };
        for (int i = 0; i < COUNT_PLAYERS; i++) {
            players.add(createPlayer(names[i]));
        }

        Set<Bone> duplicates = new HashSet<>();
        players.stream().forEach(p -> {
            p.info();
            Bone dup = dublicate(p.getBones());
            if (dup != null) {
                duplicates.add(dup);
            }
        });

        Bone max = null;
        if (!duplicates.isEmpty())
            max = duplicates.stream().max(Bone::compare).get();
        if (max != null) {
            max.getOwner().setPlayed(true);
            max.getOwner().getBones().remove(max);
            start = max;
            end = max;
        } else {
            Set<Bone> maxs = new HashSet<>();
            for (Player p : players) {
                maxs.add(p.getBones().stream().max(Bone::compare).get());
            }
            max = maxs.stream().max(Bone::compare).get();
            max.getOwner().getBones().remove(max);
            start = max;
            end = max;
        }

        for (Player p : players) {
            p.info();
        }

        display(start, null);
        while (!over) {
            for (Player p : players) {
                if (!p.isPlayed()) {
                    System.out.println("Ходит " + p.getName());
                    p.go();
                    display(start, null);
                }
                    
            }
            for (Player p : players) {
                p.setPlayed(false);
            }
        }
        Bone st = start;
        display(start, null);
        /*display();
        for (int i = 0; i < 3; i++) {
            for (Player p : players) {
                 try { Thread.currentThread(); Thread.sleep(500L); } catch
                 (InterruptedException e) { e.printStackTrace(); }

                System.out.println("Ходит " + p.getName());
                p.go();
                display();
            }
        }*/

    }

    private void display(Bone b, Bone except) {
        if (b != null) {
            Bone first = b.getFirstHalf().getLink();
            Bone second = b.getSecondHalf().getLink();
            System.out.print(b + "  ");
            if (first != except) {
                display(first, b);
            }
            if (second != except) {
                display(second, b);
            }
        }
    }

    private void fillReserve() {
        reserve = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                reserve.add(new Bone(new Half(i), new Half(j)));
            }
        }
    }

    public static Bone getRandomBone() {
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
                if (bone.getFirstHalf().getNumber() == i && bone.getSecondHalf().getNumber() == i) {
                    res.add(bone);
                }
            }
        }

        return res.stream().max(Bone::compare).orElse(null);
    }

    public static Bone getStart() {
        return start;
    }

    public static void setStart(Bone start) {
        Game.start = start;
    }

    public static List<Bone> getReserve() {
        return reserve;
    }

    public static void setReserve(List<Bone> reserve) {
        Game.reserve = reserve;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public static Bone getEnd() {
        return end;
    }

    public static void setEnd(Bone end) {
        Game.end = end;
    }

    

}