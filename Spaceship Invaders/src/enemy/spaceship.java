package enemy;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class spaceship extends Entity
{

    public spaceship(GamePanel gamePanel)
    {
        super(gamePanel);

        type = 3;
        name = "Space Ship";
        speed = 1;
        maxLife = 3;
        life = maxLife;
        left_right = 0;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 32*gamePanel.scale*2;
        solidArea.height = 16*gamePanel.scale*2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    private void getImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/boss-animations.png"));
            idle = cutImage(img,90,0, new int[]{32, 32}, new int[]{16, 16});
            walk = cutImage(img,90,0, new int[]{32, 32}, new int[]{16, 16});
            dead = cutImage(img,0,0, new int[]{16, 16, 16, 16, 16}, new int[]{16, 16, 16, 16, 16});
            attack = cutImage(img,90,0, new int[]{32, 32}, new int[]{16, 16});
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void setAction()
    {
        if (gamePanel.currentMap == 3)
        {
            if (!up_down)
            {
                if (!collisionOn)
                {
                    direction = "walk_down";
                }
                else
                {
                    direction = "walk_up"; up_down = true;
                }
            }
            else
            {
                if (!collisionOn)
                {
                    direction = "walk_up";

                }
                else
                {
                    direction = "walk_down";
                    up_down = false;
                }
            }
        }
    }
}
