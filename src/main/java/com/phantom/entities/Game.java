package com.phantom.entities;

import com.phantom.services.GameService;

import java.io.IOException;
import java.util.*;

//@JSONAutoDetect
public class Game {

    private List<Bone> reserve = new ArrayList<>();
    //private Set<Player> players = new HashSet<>();
    private HashMap<Player, Set<Bone>> players = new HashMap<>();
    private Bone start;
    private Bone end;
    public final int COUNT_PLAYERS = 2;
    public boolean over = false;
    private final GameService gameService;

    public Game() throws IOException {
        gameService = GameService.getInstance();
        gameService.start(this);
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

    public HashMap<Player, Set<Bone>> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<Player, Set<Bone>> players) {
        this.players = players;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}