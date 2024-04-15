package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool
{
    public BufferedImage scaleImage;

    public BufferedImage scaleImage(BufferedImage image, int Width, int Height)
    {
        BufferedImage newImage = new BufferedImage(Width, Height, image.getType());
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, Width, Height, null);
        graphics2D.dispose();

        return newImage;
    }
}
