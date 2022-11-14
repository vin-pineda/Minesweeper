package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameBoard {
    private int[][] board;
    private boolean firstClick = true;
    public static boolean isGameLost = false;

    private Texture emptyTile;
    private Texture flagTile;
    private Texture questionTile;
    private Texture bombTile;
    private Texture emptyFloor;
    private Texture bomb;
    private Texture oneTile, twoTile, threeTile, fourTile, fiveTile, sixTile, sevenTile, eightTile;

    public static final int BOMB = 9, EMPTYTILE = 10, FLAGGEDTILE = 20, QUESTIONTILE = 30;

    public GameBoard() {
        board = new int[16][30];
        initEmptyBoard();

        emptyTile = new Texture("biggerTile.jpg");
        bomb = new Texture("bomb.jpg");
        oneTile = new Texture("oneTile.jpg");
        twoTile = new Texture("twoTile.jpg");
        threeTile = new Texture("threeTile.jpg");
        fourTile = new Texture("fourTile.jpg");
        fiveTile = new Texture("fiveTile.jpg");
        sixTile = new Texture("sixTile.jpg");
        sevenTile = new Texture("sevenTile.jpg");
        eightTile = new Texture("eightTile.jpg");
        flagTile = new Texture("flagTile.jpg");

    }

    public boolean isValidLoc(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    public void handleClick(int x, int y) {

        //change windows x y coordinate to 2d array loc
        int rowClicked = (y - 10) / 25;
        int colClicked = (x - 10) / 25;


        if (isValidLoc(rowClicked, colClicked)) {
            if (firstClick) {
                firstClick = false;
                placeBombs(rowClicked, colClicked);
                initBoardNumbers();



            }
            startSpace(rowClicked, colClicked);
            board[rowClicked][colClicked] = board[rowClicked][colClicked] % 10;
            if (board[rowClicked][colClicked] == BOMB)
                isGameLost = true;


        }

    }

    public void handleRightClick(int x, int y) {
        int rowClicked = (y - 10) / 25;
        int colClicked = (x - 10) / 25;

        if (isValidLoc(rowClicked, colClicked) && board[rowClicked][colClicked] != 0) {
            board[rowClicked][colClicked] = FLAGGEDTILE;
        }
        else if (board[rowClicked][colClicked] == FLAGGEDTILE)
            board[rowClicked][colClicked] %= 20;
    }

    private void placeBombs(int rowClicked, int colClicked) {
        ArrayList<Location> locs = new ArrayList<>(getNeighbors(rowClicked, colClicked));
        int bombCount = 0;
        while (bombCount < 99) {
            int randomRow = (int) (Math.random() * board.length);
            int randomCol = (int) (Math.random() * board[0].length);

            for (int i = 0; i < locs.size(); i++) {
                board[locs.get(i).getRow()][ locs.get(i).getCol()] = 0;
            }

            if (randomRow != rowClicked && randomCol != colClicked) {
                if (board[randomRow][randomCol] == EMPTYTILE) {
                    board[randomRow][randomCol] = BOMB + 10;
                    bombCount++;
                }
            }
        }
        System.out.println("bombs placed: " + bombCount);
    }


    public void draw(SpriteBatch spriteBatch) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                //if we have emptyTile
                if (board[row][col] >= EMPTYTILE && board[row][col] < FLAGGEDTILE) {
                    spriteBatch.draw(emptyTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == BOMB) {
                    spriteBatch.draw(bomb, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 1) {
                    spriteBatch.draw(oneTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 2) {
                    spriteBatch.draw(twoTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 3) {
                    spriteBatch.draw(threeTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 4) {
                    spriteBatch.draw(fourTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 5) {
                    spriteBatch.draw(fiveTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 6) {
                    spriteBatch.draw(sixTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 7) {
                    spriteBatch.draw(sevenTile, 10 + (col * 25), (600 - 35) - (row * 25));
                } else if (board[row][col] == 8) {
                    spriteBatch.draw(eightTile, 10 + (col * 25), (600 - 35) - (row * 25));
                }
                else if (board[row][col] == FLAGGEDTILE) {
                    spriteBatch.draw(flagTile, 10 + (col * 25), (600 - 35) - (row * 25));
                }

            }
        }
    }

    private void initEmptyBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 10;
            }
        }
    }

    private ArrayList<Location> getNeighbors(int row, int col) {
        ArrayList<Location> ans = new ArrayList<>();

        //check all possible locations, if they are valid, add them to the arraylist

        if (isValidLoc(row, col) == false)
            return ans;
        else {
            if (isValidLoc(row - 1, col)) {
                ans.add(new Location(row - 1, col));
            }
            if (isValidLoc(row - 1, col + 1)) {
                ans.add(new Location(row - 1, col + 1));
            }
            if (isValidLoc(row, col + 1))
                ans.add(new Location(row, col + 1));
            if (isValidLoc(row + 1, col + 1))
                ans.add(new Location(row + 1, col + 1));
            if (isValidLoc(row + 1, col))
                ans.add(new Location(row + 1, col));
            if (isValidLoc(row + 1, col - 1))
                ans.add(new Location(row + 1, col - 1));
            if (isValidLoc(row, col - 1))
                ans.add(new Location(row, col - 1));
            if (isValidLoc(row - 1, col - 1))
                ans.add(new Location(row - 1, col - 1));

        }
        return ans;
    }

    private int bombsAroundLoc(int row, int col) {
        ArrayList<Location> locs = getNeighbors(row, col);

        int count = 0;
        for (Location temp : locs) {
            if (board[temp.getRow()][temp.getCol()] % 10 == BOMB) {
                count++;
            }
        }

        return count;
    }

    private void initBoardNumbers() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] % 10 != BOMB) {
                    int numOfbombs = bombsAroundLoc(row, col);
                    board[row][col] = numOfbombs + 10;
                }
            }
        }
    }

    private void startSpace(int row, int col) {
        //get neighbors, set the tile to emppty by modding it by ten, if neighboring tile is empty, call method again
        ArrayList<Location> locs = new ArrayList<>(getNeighbors(row, col));
        if (board[row][col] != 0) {
            board[row][col] = board[row][col] % 10;
            if (board[row][col] == 0) {
                for (int i = 0; i < locs.size(); i++) {
                    startSpace(locs.get(i).getRow(), locs.get(i).getCol());
                }
            }


        }

    }

}