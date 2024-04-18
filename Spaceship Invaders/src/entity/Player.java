package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity
{
    private int left_right = 0; //variable for player position (left/right)
    KeyHandler keyHandler;
    private static Player INSTANCE;

    public final int screenX;
    public final int screenY;
    public int width;
    public int height;



    private Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        super(gamePanel);
        this.keyHandler = keyHandler;

        width = 64;
        height = 64;

        screenX = (gamePanel.screenWidth / 2) - (width* gamePanel.scale / 2);
        screenY = (gamePanel.screenHeight / 2) - (height* gamePanel.scale / 2);

        solidArea = new Rectangle(25*gamePanel.scale, 35*gamePanel.scale, gamePanel.scale*12, gamePanel.scale*15);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }


    public static Player getInstance(GamePanel gamePanel, KeyHandler keyHandler)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Player(gamePanel, keyHandler);
        }
        return INSTANCE;
    }


    public void setDefaultValues()
    {
        worldX = gamePanel.tileSize*8;
        worldY = gamePanel.tileSize*7;
        speed = 4;
        direction = "idle";
        bullet = new OBJ_Bullet(gamePanel);

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }


    public void getPlayerImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/marine.png"));
            idle = cutImage(img,0,0, new int[]{64, 64, 64, 64, 64}, new int[]{64, 64, 64, 64, 64});
            walk = cutImage(img, 0, 64, new int[]{64, 64, 64, 64, 64, 64, 64, 64}, new int[]{64, 64, 64, 64, 64, 64, 64, 64});
            dead = cutImage(img, 0, 128, new int[]{64, 64, 64, 64, 64, 64, 64, 64}, new int[]{64, 64, 64, 64, 64, 64, 64, 64});
            attack = cutImage(img, 0, 192, new int[]{64, 64}, new int[]{64, 64});
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public static BufferedImage mirrorImage(BufferedImage originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-width, 0);

        Graphics2D g2d = mirroredImage.createGraphics();
        g2d.setTransform(tx);

        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return mirroredImage;
    }


    public BufferedImage[] cutImage(BufferedImage img, int x, int y, int[] width, int[] height)
    {
        BufferedImage[] bufferedImages = new BufferedImage[width.length];
        for (int i = 0; i < width.length; ++i)
        {
            bufferedImages[i] = img.getSubimage(x, y, width[i], height[i]);
            x += width[i];
        }
        return bufferedImages;
    }


    public void update()
    {
        if (keyHandler.upPressed)
        {
            direction = "walk_up";
        }
        else if (keyHandler.downPressed)
        {
            direction = "walk_down";
        }
        else if (keyHandler.leftPressed)
        {
            direction = "walk_left";
            left_right = 1;
        }
        else if (keyHandler.rightPressed)
        {
            direction = "walk_right";
            left_right = 0;
        }
        else if (keyHandler.attackSpace)
        {
            direction = "attack";
        }
        else
        {
            direction = "idle";
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        //CHECK OBJECT COLLISION
        int objIndex = gamePanel.collisionChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //CHECK EVENT
        gamePanel.eventHandler.checkEvent();

        //CHECK NPC COLLISION
        int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        interactNPC(npcIndex);

        //CHECK ENEMY COLLISION
        int enemyIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.marine_troop);
        contactEnemy(enemyIndex);

        if (collisionOn == false)
        {

            switch (direction)
            {
                case "walk_up": worldY -= speed;
                    break;
                case "walk_down": worldY += speed;
                    break;
                case "walk_left": worldX -= speed;
                    break;
                case "walk_right": worldX += speed;
                    break;
            }
        }

        ++spriteCounter;
        if (spriteCounter > 12)
        {
            spriteCounter = 0;
            switch (direction)
            {
                case "idle":
                    spriteNum = (spriteNum < idle.length - 1) ? ++spriteNum : 0;
                    break;
                case "attack":
                    spriteNum = (spriteNum < attack.length - 1) ? ++spriteNum : 0;
                    break;
                default:
                    spriteNum = (spriteNum < walk.length - 1) ? ++spriteNum : 0; if (spriteNum%2==0) gamePanel.playSE(1);
            }
        }

        if (gamePanel.keyH.attackSpace == true)
        {
            bullet.set(worldX, worldY, direction, true, this);

            gamePanel.bullets.add(bullet);
        }

        if (invincible == true)
        {
            ++invincibleCounter;
            if (invincibleCounter > 60)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void contactEnemy(int enemyIndex)
    {
        if (enemyIndex != 1000)
        {
            if (invincible == false)
            {
                life -= 1;
                invincible = true;
                gamePanel.playSE(3);
            }
        }
    }

    private void interactNPC(int x)
    {
        if (x != 1000)
        {
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.npc[x].speak();
        }
    }


    public void pickUpObject(int x)
    {
        if (x != 1000)
        {

        }
    }

    @Override
    public void draw(Graphics2D graphics2D)
    {
        switch (direction)
        {
            case "walk_left":
                image = mirrorImage(walk[spriteNum]);
                break;
            case "walk_right":
                image = walk[spriteNum];
                break;
            case "idle":
                spriteNum = (spriteNum > idle.length - 1) ? 0 : spriteNum;
                image = (left_right == 0) ? idle[spriteNum] : mirrorImage(idle[spriteNum]);
                break;
            case "attack":
                if (spriteNum < 2)
                {
                    image = (left_right == 0) ? attack[spriteNum] : mirrorImage(attack[spriteNum]);
                }
                break;
            default:
                image = (left_right == 0) ? walk[spriteNum] : mirrorImage(walk[spriteNum]);
        }

        if (invincible == true)
        {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        graphics2D.drawImage(image, screenX, screenY, width*gamePanel.scale, height*gamePanel.scale, null);

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
