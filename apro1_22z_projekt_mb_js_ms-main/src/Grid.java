import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Grid is generated from the seed. It's a 2D array of Square objects with properties read 
 * from the CSV. It allows for straightforward and easy-to-read way to access properties of 
 * different squares on the grid.
 */
public class Grid implements ActionListener {
    /**
     * "grid" object will be used as a reference for the game to know how to react to player's input
     */
    static Square[][] grid = null;

    JButton[][] squares;

    ImageIcon lightBulb = new ImageIcon("Plansze/Ikonka.png");
    
    int SIDE; /** For denoting grid side length */

    /** Colors - for convenience */
    Color emptyField = new Color(194, 197, 185);
    Color litField = new Color(180, 29, 29);
    Color wallColor = new Color(49, 34, 34);

    /**
     * The constructor initializes not only the Square objects themselves, but also JButtons 
     * corresponding with them, reason being: it gives easy access to both from the class itself;
     * @param seed
     */
    public Grid(Seed seed) {
        /**
         * "." means an empty field, "z" means a wall with limit 4 (regular wall)
         * integers indicate the limit of bulbs adjascent to the wall
         */
        SIDE = seed.getDimensions();
        grid = new Square[SIDE][SIDE];
        squares = new JButton[SIDE][SIDE];
        int i = 0;
        int j = 0;
        for (String[] line : seed.gridSeed) {
            j = 0;
            for (String c : line) {
                if (c.equals(".")) {
                    grid[i][j] = new Square();

                    squares[i][j] = new JButton();
                    squares[i][j].setBackground(emptyField);
                    squares[i][j].addActionListener(this);
                } else if (c.equals("z")) {
                    grid[i][j] = new Square(4);

                    squares[i][j] = new JButton();
                    squares[i][j].setText("");
                    squares[i][j].setFocusable(false);
                    squares[i][j].setBackground(wallColor);
                } else {
                    grid[i][j] = new Square(Integer.parseInt(c));

                    squares[i][j] = new JButton();
                    squares[i][j].setFocusable(false);
                    squares[i][j].setText(c);
                    squares[i][j].setBackground(wallColor);
                    squares[i][j].setForeground(Color.cyan);
                }
                j++;
            }
            i++;
        }
    }



    /**
     * Checks adjascent squares for the number of bulbs that can be placed near them 
     * if a limit is reached - blocks that possibility returning "false"
     * @param x
     * @param y
     * @return true if none has reached its limit
     * @return false when contrary
     */
    public boolean isInLimit(int y, int x) {
        if (x == 0) { /** if the square is on the left edge */
            if (y == 0) {
                if (grid[y + 1][x].bulbPlacement > 0 && grid[y][x + 1].bulbPlacement > 0) {
                    grid[y+1][x].bulbPlacement--;
                    grid[y][x+1].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            } else if (y == SIDE-1) {
                if (grid[y - 1][x].bulbPlacement > 0 && grid[y][x + 1].bulbPlacement > 0) {
                    grid[y-1][x].bulbPlacement--;
                    grid[y][x+1].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                if (grid[y - 1][x].bulbPlacement > 0 && grid[y + 1][x].bulbPlacement > 0 && grid[y][x + 1].bulbPlacement > 0) {
                    grid[y-1][x].bulbPlacement--;
                    grid[y+1][x].bulbPlacement--;
                    grid[y][x+1].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            }
        } else if (x == SIDE-1) { /** square on right edge */
            if (y == 0) {
                if (grid[y][x - 1].bulbPlacement > 0 && grid[y + 1][x].bulbPlacement > 0) {
                    grid[y][x-1].bulbPlacement--;
                    grid[y+1][x].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            } else if (y == SIDE-1) {
                if (grid[y - 1][x].bulbPlacement > 0 && grid[y][x - 1].bulbPlacement > 0) {
                    grid[y][x-1].bulbPlacement--;
                    grid[y-1][x].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                if (grid[y - 1][x].bulbPlacement > 0 && grid[y + 1][x].bulbPlacement > 0 && grid[y][x - 1].bulbPlacement > 0) {
                    grid[y-1][x].bulbPlacement--;
                    grid[y+1][x].bulbPlacement--;
                    grid[y][x-1].bulbPlacement--;
                    grid[y][x].bulbPresent = true;
                    return true;
                } else {
                    return false;
                }
            }
        } else if (y == 0) { /** square on top side, but not in a corner */
            if (grid[y][x - 1].bulbPlacement > 0 && grid[y][x + 1].bulbPlacement > 0 && grid[y + 1][x].bulbPlacement > 0) {
                grid[y][x-1].bulbPlacement--;
                grid[y][x+1].bulbPlacement--;
                grid[y+1][x].bulbPlacement--;
                grid[y][x].bulbPresent = true;
                return true;
            } else { return false; }
        } else if (y == SIDE-1) { /** square on bottom side, but not in a corner */
            if (grid[y][x - 1].bulbPlacement > 0 && grid[y][x + 1].bulbPlacement > 0 && grid[y - 1][x].bulbPlacement > 0) {
                grid[y][x-1].bulbPlacement--;
                grid[y][x+1].bulbPlacement--;
                grid[y-1][x].bulbPlacement--;
                grid[y][x].bulbPresent = true;
                return true;
            } else { return false; }
        } else {  /** square inside the grid, away from walls */
            if(grid[y][x+1].bulbPlacement > 0 && grid[y][x-1].bulbPlacement > 0
            && grid[y+1][x].bulbPlacement > 0 && grid[y-1][x].bulbPlacement > 0){
                grid[y][x+1].bulbPlacement--;
                grid[y][x-1].bulbPlacement--;
                grid[y+1][x].bulbPlacement--;
                grid[y-1][x].bulbPlacement--;
                grid[y][x].bulbPresent = true;
                return true;
            }else { return false; }
        }
    }

    /**
     * Method that checks whether a row or column of player-indicated square already contains a bulb
     * that's not behind a wall
     * If so, it evaluates to "false" blocking the possibility 
     * @param y
     * @param x
     * @return
     */
    public boolean isPossible(int y, int x){
        int indexX = x + 1;
        int indexY = y + 1;
        check : while(indexX < SIDE){
                if(grid[y][indexX].wall) { break check; } /** it stops on a wall, as what's behind is irrelevant */
                if(grid[y][indexX].bulbPresent) { return false; }
                indexX++;
        }
        indexX = x - 1;
        check : while(indexX >= 0){
            if(grid[y][indexX].wall) { break check; }
            if(grid[y][indexX].bulbPresent) { return false; }
            indexX--;
        }
        check : while(indexY < SIDE){
            if(grid[indexY][x].wall) { break check; }
            if(grid[indexY][x].wall) { return false; }
            indexY++;
        }
        indexY = y - 1;
        check : while(indexY >= 0){
            if(grid[indexY][x].wall) { break check; }
            if(grid[indexY][x].wall) { return false; }
            indexY--;
        }
        return true;
    }

    /**
     * Method illuminates all squares in a row and column, that are not behind a wall.
     * @param y
     * @param x
     */
    public void illuminate(int y, int x){
        grid[y][x].illuminated++;
        squares[y][x].setBackground(litField);
        squares[y][x].setIcon(lightBulb);
        int indexX = x + 1;
        int indexY = y + 1;
        color : while(indexX < SIDE){
            if(grid[y][indexX].wall) { break color; } /** again, what's behind is irrelevant */
            else{
                grid[y][indexX].illuminated++;
                squares[y][indexX].setBackground(litField);
                indexX++;
            }

        }
        indexX = x - 1;
        color : while(indexX >= 0){
            if(grid[y][indexX].wall) { break color; }
            else{
                grid[y][indexX].illuminated++;
                squares[y][indexX].setBackground(litField);
                indexX--;
            }
        }
        color : while(indexY < SIDE){
            if(grid[indexY][x].wall) { break color; }
            else{
                grid[indexY][x].illuminated++;
                squares[indexY][x].setBackground(litField);
                indexY++;
            }
        }
        indexY = y - 1;
        color : while(indexY >= 0){
            if(grid[indexY][x].wall) { break color; }
            else{
                grid[indexY][x].illuminated++;
                squares[indexY][x].setBackground(litField);
                indexY--;
            }
        }
    }

    /**
     * To evaluate to "true" all non-wall squares must be illuminated, all walls' limit must be met,
     * except for those without a limit specified by a  String
     * @return
     */
    public boolean winCheck(){
        for(int i = 0; i < SIDE; i ++){
            for(int j = 0; j < SIDE; j++){
                if(grid[i][j].wall && grid[i][j].bulbPlacement != 0 && !squares[i][j].getText().equals("")){ /** emppty String */
                    return false;                                                                       /** indicates a "regular" wall */
                }else if(!grid[i][j].wall && grid[i][j].illuminated == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * When player removes a light bulb, the limits of adjascent walls must be again increased by 1
     * @param y
     * @param x
     */
    public void resetLimit(int y, int x){
        if (x == 0) {
            if (y == 0) {
                grid[y+1][x].bulbPlacement++;
                grid[y][x+1].bulbPlacement++;
            } else if (y == SIDE-1) {
                    grid[y-1][x].bulbPlacement++;
                    grid[y][x+1].bulbPlacement++;
            } else {
                    grid[y-1][x].bulbPlacement++;
                    grid[y+1][x].bulbPlacement++;
                    grid[y][x+1].bulbPlacement++;
            }
        } else if (x == SIDE-1) {
            if (y == 0) {
                    grid[y][x-1].bulbPlacement++;
                    grid[y+1][x].bulbPlacement++;
            } else if (y == SIDE-1) {
                    grid[y][x-1].bulbPlacement++;
                    grid[y-1][x].bulbPlacement++;
            } else {
                    grid[y-1][x].bulbPlacement++;
                    grid[y+1][x].bulbPlacement++;
                    grid[y][x-1].bulbPlacement++;
            }
        } else if (y == 0) {
                grid[y][x-1].bulbPlacement++;
                grid[y][x+1].bulbPlacement++;
                grid[y+1][x].bulbPlacement++;
        } else if (y == SIDE-1) {
                grid[y][x-1].bulbPlacement++;
                grid[y][x+1].bulbPlacement++;
                grid[y-1][x].bulbPlacement++;
        } else {
                grid[y][x+1].bulbPlacement++;
                grid[y][x-1].bulbPlacement++;
                grid[y+1][x].bulbPlacement++;
                grid[y-1][x].bulbPlacement++;
        }
    }

    /**
     * Resets the lighting on squares that lost their light bulb. Mechanism the same as in illuminate()
     * @param y
     * @param x
     */
    public void switchOff(int y, int x){
        grid[y][x].bulbPresent = false;
        grid[y][x].illuminated--;
        squares[y][x].setIcon(null);
        squares[y][x].setBackground(emptyField);
        squares[y][x].setText("");
        resetLimit(y,x);

        int indexX = x + 1;
        int indexY = y + 1;
        color : while(indexX < SIDE){
            if(grid[y][indexX].wall) { break color; }
            else if(grid[y][indexX].illuminated == 1){
                grid[y][indexX].illuminated--;
                squares[y][indexX].setBackground(emptyField);
                indexX++;
            }else {
                grid[y][indexX].illuminated--;
                indexX++;
            }

        }
        indexX = x - 1;
        color : while(indexX >= 0){
            if(grid[y][indexX].wall) { break color; }
            else if(grid[y][indexX].illuminated == 1){
                grid[y][indexX].illuminated--;
                squares[y][indexX].setBackground(emptyField);
                indexX--;
            }else {
                grid[y][indexX].illuminated--;
                indexX--;
            }
        }
        color : while(indexY < SIDE){
            if(grid[indexY][x].wall) { break color; }
            else if(grid[indexY][x].illuminated == 1){
                grid[indexY][x].illuminated--;
                squares[indexY][x].setBackground(emptyField);
                indexY++;
            }else {
                grid[indexY][x].illuminated--;
                indexY++;
            }
        }
        indexY = y - 1;
        color : while(indexY >= 0){
            if(grid[indexY][x].wall) { break color; }
            else if(grid[indexY][x].illuminated == 1){
                grid[indexY][x].illuminated--;
                squares[indexY][x].setBackground(emptyField);
                indexY--;
            }else{
                grid[indexY][x].illuminated--;
                indexY--;
            }
        }
    }

    public void gameOver(){
        //TODO
        // Popup, który obwieści, że wygrałeś/aś i blokuje możliwości ruchu etc.
    }

    /**
     * Depending on the clicked button's corresponding Square object, button either places or removes a bulb.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton temp = (JButton) e.getSource();
        int xPos;
        int yPos;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (temp == squares[i][j]) {
                    if(!grid[i][j].bulbPresent && grid[i][j].illuminated == 0){
                        if (isInLimit(i, j) && isPossible(i,j)) {
                            illuminate(i,j);
                            if(winCheck()){
                                /** PO SKOŃCZENIU METODY
                                 * USUŃ KOMENTA I USUŃ "System.out..."
                                 **/
                                //gameOver();
                                System.out.println("Wygrana!");
                                System.out.println("Slay, Queen");
                            }
                        }
                    } else{
                        if(grid[i][j].bulbPresent) {
                            switchOff(i,j);
                        }
                    }
                }
            }
        }
    }
}