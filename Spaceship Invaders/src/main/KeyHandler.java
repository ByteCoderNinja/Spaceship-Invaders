package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    GamePanel gamePanel;

    public boolean upPressed, downPressed, leftPressed, rightPressed, attackSpace, fireF;

    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        //TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState)
        {
            if (code == KeyEvent.VK_UP)
            {
                --gamePanel.ui.commandNumber;
                if (gamePanel.ui.commandNumber < 0)
                {
                    gamePanel.ui.commandNumber = 2;
                }
            }
            if (code == KeyEvent.VK_DOWN)
            {
                ++gamePanel.ui.commandNumber;
                if (gamePanel.ui.commandNumber > 2)
                {
                    gamePanel.ui.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER)
            {
                if (gamePanel.ui.commandNumber == 0)
                {
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 1)
                {

                }
                if (gamePanel.ui.commandNumber == 2)
                {
                    System.exit(0);
                }
            }
        }

        //PLAY STATE
        if (gamePanel.gameState == gamePanel.playState)
        {
            switch (code)
            {
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_SPACE:
                    attackSpace = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    gamePanel.gameState = gamePanel.pauseState;
                    break;
                case KeyEvent.VK_F:
                    fireF = true;
                    break;
                case KeyEvent.VK_T:
                    checkDrawTime = (checkDrawTime == false) ? true : false;
            }
        }
        //PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
        {
            if (code == KeyEvent.VK_ESCAPE)
            {
                gamePanel.gameState = gamePanel.playState;
            }
        }
        //DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.dialogueState)
        {
            if (code == KeyEvent.VK_ENTER)
            {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        switch(code)
        {
            case KeyEvent.VK_UP : upPressed = false;
                break;
            case KeyEvent.VK_DOWN : downPressed = false;
                break;
            case KeyEvent.VK_LEFT : leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT : rightPressed = false;
                break;
            case KeyEvent.VK_SPACE : attackSpace = false;
                break;
            case KeyEvent.VK_ESCAPE : attackSpace = false;
                break;
            case KeyEvent.VK_F : fireF = false;
        }
    }
}
