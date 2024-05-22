package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    GamePanel gamePanel;

    public boolean upPressed, downPressed, leftPressed, rightPressed, attackSpace;

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
                if (UI.hasEntered)
                {
                    if (gamePanel.ui.commandNumber < -1)
                    {
                        gamePanel.ui.commandNumber = 2;
                    }
                }
                else
                {
                    if (gamePanel.ui.commandNumber < 0)
                    {
                        gamePanel.ui.commandNumber = 2;
                    }
                }
            }
            if (code == KeyEvent.VK_DOWN)
            {
                ++gamePanel.ui.commandNumber;
                if (gamePanel.ui.commandNumber > 2)
                {
                    gamePanel.ui.commandNumber = (UI.hasEntered) ? -1 : 0;
                }
            }
            if (code == KeyEvent.VK_ENTER)
            {
                if (gamePanel.ui.commandNumber == -1)
                {
                    gamePanel.stopMusic();
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 0)
                {
                    UI.hasEntered = true;
                    gamePanel.ui.commandNumber = -1;
                    gamePanel.stopMusic();
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                    gamePanel.player.setDefaultValues();
                    gamePanel.setupGame();
                    gamePanel.currentMap = 0;
                }
                if (gamePanel.ui.commandNumber == 1)
                {
                    gamePanel.dataBase.loadData();
                    UI.hasEntered = true;
                    gamePanel.ui.commandNumber = -1;
                    gamePanel.stopMusic();
                    gamePanel.gameState = gamePanel.playState;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNumber == 2)
                {
                    gamePanel.dataBase.saveData();
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
                    gamePanel.stopMusic();
                    gamePanel.playMusic(4);
                    gamePanel.gameState = gamePanel.titleState;
                    break;
                case KeyEvent.VK_P:
                    gamePanel.gameState = gamePanel.pauseState;
                    break;
                case KeyEvent.VK_T:
                    checkDrawTime = (checkDrawTime == false) ? true : false;
            }
        }

        if (code == KeyEvent.VK_R)
        {
            switch (gamePanel.currentMap)
            {
                case 0: gamePanel.tileM.loadMap("maps/map1.txt", 0);
                case 1: gamePanel.tileM.loadMap("maps/map2.txt", 1);
                case 2: gamePanel.tileM.loadMap("maps/map3.txt", 2);
                case 3: gamePanel.tileM.loadMap("maps/map4.txt", 3);
            }
        }

        //PAUSE STATE
        else if (gamePanel.gameState == gamePanel.pauseState)
        {
            if (code == KeyEvent.VK_P)
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
        //GAME OVER STATE
        else if (gamePanel.gameState == gamePanel.gameOverState)
        {
            gameOverState(code);
        }
        //GAME WON STATE
        else if (gamePanel.gameState == gamePanel.gameWonState)
        {
            gameWonState(code);
        }
    }

    private void gameWonState(int code)
    {
        if (code  == KeyEvent.VK_UP)
        {
            --gamePanel.ui.commandNumber;
            if (gamePanel.ui.commandNumber < 0)
            {
                gamePanel.ui.commandNumber = 1;
            }
        }
        if (code  == KeyEvent.VK_DOWN)
        {
            ++gamePanel.ui.commandNumber;
            if (gamePanel.ui.commandNumber > 1)
            {
                gamePanel.ui.commandNumber = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER)
        {
            if (gamePanel.ui.commandNumber == 0)
            {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.ui.commandNumber = 0;
                gamePanel.ui.hasEntered = false;
                gamePanel.restart();
            }
            else if (gamePanel.ui.commandNumber == 1)
            {
                System.exit(0);
            }
        }
    }

    private void gameOverState(int code)
    {
        if (code  == KeyEvent.VK_UP)
        {
            --gamePanel.ui.commandNumber;
            if (gamePanel.ui.commandNumber < 0)
            {
                gamePanel.ui.commandNumber = 1;
            }
        }
        if (code  == KeyEvent.VK_DOWN)
        {
            ++gamePanel.ui.commandNumber;
            if (gamePanel.ui.commandNumber > 1)
            {
                gamePanel.ui.commandNumber = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER)
        {
            if (gamePanel.ui.commandNumber == 0)
            {
                gamePanel.gameState = gamePanel.playState;
                gamePanel.retry();
            }
            else if (gamePanel.ui.commandNumber == 1)
            {
                gamePanel.gameState = gamePanel.titleState;
                gamePanel.ui.commandNumber = 0;
                gamePanel.ui.hasEntered = false;
                gamePanel.restart();
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
        }
    }
}
