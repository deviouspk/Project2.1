package com.ingenious.providers.impl;

import com.ingenious.algorithms.bots.algorithms.Random.RandomTonyAlgorithm;
import com.ingenious.containers.PlayerContainer;
import com.ingenious.engine.Game;
import com.ingenious.gui.MainFrame;
import com.ingenious.models.board.Board;
import com.ingenious.models.bag.Bag;
import com.ingenious.models.players.impl.Bot;
import com.ingenious.models.players.impl.Human;
import com.ingenious.providers.Provider;

public class GameServiceProvider extends Provider {

    public Board board;
    public PlayerContainer players;
    public Bag bag;
    public Game game;
    public MainFrame gui;

    protected void initialize() {
        this.board = new Board();
        this.bag = new Bag();
        this.players = new PlayerContainer(bag);
        this.initPlayers();
        this.game = new Game(board, players.getPlayers(), bag);
        this.gui = new MainFrame();
    }

    /* ADD PLAYERS OR BOTS HERE */
    protected void initPlayers() {
        players.addPlayer(new Human("human player"));
        players.addPlayer(new Bot(new RandomTonyAlgorithm()));
    }

    public static MainFrame gui() {
        return getInstance().gui;
    }

    public static Board board() {
        return getInstance().board;
    }

    public static PlayerContainer players() {
        return getInstance().players;
    }

    public static Bag bag() {
        return getInstance().bag;
    }

    public static Game game() {
        return getInstance().game;
    }

    public static void restart() {
        gui().dispose();
        reboot();
    }

    public static Game toState() {
        long startTime = System.nanoTime();
        Game game = game().getClone();
        long endTime = System.nanoTime();
        System.out.println("cloning gamestate took " + (endTime - startTime) / 1000 + " ns");
        System.out.println("");

        return game;
    }

}
