package enemy;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;

public class space_troop extends Entity
{

    public space_troop(GamePanel gamePanel)
    {
        super(gamePanel);

        type = 2;
        name = "Space Troop";
        speed = 1;
        maxLife = 6;
        life = maxLife;
        bullet = new OBJ_Bullet(gamePanel);
        left_right = 0;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 64;
        solidArea.height = 64;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }


    public void getImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("enemy/enemy_alien.png"));
            idle = cutImage(img,0,0, new int[]{33, 33, 33, 33}, new int[]{32, 32, 32, 32});
            walk = cutImage(img,0,0, new int[]{33, 33, 33, 33}, new int[]{32, 32, 32, 32});
            dead = cutImage(img,0,0, new int[]{33, 33, 33, 33}, new int[]{32, 32, 32, 32});
            attack = cutImage(img,0,0, new int[]{33, 33, 33, 33}, new int[]{32, 32, 32, 32});
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void setAction()
    {
        if (gamePanel.currentMap == 2) {
            int playerX = gamePanel.player.worldX + gamePanel.tileSize;
            int playerY = gamePanel.player.worldY + gamePanel.tileSize;
            int distX = abs(worldX - playerX);
            int distY = abs(worldY - playerY);

            if (distX > distY)
            {
                if (worldX < playerX)
                {
                    direction = "walk_right";
                }
                else if (worldX > playerX)
                {
                    direction = "walk_left";
                }
            }
            else
            {
                if (worldY < playerY)
                {
                    direction = "walk_down";
                }
                else
                {
                    direction = "walk_up";
                }
            }
        }
        else
        {
            ++actionLockCounter;
            if (actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25)
                {
                    direction = "walk_up";
                }
                if (i > 25 && i <= 50)
                {
                    direction = "walk_down";
                }
                if (i > 50 && i <= 75)
                {
                    direction = "walk_left";
                    left_right = 1;
                }
                if (i > 75 && i <= 100)
                {
                    direction = "walk_right";
                    left_right = 0;
                }

                actionLockCounter = 0;

            }

            int i = new Random().nextInt(100)+1;
            if (i > 99 && bullet.alive == false)
            {
                bullet.set(worldX, worldY, direction, false, this);
                gamePanel.bullets.add(bullet);
            }
        }

    }

    public void damageReaction(GamePanel gamePanel)
    {
        actionLockCounter = 0;
        direction = gamePanel.player.direction;
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
}
