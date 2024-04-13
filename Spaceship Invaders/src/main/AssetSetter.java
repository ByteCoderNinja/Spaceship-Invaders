package main;

import object.OBJ_AutoDestroyButton;
import object.OBJ_Door;
import object.OBJ_key;

public class AssetSetter
{
    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public void setObject()
    {
        gamePanel.obj[0] = new OBJ_key();
        gamePanel.obj[0].worldX = 1 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 17 * gamePanel.tileSize;

        gamePanel.obj[1] = new OBJ_key();
        gamePanel.obj[1].worldX = 1 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 16 * gamePanel.tileSize;

        gamePanel.obj[2] = new OBJ_Door();
        gamePanel.obj[2].worldX = 9 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 19 * gamePanel.tileSize;

        gamePanel.obj[3] = new OBJ_Door();
        gamePanel.obj[3].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 19 * gamePanel.tileSize;

        gamePanel.obj[4] = new OBJ_AutoDestroyButton();
        gamePanel.obj[4].worldX = 38 * gamePanel.tileSize;
        gamePanel.obj[4].worldY = 1 * gamePanel.tileSize;
    }
}
