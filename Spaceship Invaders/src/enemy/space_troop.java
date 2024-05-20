package enemy;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Math.abs;

public class space_troop extends Entity
{

    public space_troop(GamePanel gamePanel)
    {
        super(gamePanel);

        type = 2;
        name = "Space Troop";
        speed = 1;
        maxLife = 3;
        life = maxLife;
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


    public void setAction() {
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
}
