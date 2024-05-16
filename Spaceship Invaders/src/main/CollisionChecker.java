package main;

import entity.Entity;

import java.util.Objects;

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

        if (entity.left_right == 0 && Objects.equals(entity.name, "Bullet"))
        {
            entityRightColumn = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
            tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityTopRow];
            tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityBottomRow];
            if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
        }
        else if (entity.left_right == 1 && Objects.equals(entity.name, "Bullet"))
        {
            entityLeftColumn = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
            tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityTopRow];
            tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityBottomRow];
            if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
            {
                entity.collisionOn = true;
            }
        }


        switch (entity.direction)
        {
            case "walk_up":
                entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityTopRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                if (gamePanel.tileM.tile[tileNum1].index == 9 || gamePanel.tileM.tile[tileNum2].index == 9)
                {
                    if (entity.invincible == false)
                    {
                        --entity.life;
                        entity.invincible = true;
                        gamePanel.playSE(3);
                    }
                }
                break;
            case "walk_down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityBottomRow];
                tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                if (gamePanel.tileM.tile[tileNum1].index == 9 || gamePanel.tileM.tile[tileNum2].index == 9)
                {
                    if (entity.invincible == false)
                    {
                        --entity.life;
                        entity.invincible = true;
                        gamePanel.playSE(3);
                    }
                }
                break;
            case "walk_left":
                entityLeftColumn = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityLeftColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                if (gamePanel.tileM.tile[tileNum1].index == 9 || gamePanel.tileM.tile[tileNum2].index == 9)
                {
                    if (entity.invincible == false)
                    {
                        --entity.life;
                        entity.invincible = true;
                        gamePanel.playSE(3);
                    }
                }
                break;
            case "walk_right":
                entityRightColumn = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
                tileNum1 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityTopRow];
                tileNum2 = gamePanel.tileM.mapTileNum[gamePanel.currentMap][entityRightColumn][entityBottomRow];
                if (gamePanel.tileM.tile[tileNum1].collision == true || gamePanel.tileM.tile[tileNum2].collision == true)
                {
                    entity.collisionOn = true;
                }
                if (gamePanel.tileM.tile[tileNum1].index == 9 || gamePanel.tileM.tile[tileNum2].index == 9)
                {
                    if (entity.invincible == false)
                    {
                        --entity.life;
                        entity.invincible = true;
                        gamePanel.playSE(3);
                    }
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player)
    {
        int index = 1000;

        for (int i = 0; i < gamePanel.obj[gamePanel.currentMap].length; ++i)
        {
            if (gamePanel.obj[gamePanel.currentMap][i] != null)
            {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = gamePanel.obj[gamePanel.currentMap][i].worldX + gamePanel.obj[gamePanel.currentMap][i].solidArea.x;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = gamePanel.obj[gamePanel.currentMap][i].worldY + gamePanel.obj[gamePanel.currentMap][i].solidArea.y;

                switch (entity.direction)
                {
                    case "walk_up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "walk_down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "walk_left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "walk_right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(gamePanel.obj[gamePanel.currentMap][i].solidArea))
                {
                    if (gamePanel.obj[gamePanel.currentMap][i].collision == true)
                    {
                        entity.collisionOn = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.x = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultX;
                gamePanel.obj[gamePanel.currentMap][i].solidArea.y = gamePanel.obj[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //ENEMY
    public int checkEntity(Entity entity, Entity[][] target)
    {
        int index = 1000;

        for (int i = 0; i < target[0].length; ++i)
        {
            if (target[gamePanel.currentMap][i] != null)
            {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].solidArea.x;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].solidArea.y;

                switch (entity.direction)
                {
                    case "walk_up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "walk_down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "walk_left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "walk_right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[gamePanel.currentMap][i].solidArea))
                {
                    if (target[gamePanel.currentMap][i] != entity)
                    {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].solidAreaDefaultX;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity)
    {
        boolean contactPlayer = false;

        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction)
        {
            case "walk_up":
                entity.solidArea.y -= entity.speed;
                break;
            case "walk_down":
                entity.solidArea.y += entity.speed;
                break;
            case "walk_left":
                entity.solidArea.x -= entity.speed;
                break;
            case "walk_right":
                entity.solidArea.x += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gamePanel.player.solidArea))
        {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
