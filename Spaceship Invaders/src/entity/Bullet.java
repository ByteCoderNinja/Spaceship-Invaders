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
        this.solidArea = new Rectangle(20, 20, 10, 5);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;


        left_right = gamePanel.player.left_right;
        if (left_right == 1)
        {
            this.worldX -= gamePanel.tileSize*2 - 15;
        }
        screenX = gamePanel.player.worldX + 20;
        screenY = gamePanel.player.worldY - 35;
    }


    public void update()
    {
        if (user == gamePanel.player)
        {
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.space_troop);
            int shipIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.space_ship);
            gamePanel.collisionChecker.checkObject(this, true);
            if (monsterIndex != 1000)
            {
                gamePanel.player.damageEnemy(monsterIndex);
                alive = false;
            }
            if (shipIndex != 1000)
            {
                gamePanel.player.damageShip(shipIndex);
                alive = false;
            }
        }
        if (user != gamePanel.player)
        {
            boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
            if (gamePanel.player.invincible == false && contactPlayer == true)
            {
                damagePlayer(attackPower);
                alive = false;
            }
        }

        if (left_right == 0)
        {
            worldX += speed;
        }
        else
        {
            worldX -= speed;
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
}
