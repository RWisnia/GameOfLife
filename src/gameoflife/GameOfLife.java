package gameoflife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author Rafkus
 */
public class GameOfLife implements MouseMotionListener, ActionListener, ChangeListener, Runnable{

    boolean[][] table = new boolean[45][55];
    
    JFrame MyFrame = new JFrame("Game of Life");
    mainView mainview = new mainView(table);
    
    ////ButtomBar/////
    Container ButtomBar = new Container();
    JButton NextStep = new JButton("Next Step");
    JButton Start = new JButton("Start");
    JButton Stop = new JButton("Stop");
    
    /////EastBar/////
    Container EastBar = new Container();
    JSlider BrushSizeSlider = new JSlider(JSlider.VERTICAL, 0,100,0);
    JLabel LabelBrush = new JLabel("Brush Size");
    JSlider SpeedSlider = new JSlider(JSlider.VERTICAL, 0,10,0);
    
    int BrushSize;
    boolean run = false;
    int Speed;
    
    
    public static void main(String[] args) {
        new GameOfLife();
    }
    
    public GameOfLife(){
        MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyFrame.setVisible(true);
        
        MyFrame.setSize(500,500);
        MyFrame.setLayout(new BorderLayout());
        MyFrame.setLocationRelativeTo(null);
        MyFrame.add(mainview, BorderLayout.CENTER);
        mainview.addMouseMotionListener(this);
        
            ///////////ButtomBar///////////
        MyFrame.add(ButtomBar, BorderLayout.PAGE_END);
        ButtomBar.setLayout(new GridLayout(1,3));
        ButtomBar.add(NextStep);
        ButtomBar.add(Start);
        ButtomBar.add(Stop);
        NextStep.addActionListener(this);
        Start.addActionListener(this);
        Stop.addActionListener(this);
        
        
            ///////////EastBar/////////// 
        EastBar.setLayout(new GridLayout(2,2));
        MyFrame.add(EastBar, BorderLayout.EAST);
        //Images//
        ImageIcon SpeedImage = new ImageIcon("/home/wisnia/Obrazy/speed.png");
        ImageIcon BrushImage = new ImageIcon("/home/wisnia/Obrazy/BrushSize.png");
        
        JLabel LabelSpeed = new JLabel();
        LabelSpeed.setIcon(SpeedImage);
        
        JLabel LabelBrush = new JLabel();
        LabelBrush.setIcon(BrushImage);
        
        EastBar.add(BrushSizeSlider, BorderLayout.WEST);
        EastBar.add(LabelBrush, BorderLayout.EAST);
        
        EastBar.add(SpeedSlider, BorderLayout.SOUTH);
        EastBar.add(LabelSpeed, BorderLayout.EAST);
        
        //Sliders//
        BrushSizeSlider.setMajorTickSpacing(10);
        BrushSizeSlider.setPaintLabels(true);
        BrushSizeSlider.addChangeListener(this);
        
        SpeedSlider.setMajorTickSpacing(1);
        SpeedSlider.setPaintLabels(true);
        SpeedSlider.addChangeListener(this);
        
    }
    
    public void oneStep(){
       
        int neighbors = 0;
        boolean[][] tempTable = new boolean[table.length][table[0].length];
        
        for(int x = 0; x < table.length; x++){
            for(int y = 0; y < table[0].length; y++){
                
                ///////Count my neighbors///////
                if((x-1)>=0 && (y-1)>=0 && table[x-1][y-1] == true) neighbors++;
                if((y-1)>=0 && table[x][y-1] == true) neighbors++;
                if(x+1<table.length && (y-1)>=0 && table[x+1][y-1] == true) neighbors++;
                if((x-1)>=0 && table[x-1][y] == true) neighbors++;
                if(x+1<table.length && table[x+1][y] == true) neighbors++;
                if((x-1)>=0 && y+1<table[0].length && table[x-1][y+1] == true) neighbors++;
                if(y+1<table[0].length && table[x][y+1] == true) neighbors++;
                if(y+1<table[0].length && x+1<table.length && table[x+1][y+1] == true) neighbors++;
                
                if(table[x][y]==true && (neighbors == 2 || neighbors == 3))   tempTable[x][y] = true;
                else tempTable[x][y] = false;
               
                if(table[x][y]==false && neighbors == 3)    tempTable[x][y] = true;
                
                neighbors = 0;
            }
        }
        
        table = tempTable;
        mainview.editMyTable(tempTable);
        MyFrame.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double CellWidth = (double)mainview.getWidth()/table.length;
        double CellHeight = (double)mainview.getHeight()/table[0].length;
        int CurrentCellX = (int)(e.getX()/CellWidth);
        int CurrentCellY = (int)(e.getY()/CellHeight);
        
                for(int x = CurrentCellX-Math.round(BrushSize/10); x < CurrentCellX+Math.round(BrushSize/10); x++){
                    for(int y = CurrentCellY-Math.round(BrushSize/10); y < CurrentCellY+Math.round(BrushSize/10); y++){
                        if(x>=0 && x<table.length && y>=0 && y<table[0].length) table[x][y] = true;
                    }
                }
                
                if(BrushSize<10 && BrushSize!=0) table[CurrentCellX][CurrentCellY] = true;    
 
        MyFrame.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(NextStep) == true)  oneStep();
    
        if(e.getSource().equals(Start) == true)  {
            if(run==false){
                run = true;
                Thread StartGame = new Thread(this);
                StartGame.start();
            }
        }
        
        if(e.getSource().equals(Stop) == true)  run = false; 
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        BrushSize = BrushSizeSlider.getValue();
        Speed = SpeedSlider.getValue();
    }  

    @Override
    public void run() {
        while(run == true){
            oneStep();
            try {
                if(Speed!=10)    Thread.sleep((10-Speed)*100);
            } catch (InterruptedException ex) {
                
            }
        }
    }
}