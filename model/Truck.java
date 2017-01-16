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
 * Truck class states the behavior and properties of the truck.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class Truck extends AbstractVehicle {
    
    /**Number of turns to skip when dead. */
    private static final int MY_DEATH_TIME = 0;

    
    /**
     * Constructor for a truck, taking a x, y, direction.
     * 
     * @param theX theX coordinate.
     * @param theY theY coordinate.
     * @param theDir theDir in which the bike is located.
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, MY_DEATH_TIME);
    }

   
    /**
     * Tells the truck what direction to go from the light color and terrain from 
     * its neighbors.
     * 
     * @param theNeighbors theNeighbors is the map of the neighboring terrain.
     * @return Direction in which the truck must go.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Random rand = new Random();
        final Direction dir = getDirection();
                
        final Direction[] directionArray = {dir, dir.left(), dir.right()};
        final List<Direction> possibleDirectionArray = new ArrayList<Direction>();
        
        for (int i = 0; i < directionArray.length; i++) {
            final Terrain newTerrain = theNeighbors.get(directionArray[i]);
            if (newTerrain == Terrain.STREET || newTerrain == Terrain.LIGHT) {
                possibleDirectionArray.add(directionArray[i]);
            }
        }
        if (possibleDirectionArray.isEmpty()) {
            return dir.reverse();
        } else {
            final int randomIndex = rand.nextInt(possibleDirectionArray.size());
            return possibleDirectionArray.get(randomIndex);
        }
    }

    
    /**
     * Lets the truck know what terrain and what light color there is to
     * be able to move.
     * 
     * @param theTerrain theTerrain passed in.
     * @param theLight theLight color passed in.
     * @return boolean whether or not the truck can move onto the given terrain
     * from the light color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT;
    }
}