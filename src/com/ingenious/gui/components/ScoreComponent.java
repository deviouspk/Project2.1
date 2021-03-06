package com.ingenious.gui.components;

import com.ingenious.providers.impl.GameServiceProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreComponent extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int circleSize = 20;
        int gapSize = 26;
        int startingX = 70;
        int startingY = 25;
        int currentX = startingX;
        int currentY = startingY;
        List<Color> colors = new ArrayList<Color>();
        colors.add(Color.red);
        colors.add(Color.green);
        colors.add(Color.blue);
        colors.add(new Color(255, 140, 0));
        colors.add(Color.yellow);
        colors.add(new Color(160, 32, 240));


        int[] myScore = GameServiceProvider.players().getPlayer(0).getScore();
        int[] opponentScore = GameServiceProvider.players().getPlayer(1).getScore();

        //score on score com.ingenious.models.board
        for (int i = 0; i < 6; i++) {
            g2d.setStroke(new BasicStroke(1));
            for (int j = 18; j >= 0; j--) {
                if (myScore[i] == j) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setColor(colors.get(i));
                    g2d.fillOval(currentX, currentY, circleSize, circleSize);
                } else {
                    if (myScore[i] < j) {
                        g2d.setColor(Color.gray);
                        g2d.drawOval(currentX, currentY, circleSize, circleSize);
                    }
                    g2d.setColor(new Color(g2d.getColor().getRed(), g2d.getColor().getGreen(), g2d.getColor().getBlue(), 50));
                    g2d.fillOval(currentX, currentY, circleSize, circleSize);
                }
                currentY += gapSize;
            }
            currentX += gapSize;
            currentY = startingY;
        }

        //score labels to the side
        currentX = startingX - gapSize;
        currentY = startingY + circleSize;

        for (int z = 18; z >= 0; z--) {
            g2d.setColor(Color.black);
            g2d.drawString(Integer.toString(z), currentX, currentY-6);
            currentY += gapSize;
        }

        //opponent's score
        currentX = (int) (startingX - (gapSize * 0.5));
        currentY -= (int) gapSize * 0.5;

        g2d.setColor(Color.black);
        g2d.drawRect(currentX,
                currentY,
                (int) (6.5 * gapSize),
                (int) ((2 * circleSize) + (0.5 * gapSize)));

        currentX = startingX;
        currentY += (int) gapSize * 0.25;
        for (int i = 0; i < 6; i++) {
            g2d.setColor(colors.get(i));
            g2d.fillOval(currentX, currentY, circleSize, circleSize);
            g2d.setColor(Color.black);
            g2d.drawString(Integer.toString(opponentScore[i]),
                    (int) (currentX + (0.25 * circleSize)), (int) (currentY + (1.5 * gapSize)));
            currentX += gapSize;
        }
    }

}
