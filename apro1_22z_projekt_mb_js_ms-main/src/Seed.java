import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Seed class generates a reference for creating a grid, which stores all the
 * necessary information about the current game. It reads a CSV in which every value corresponds with a type of
 * Square, and generates them accordingly in an array resembling the CSV's layout.
 */
public class Seed {
    static ArrayList<String[]> gridSeed = new ArrayList<>();
    public Seed(String filename) {
        BufferedReader br = null;
        try {
            String line;
            String[] squareSeeds;
            br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                squareSeeds = line.split(";");
                gridSeed.add(squareSeeds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Important for making the code universal for many boards of different sizes */
    public static int getDimensions(){
        return gridSeed.size();
    }

    /** Helper method for development - not necessary anymore
     *
     *
     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : gridSeed) {
            for (String s : row) {
                sb.append(s + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    */
}

