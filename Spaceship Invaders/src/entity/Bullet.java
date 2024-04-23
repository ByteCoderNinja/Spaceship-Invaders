package entity;

import main.GamePanel;

import java.awt.*;

public class Bullet extends Entity
{
    Entity user;
    int screenX, screenY;

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

        screenX = gamePanel.player.worldX + 20;
        screenY = gamePanel.player.worldY - 35;
    }


    public void update()
    {
        if (direction == "attack")
        {
            screenX += speed;
        }

        --life;
        if (life <= 0)
        {
            //alive = 0;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D)
    {
        /*switch (direction)
        {
            case "walk_right":
                screenX = gamePanel.player.worldX;
                screenY = gamePanel.player.worldY;
            case "walk_left":
                screenX = gamePanel.player.worldX;
                screenY = gamePanel.player.worldY;
        }*/
        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
