package main;

import object.OBJ_Heart;
import object.OBJ_key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI
{
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font arial_40, arial_80B;
    BufferedImage heart_full, hear_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;


    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 70);

        //CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        hear_half = heart.image2;
        heart_blank = heart.image3;
    }


    public void showMessage(String message)
    {
        this.message = message;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(Color.white);

        //TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState)
        {
            drawTitleScreen();
        }

        //PLAY STATE
        if (gamePanel.gameState == gamePanel.playState)
        {
            drawPlayerLife();
        }

        //PAUSE STATE
        if (gamePanel.gameState == gamePanel.pauseState)
        {
            drawPlayerLife();
            drawPauseScreen();
        }

        //DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.dialogueState)
        {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    private void drawPlayerLife()
    {
        int x = gamePanel.tileSize/2;
        int y = gamePanel.tileSize/2;
        int i = 0;

        while (i < gamePanel.player.maxLife/2)
        {
            graphics2D.drawImage(heart_blank, x, y, null);
            ++i;
            x += gamePanel.tileSize;
        }
    }

    private void drawTitleScreen()
    {
        graphics2D.setColor(new Color(48, 213, 200));
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 76F));
        String text = "Spaceship Invaders";
        int x = getXforCenteredText(text);
        int y =gamePanel.tileSize*3;

        //SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x+5, y+5);

        //MAIN COLOR
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);

        //JACOB IMAGE
        x = gamePanel.screenWidth/2 - gamePanel.tileSize;
        y += gamePanel.tileSize*2;
        graphics2D.drawImage(gamePanel.player.idle[0], x, y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);

        //MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize*4;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 0)
        {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 1)
        {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 2)
        {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }
    }

    public void drawDialogueScreen()
    {
        //WINDOW
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN,32F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialogue.split("\n"))
        {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0, 200);
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        graphics2D.setColor(c);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void drawPauseScreen()
    {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gamePanel.screenHeight;

        graphics2D.drawString(text, x, y);
    }

    public int getXforCenteredText(String text)
    {
        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }
}
