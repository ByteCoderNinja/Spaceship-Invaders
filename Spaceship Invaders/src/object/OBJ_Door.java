package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.System.exit;

public class OBJ_Door extends Entity
{
    public OBJ_Door(GamePanel gamePanel)
    {
        super(gamePanel);
        name = "Door";
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/scifitiles-sheet.png"));
            image = img.getSubimage(64, 132, 32, 16);
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            exit(-1);
        }
        collision = true;
    }
}
