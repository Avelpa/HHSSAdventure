/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhssadventure;

import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author lamon
 */
public class HHSSAdventure {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try
        {
              FileReader file = new FileReader("images\\pics.txt");
              
              Scanner s = new Scanner(file);
              while(s.hasNext())
              {
                 System.out.println(s.next());
              }
         }catch(Exception e)
         {
              e.printStackTrace();
         }        
    }
    
}
