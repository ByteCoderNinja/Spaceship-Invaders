package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager
{
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;

        tile = new Tile[15];
        mapTileNum = new int [gamePanel.maxWorldColumns][gamePanel.maxWorldRows];

        getTileImage();
        loadMap("maps/map02.txt");
    }

    public void getTileImage()
    {
        try
        {
            BufferedImage img1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/scifitiles-sheet.png"));
            BufferedImage img2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sprite-sheet.png"));

            tile[0] = new Tile(); //Simple platform
            tile[0].image = img1.getSubimage(191, 31,33, 33);

            tile[1] = new Tile(); //Walls
            tile[1].image = img1.getSubimage(0, 80, 31, 48);
            tile[1].collision = true;

            tile[2] = new Tile(); //Platform with orange square in center
            tile[2].image = img1.getSubimage(256, 0, 63, 63);

            tile[3] = new Tile(); //Space Photo
            tile[3].image = img2.getSubimage(880, 520, 798, 639);
            tile[3].collision = true;

            tile[4] = new Tile(); //Door Photo
            tile[4].image = img1.getSubimage(64, 132, 32, 16);

            tile[5] = new Tile(); //Final level floor
            tile[5].image = img1.getSubimage(128, 96, 30, 30);

            tile[6] = new Tile(); //Computer
            tile[6].image = img2.getSubimage(880,0, 96, 65);
            tile[6].collision = true;

            tile[7] = new Tile(); //Obstacle for hiding
            tile[7].image = img1.getSubimage(96,144, 31, 47);
            tile[7].collision = true;

            tile[8] = new Tile(); //Oxygen tube
            tile[8].image = img2.getSubimage(1371,1, 418, 510);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath)
    {
        try
        {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldColumns && row < gamePanel.maxWorldRows)
            {
                String line = br.readLine();

                while (col < gamePanel.maxWorldColumns)
                {
                    String[] numbers = line.split(" |\\t");


                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    ++col;
                }
                if (col == gamePanel.maxWorldColumns)
                {
                    col = 0;
                    ++row;
                }
            }
            br.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D)
    {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldColumns && worldRow < gamePanel.maxWorldRows)
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY)
            {
                graphics2D.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            ++worldCol;

            if (worldCol == gamePanel.maxWorldColumns)
            {
                worldCol = 0;
                ++worldRow;
            }
        }
    }
}
