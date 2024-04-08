package main;

import entity.Entity;

public class CollisionChecker
{
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftColumn = entityLeftWorldX/gamePanel.tileSize;
        int entityRightColumn = entityRightWorldX/gamePanel.tileSize;
        int entityTopRow = entityTopWorldY/ gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction)
        {
            case "walk_up":
                entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightColumn][entityTopRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "walk_down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftColumn][entityBottomRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "walk_left":
                entityLeftColumn = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityLeftColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
            case "walk_right":
                entityRightColumn = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[entityRightColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[entityRightColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
