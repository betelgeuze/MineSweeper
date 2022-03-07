package model;

public class explosiveTiles extends Tiles{
    private Boolean isExplosive = true;

    @Override
    public boolean isExplosive() {
        return isExplosive;
    }
}
