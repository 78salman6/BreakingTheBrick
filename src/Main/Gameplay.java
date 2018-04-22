/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics2D;
/**
 *
 * @author salman
 */
public class Gameplay extends JPanel implements KeyListener,ActionListener{
    private boolean play=false;
    private int score=0;
    
    private int totalBricks=21;
    private Timer timer;
    private int delay=8;
    private int playerX=310;
    //initial position of ball
    private int ballposX=120;
    private int ballposY=350;
    //movement in x direction in every delay
    private int ballXdir=-1;
    //movement in y direction in every delay
    private int ballYdir=-2;
    //create reference to object of map
    private MapGenerator map;
    
    public Gameplay() {
        //create object of map
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //make Timer object
        timer = new Timer(delay,this);
        //start the thread
        timer.start();
    }
    
    public void paint(Graphics g) {
        //background
        g.setColor(Color.WHITE);
        g.fillRect(1,1,692,592);
        
        //draw bricks(drawing maps)
        map.draw((Graphics2D)g); 
        
        //borders
        g.setColor(Color.CYAN);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        
        //scores
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
        //paddle
        g.setColor(Color.BLACK);
        g.fillRect(playerX,550,100,8);
        
        //the ball
        g.setColor(Color.RED);
        g.fillOval(ballposX,ballposY,20,20);
        //if no brick remaining then you already won the match
        if(totalBricks<=0) {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won!", 260, 300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press enter to restart", 230, 350);
        }
        //if user is unable to take ball on paddle then game over message will appear
        if(ballposY>570) {
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over,Score:"+score, 190, 300);
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press enter to restart", 230, 350);
        }
        
        g.dispose();
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            //if ball strikes the paddle
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYdir=-ballYdir;
            }
            
            A: for(int i=0;i<map.map.length;i++) {
                
                for(int j=0;j<map.map[0].length;j++) {
                    if(map.map[i][j]>0) {
                        int brickX=j*map.brickWidth+80;
                        int brickY=i*map.brickHeight+50;
                        int brickWidth=map.brickWidth;
                        int brickHeight=map.brickHeight;
                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=rect;
                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+=5;
                            if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width) {
                                ballXdir=-ballXdir;
                            } else {
                                ballYdir=-ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if(ballposX<0) {
                ballXdir=-ballXdir;
            }
            if(ballposY<0) {
                ballYdir=-ballYdir;
            }
            if(ballposX>670) {
                ballXdir=-ballXdir;
            }
        }
        repaint();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
           if(playerX>=600) {
               playerX=600;
           } else {
               moveRight();
           }
       }
       if(e.getKeyCode() == KeyEvent.VK_LEFT) {
           if(playerX<10) {
               playerX=10;
           } else {
               moveLeft();
           }
       }
       if(e.getKeyCode() == KeyEvent.VK_ENTER) {
           if(!play) {
               play=true;
               ballposX=120;
               ballposY=350;
               ballXdir=-1;
               ballYdir=-2;
               playerX=310;
               score=0;
               totalBricks=21;
               map=new MapGenerator(3, 7);
               repaint();
           }
       }
    }

    public void moveLeft() {
        play=true;
        playerX-=20;
    }
    public void moveRight() {
        play=true;
        playerX+=20;
    }
}
