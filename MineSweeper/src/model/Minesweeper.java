package model;

import java.util.ArrayList;
import java.util.Collections;

public class Minesweeper extends AbstractMineSweeper{

    private int rows,cols;
    ArrayList<AbstractTile> tiles =new ArrayList<>();

//    int flag;

    @Override
    public int getWidth() {

        return cols;
    }

    @Override
    public int getHeight() {
        return rows;
    }

    @Override
    public void startNewGame(Difficulty level) {
        if (level == Difficulty.EASY)
        {   startNewGame(8,8,10);}
        if (level == Difficulty.MEDIUM)
        {   startNewGame(16,16,40);}
        if (level == Difficulty.HARD)
        {   startNewGame(16,30,99);}

    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {

        this.rows = row;
        this.cols = col;


        int emptyTile, explosionTile;

        explosionTile = explosionCount;

        emptyTile = row*col-explosionTile;



        for (int i=0; i<emptyTile; ++i)
        {
            tiles.add(this.generateEmptyTile());
        }

        for (int i=0; i<explosionTile; ++i)
        {
            tiles.add(this.generateExplosiveTile());
        }

        Collections.shuffle(tiles);

    }

    @Override
    public void setWorld(AbstractTile[][] world) {

        int count = 0;

        for (int i = 0; i < rows; ++i) {
            for (int j=0; j<cols; ++j){
                world[i][j] = tiles.get(count);
                count = count+1;
            }
        }
    }


    @Override
    public AbstractTile getTile(int x, int y) {
        AbstractTile tile = tiles.get(cols*(x-1)+y);
        return tile;
    }


    @Override
    public void toggleFlag(int x, int y) {
        AbstractTile t;
        t = getTile(x,y);

        if (t.isFlagged()){
            t.unflag();
        }

        if (!t.isFlagged()){
            t.flag();
        }

    }


    @Override
    public void open(int x, int y) {
        AbstractTile t;
        t = getTile(x,y);

        t.isOpened();

    }

    @Override
    public void flag(int x, int y) {

    }

    @Override
    public void unflag(int x, int y) {

    }

    @Override
    public void deactivateFirstTileRule() {

    }

    @Override
    public AbstractTile generateEmptyTile() {
        return null;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        return null;
    }
}
