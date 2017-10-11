package com.ingenious.gui.components;

import com.ingenious.models.board.Node;
import com.ingenious.config.Configuration;
import com.ingenious.models.pieces.Piece;
import com.ingenious.models.tiles.C;
import com.ingenious.providers.impl.GameServiceProvider;
import com.sun.prism.image.Coords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by alexisguillot on 14/09/2017.
 */
public class BoardComponent extends JComponent {

    int startingX = 350; // coordinates for node 0,0
    int startingY = 253;


    public BoardComponent() {
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        ArrayList<com.ingenious.models.board.Node> nodeList = GameServiceProvider.board().getNodes();

        for (int i = 0; i < nodeList.size(); i++) {
            int[] xP = new int[6];
            int[] yP = new int[6];
            int nodeX;
            int nodeY;

            //adjust x and y of node to actual location
            nodeX = (int) (startingX + (nodeList.get(i).x * (0.75 * Hexagon.getWidth())));
            nodeY = (int) (startingY + (nodeList.get(i).y * Hexagon.getHeight()) + (nodeList.get(i).x * 0.5 * Hexagon.getHeight()));

            Hexagon hexagon = new Hexagon(new Point(nodeX, nodeY));

            g.setColor(nodeList.get(i).getTile());

            g.fillPolygon(hexagon.getHexagon());

            g.setColor(C.getColor(C.LINE));

            if (Configuration.showCoordinates)
                g.drawString((nodeList.get(i).getX()) + "," + (nodeList.get(i).getY()), nodeX - 9, nodeY + 3);

            g.drawPolygon(hexagon.getHexagon());
        }

        if(GameServiceProvider.game().getCurrentPlayer().getRack().selected()) {
            Piece piece = GameServiceProvider.game().getCurrentPlayer().getRack().getPieceSelected();
            Hexagon hexagon = new Hexagon(new Point(670, 140));
            g.setColor(piece.getHead());
            g.fillPolygon(hexagon.getHexagon());
            g.setColor(Color.BLACK);
            g.drawPolygon(hexagon.getHexagon());
            hexagon = new Hexagon(new Point(670,(int)(140+Hexagon.getHeight())));
            g.setColor(piece.getTail());
            g.fillPolygon(hexagon.getHexagon());
            g.setColor(Color.BLACK);
            g.drawPolygon(hexagon.getHexagon());
        }


        //System.out.println("Center point: " + testnodecoord[0] + ", " + testnodecoord[1]);
        //System.out.println(hex.getX() + ", " + hex.getY());
        BoardListener listener = new BoardListener();
        addMouseListener(listener);
        addKeyListener(listener);
        requestFocus();
    }

    public Point point_to_hex(int x, int y) {
        int q = (int) Math.round((x - startingX) / (0.75 * Hexagon.getWidth()));
        int r = (int) Math.round((y - startingY - (q * 0.5 * Hexagon.getHeight())) / Hexagon.getHeight());

        return new Point(q, r);
    }


    class BoardListener implements MouseListener, KeyListener {

        Node clicked;
        Node clicked2;
        int cnt = 0;
        @Override
        public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            Point coord = point_to_hex(x, y);
            if(cnt == 0){
                clicked = GameServiceProvider.board().getNode((int) coord.getX(), (int) coord.getY());
                cnt++;
            }
            else{
                clicked2 = GameServiceProvider.board().getNode((int) coord.getX(), (int) coord.getY());

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                if(GameServiceProvider.game().getCurrentPlayer().getRack().selected() && cnt != 0){
                    GameServiceProvider.game().place_piece(GameServiceProvider.game().getCurrentPlayer().getRack().getPieceSelected(), clicked, clicked2);
                    cnt = 0;
                    GameServiceProvider.gui().repaintAll();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                cnt = 0;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
