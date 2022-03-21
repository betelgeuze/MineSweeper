package model;

import notifier.ITileStateNotifier;

public abstract class Tiles extends AbstractTile{

    private Boolean isOpened = false;
    private Boolean isFlagged = false;



    public void initTile() {
        isOpened = false;
    }

    @Override
    public boolean open() {
        isOpened = true;
        isFlagged = false;
        return true;
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
