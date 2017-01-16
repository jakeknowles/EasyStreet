/*
 * TCSS 305 - Autumn 2015
 * Assignment 3 - Easy Street
 */

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Atv class states the behavior and properties of the Atv.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class Atv extends AbstractVehicle  {

    /**Number of turns to skip when dead. */
    public static final int MY_DEATH_TIME = 15;
    
    /**
     * Constructor for an Atv, taking a x, y, direction.
     * 
     * @param theX theX coordinate.
     * @param theY theY coordinate.
     * @param theDir theDir in which the Atv is located.
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, MY_DEATH_TIME);
    }

    /**
     * Lets the Atv know what terrain and what light color there is to
     * be able to move.
     * 
     * @param theTerrain theTerrain passed in.
     * @param theLight theLight color passed in.
     * @return boolean whether or not the Atv can move onto the given terrain
     * from the light color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        
        if (theTerrain == Terrain.WALL) {
            result = false;
        }

        return result;
    }

    /**
     * Tells the Atv what direction to go from the light color and terrain from 
     * its neighbors.
     * 
     * @param theNeighbors theNeighbors is the map of the neighboring terrain.
     * @return Direction in which the car must go.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Random rand = new Random();
        final Direction dir = getDirection();
                
        final Direction[] directionArray = {dir, dir.left(), dir.right()};
        final List<Direction> possibleDirectionArray = new ArrayList<Direction>();
        
        for (int i = 0; i < directionArray.length; i++) {
            possibleDirectionArray.add(directionArray[i]);
        }
        
        final int randomIndex = rand.nextInt(possibleDirectionArray.size());
        return possibleDirectionArray.get(randomIndex);
    }
}