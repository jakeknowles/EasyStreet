/*
 * TCSS 305 - Autumn 2015
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * Car class states the behavior and properties of the car.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class Car extends AbstractVehicle {

    /**Number of turns to skip when dead. */
    private static final int MY_DEATH_TIME = 5;

    /**
     * Constructor for a car, taking a x, y, direction.
     * 
     * @param theX theX coordinate.
     * @param theY theY coordinate.
     * @param theDir theDir in which the car is located.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, MY_DEATH_TIME);
    }
       
    /**
     * Lets the car know what terrain and what light color there is to
     * be able to move.
     * 
     * @param theTerrain theTerrain passed in.
     * @param theLight theLight color passed in.
     * @return boolean whether or not the car can move onto the given terrain
     * from the light color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        
        switch (theTerrain) {
            case WALL:
            case GRASS:
            case TRAIL:
                result = false;
                break;
            case LIGHT:
                if (theLight == Light.RED) {
                    result = false;
                }
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * Tells the car what direction to go from the light color and terrain from 
     * its neighbors.
     * 
     * @param theNeighbors theNeighbors is the map of the neighboring terrain.
     * @return Direction in which the car must go.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        
        final Direction dir = getDirection();
        
        Direction newDirection = dir;
        final Terrain terrainAhead = theNeighbors.get(dir);
        final Terrain terrainLeft = theNeighbors.get(dir.left());
        final Terrain terrainRight = theNeighbors.get(dir.right());
        
        switch (terrainAhead) {
            case WALL:
            case GRASS:
            case TRAIL:
                if (terrainLeft == Terrain.STREET || terrainLeft == Terrain.LIGHT) {
                    newDirection = dir.left();
                    break;
                } else {
                    if (terrainRight == Terrain.STREET || terrainLeft == Terrain.LIGHT) {
                        newDirection = dir.right();
                        break;
                    } else {
                        newDirection = dir.reverse();
                        break;
                    }
                }
            default:
                break;
        }
        return newDirection;
    }
}