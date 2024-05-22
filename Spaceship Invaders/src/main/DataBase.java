package main;

import main.GamePanel;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String databaseName;
    GamePanel gamepanel;

    public DataBase(String databaseName, GamePanel gamepanel) {
        this.databaseName = databaseName;
        this.gamepanel = gamepanel;
    }

    public void createPlayerTable(String tableName, ArrayList<String> fields) {
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE if NOT EXISTS " + tableName + " (";
            for (int i = 0; i < fields.size(); i += 2) {
                sql += fields.get(i) + " " + fields.get(i + 1) + " NOT NULL, ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ");";
            stmt.execute(sql);
            stmt.close();
            conn.close();
            System.out.println("Tabelul " + tableName + " a fost creat cu succes.");
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Eroare: Eroare la crearea tabelului.");
            e.printStackTrace();
        }
    }

    public void insertPlayerTable(String tableName, ArrayList<String> fields, ArrayList<String> values) {
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "INSERT OR IGNORE INTO " + tableName + " (";
            for (int i = 0; i < fields.size(); ++i) {
                sql += "\"" + fields.get(i) + "\"" + ", ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ") VALUES (";
            for (int i = 0; i < values.size(); ++i) {
                sql += "\"" + values.get(i) + "\"" + ", ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += "); UPDATE " + tableName + " SET ";
            for (int i = 0; i < fields.size(); ++i) {
                sql += "\"" + fields.get(i) + "\"" + "=" + "\"" + values.get(i) + "\"" + ", ";
            }

            sql = sql.substring(0, sql.length() - 2);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            System.out.println("Update la tabelul " + tableName + " cu succes.");
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Eroare: Eroare la inserarea in tabel.");
            e.printStackTrace();
        }
    }

    public void selectPlayerTable(String tableName) {
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");

            // Variables
            int playerX = 0;
            int playerY = 0;
            int currentMap = 1;
            String direction = "";
            int life = 0;
            int hasKey = 0;
            String monsters = "";
            String npc = "";
            String objects = "";

            while (rs.next()) {
                playerX = Integer.parseInt(rs.getString("PLAYERPOSX"));
                playerY = Integer.parseInt(rs.getString("PLAYERPOSY"));
                currentMap = rs.getInt("CURRENTMAP");
                direction = rs.getString("DIRECTION");
                life = rs.getInt("LIFE");
                hasKey = rs.getInt("HASKEY");
                monsters = rs.getString("MONSTERS");
                npc = rs.getString("NPC");
                objects = rs.getString("OBJECTS");
//                music = rs.getInt("MUSIC");
//                fx = rs.getInt("FX");
            }
            gamepanel.player.worldX = playerX;
            gamepanel.player.worldY = playerY;
            gamepanel.currentMap = currentMap;
            gamepanel.player.direction = direction;
            gamepanel.player.life = life;
            gamepanel.player.hasKey = hasKey;

            // Parse
            String[] arrayMonsters = monsters.split(", ");
            int k = 0;
            for (int i = 0; i < gamepanel.maxMap; ++i) {
                for (int j = 0; j < gamepanel.space_troop[i].length; ++j) {
                    if (gamepanel.space_troop[i][j] != null) {
                        gamepanel.space_troop[i][j].worldX = Integer.parseInt(arrayMonsters[k]);
                        gamepanel.space_troop[i][j].worldY = Integer.parseInt(arrayMonsters[k + 1]);
                        gamepanel.space_troop[i][j].life = Integer.parseInt(arrayMonsters[k + 2]);
                    }
                    k += 3;
                }
            }
            String[] arrayNPC = npc.split(", ");
            k = 0;
            for (int i = 0; i < gamepanel.maxMap; ++i) {
                for (int j = 0; j < gamepanel.npc[i].length; ++j) {
                    if (gamepanel.npc[i][j] != null) {
                        gamepanel.npc[i][j].worldX = Integer.parseInt(arrayNPC[k]);
                        gamepanel.npc[i][j].worldY = Integer.parseInt(arrayNPC[k + 1]);
                    }
                    k += 2;
                }
            }
            String[] arrayObjects = objects.split(", ");
            k = 0;
            for (int i = 0; i < gamepanel.maxMap; ++i) {
                for (int j = 0; j < gamepanel.obj[i].length; ++j) {
                    if (gamepanel.obj[i][j] != null) {
                        gamepanel.obj[i][j].worldX = Integer.parseInt(arrayObjects[k]);
                        gamepanel.obj[i][j].worldY = Integer.parseInt(arrayObjects[k + 1]);
                    }
                    k += 2;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Eroare: Eroare la extragerea datelor din tabel.");
            e.printStackTrace();
        }
    }

    public void loadData() {

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Extragere date din tabel
        selectPlayerTable(tableName);
    }

    public void saveData() {

        // Entities
        String monsters = "";
        for (int i = 0; i < gamepanel.maxMap; ++i) {
            for (int j = 0; j < gamepanel.space_troop[i].length; ++j) {
                if (gamepanel.space_troop[i][j] != null) {
                    monsters += gamepanel.space_troop[i][j].worldX + ", " + gamepanel.space_troop[i][j].worldY + ", " + gamepanel.space_troop[i][j].life + ", ";
                } else {
                    monsters += -1 + ", " + -1 + ", " + -1 + ", ";
                }
            }
        }
        if (!monsters.isEmpty()) {
            monsters = monsters.substring(0, monsters.length() - 2);
        }

        String NPC = "";
        for (int i = 0; i < gamepanel.maxMap; ++i) {
            for (int j = 0; j < gamepanel.npc[i].length; ++j) {
                if (gamepanel.npc[i][j] != null) {
                    NPC += gamepanel.npc[i][j].worldX + ", " + gamepanel.npc[i][j].worldY + ", ";
                } else {
                    NPC += -1 + ", " + -1 + ", ";
                }
            }
        }
        if (!NPC.isEmpty()) {
            NPC = NPC.substring(0, NPC.length() - 2);
        }

        String objects = "";
        for (int i = 0; i < gamepanel.maxMap; ++i) {
            for (int j = 0; j < gamepanel.obj[i].length; ++j) {
                if (gamepanel.obj[i][j] != null) {
                    objects += gamepanel.obj[i][j].worldX + ", " + gamepanel.obj[i][j].worldY + ", ";
                } else {
                    objects += -1 + ", " + -1 + ", ";
                }
            }
        }
        if (!objects.isEmpty()) {
            objects = objects.substring(0, objects.length() - 2);
        }

        // Player Settings
        String tableName = "PLAYER_SETTINGS";

        // Creare tabel daca nu exista
        ArrayList<String> fields = new ArrayList<>();
        fields.add("PLAYERPOSX");
        fields.add("TEXT");
        fields.add("PLAYERPOSY");
        fields.add("TEXT");
        fields.add("CURRENTMAP");
        fields.add("TEXT");
        fields.add("DIRECTION");
        fields.add("TEXT");
        fields.add("LIFE");
        fields.add("TEXT");
        fields.add("HASKEY");
        fields.add("TEXT");
        fields.add("MONSTERS");
        fields.add("TEXT");
        fields.add("NPC");
        fields.add("TEXT");
        fields.add("OBJECTS");
        fields.add("TEXT");
        createPlayerTable(tableName, fields);

        // Adaugare date in tabel
        fields.clear();
        fields.add("PLAYERPOSX"); // 1
        fields.add("PLAYERPOSY"); // 2
        fields.add("CURRENTMAP"); // 3
        fields.add("DIRECTION"); // 4
        fields.add("LIFE"); // 5
        fields.add("HASKEY");
        fields.add("MONSTERS"); // 7
        fields.add("NPC"); // 8
        fields.add("OBJECTS"); // 9
        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(gamepanel.player.worldX)); // 1
        values.add(String.valueOf(gamepanel.player.worldY)); // 2
        values.add(String.valueOf(gamepanel.currentMap)); // 3
        values.add(gamepanel.player.direction); // 4
        values.add(String.valueOf(gamepanel.player.life)); // 5
        values.add(String.valueOf(gamepanel.player.hasKey));
        values.add(monsters); // 7
        values.add(NPC); // 8
        values.add(objects); // 9
        insertPlayerTable(tableName, fields, values);
    }
}