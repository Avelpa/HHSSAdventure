/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhssadventure;

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamon
 */
public class HHSSAdventure {

    /**
     * @param args the command line arguments
     */
    private static UserInterface window;
    
    public static void run() throws AWTException {
        boolean done = false;
        
        window = new UserInterface();
        while(!done) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(HHSSAdventure.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    public static void main(String[] args) throws AWTException {
        run();
        
        
    }

    
}
