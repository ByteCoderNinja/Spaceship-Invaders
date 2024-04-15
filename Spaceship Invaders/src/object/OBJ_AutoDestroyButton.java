package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_AutoDestroyButton extends Entity
{
    public OBJ_AutoDestroyButton(GamePanel gamePanel)
    {
        super(gamePanel);
        name = "AutoDestroyButton";
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/scifitiles-sheet.png"));
            image = img.getSubimage(72, 167, 15, 15);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}
