package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public final int maxWorldColumns = 54;
    public final int maxWorldRows = 52;

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
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public Player player = Player.getInstance(this, keyH);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    ArrayList<Entity> entities = new ArrayList<Entity>();

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

            //ADD ENTITIES TO THE LIST
            entities.add(player);

            for (int i = 0; i < npc.length; ++i)
            {
                if (npc[i] != null)
                {
                    entities.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; ++i)
            {
                if (obj[i] != null)
                {
                    entities.add(obj[i]);
                }
            }

            //SORT
            Collections.sort(entities, new Comparator<Entity>()
            {
                @Override
                public int compare(Entity e1, Entity e2)
                {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }

                @Override
                public boolean equals(Object obj)
                {
                    return false;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entities.size(); ++i)
            {
                entities.get(i).draw(graphics2d);
            }

            //EMPTY ENTITY LIST
            for (int i = 0; i < entities.size(); ++i)
            {
                entities.remove(i);
            }

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
