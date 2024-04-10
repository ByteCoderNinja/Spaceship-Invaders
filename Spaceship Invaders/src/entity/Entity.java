package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity
{
    public int worldX, worldY;
    public int speed;

    public BufferedImage[] idle, turn, walk, run, hurt, dead, jump, attack, fire;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
}
