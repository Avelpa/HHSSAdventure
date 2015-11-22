/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hhssadventure;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Dmitry
 */
public class View extends JComponent implements MouseMotionListener, MouseListener, KeyListener{
    
    private final int WIDTH = 650, HEIGHT = 700;
    private JFrame window;
    
    private Robot robot;
    private Cursor invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(""),
            new Point(0, 0),
            "invisible");
    private boolean inGame = true;
    
    BufferedImage laserGun;
    
    private double growth = 0.2;
    private int midX = 364;
    private int midY = 230;
    private double sceneY = 50;
    private double verticalVelocity = 0;
    private final int FLOOR = 50;
    private final int CROUCH_HEIGHT = 0;
    
    private Scene currentScene;
    
    private int horizontalMovement = 0;
    private boolean mousePressed = false;
    private char keyPressed;
    
    private boolean jumping = false;
    
    public View() throws AWTException
    {
        
        try {
            laserGun = ImageIO.read(new File("laserGun.png"));
        } catch (IOException e) {System.out.println("Error loading image");}
        
        window = new JFrame("MY VERSION");
        window.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.add(this);
        
        robot = new Robot();
        this.setCursor(invisibleCursor);
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        window.addKeyListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.ORANGE);
        g.fillRect(-horizontalMovement, (int)sceneY-20, currentScene.getImage().getWidth(), currentScene.getImage().getHeight()+40);
        g.drawImage(currentScene.getImage(), -horizontalMovement, (int)sceneY, null);
        g.drawImage(currentScene.getLeft().getImage(), -horizontalMovement - currentScene.getLeft().getImage().getWidth(), (int)sceneY, null);
        g.drawImage(currentScene.getRight().getImage(), -horizontalMovement + currentScene.getImage().getWidth(), (int)sceneY, null);
        
        int i = 0;
        Scene next = currentScene;
        do
        {
            ArrayList<Rectangle> rectangles = next.getRectangles();
            g.setColor(Color.YELLOW);
            for (Rectangle r: rectangles)
            {
                int newX = -horizontalMovement + midX + (int)((r.x-midX)*Math.pow(growth, i));
                int newY = midY + (int)((r.y-midY)*Math.pow(growth, i));
                int newWidth = (int)(r.width*Math.pow(growth, i));
                int newHeight = (int)(r.height*Math.pow(growth, i));
                g.fillRect(newX, newY, newWidth, newHeight);
            }
            next = next.getForward();
            i++;
        }while(next != null);
        
        if (laserGun != null)
        {
            g.drawImage(laserGun, midX+50, midY+50, null);
        }
        
        if (mousePressed)
        {
            int[] xs = {midX, 415, 430};
            int[] ys = {midY, 415, 384};
            g.fillPolygon(xs, ys, 3);
        }
        
        if (currentScene.isBlocked())
        {
            g.setColor(new Color(200, 0, 0, 100));
        }
        else
        {
            g.setColor(new Color(0, 200, 0, 100));
        }
        Graphics2D g2D = (Graphics2D) g;      
        g2D.setStroke(new BasicStroke(10F));  // set stroke width of 10

        g2D.drawOval(midX-50, midY-50, 100, 100);
        g2D.setStroke(new BasicStroke(5F));
        g2D.drawLine(midX-75, midY, midX+75, midY);
        g2D.drawLine(midX, midY-75, midX, midY+75);
        
    }
    
    public void setScene(Scene scene)
    {
        this.currentScene = scene;
    }
    
    public int getHorizontalMovement()
    {
        return horizontalMovement;
    }
    
    public void resetHorizontalMovement()
    {
//        if (Math.abs(horizontalMovement) >= currentScene.getImage().getWidth())
//        {
//            horizontalMovement = 0;
//        }
//        else
        if (horizontalMovement < 0)
        {
            horizontalMovement += currentScene.getImage().getWidth();
        }
        else
        {
            horizontalMovement -= currentScene.getImage().getWidth();
        }
    }
    
    private void centerMouse()
    {
        if (inGame)
        {
            robot.mouseMove((int)this.getLocationOnScreen().getX() + WIDTH/2, (int)this.getLocationOnScreen().getY() + HEIGHT/2);
        }
    }
    
    public boolean isMousePressed()
    {
        return mousePressed;
    }
    public void clearMousePressed()
    {
        mousePressed = false;
    }
    
    public void toggleCursor()
    {
        if (this.getCursor() == invisibleCursor)
        {
            this.setCursor(Cursor.getDefaultCursor());
        }
        else
        {
            this.setCursor(invisibleCursor);
        }
    }
    
    public char getKeyPressed()
    {
        return keyPressed;
    }
    
    public void clearKeyPressed()
    {
        keyPressed = '\0';
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (inGame)
        {
            horizontalMovement += (e.getX() - WIDTH/2)*1.5;
            centerMouse();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (inGame)
        {
            horizontalMovement += (e.getX() - WIDTH/2)*1.5;
            centerMouse();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (inGame)
        {
            mousePressed = true;
        } 
        else
        {
            currentScene.addRect(new Rectangle(e.getX() + horizontalMovement, e.getY(), 10, 10));
            if (currentScene.getRight().getForward() != null)
            {
                currentScene.getRight().getForward().getLeft().getLeft().addRect(new Rectangle(midX, e.getY(), 10, 10));
            }
            if (currentScene.getLeft().getForward() != null)
            {
                currentScene.getLeft().getForward().getRight().getRight().addRect(new Rectangle(midX, e.getY(), 10, 10));
            }
            if (currentScene.getForward() != null)
            {
                currentScene.getForward().getLeft().getLeft().getForward().addRect(new Rectangle(midX - (e.getX() - midX), e.getY(), 10, 10));
            }
        }
    }
    
    public boolean isJumping()
    {
        return jumping;
    }
    
    public void jump()
    {
        verticalVelocity += 0.05;
        sceneY -= verticalVelocity;
        if (sceneY <= FLOOR)
        {
            verticalVelocity = 0;
            sceneY = FLOOR;
            jumping = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clearMousePressed();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            inGame = inGame ? false: true;
            toggleCursor();
        }
        else
        {
            keyPressed = Character.toLowerCase(e.getKeyChar());
        }
        if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
        {
            sceneY = CROUCH_HEIGHT;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (!jumping)
            {
                verticalVelocity = -5;
                jumping = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)
        {
            sceneY = FLOOR;
        }
    }
}
