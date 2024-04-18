package entity;

import main.GamePanel;

public class Bullet extends Entity
{
    Entity user;

    public Bullet(GamePanel gamePanel)
    {
        super(gamePanel);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        //this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update()
    {
        switch (direction)
        {
            case "walk_up": worldY -= speed; break;
            case "walk_down": worldY += speed; break;
            case "walk_left": worldX -= speed; break;
            case "walk_right": worldX += speed; break;
        }

        --life;
        if (life <= 0)
        {
            //alive = 0;
        }

        ++spriteCounter;
        if (spriteCounter > 12)
        {
            if (spriteNum == 1)
            {
                spriteNum = 2;
            }
            else if (spriteNum == 2)
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
