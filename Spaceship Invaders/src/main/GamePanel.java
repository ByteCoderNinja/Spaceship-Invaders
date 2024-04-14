package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable
{
    // SCREEN SETTINGS
    static final int originalTileSize = 16;
    public static final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenColumns = 16;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenColumns;
    public final int screenHeight = tileSize * maxScreenRows;

    //WORLD SETTINGS
    public final int maxWorldColumns = 40;
    public final int maxWorldRows = 40;

    //FPS
    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocus();
    }


    public void setupGame()
    {
        assetSetter.setObject();
        assetSetter.setNPC();
        playMusic(0);
        gameState = titleState;
    }


    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run()
    {
        double drawInterval = (double) 1000000000 / FPS;
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
        if (gameState == playState)
        {
            //PLAYER
            player.update();
            //NPC
            for (int i = 0; i < npc.length; ++i)
            {
                if (npc[i] != null)
                {
                    npc[i].update();
                }
            }
        }

        if (gameState == pauseState)
        {
            //nothing
        }
    }

    public void paintComponent(Graphics graphics1)
    {
        super.paintComponent(graphics1);
        Graphics2D graphics2d = (Graphics2D)graphics1;

        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true)
        {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if (gameState == titleState)
        {
            ui.draw(graphics2d);
        }
        else
        {
            //TILE
            tileM.draw(graphics2d);

            for (int i = 0; i < obj.length; i++)
            {
                if (obj[i] != null)
                {
                    obj[i].draw(graphics2d, this);
                }
            }
            //NPC
            for (int i = 0; i < npc.length; i++)
            {
                if (npc[i] != null)
                {
                    npc[i].draw(graphics2d);
                }
            }

            //PLAYER
            player.draw(graphics2d);


            //UI
            ui.draw(graphics2d);
        }

        if (keyH.checkDrawTime == true)
        {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            graphics2d.setColor(Color.white);
            graphics2d.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }

        graphics2d.dispose();
    }

    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic()
    {
        music.stop();
    }

    public void playSE(int i)
    {
        se.setFile(i);
        se.play();
    }
}
