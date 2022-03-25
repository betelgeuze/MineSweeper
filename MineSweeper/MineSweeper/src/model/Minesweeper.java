package model;

import java.util.ArrayList;
import java.util.Collections;

public class Minesweeper extends AbstractMineSweeper {

    private int rows, cols;
    ArrayList<AbstractTile> tile = new ArrayList<>();
    AbstractTile[][] tiles;

    private Boolean firstTile = true;
    private Boolean firstTileRuled = true;
    private int flagCount = 0;

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
        if (level == Difficulty.EASY) {
            startNewGame(8, 8, 10);
        }

        if (level == Difficulty.MEDIUM) {
            startNewGame(16, 16, 40);
        }

        if (level == Difficulty.HARD) {
            startNewGame(16, 30, 99);
        }

    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {

        rows = row;
        cols = col;

        Tiles[][] tileNew = new Tiles[rows][cols];

        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                count++;
                if (count <= explosionCount){
                    tileNew[i][j] = generateExplosiveTile();
                    System.out.println(count);}
                else{
                    tileNew[i][j] = generateEmptyTile();}
            }
        }

        setWorld(tileNew);

        mapping();

        setWorld(tiles);

        viewNotifier.notifyNewGame(rows, cols);


    }

    @Override
    public void setWorld(AbstractTile[][] world) {

        rows = world.length;
        cols = world[0].length;

        this.tiles = world;

        flagCount = 0;
        firstTile = true;
        firstTileRuled = true;

        for (int i=0;i<rows;i++) {
            for (int j = 0; j < cols; j++){
                tiles[i][j].initTile();
            }
        }

    }


    @Override
    public Tiles getTile(int x, int y) {
        int a, b;

        if (x < 0)
            a = 0;
        else if (x >= cols)
            a = x - 1;
        else
            a = x;

        if (y < 0)
            b = 0;
        else if (y >= rows)
            b = y - 1;
        else
            b = y;

        return (Tiles) tiles[b][a];
    }


    @Override
    public void flag(int x, int y) {
        Tiles t = getTile(y, x);

        if (!t.isOpened()) {
            viewNotifier.notifyFlagCountChanged(flagCount);
            flagCount = flagCount + 1;
            viewNotifier.notifyFlagCountChanged(flagCount);
            viewNotifier.notifyFlagged(x, y);

            t.flag();
        }
    }


    @Override
    public void unflag(int x, int y) {
        Tiles t;
        t = getTile(y, x);


        if (!t.isOpened()) {
            viewNotifier.notifyFlagCountChanged(flagCount);

            flagCount = flagCount - 1;
            viewNotifier.notifyFlagCountChanged(flagCount);
            viewNotifier.notifyUnflagged(x, y);

            t.unflag();
        }


    }


    @Override
    public void toggleFlag(int x, int y) {
        Tiles t;
        t = this.getTile(y, x);

        if (!t.isOpened()) {
            if (t.isFlagged())
                unflag(x, y);
            else
                flag(x, y);
        }


    }


    @Override
    public void open(int x, int y) {

        if (!tiles[x][y].isOpened()) {
            if (!tiles[x][y].isExplosive()) {
                if (countExplosive(x, y) > 0){
                    if (tiles[x][y].isFlagged()) {
                        unflag(x, y);
                    }
                    tiles[x][y].open();
                    viewNotifier.notifyOpened(x, y, countExplosive(x, y));
                    firstTile = false;

                }
                else {
                    tiles[x][y].open();
                    viewNotifier.notifyOpened(x, y, countExplosive(x,y));
                    firstTile = false;
                    for(int i=Math.max(0,x-1);i<=Math.min(x+1,rows-1);i++) {
                        for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, cols - 1); j++) {
                            open(i,j);
                        }
                    }
                }
            }
            else if (firstTileRuled && firstTile && tiles[x][y].isExplosive()) {

                while (tiles[x][y].isExplosive()) {
                    mapping();
                }

                setWorld(tiles);


                if (countExplosive(x, y) > 0){
                    if (tiles[x][y].isFlagged()) {
                        unflag(x, y);
                    }
                    tiles[x][y].open();
                    viewNotifier.notifyOpened(x, y, countExplosive(x, y));
                    firstTile = false;

                }
                else {
                    tiles[x][y].open();
                    viewNotifier.notifyOpened(x, y, 0);
                    firstTile = false;
                    for(int i=Math.max(0,x-1);i<=Math.min(x+1,rows-1);i++) {
                        for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, cols - 1); j++) {
                            open(i,j);
                        }
                    }
                }
            }

            else if (firstTileRuled && !firstTile && tiles[x][y].isExplosive()) {
                viewNotifier.notifyExploded(x, y);
                viewNotifier.notifyGameLost();
                firstTile = false;
            } else if (!firstTileRuled && firstTile && tiles[x][y].isExplosive()) {
                viewNotifier.notifyExploded(x, y);
                viewNotifier.notifyGameLost();
                firstTile = false;
            }
            else {
                viewNotifier.notifyExploded(x, y);
                viewNotifier.notifyGameLost();
                firstTile = false;
            }
        }
    }




    @Override
    public void deactivateFirstTileRule() {
        firstTileRuled =  false;
    }

    @Override
    public Tiles generateEmptyTile() {
        Tiles t = new normalTiles();
        return t;
    }

    @Override
    public Tiles generateExplosiveTile() {
        Tiles t = new explosiveTiles();
        return t;
    }


    public int countExplosive(int x,int y){
        Tiles t,tmpT;
        int c,r,count=0;

        for(int i=Math.max(0,x-1);i<=Math.min(x+1,rows-1);i++)
        {
            for(int j=Math.max(0,y-1);j<=Math.min(y+1,cols-1);j++)
            {
                if (tiles[i][j].isExplosive())
                {
                    count=count+1;
                }
            }
        }
        return count;

    }



    public void mapping(){
        for (int a=0; a<rows; a++){
            for (int b=0; b<cols; b++){
                tile.add(tiles[a][b]);
            }
        }

        Collections.shuffle(tile);

        for (int i=0; i<rows;i++){
            for (int j=0;j<cols;j++){
                tiles[i][j] = tile.get(i*cols+j);
            }
        }
    }



}


