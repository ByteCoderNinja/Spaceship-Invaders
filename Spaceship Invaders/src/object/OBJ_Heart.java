package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject
{
    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        name = "Heart";
        try
        {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sprite-sheet.png")).getSubimage(2931, 281, 435, 437);
            image2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sprite-sheet.png")).getSubimage(3453, 281, 435, 437);
            image3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sprite-sheet.png")).getSubimage(3970, 281, 435, 437);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
