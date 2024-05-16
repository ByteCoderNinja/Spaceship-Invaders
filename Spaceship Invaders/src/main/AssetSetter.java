package main;

import enemy.space_troop;
import entity.NPC_Alien;
import object.OBJ_AutoDestroyButton;
import object.OBJ_Bullet;
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
        int i = 0;
        gamePanel.obj[0][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[0][i].worldX = gamePanel.tileSize*16;
        gamePanel.obj[0][i].worldY = gamePanel.tileSize*25;
        ++i;
        gamePanel.obj[0][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[0][i].worldX = gamePanel.tileSize*17;
        gamePanel.obj[0][i].worldY = gamePanel.tileSize*25;
        ++i;
        gamePanel.obj[1][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[1][i].worldX = gamePanel.tileSize*26;
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*43;
        ++i;
        gamePanel.obj[1][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[1][i].worldX = gamePanel.tileSize*26;
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*44;
        ++i;
        gamePanel.obj[2][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*28;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*26;
        ++i;
        gamePanel.obj[2][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*29;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*26;
        ++i;
        gamePanel.obj[0][i] = new OBJ_key(gamePanel);
        gamePanel.obj[0][i].worldX = gamePanel.tileSize*11;
        gamePanel.obj[0][i].worldY = gamePanel.tileSize*11;
        ++i;
        gamePanel.obj[1][i] = new OBJ_key(gamePanel);
        gamePanel.obj[1][i].worldX = gamePanel.tileSize*25;
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*27;
        ++i;
        gamePanel.obj[2][i] = new OBJ_key(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*31;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*31;
        ++i;
    }
    public void setNPC()
    {
        gamePanel.npc[0][0] = new NPC_Alien(gamePanel);
        gamePanel.npc[0][0].worldX = gamePanel.tileSize*8;
        gamePanel.npc[0][0].worldY = gamePanel.tileSize*12;
    }

    public void setMarineTroop()
    {
        int i = 0;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*30;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*30;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*31;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*30;
        ++i;
    }

}
