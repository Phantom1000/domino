package com.phantom.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phantom.entities.Bone;
import com.phantom.entities.Game;
import com.phantom.entities.Half;
import com.phantom.entities.Player;
import com.phantom.utils.BoneComparator;
import com.phantom.utils.GoThread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameService {
    private static GameService instance;
    private final BoneService boneService;
    public static final String ANSI_RESET = "\u001B[0m";

    private GameService() {
        this.boneService = BoneService.getInstance();
    }

    public String playerInfo(Player p, Game game) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("target/domino.json"), game.getPlayers().get(p));
            return p.getColor() + p.getName() + ": " + game.getPlayers().get(p).stream().map(x -> x.toString()).collect(Collectors.joining(", ")) + ANSI_RESET;
        } catch (IOException exception) {
            return "";
        }

    }

    private Player getOwner(Bone b, Game game) {
        for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()){
            if (item.getValue().contains(b)) {
                return item.getKey();
            }
        }
        return null;
    }

    public void start(Game game) {
        fillReserve(game.getReserve());
        String[] names = {"Tom", "Bob"};
        String[] colors = {"\u001B[31m", "\u001B[34m"};
        for (int i = 0; i < game.COUNT_PLAYERS; i++) {
            game.getPlayers().put(new Player(names[i], colors[i]), createBones(game.getReserve()));
        }

        Set<Bone> duplicates = new HashSet<>();
        for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()){
            System.out.println(playerInfo(item.getKey(), game));
            Bone dup = dublicate(item.getValue());
            if (dup != null) {
                duplicates.add(dup);
            }

        }
        /*game.getPlayers().stream().forEach(p -> {
            System.out.println(p);
            Bone dup = dublicate(p.getBones());
            if (dup != null) {
                duplicates.add(dup);
            }
        });*/
        Player first;
        Bone max = null;
        if (!duplicates.isEmpty())
            max = duplicates.stream().max(new BoneComparator()).get();
        if (max != null) {
            first = getOwner(max, game);
            game.getPlayers().get(first).remove(max);
            //max.getOwner().getBones().remove(max);
            game.setStart(max);
            game.setEnd(max);
        } else {
            Set<Bone> maxs = new HashSet<>();
            for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()){
                maxs.add(item.getValue().stream().max(new BoneComparator()).get());
            }
            /*for (Player p : game.getPlayers()) {
                maxs.add(p.getBones().stream().max(new BoneComparator()).get());
            }*/
            max = maxs.stream().max(new BoneComparator()).get();
            //max.getOwner().getBones().remove(max);
            first = getOwner(max, game);
            game.getPlayers().get(first).remove(max);
            game.setStart(max);
            game.setEnd(max);
        }

        List<Player> queue = new ArrayList<>();
        for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()) {
            if (item.getKey() != first) queue.add(item.getKey());
        }
        queue.add(first);
        System.out.println(first.getColor() + "Ходит " + first.getName() + ANSI_RESET);
        display(game.getStart(), null, false);
        System.out.println();
        /*outer:
        while (!game.isOver()) {
            for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()) {
                if (item.getValue().isEmpty()) {
                    game.setOver(true);
                }
            }
            for(Player p : queue) {
                if (!game.isOver()) {
                    System.out.println("Ходит " + p.getName());
                    go(p, game);
                    display(game.getStart(), null);
                    System.out.println();
                    for (Map.Entry<Player, Set<Bone>> player : game.getPlayers().entrySet()) {
                        System.out.println(playerInfo(player.getKey(), game));
                    }
                    System.out.println("Базар: " + game.getReserve().stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
                } else {
                    break outer;
                }
            }
        }*/
        for(Player p : queue) {
            Thread t = new Thread(new GoThread(game, this, p));
            t.start();
        }

        /*int win = -1;
        Player winPlayer = null;
        for (Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()) {
            int sum = 0;
            for (Bone b: item.getValue()) {
                sum += b.getSum();
            }
            if (sum < win || win == -1) {
                win = sum;
                winPlayer = item.getKey();
            }
        }
        System.out.println("Игра закончилась, победил " + winPlayer.getName() + " со счетом " + win);*/
        /*for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()){
            System.out.println(playerInfo(item.getKey(), game));
        }*/
        /*for (Player p : game.getPlayers()) {
            System.out.println(p);
        }*/
    }

    public static GameService getInstance() {
        if (instance == null)
            instance = new GameService();
        return instance;
    }

    public void display(Bone b, Bone except, boolean flag) {
        if (b != null) {
            System.out.print(b + "  ");
            if (flag) {
                Half temp = b.getHalfs().get(0);
                b.getHalfs().set(0, b.getHalfs().get(1));
                b.getHalfs().set(1, temp);
            }
            for (Half h : b.getHalfs()) {
                Bone link = h.getLink();
                boolean f = false;
                if (link != null) {
                    if (link.getHalfs().get(0).getPoints() != b.getHalfs().get(1).getPoints()) {
                        Half temp = link.getHalfs().get(0);
                        link.getHalfs().set(0, link.getHalfs().get(1));
                        link.getHalfs().set(1, temp);
                        f = true;
                    }
                }
                if (link != except) {
                    display(link, b, f);
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

    private Set<Bone> createBones(List<Bone> reserve) {
        Set<Bone> bones = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            bones.add(reserve.get(0));
            reserve.remove(0);
        }
        /*Player res = new Player(bones, name);
        bones.stream().forEach(bone -> {
            bone.setOwner(res); //*
        });*/
        return bones;
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

    public void go(Player p, Game game) {
        if (game.getPlayers().get(p).isEmpty()) { //*
            game.over = true;
            return;
        }

        boolean b = false;

        for (Bone bone : game.getPlayers().get(p)) {
            if (boneService.attach(bone, game.getStart())) {
                game.setStart(bone);
                game.getPlayers().get(p).remove(bone); //*
                b = true;
                break;
            }
            if (boneService.attach(bone, game.getEnd())) {
                game.setEnd(bone);
                game.getPlayers().get(p).remove(bone); //*
                b = true;
                break;
            }
        }

        while (!b) {
            if (!game.getReserve().isEmpty()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bone rand = getRandomBone(game.getReserve(), game);
                /*ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new File("target/domino.json"), game.getReserve());*/
                System.out.println(p.getColor() + p.getName() + " вынимает случаную костяшку - " + rand + ANSI_RESET);
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
                    game.getPlayers().get(p).add(rand); //*
                }
            } else {
                game.over = true;
                break;
            }

        }

    }
}
