package main;

import entity.Entity;
import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class UI
{
    GamePanel gamePanel;
    Graphics2D graphics2D;
    Font arial_40, arial_80B;
    BufferedImage heart_full, heart_half, heart_blank;
    Font speedy;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNumber = 0;
    public static boolean hasEntered = false;


    public UI(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        try
        {
            speedy = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/font/SpeedyRegular.ttf"))).deriveFont(Font.PLAIN, 40);
        }
        catch (FontFormatException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 70);

        //CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
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

        graphics2D.setColor(Color.white);
        graphics2D.setFont(speedy);
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

        //GAME OVER STATE
        if (gamePanel.gameState == gamePanel.gameOverState)
        {
            drawGameOverScreen();
        }

        //GAME WON STATE
        if (gamePanel.gameState == gamePanel.gameWonState)
        {
            drawGameWonScreen();
        }
}

    private void drawGameWonScreen()
    {
        try
        {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/GameWonPhoto.jpg")));
            graphics2D.drawImage(img, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*graphics2D.setColor(new Color(0,0,0,150));
        graphics2D.fillRect(0,0,gamePanel.screenWidth, gamePanel.screenHeight);*/

        int x;
        int y;
        String text;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30f));

        text = "Congratulations!";
        graphics2D.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize*4;
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);

        text = "You saved the Space Ship!";
        graphics2D.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize*5;
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);

        //Retry
        graphics2D.setFont(graphics2D.getFont().deriveFont(50f));
        text = "Back To Main Menu";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize*4;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 0)
        {
            graphics2D.drawString(">", x-40, y);
        }

        //Back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 1)
        {
            graphics2D.drawString(">", x-40, y);
        }
    }

    private void drawGameOverScreen()
    {
        graphics2D.setColor(new Color(0,0,0,150));
        graphics2D.fillRect(0,0,gamePanel.screenWidth, gamePanel.screenHeight);

        int x;
        int y;
        String text;
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 50f));

        text = "Game Over";
        graphics2D.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gamePanel.tileSize*4;
        graphics2D.drawString(text, x, y);
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x-4, y-4);

        //Retry
        graphics2D.setFont(graphics2D.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize*4;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 0)
        {
            graphics2D.drawString(">", x-40, y);
        }

        //Back to title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        graphics2D.drawString(text, x, y);
        if (commandNumber == 1)
        {
            graphics2D.drawString(">", x-40, y);
        }
    }

    private void drawPlayerLife()
    {
    //    gamePanel.player.life = 6;

        int x = gamePanel.tileSize/2;
        int y = gamePanel.tileSize/2;
        int i = 0;

        //DRAW MAX LIFE
        while (i < gamePanel.player.maxLife/2)
        {
            graphics2D.drawImage(heart_blank, x, y, null);
            ++i;
            x += gamePanel.tileSize;
        }

        //RESET
        x = gamePanel.tileSize/2;
        y = gamePanel.tileSize/2;
        i = 0;

        //DRAW CURRENT LIFE
        while (i < gamePanel.player.life)
        {
            graphics2D.drawImage(heart_half, x, y, null);
            ++i;
            if (i < gamePanel.player.life)
            {
                graphics2D.drawImage(heart_full, x, y, null);
            }
            ++i;
            x += gamePanel.tileSize;
        }
    }

    private void drawTitleScreen()
    {
        try
        {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/MenuPhoto.png"))).getSubimage(0, 80, 714, 394);
            graphics2D.drawImage(img, 0, 0, 766, 576, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 50F));
        String text = "Spaceship Invaders";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        //SHADOW
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(text, x + 5, y + 5);

        //MAIN COLOR
        graphics2D.setColor(Color.white);
        graphics2D.drawString(text, x, y);

        //JACOB IMAGE
        x = gamePanel.screenWidth / 2 - gamePanel.tileSize;
        y += gamePanel.tileSize * 2;
        graphics2D.drawImage(gamePanel.player.idle[0], x - gamePanel.tileSize * 2, y - gamePanel.tileSize * 3, gamePanel.tileSize * 6, gamePanel.tileSize * 6, null);

        //MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        if (hasEntered == true)
        {
            text = "RESUME";
            x = getXforCenteredText(text);
            y += gamePanel.tileSize * 3;
            graphics2D.drawString(text, x, y);
            if (commandNumber == -1)
            {
                graphics2D.drawString(">", x - gamePanel.tileSize, y);
            }
        }

        text = "NEW GAME";
        x = getXforCenteredText(text);
        if (hasEntered)
        {
            y += gamePanel.tileSize;
        }
        else
        {
            y+= gamePanel.tileSize*4;
        }
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

        int y = gamePanel.screenHeight/2 + gamePanel.tileSize;

        graphics2D.drawString(text, x, y);
    }

    public int getXforCenteredText(String text)
    {
        int length = (int)graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;
        return x;
    }
}
