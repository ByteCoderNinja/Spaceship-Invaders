package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        switch(code)
        {
            case KeyEvent.VK_UP : upPressed = true;
            break;
            case KeyEvent.VK_DOWN : downPressed = true;
            break;
            case KeyEvent.VK_LEFT : leftPressed = true;
            break;
            case KeyEvent.VK_RIGHT : rightPressed = true;
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
        }
    }
}
