package com.ingenious.gui;

import com.ingenious.gui.components.BoardComponent;
import com.ingenious.gui.components.RackComponent;
import com.ingenious.gui.components.ScoreComponent;
import com.ingenious.providers.impl.GameServiceProvider;
import com.ingenious.models.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class MainFrame extends JFrame {

    private JPanel rootPanel;
    private JPanel boardPanel;
    private JPanel scorePanel;
    private JPanel rackPanel;

    public MainFrame() {
        setContentPane(rootPanel);

        initScoreComponent();
        initBoardComponent();
        initRackComponent();

        setSize(1280, 768);
        setResizable(false);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initScoreComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        Component scoreComponent = new ScoreComponent();
        this.scorePanel.add(scoreComponent, gbc);
    }

    private void initBoardComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        BoardComponent boardComponent = new BoardComponent();
        this.boardPanel.add(boardComponent, gbc);
    }

    private void initRackComponent() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        Component rackComponent = new RackComponent();
        //rackPanel.setBackground(Color.WHITE);
        this.rackPanel.add(rackComponent, gbc);
        JButton swapButton = new JButton("Swap");
        SwapListener listener = new SwapListener();
        swapButton.addActionListener(listener);
        this.rackPanel.add(swapButton);
    }

    class SwapListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            GameServiceProvider.game().swap();
            getRackPanel().repaint();
        }
    }


    public void repaintAll() {
        this.getBoardPanel().repaint();
        this.getRackPanel().repaint();
        this.getRootPanel().repaint();
        this.getScorePanel().repaint();
        this.repaint();
    }


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JPanel getScorePanel() {
        return scorePanel;
    }

    public JPanel getRackPanel() {
        return rackPanel;
    }
}


