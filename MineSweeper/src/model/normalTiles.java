package model;

public class normalTiles extends Tiles{
    private static Boolean isExplosive = false;


    @Override
    public boolean isExplosive() {
        return isExplosive;
    }
}
