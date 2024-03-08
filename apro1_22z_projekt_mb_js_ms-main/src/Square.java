public class Square {
    boolean wall;
    int illuminated;
    boolean bulbPresent = false;
    int bulbPlacement;

    /**
     * Empty constructor constructs empty squares. Emtpy squares, of course,
     * have a bulb limit of 4 - the highest possible
     */
    public Square() {
        wall = false;
        illuminated = 0;
        bulbPlacement = 4;
    }

    /**
     * This constructor constructs walls - @param specifies
     * number of bulbs that can be placed near it
     *
     * @param bulbPlacement
     */
    public Square(int bulbPlacement) {
        wall = true;
        illuminated = 1; /** walls iluminated by default, as winCheck() requires all squares to be lit */
        this.bulbPlacement = bulbPlacement;
    }
}
