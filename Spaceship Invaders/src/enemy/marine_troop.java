package enemy;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class marine_troop extends Entity
{

    public marine_troop(GamePanel gamePanel)
    {
        super(gamePanel);

        type = 2;
        name = "Marine Troop";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 24*gamePanel.scale;
        solidArea.y = 39*gamePanel.scale;
        solidArea.width = 20;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }


    public void getImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("enemy/enemy.png"));
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


    public void setAction()
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
            }
            if (i > 75 && i <= 100)
            {
                direction = "walk_right";
            }

            actionLockCounter = 0;
        }
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
