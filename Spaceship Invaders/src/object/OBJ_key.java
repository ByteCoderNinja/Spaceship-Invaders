package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_key extends Entity
{

    public OBJ_key(GamePanel gamePanel)
    {
        super(gamePanel);

        name = "Key";
        try
        {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sprite-sheet.png")).getSubimage(1260, 10, 863, 980);
            image = uTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
