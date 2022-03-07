package model;

public abstract class Tiles extends AbstractTile{

    private Boolean open = false;
    private Boolean isOpened = false;
    private Boolean isFlagged = false;


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
    public abstract boolean isExplosive();

    @Override
    public boolean isOpened() {
        return isOpened;
    }
}
