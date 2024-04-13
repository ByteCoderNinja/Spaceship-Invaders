package main;

import object.OBJ_key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI
{
    GamePanel gamePanel;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 70);
        OBJ_key key = new OBJ_key();
        keyImage = key.image;
    }

    public void showMessage(String message)
    {
        this.message = message;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D)
    {
        if (gameFinished == true)
        {
            graphics2D.setFont(arial_40);
            graphics2D.setColor(Color.white);

            String text;
            int textLength;
            int x;
            int y;

            text = "You destroyed the Space Ship!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 - (gamePanel.tileSize*3);
            graphics2D.drawString(text, x, y);

            text = "Your Time is :" + dFormat.format(playTime) + "!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 - (gamePanel.tileSize*4);
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(arial_80B);
            graphics2D.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.screenWidth/2 - textLength/2;
            y = gamePanel.screenHeight/2 - (gamePanel.tileSize*2);
            graphics2D.drawString(text, x, y);

            gamePanel.gameThread = null;
        }
        else
        {
            graphics2D.setFont(arial_40);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.drawString("x " + gamePanel.player.hasKey, 80, 70);

            //TIME
            playTime += (double)1/60;
            graphics2D.drawString("Time:" + dFormat.format(playTime), gamePanel.tileSize*11, 65);

            //MESSAGE
            if (messageOn == true)
            {
                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(message, gamePanel.tileSize/2, gamePanel.tileSize*3);

                messageCounter++;

                if (messageCounter > 120)
                {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
