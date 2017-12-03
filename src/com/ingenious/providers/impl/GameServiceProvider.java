package com.ingenious.providers.impl;

import com.ingenious.algorithms.impl.ExampleAlgorithm;
import com.ingenious.algorithms.impl.State;
import com.ingenious.containers.PlayerContainer;
import com.ingenious.engine.Game;
import com.ingenious.gui.MainFrame;
import com.ingenious.models.board.Board;
import com.ingenious.models.bag.Bag;
import com.ingenious.models.players.Player;
import com.ingenious.models.players.impl.Bot;
import com.ingenious.models.players.impl.Human;
import com.ingenious.providers.Provider;

public class GameServiceProvider extends Provider {

    private Board board;
    private PlayerContainer players;
    private Bag bag;
    private Game game;
    private MainFrame gui;

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
        players.addPlayer(new Human("random_player_name"));
        players.addPlayer(new Bot("test bot", new ExampleAlgorithm()));
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

    public static State toState()
    {
        Board board = board().getClone();
        Bag bag = bag().getClone();
        PlayerContainer playerContainer = new PlayerContainer(bag);
        for (Player player : players().getPlayers()){
            playerContainer.addPlayer(player.getClone());
        }
        Game game = new Game(board, playerContainer.getPlayers(), bag);
        return new State(board, game, bag, playerContainer);
    }

}
