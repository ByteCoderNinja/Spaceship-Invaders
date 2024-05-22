package entity;

public class EntityFactory
{
    public static Entity[][] createNPC()
    {
        return new Entity[10][10];
    }
    public static Entity[][] createObj()
    {
        return new Entity[10][12];
    }
    public static Entity[][] createEnemy()
    {
        return new Entity[10][50];
    }
    public static Entity[][] createBoss()
    {
        return new Entity[10][20];
    }
}
