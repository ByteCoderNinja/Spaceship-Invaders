package main;

import entity.NPC_Alien;
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
        gamePanel.obj[0] = new OBJ_Door(gamePanel);
        gamePanel.obj[0].worldX = gamePanel.tileSize*8;
        gamePanel.obj[0].worldY = gamePanel.tileSize*15;

        gamePanel.obj[1] = new OBJ_Door(gamePanel);
        gamePanel.obj[1].worldX = gamePanel.tileSize*9;
        gamePanel.obj[1].worldY = gamePanel.tileSize*15;
    }
    public void setNPC()
    {
        gamePanel.npc[0] = new NPC_Alien(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize*8;
        gamePanel.npc[0].worldY = gamePanel.tileSize*12;

        gamePanel.npc[1] = new NPC_Alien(gamePanel);
        gamePanel.npc[1].worldX = gamePanel.tileSize*8;
        gamePanel.npc[1].worldY = gamePanel.tileSize*13;

    }

}
