package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_AutoDestroyButton extends SuperObject
{
    public OBJ_AutoDestroyButton()
    {
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
