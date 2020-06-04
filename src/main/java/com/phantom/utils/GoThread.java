package com.phantom.utils;

import com.phantom.entities.Bone;
import com.phantom.entities.Game;
import com.phantom.entities.Player;
import com.phantom.services.GameService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GoThread implements Runnable {
    private Game game;
    private GameService gameService;
    private Player player;

    public GoThread(Game game, GameService gameService, Player player) {
        this.game = game;
        this.gameService = gameService;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void run() {
        while (!game.isOver()) {
            synchronized (game) {
                for(Map.Entry<Player, Set<Bone>> item : game.getPlayers().entrySet()) {
                    if (item.getValue().isEmpty()) {
                        game.setOver(true);
                    }
                }
                if (!game.isOver()) {
                    System.out.println(player.getColor() + "Ходит " + player.getName() + GameService.ANSI_RESET);
                    gameService.go(player, game);
                    gameService.display(game.getStart(), null, false);
                    System.out.println();
                    for (Map.Entry<Player, Set<Bone>> player : game.getPlayers().entrySet()) {
                        System.out.println(gameService.playerInfo(player.getKey(), game));
                    }
                    System.out.println("Базар: " + game.getReserve().stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int win = -1;
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
                    System.out.println("Игра закончилась, победил " + winPlayer.getName() + " со счетом " + win);
                    System.exit(0);
                    break;
                }
            }

        }
    }
}
