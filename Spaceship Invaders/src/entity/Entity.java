package entity;

import main.GamePanel;
import main.UtilityTool;
import tile.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Entity
{
    public GamePanel gamePanel;
    public int left_right = 0;
    public BufferedImage[] idle, walk, dead, attack;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public BufferedImage image, image2, image3;
    public UtilityTool uTool = new UtilityTool();
    public boolean collision = false;
    String[] dialogues = new String[20];

    //STATE
    public boolean up_down = false;
    public int worldX, worldY;
    public String direction = "walk_down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int explosionCounter = 0;

    //CHARACTER STATUS
    public int attackPower;
    public int maxLife;
    public int life;
    public int speed;
    public int type; //// 0 = player, 1 = npc, 2 = enemy, 3 = spaceship/boss
    public String name;
    public Bullet bullet;

    public Entity(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}

    public void damageReaction() {}

    public void speak()
    {
        if (dialogues[dialogueIndex] == null)
        {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        ++dialogueIndex;
    }


    public void update()
    {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        boolean contactPlayer = false;
        gamePanel.collisionChecker.checkObject(this, false);
        if (this.type != 3)
        {
            gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
        }

        if (this.type != 2 && this.type != 3)
        {
            gamePanel.collisionChecker.checkEntity(this, gamePanel.space_troop);
        }

        if (this.type == 2 && contactPlayer == true)
        {
            damagePlayer(attackPower);
        }

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
                    break;
                case "dead":
                    spriteNum = (spriteNum < dead.length - 1) ? ++spriteNum : 0;
                    break;
                default:
                    spriteNum = (spriteNum < walk.length - 1) ? ++spriteNum : 0;
            }
        }

        if (invincible == true)
        {
            ++invincibleCounter;
            if (invincibleCounter > 40)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }


    public void damagePlayer(int attack)
    {
        if (gamePanel.player.invincible == false)
        {
            gamePanel.player.life -= 1;
            gamePanel.player.invincible = true;
        }
    }


    public void draw(Graphics2D graphics2D)
    {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            switch (direction)
            {
                case "walk_down":
                case "walk_up":
                    if (name != "Door" && name != "Key")
                    {
                        image = (left_right == 0) ? walk[spriteNum] : mirrorImage(walk[spriteNum]);
                    }
                    break;
                case "walk_left":
                    image = mirrorImage(walk[spriteNum]);
                    left_right = 1;
                    break;
                case "walk_right":
                    image = walk[spriteNum];
                    left_right = 0;
                    break;
                case "idle":
                    spriteNum = (spriteNum > idle.length - 1) ? 0 : spriteNum;
                    image = (left_right == 0) ? idle[spriteNum] : mirrorImage(idle[spriteNum]);
                    break;
                case "attack":
                    if (attack != null)
                    {
                        image = (left_right == 0) ? attack[spriteNum] : mirrorImage(attack[spriteNum]);
                    }
                    break;
                case "dead":
                    spriteNum = (spriteNum > dead.length - 1) ? 0 : spriteNum;
                    image = dead[spriteNum];
            }

        //HPBar
        if (type == 2 && hpBarOn == true)
        {
            double oneScale = (double)gamePanel.tileSize/maxLife;
            double hpBarValue = oneScale*life;

            graphics2D.setColor(new Color(0x201212));
            graphics2D.fillRect(screenX + 7, screenY - 15, gamePanel.tileSize + 2, 12);

            graphics2D.setColor(new Color(215, 0, 0, 255));
            graphics2D.fillRect(screenX + 7, screenY - 15, (int)hpBarValue, 10);

            ++hpBarCounter;

            if (hpBarCounter > 600)
            {
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if (type == 3 && hpBarOn == true)
        {
            double oneScale = (double)gamePanel.tileSize*4/maxLife;
            double hpBarValue = oneScale*life;

            graphics2D.setColor(new Color(0x201212));
            graphics2D.fillRect(screenX, screenY - 15, gamePanel.tileSize*4, 12);

            graphics2D.setColor(new Color(215, 0, 0, 255));
            graphics2D.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            ++hpBarCounter;

            if (hpBarCounter > 600)
            {
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

            if (invincible == true)
            {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(graphics2D, 0.4F);
            }
            if (dying == true)
            {
                dyingAnimation(graphics2D);
            }

            if (name == "Space Ship")
            {
                graphics2D.drawImage(image, screenX, screenY, 32*gamePanel.scale*2, 16*gamePanel.scale*2, null);
                changeAlpha(graphics2D, 1F);
            }
            if (dying == true && type == 3 && explosionCounter < 5)
            {
                graphics2D.drawImage(dead[explosionCounter], screenX - 50, screenY - 80, gamePanel.tileSize * 6, gamePanel.tileSize * 6, null);
                ++explosionCounter;
            }

            if (name == "Space Troop")
            {
                graphics2D.drawImage(image, screenX, screenY, 64, 64, null);
                changeAlpha(graphics2D, 1F);
            }
            if (name != "Space Ship" && name != "Space Troop")
            {
                graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(screenX+solidArea.x, screenY+solidArea.y, solidArea.width, solidArea.height);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void dyingAnimation(Graphics2D graphics2D)
    {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
        ++dyingCounter;

        int i = 5;

        if (dyingCounter <= i) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i && dyingCounter <= i*2) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(graphics2D, 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(graphics2D, 1f);}
        if (dyingCounter > i*8)
        {
            dying = false;
            alive = false;
        }
    }


    public void changeAlpha(Graphics2D graphics2D, float alphaValue)
    {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
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

}
