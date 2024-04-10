package entity;

import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity
{
    private int i = 0; //variable for player position (left/right)
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    int hasKey = 0;


    public Player(GamePanel gamePanel, KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        screenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(0, 0, gamePanel.tileSize - 2, gamePanel.tileSize - 2);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }


    public void setDefaultValues()
    {
        worldX = gamePanel.tileSize;
        worldY = gamePanel.tileSize;
        speed = 4;
        direction = "idle";
    }


    public void getPlayerImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/predatormask_finalizat.png"));
            idle = cutImage(img,0,0, new int[]{230, 230, 230}, new int[]{396, 404, 413});
            turn = cutImage(img, 0, 416, new int[]{228, 228, 228}, new int[]{399, 399, 395});
            walk = cutImage(img, 0, 816, new int[]{225, 225, 223, 237, 238, 237}, new int[]{400, 403, 405, 390, 393, 393});
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
            i = 1;
        }
        else if (keyHandler.rightPressed)
        {
            direction = "walk_right";
            i = 0;
        }
        else
        {
            direction = "idle";
        }

        //Check tile collision
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        //Check object collision
        int objIndex = gamePanel.collisionChecker.checkObject(this, true);
        pickUpObject(objIndex);

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
                default:
                    spriteNum = (spriteNum < walk.length - 1) ? ++spriteNum : 0;
            }
        }
    }


    public void pickUpObject(int i)
    {
        if (i != 1000)
        {
            String objectName = gamePanel.obj[i].name;

            switch (objectName)
            {
                case "Key":
                    ++hasKey;
                    gamePanel.obj[i] = null;
                    break;
                case "Door":
                    if (hasKey > 0)
                    {
                        gamePanel.obj[i] = null;
                        --hasKey;
                    }
                    break;
            }
        }
    }


    public void draw(Graphics graphics2)
    {
        BufferedImage image = null;

        switch (direction)
        {
            case "walk_left": image = mirrorImage(walk[spriteNum]);
            break;
            case "walk_right": image = walk[spriteNum];
            break;
            case "idle": spriteNum = (spriteNum > idle.length - 1) ? 0 : spriteNum;
                image = (i == 0) ? idle[spriteNum] : mirrorImage(idle[spriteNum]);
            break;
            default: image = (i == 0) ? walk[spriteNum] : mirrorImage(walk[spriteNum]);
        }
        graphics2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

}
