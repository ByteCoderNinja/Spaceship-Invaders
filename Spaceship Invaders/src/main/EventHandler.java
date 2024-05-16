package main;

import java.awt.*;

public class EventHandler
{
    GamePanel gamePanel;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        eventRect = new EventRect[gamePanel.maxMap][gamePanel.maxWorldColumns][gamePanel.maxWorldRows];

        int map = 0;
        int column = 0;
        int row = 0;
        while (map < gamePanel.maxMap && column < gamePanel.maxWorldColumns && row < gamePanel.maxWorldRows)
        {
            eventRect[map][column][row] = new EventRect();
            eventRect[map][column][row].x = 23;
            eventRect[map][column][row].y = 23;
            eventRect[map][column][row].width = 2;
            eventRect[map][column][row].height = 2;
            eventRect[map][column][row].eventRectDefaultX = eventRect[map][column][row].x;
            eventRect[map][column][row].eventRectDefaultY = eventRect[map][column][row].y;

            ++column;
            if (column == gamePanel.maxWorldColumns)
            {
                column = 0;
                ++row;

                if (row == gamePanel.maxWorldRows)
                {
                    row = 0;
                    ++map;
                }
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
            if (hit(0,16,25,"walk_down") == true) {teleport(1, 15, 5);}
            if (hit(0,17, 25, "walk_down") == true) {teleport(1, 15, 5);}
            if (hit(1,16, 7, "walk_up") == true) {teleport(0, 15, 23);}
            if (hit(1,17, 7, "walk_up") == true) {teleport(0, 15, 23);}

        }
    }

    private void teleport(int map, int col, int row)
    {
        gamePanel.currentMap = map;
        gamePanel.player.worldX = gamePanel.tileSize*col;
        gamePanel.player.worldY = gamePanel.tileSize*row;
        previousEventX = gamePanel.player.worldX;
        previousEventY = gamePanel.player.worldY;
        canTouchEvent = false;
    }


    public boolean hit(int map, int column, int row, String reqDirection)
    {
        boolean hit = false;

        if (map == gamePanel.currentMap)
        {
            gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
            gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
            eventRect[map][column][row].x = column * gamePanel.tileSize + eventRect[map][column][row].x;
            eventRect[map][column][row].y = row * gamePanel.tileSize + eventRect[map][column][row].y;

            if (gamePanel.player.solidArea.intersects(eventRect[map][column][row]) && eventRect[map][column][row].eventDone == false)
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
            eventRect[map][column][row].x = eventRect[map][column][row].eventRectDefaultX;
            eventRect[map][column][row].y = eventRect[map][column][row].eventRectDefaultY;
        }

        return hit;
    }

    private void damagePit(int gameState)
    {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into pit!";
        gamePanel.player.life -= 1;

//        eventRect[column][row].eventDone = true;
        canTouchEvent = false;
    }

}
