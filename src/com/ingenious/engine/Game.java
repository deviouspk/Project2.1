package com.ingenious.engine;

import com.ingenious.models.board.Board;
import com.ingenious.models.board.Node;
import com.ingenious.models.players.Player;
import com.ingenious.models.bag.Bag;
import com.ingenious.models.pieces.Piece;
import com.ingenious.models.tiles.Tile;
import com.ingenious.providers.impl.GameServiceProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolley on 29-Sep-17.
 */
public class Game {

    private Board board;
    private Bag bag;
    private List<Player> players;
    private int current_player_index;

    public int bonus_play = 0;


    public Game(Board board, List<Player> players, Bag bag) {
        this.board = board;
        this.players = players;
        this.bag = bag;
        this.current_player_index = 0;
    }

    public void startGame() {
        for (Player player : players) {
            draw(player);
        }
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.current_player_index);
    }

    public Player getOppenent(){
        if(current_player_index == 0){
            return this.players.get(1);
        }
        else{
            return this.players.get(0);
        }
    }

    public void draw(Player player) {
        for (int i = 0; i < 6; i++) {
            Piece piece = bag.getAndRemoveRandomPiece();
            player.getRack().getContents().set(i, piece);
        }
    }

    public void swap() {
        for (int i = 0; i < 6; i++) {
            bag.addPiece(getCurrentPlayer().getRack().getContents().get(i));
        }
        draw(getCurrentPlayer());
    }

    //at the end of each turn make sure you have 6 tiles
    public void refresh() {
        getCurrentPlayer().getRack().getContents().add(bag.getAndRemoveRandomPiece());
    }



    public void place_piece(Piece piece, Node node_1, Node node_2) {
        if (valid_placement(piece, node_1, node_2)) {
            if (bonus_play != 0) {
                bonus_play--;
            }
            getCurrentPlayer().getRack().getContents().remove(piece);
            board.addTile(piece.getHead(), node_1);
            board.addTile(piece.getTail(), node_2);
            int newScore_1 = calculate_score(node_1, node_2);
            int newScore_2 = calculate_score(node_2, node_1);
            if (getCurrentPlayer().getRack().getContents().isEmpty()) {
                draw(getCurrentPlayer());
                turn();
            } else {
                if (bonus_play(newScore_1)) {
                    bonus_play++;
                }
                if (bonus_play(newScore_2)) {
                    bonus_play++;
                }
                if (bonus_play == 0) {
                    turn();
                }
            }
        }
    }


    public int calculate_score(Node node_1, Node node_2) {
        int x = node_1.getX();
        int y = node_1.getY();
        int l = 1;
        int score = 0;
        Tile tile = node_1.getTile();
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x,y-l) != node_2 && tile.equals(GameServiceProvider.board().getNode(x,y-l).getTile())){
            score++;
            y = y - l;
        }
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x,y+l) != node_2 && tile.equals(GameServiceProvider.board().getNode(x,y+l).getTile())){
            score++;
            y = y + l;
        }
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x + l,y) != node_2 && tile.equals(GameServiceProvider.board().getNode(x + l,y).getTile())){
            score++;
            x = x + l;
        }
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x - l,y)  != node_2 && tile.equals(GameServiceProvider.board().getNode(x - l,y).getTile())){
            score++;
            x = x - l;
        }
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x+l,y-l)  != node_2 && tile.equals(GameServiceProvider.board().getNode(x+l,y-l).getTile())){
            score++;
            y = y - l;
            x = x + l;
        }
        while(5 > x && x > -5 && y > -5 && y < 5 && GameServiceProvider.board().getNode(x-l,y+l) != node_2 &&  tile.equals(GameServiceProvider.board().getNode(x-l,y+l).getTile())){
            score++;
            y = y + l;
            x = x - l;
        }

        if(tile.equals(Color.green)){
            getCurrentPlayer().score().setGreenScore(score+getCurrentPlayer().score().getGreenScore());
        }
        else if (tile.equals(Color.blue)){
            getCurrentPlayer().score().setBlueScore(score+getCurrentPlayer().score().getBlueScore());
        }
        else if(tile.equals(Color.red)){
            getCurrentPlayer().score().setRedScore(score+getCurrentPlayer().score().getRedScore());
        }
        else if(tile.equals(Color.yellow)){
            getCurrentPlayer().score().setYellowScore(score+getCurrentPlayer().score().getYellowScore());
        }
        else if(tile.equals(Color.orange)){
            getCurrentPlayer().score().setOrangeScore(score+getCurrentPlayer().score().getOrangeScore());
        }
        else{
            getCurrentPlayer().score().setPurpleScore(score+getCurrentPlayer().score().getPurpleScore());
        }
        //repaint score
        return score;
    }

    public boolean bonus_play(int newScore) {
        if (newScore > 18) {
            return true;
        }
        return false;
    }

    public boolean won() {
        for (int i = 0; i < 6; i++) {
            if (getCurrentPlayer().getScoreArray()[i] >= 18) {
                return true;
            }
        }
        return false;
    }

    public void turn() {
        while (getCurrentPlayer().getRack().getContents().size() < 6) {
            refresh();
        }

        if (won()) {
            System.out.println(getCurrentPlayer().getName() + " has won the game");
        }

        setNextPlayerAsCurrent();
        bonus_play = 0;
    }

    public void setNextPlayerAsCurrent() {
        for (Player player : players) {
            if (this.current_player_index >= this.players.size()) {
                this.current_player_index = 0;
            } else {
                this.current_player_index++;
            }
        }
    }

    public boolean valid_placement(Piece piece, Node node_1, Node node_2) {
        ArrayList<Node> neighbours_1 = board.getNeighboursOfNode(node_1);
        ArrayList<Node> neighbours_2 = board.getNeighboursOfNode(node_2);
        if (piece.hasEqualTiles()) {
            int cnt = 0;
            for (int i = 0; i < neighbours_1.size(); i++) {
                if (neighbours_1.get(i).getTile().equals(piece.getHead()) || neighbours_2.get(i).getTile().equals(piece.getTail())) {
                    cnt++;
                    if (cnt == 2) {
                        return true;
                    }
                }
            }

        } else {
            for (int i = 0; i < neighbours_1.size(); i++) {
                if (neighbours_1.get(i).getTile().equals(piece.getHead()) || neighbours_2.get(i).getTile().equals(piece.getTail())) {
                    return true;
                }
            }
        }

        return false;
    }

}
