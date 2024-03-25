package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenColumns = 16;
    final int maxScreenRows = 12;
    final int screenWidth = tileSize * maxScreenColumns;
    final int screenHeight = tileSize * maxScreenRows;

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocus();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run()
    {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1)
            {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update()
    {
        if (keyH.upPressed)
        {
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed)
        {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed)
        {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed)
        {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics graphics1)
    {
        super.paintComponent(graphics1);

        Graphics graphics2 = (Graphics2D)graphics1;

        graphics2.setColor(Color.red);

        graphics2.fillRect(playerX, playerY, tileSize, tileSize);

        graphics2.dispose();
    }
}
