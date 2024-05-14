package object;

import entity.Bullet;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Bullet extends Bullet
{
    GamePanel gamePanel;

    public OBJ_Bullet(GamePanel gamePanel)
    {
        super(gamePanel);
        this.gamePanel = gamePanel;

        name = "Bullet";
        speed = 1;
        maxLife = 80;
        life = maxLife;
        attackPower = 2;
        alive = false;
        getImage();
    }

    private void getImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("bullet/muzzle flash.png"));
            image = img.getSubimage(0, 0, 64, 64);
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
