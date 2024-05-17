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
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*25;
        ++i;
        gamePanel.obj[1][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[1][i].worldX = gamePanel.tileSize*26;
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*26;
        ++i;
        gamePanel.obj[2][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*9;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*7;
        ++i;
        gamePanel.obj[2][i] = new OBJ_Door(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*10;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*7;
        ++i;
        gamePanel.obj[0][i] = new OBJ_key(gamePanel);
        gamePanel.obj[0][i].worldX = gamePanel.tileSize*8;
        gamePanel.obj[0][i].worldY = gamePanel.tileSize*13;
        ++i;
        gamePanel.obj[1][i] = new OBJ_key(gamePanel);
        gamePanel.obj[1][i].worldX = gamePanel.tileSize*15;
        gamePanel.obj[1][i].worldY = gamePanel.tileSize*26;
        ++i;
        gamePanel.obj[2][i] = new OBJ_key(gamePanel);
        gamePanel.obj[2][i].worldX = gamePanel.tileSize*25;
        gamePanel.obj[2][i].worldY = gamePanel.tileSize*8;
        ++i;
    }
    public void setNPC()
    {
        int i = 0;
        gamePanel.npc[0][i] = new NPC_Alien(gamePanel);
        gamePanel.npc[0][i].worldX = gamePanel.tileSize*8;
        gamePanel.npc[0][i].worldY = gamePanel.tileSize*12;
        ++i;
    }

    public void setMarineTroop()
    {
        int i = 0;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*20;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*9;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*20;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*11;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*20;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*13;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*20;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*15;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*20;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*17;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*18;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*9;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*18;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*11;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*18;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*13;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*18;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*15;
        ++i;
        gamePanel.space_troop[2][i] = new space_troop(gamePanel);
        gamePanel.space_troop[2][i].worldX = gamePanel.tileSize*18;
        gamePanel.space_troop[2][i].worldY = gamePanel.tileSize*17;
        ++i;
    }

}
