package main;

import enemy.space_troop;
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
        gamePanel.npc[0].worldX = gamePanel.tileSize*8;
        gamePanel.npc[0].worldY = gamePanel.tileSize*12;
    }

    public void setMarineTroop()
    {
        gamePanel.space_troop[0] = new space_troop(gamePanel);
        gamePanel.space_troop[0].worldX = gamePanel.tileSize*8;
        gamePanel.space_troop[0].worldY = gamePanel.tileSize*13;

        gamePanel.space_troop[1] = new space_troop(gamePanel);
        gamePanel.space_troop[1].worldX = gamePanel.tileSize*8;
        gamePanel.space_troop[1].worldY = gamePanel.tileSize*14;
    }

}
