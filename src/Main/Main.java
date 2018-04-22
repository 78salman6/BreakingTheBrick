/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import javax.swing.JFrame;
/**
 *
 * @author salman
 */
public class Main {
    public static void main(String args[]) {
        //this is the frame where every action is going to perform
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        //set size of frame
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Break out ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
        
    }
}
