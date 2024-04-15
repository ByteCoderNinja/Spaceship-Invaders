package main;

import java.awt.*;

public class EventHandler
{
    GamePanel gamePanel;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxWorldColumns][gamePanel.maxWorldRows];

        int column = 0;
        int row = 0;
        while (column < gamePanel.maxWorldColumns && row < gamePanel.maxWorldRows)
        {
            eventRect[column][row] = new EventRect();
            eventRect[column][row].x = 23;
            eventRect[column][row].y = 23;
            eventRect[column][row].width = 2;
            eventRect[column][row].height = 2;
            eventRect[column][row].eventRectDefaultX = eventRect[column][row].x;
            eventRect[column][row].eventRectDefaultY = eventRect[column][row].y;

            ++column;
            if (column == gamePanel.maxWorldColumns)
            {
                column = 0;
                ++row;
            }
        }
    }

    public void checkEvent()
    {
        //CHECK IF THE PLAYER IS MORE THAN 1 TILE AWAY FROM THE LAST EVENT
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gamePanel.tileSize)
        {
            canTouchEvent = true;
        }

        if (canTouchEvent == true)
        {
            if (hit(16,26,"walk_down") == true) {damagePit(16, 26, gamePanel.dialogueState);}
            if (hit(16,25,"walk_up") == true) {damagePit(16, 26, gamePanel.dialogueState);}
        }
    }


    public boolean hit(int column, int row, String reqDirection)
    {
        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
        eventRect[column][row].x = column * gamePanel.tileSize + eventRect[column][row].x;
        eventRect[column][row].y = row * gamePanel.tileSize + eventRect[column][row].y;

        if (gamePanel.player.solidArea.intersects(eventRect[column][row]) && eventRect[column][row].eventDone == false)
        {
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
            {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect[column][row].x = eventRect[column][row].eventRectDefaultX;
        eventRect[column][row].y = eventRect[column][row].eventRectDefaultY;

        return hit;
    }

    private void damagePit(int column, int row, int gameState)
    {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into pit!";
        gamePanel.player.life -= 1;

//        eventRect[column][row].eventDone = true;
        canTouchEvent = false;
    }

}
