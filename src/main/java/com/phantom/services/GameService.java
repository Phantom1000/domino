package com.phantom.services;

import com.phantom.entities.Bone;
import com.phantom.entities.Game;
import com.phantom.entities.Half;
import com.phantom.entities.Player;
import com.phantom.utils.BoneComparator;

import java.util.*;

public class GameService {
    private static GameService instance;
    private final BoneService boneService;

    private GameService() {
        this.boneService = BoneService.getInstance();
    }

    public void start(Game game) {
        fillReserve(game.getReserve());

        String[] names = {"Tom", "Bob"};
        for (int i = 0; i < game.getCOUNT_PLAYERS(); i++) {
            game.getPlayers().add(createPlayer(names[i], game.getReserve()));
        }

        Set<Bone> duplicates = new HashSet<>();
        game.getPlayers().stream().forEach(p -> {
            System.out.println(p);
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
            game.setStart(max);
            game.setEnd(max);
        } else {
            Set<Bone> maxs = new HashSet<>();
            for (Player p : game.getPlayers()) {
                maxs.add(p.getBones().stream().max(new BoneComparator()).get());
            }
            max = maxs.stream().max(new BoneComparator()).get();
            max.getOwner().getBones().remove(max);
            game.setStart(max);
            game.setEnd(max);
        }


        display(game.getStart(), null);
        outer:
        while (!game.isOver()) {
            for (Player p : game.getPlayers()) {
                if (!game.isOver()) {
                    System.out.println("Ходит " + p.getName());
                    go(p, game);
                    display(game.getStart(), null);
                } else {
                    break outer;
                }

            }
        }

        System.out.println("Game over");
        for (Player p : game.getPlayers()) {
            System.out.println(p);
        }
    }

    public static GameService getInstance() {
        if (instance == null)
            instance = new GameService();
        return instance;
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

    private void fillReserve(List<Bone> reserve) {
        for (int i = 0; i < 7; i++) {
            for (int j = i; j < 7; j++) {
                List<Half> halfs = new ArrayList<>();
                halfs.add(new Half(i));
                halfs.add(new Half(j));
                reserve.add(new Bone(halfs));
            }
        }
        Collections.shuffle(reserve);
    }

    private Bone getRandomBone(List<Bone> reserve, Game game) {
        Bone res = reserve.get(0);
        reserve.remove(0);
        return res;
    }

    private Player createPlayer(String name, List<Bone> reserve) {
        Set<Bone> bones = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            bones.add(reserve.get(0));
            reserve.remove(0);
        }
        Player res = new Player(bones, name);
        bones.stream().forEach(bone -> {
            bone.setOwner(res); //*
        });
        return res;
    }

    private Bone dublicate(Set<Bone> bones) {
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

    private void go(Player p, Game game) {
        if (p.getBones().isEmpty()) { //*
            game.over = true;
            return;
        }

        boolean b = false;

        for (Bone bone : p.getBones()) {
            if (boneService.attach(bone, game.getStart())) {
                game.setStart(bone);
                p.getBones().remove(bone); //*
                b = true;
                break;
            }
            if (boneService.attach(bone, game.getEnd())) {
                game.setEnd(bone);
                p.getBones().remove(bone); //*
                b = true;
                break;
            }
        }

        while (!b) {
            if (!game.getReserve().isEmpty()) {
                Bone rand = getRandomBone(game.getReserve(), game);
                System.out.println(p.getName() + " get random bone - " + rand);
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
                    p.getBones().add(rand); //*
                }
            } else {
                game.over = true;
                break;
            }

        }

    }
}
