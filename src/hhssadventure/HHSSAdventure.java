/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhssadventure;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
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
        
        
        Scene currentScene = null;
        
        
        HashMap<String, Scene> scenes = new HashMap<>();
        HashMap<String, String[]> locationScenes = new HashMap<>();
        // scene, nextLocation nextDirection (index of above array)
        HashMap<String, HashMap<String, Integer>> sceneForwards = new HashMap<>();
        
        String curLocationName = "";
        int curLocationIndex = 0;
        
        try
        {
              FileReader file = new FileReader("images\\pics.txt");
              
              Scanner s = new Scanner(file);
              
              int index = 0;
              while(s.hasNext())
              {
                 if (index >= 2)
                 {
                     String location = s.next();
                     // scenes in this location in N E S W order
                     String[] locationScenesArr = new String[4];
                     
                     for (int i = 0; i < 4; i ++)
                     {
                            HashMap<String, Integer> currentSceneNextRoomLocationIndex = new HashMap<>();
                            
                            String direction = s.next();
                            String image = s.next();
                            String blockedString = s.next();
                            boolean blocked = blockedString.equals("true") ? true: false;
                            
                            // populate sceneForwards
                            if (!blocked)
                            {
                                String nextLocation = s.next();
                                // don't need next room's location
                                int dir = convertDirectionToIndex(s.next().charAt(0));
                                currentSceneNextRoomLocationIndex.put(nextLocation, dir);
                            }
                            else
                            {
                                currentSceneNextRoomLocationIndex.put(null, -1);
                            }
                            
                            // populate scenes
                            scenes.put(image, new Scene(location, image, blocked, direction.charAt(0)));
                            // populate scenes array for each locatio (N E S W)
                            locationScenesArr[i] = image;
                            // add to sceneForwards
                            sceneForwards.put(image, currentSceneNextRoomLocationIndex);
                     }
                     
                     locationScenes.put(location, locationScenesArr);
                 }
                 else if (index == 0)
                 {
                     curLocationName = s.next();
                 }
                 else if (index == 1)
                 {
                     curLocationIndex = convertDirectionToIndex(s.next().charAt(0));
                 }
                 else
                 {
                     s.next();
                 }
                 index ++;
              }
         }catch(Exception e)
         {
              e.printStackTrace();
         }        
        for (Map.Entry location: locationScenes.entrySet())
        {
            // the N E S W Scene array
            String[] curLocations = (String[])location.getValue();
            for (int i = 0; i < curLocations.length; i ++)
            {
                String left = curLocations[modifyIndex(i-1, curLocations.length)];
                String right = curLocations[modifyIndex(i+1, curLocations.length)];
                Map.Entry nextSceneLocation = sceneForwards.get(curLocations[i]).entrySet().iterator().next();
                
                Scene curLocation = scenes.get(curLocations[i]);
                
                curLocation.setLeft(scenes.get(left));
                curLocation.setRight(scenes.get(right));
                
                if (nextSceneLocation.getKey() != null)
                {
                    String forward = locationScenes.get((String)nextSceneLocation.getKey())[(int)nextSceneLocation.getValue()];
                    curLocation.setForward(scenes.get(forward));
                }
            }
        }
        
        currentScene = scenes.get(locationScenes.get(curLocationName)[curLocationIndex]);
    }
    
    public static int modifyIndex(int index, int arrLength)
    {
        if (index < 0)
        {
            index = arrLength-1;
        }
        if (index >= arrLength)
        {
            index = 0;
        }
        
        return index;
    }
    
    public static int convertDirectionToIndex(char direction)
    {
        switch(direction)
        {
            case 'N':
                return 0;
            case 'E':
                return 1;
            case 'S':
                return 2;
            case 'W':
                return 3;
            default:
                return -1;
        }
    }
    
    
}
