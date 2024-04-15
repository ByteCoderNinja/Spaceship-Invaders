package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC_Alien extends Entity
{
    public NPC_Alien(GamePanel gamePanel)
    {
        super(gamePanel);

        direction = "idle";
        speed = 1;

        getImage();
        setDialogue();
    }


    public void getImage()
    {
        try
        {
            BufferedImage img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("npc/NPC_alien.png"));
            idle = cutImage(img,0,0, new int[]{223, 202, 212}, new int[]{438, 422, 427});
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void setDialogue()
    {
        dialogues[0] = "Hello, Jacob!";
        dialogues[1] = "We've come to destroy this entire \nship!";
        dialogues[2] = "There's nothing you can do to stop \nus!";
        dialogues[3] = "Leave as soon as you can! You \nwon't survive if you try to save the \nspaceship!";
    }


    public BufferedImage[] cutImage(BufferedImage img, int x, int y, int[] width, int[] height)
    {
        BufferedImage[] bufferedImages = new BufferedImage[width.length];
        for (int i = 0; i < width.length; ++i)
        {
            bufferedImages[i] = img.getSubimage(x, y, width[i], height[i]);
            x += width[i];
        }
        return bufferedImages;
    }


    public void setAction()
    {
        ++actionLockCounter;
        if (actionLockCounter == 120)
        {
            direction = "idle";
            actionLockCounter = 0;
        }
    }

    public void speak()
    {
        super.speak();
    }
}
