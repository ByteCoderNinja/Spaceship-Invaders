package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Entity
{
    GamePanel gamePanel;
    private int left_right = 0;
    public BufferedImage[] idle, walk, hurt, dead, attack, fire;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public BufferedImage image, image2, image3;
    public UtilityTool uTool = new UtilityTool();
    public boolean collision = false;
    String[] dialogues = new String[20];

    //STATE
    public int worldX, worldY;
    public String direction = "walk_down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;

    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;

    //CHARACTER STATUS
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int speed;
    public int type; //// 0 = player, 1 = npc, 2 = enemy
    public String name;
    public Bullet bullet;

    public Entity(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}
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
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.marine_troop);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true)
        {
            if (gamePanel.player.invincible == false)
            {
                gamePanel.player.life -= 1;
                gamePanel.player.invincible = true;
            }
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
                case "fire":
                    spriteNum = (spriteNum < fire.length - 1) ? ++spriteNum : 0;
                    break;
                default:
                    spriteNum = (spriteNum < walk.length - 1) ? ++spriteNum : 0;
            }
        }
    }

    public void draw(Graphics2D graphics2D)
    {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            int imageSizeX = gamePanel.tileSize;

            switch (direction)
            {
                case "walk_down":
                case "walk_up":
                    if (name != "Door") {
                        image = (left_right == 0) ? walk[spriteNum] : mirrorImage(walk[spriteNum]);
                    } else {}
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
                    image = (left_right == 0) ? attack[spriteNum] : mirrorImage(attack[spriteNum]);
                    if (spriteNum >= 2)
                    {
                        imageSizeX = gamePanel.tileSize * 2;
                    }
                    break;
                case "fire":
                    image = (left_right == 0) ? fire[spriteNum] : mirrorImage(fire[spriteNum]);
                    if (spriteNum >= 5)
                    {
                        imageSizeX = gamePanel.tileSize * 2;
                    }
                    break;
            }

            if (name == "Marine Troop")
            {
                graphics2D.drawImage(image, screenX, screenY, 64*gamePanel.scale, 64*gamePanel.scale, null);
            }
            else
            {
                graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
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
}
