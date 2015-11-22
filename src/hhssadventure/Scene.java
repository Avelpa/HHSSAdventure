/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hhssadventure;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author johns6971
 */
public class Scene {
    private String imageLocation;
    private BufferedImage image;
    private char direction;
    private String location;
    private boolean frontBlocked;
    
    private Scene left;
    private Scene right;
    private Scene forward;
    
    private ArrayList<Rectangle> rectangles;
    
    public Scene(String location, String imageLocation, boolean frontBlocked, char direction) {
        this.imageLocation = imageLocation;
        this.direction = direction;
        this.frontBlocked = frontBlocked;
        this.location = location;
        
        rectangles = new ArrayList();
    }
    
    public void addRect(Rectangle r)
    {
        rectangles.add(r);
    }
    
    public boolean isBlocked()
    {
        return frontBlocked;
    }
    
    public void setLeft(Scene s) {
        this.left = s;
    }
    
    public void setRight(Scene s) {
        this.right = s;
    }
    
    public void setForward(Scene s) {
        this.forward = s;
    }
    
    public Scene getForward()
    {
        return forward;
    }
    
    public Scene getRight()
    {
        return right;
    }
    
    public Scene getLeft()
    {
        return left;
    }
    
    public BufferedImage getImage()
    {
        if (image == null)
        {
            loadImage();
        }
        return image;
    }
    
    private void loadImage()
    {
        try {
            image = ImageIO.read(new File("images\\" + imageLocation));
        } catch (IOException e) {}
    }
    
    public String getImagePath()
    {
        return imageLocation;
    }
    
    public ArrayList getRectangles()
    {
        return rectangles;
    }
    
    
    
    
    
    
}
