package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity
{
    public int left_right = 0; //variable for player position (left/right)
    KeyHandler keyHandler;
    private static Player INSTANCE;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
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

        solidArea = new Rectangle(27*gamePanel.scale, 35*gamePanel.scale, gamePanel.scale*10, gamePanel.scale*15);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 50;
        attackArea.height = 72;
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
        speed = 6;
        direction = "idle";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        bullet = new OBJ_Bullet(gamePanel);
    }

    public void setDefaultPositions()
    {
        worldX = gamePanel.tileSize*8;
        worldY = gamePanel.tileSize*7;
        direction = "walk_down";
    }

    public void restoreLife()
    {
        life = maxLife;
        invincible = false;
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


    public void update()
    {
        if (keyHandler.upPressed && !keyHandler.attackSpace)
        {
            direction = "walk_up";
        }
        else if (keyHandler.downPressed && !keyHandler.attackSpace)
        {
            direction = "walk_down";
        }
        else if (keyHandler.leftPressed && !keyHandler.attackSpace)
        {
            direction = "walk_left";
            left_right = 1;
        }
        else if (keyHandler.rightPressed && !keyHandler.attackSpace)
        {
            direction = "walk_right";
            left_right = 0;
        }
        else if (keyHandler.attackSpace)
        {
            direction = "attack";
            attacking();
        }
        else
        {
            direction = "idle";
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        //contactDamageTile(9);

        //CHECK OBJECT COLLISION
        int objIndex = gamePanel.collisionChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //CHECK EVENT
        gamePanel.eventHandler.checkEvent();

        //CHECK NPC COLLISION
        int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        interactNPC(npcIndex);

        //CHECK ENEMY COLLISION
        int enemyIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.space_troop);
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
                    bullet = new OBJ_Bullet(gamePanel);
                    gamePanel.playSE(5);
                    bullet.set(worldX + gamePanel.tileSize*2 + 15, worldY + gamePanel.tileSize*2 + 6, direction, true, this);
                    gamePanel.bullets.add(bullet);
                    break;
                default:
                    spriteNum = (spriteNum < walk.length - 1) ? ++spriteNum : 0; if (spriteNum%2==0) gamePanel.playSE(1);
            }
        }

        /*if (gamePanel.keyH.attackSpace == true)
        {
            bullet.set(worldX + gamePanel.tileSize*2 + 15, worldY + gamePanel.tileSize*2 + 6, direction, true, this);

            gamePanel.bullets.add(bullet);
        }*/

        if (gamePanel.keyH.attackSpace == true && bullet.alive == false)
        {
            // SET DEFAULT COORDINATES, DIRECTIONS AND USER
            bullet.set(worldX, worldY, direction, false, this);

            // ADD IT TO THE LIST
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

        if (life <= 0)
        {
            gamePanel.gameState = gamePanel.gameOverState;
        }
    }

    public void attacking()
    {
        ++spriteCounter;

        if (spriteCounter <= 5)
        {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25)
        {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            if (direction == "attack")
            {
                if (left_right == 1)
                {
                    worldX -= attackArea.width;
                }
                else
                {
                    worldX += attackArea.width;
                }
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int enemyIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.space_troop);
            damageEnemy(enemyIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25)
        {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int index)
    {
        if (index != 1000)
        {
            String objectName = gamePanel.obj[gamePanel.currentMap][index].name;

            switch (objectName)
            {
                case "Key":
                    ++hasKey;
                    gamePanel.obj[gamePanel.currentMap][index] = null;
                    break;
                case "Door":
                    if (hasKey > 0)
                    {
                        switch (index)
                        {
                            case 0:
                            case 4: gamePanel.obj[gamePanel.currentMap][index].worldX -= gamePanel.tileSize;
                            gamePanel.obj[gamePanel.currentMap][index + 1].worldX += gamePanel.tileSize;
                            break;
                            case 1:
                            case 5: gamePanel.obj[gamePanel.currentMap][index].worldX += gamePanel.tileSize;
                            gamePanel.obj[gamePanel.currentMap][index - 1].worldX -= gamePanel.tileSize;
                            break;
                            case 2: gamePanel.obj[gamePanel.currentMap][index].worldY -= gamePanel.tileSize;
                            gamePanel.obj[gamePanel.currentMap][index + 1].worldY += gamePanel.tileSize;
                            break;
                            case 3: gamePanel.obj[gamePanel.currentMap][index].worldY += gamePanel.tileSize;
                            gamePanel.obj[gamePanel.currentMap][index - 1].worldY -= gamePanel.tileSize;
                            break;
                        }
                        --hasKey;
                    }
                    break;
                case "AutoDestroyButton":
                    --life;
            }
        }
    }

    public void damageEnemy(int enemyIndex)
    {
        if (enemyIndex != 1000)
        {
            if (gamePanel.space_troop[gamePanel.currentMap][enemyIndex].invincible == false)
            {
                gamePanel.space_troop[gamePanel.currentMap][enemyIndex].life -= 1;
                gamePanel.space_troop[gamePanel.currentMap][enemyIndex].invincible = true;
                gamePanel.space_troop[gamePanel.currentMap][enemyIndex].damageReaction();

                if (gamePanel.space_troop[gamePanel.currentMap][enemyIndex].life <= 0)
                {
                    gamePanel.space_troop[gamePanel.currentMap][enemyIndex].dying = true;
                }
                gamePanel.playSE(3);
            }
        }
    }

    public void damageShip(int shipIndex)
    {
        if (shipIndex != 1000)
        {
            if (gamePanel.space_ship[gamePanel.currentMap][shipIndex].invincible == false)
            {
                gamePanel.space_ship[gamePanel.currentMap][shipIndex].life -= 1;
                gamePanel.space_ship[gamePanel.currentMap][shipIndex].invincible = true;
                gamePanel.space_ship[gamePanel.currentMap][shipIndex].damageReaction();

                if (gamePanel.space_ship[gamePanel.currentMap][shipIndex].life <= 0)
                {
                    gamePanel.space_ship[gamePanel.currentMap][shipIndex].dying = true;
                }
            }
        }
    }



    public void contactEnemy(int enemyIndex)
    {
        if (enemyIndex != 1000)
        {
            if (invincible == false)
            {
                --life;
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
            gamePanel.npc[0][x].speak();
        }
        if (gamePanel.keyH.attackSpace == true)
        {
            attacking = true;
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
