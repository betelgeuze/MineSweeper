package model;

public class explosiveTiles extends Tiles{
    private static Boolean isExplosive = true;

    @Override
    public boolean isExplosive() {
        return isExplosive;
    }
}
