package com.phantom.entities;

import com.phantom.services.GameService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {

    private List<Bone> reserve = new ArrayList<>();
    private Set<Player> players = new HashSet<>();
    private Bone start;
    private Bone end;
    private final int COUNT_PLAYERS = 2;
    public boolean over = false;
    private final GameService gameService;

    public Game() {
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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public int getCOUNT_PLAYERS() {
        return COUNT_PLAYERS;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}