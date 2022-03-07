package model;

public class Tiles extends AbstractTile{

    private Boolean open = false;
    private Boolean isOpened = false;
    private Boolean isFlagged = false;
    private Boolean isExplosive;


    @Override
    public boolean open() {
        return open;
    }

    @Override
    public void flag() {
        isFlagged = true;
    }

    @Override
    public void unflag() {
        isFlagged = false;
    }

    @Override
    public boolean isFlagged() {
        return isFlagged;
    }

    @Override
    public boolean isExplosive() {
        return isExplosive;
    }

    @Override
    public boolean isOpened() {
        return isOpened;
    }
}
