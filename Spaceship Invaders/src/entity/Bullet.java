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
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;

        screenX = gamePanel.player.worldX + 20;
        screenY = gamePanel.player.worldY - 35;
    }


    public void update()
    {
        if (user == gamePanel.player)
        {
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.space_troop);
            if (monsterIndex != 1000)
            {
                gamePanel.player.damageEnemy(monsterIndex);
                alive = false;
            }
        }
        if (user != gamePanel.player)
        {

        }

        switch (left_right)
        {
            case 0: worldX += speed; break;
            case 1: worldX -= speed; break;
        }

        --life;
        if (life <= 0)
        {
            alive = false;
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

   /* @Override
    public void draw(Graphics2D graphics2D)
    {
        switch (direction)
        {
            case "walk_right":
                screenX = gamePanel.player.worldX;
                screenY = gamePanel.player.worldY;
            case "walk_left":
                screenX = gamePanel.player.worldX;
                screenY = gamePanel.player.worldY;
        }
        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }*/
}
