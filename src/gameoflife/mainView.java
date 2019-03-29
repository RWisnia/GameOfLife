package gameoflife;

import java.awt.*;
import javax.swing.JPanel;

/**
 *
 * @author Rafkus
 */
public class mainView extends JPanel {
    
    boolean[][] GameTable;
    int BrushSize = 1;
    
    //Constructor
    mainView(boolean[][] table){
        GameTable = table;
        setPreferredSize(new Dimension(400, 400));
    }
    
    public void editMyTable(boolean[][] table){
        GameTable = table;
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
        
        double CellWidth = (double)getWidth()/GameTable.length;
        double CellHeight = (double)getHeight()/GameTable[0].length;
        
             ///////////////////////////////
            ////////Filling squares////////
           ///////////////////////////////       
        g.setColor(Color.PINK);
        for(int x = 0; x < GameTable.length; x++){
            for(int y = 0; y < GameTable[0].length; y++){
                if(GameTable[x][y]==true){
                    g.fillRect((int)(Math.round(x*CellWidth)), (int)(Math.round(y*CellHeight)), (int)(Math.round(CellWidth)), (int)(Math.round(CellHeight)));
                }
            }
        }

             ///////////////////////////////
            //Draw the edges of the table//
           ///////////////////////////////
           
        g.setColor(Color.BLACK);
        for(int x = 0; x < GameTable.length; x++){
                g.drawLine((int)(Math.round(CellWidth*x)), 0, (int)(Math.round(CellWidth*x)), getHeight());
        }
        for(int y = 0; y < GameTable[0].length; y++){
                g.drawLine(0, (int)(Math.round(CellHeight*y)), getWidth(), (int)(Math.round(CellHeight*y)));
        }
        
        g.drawLine(getWidth()-1,0,getWidth()-1,getHeight());
        }
}