package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_Door extends SuperObject
{
    public OBJ_Door()
    {
        name = "Door";
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/scifitiles-sheet.png"));
            image = img.getSubimage(64, 132, 32, 16);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        collision = true;
    }
}
