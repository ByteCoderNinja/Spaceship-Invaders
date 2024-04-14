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

    }
    public void setNPC()
    {
        gamePanel.npc[0] = new NPC_Alien(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize*2;
        gamePanel.npc[0].worldY = gamePanel.tileSize*6;
    }

}
