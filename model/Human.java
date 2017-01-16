/*
 * TCSS 305 - Autumn 2015
 * Assignment 3 - Easy Street
 */
package model;

import java.util.Map;

/**
 * Human class states the behavior and properties of the human.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class Human extends AbstractVehicle {

    /**Number of turns to skip when dead. */
    private static final int MY_DEATH_TIME = 45;
    
    /**Terrain type. */
    private final Terrain myOriginalTerrain;
    
    /**
     * Constructor for a human, taking a x, y, direction, and terrain.
     * 
     * @param theX theX coordinate.
     * @param theY theY coordinate.
     * @param theDir theDir in which the bike is located.
     * @param theTerrain theTerrain is what terrain is passed in.
     */
    public Human(final int theX, final int theY, final Direction theDir, 
                 final Terrain theTerrain) {
        super(theX, theY, theDir, MY_DEATH_TIME);
        myOriginalTerrain = theTerrain;
    }
       
    /**
     * Lets the human know what terrain and what light color there is to
     * be able to move.
     * 
     * @param theTerrain theTerrain passed in.
     * @param theLight theLight color passed in.
     * @return boolean whether or not the human can move onto the given terrain
     * from the light color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = true;
        
        if (theTerrain != myOriginalTerrain) {
            result = false;
        }
 
        return result;
    }
 
    /**
     * Tells the human what direction to go from the light color and terrain from 
     * its neighbors.
     * 
     * @param theNeighbors theNeighbors is the map of the neighboring terrain.
     * @return Direction in which the human must go.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDirection = Direction.random();
        Terrain newTerrain = theNeighbors.get(newDirection);
        
        switch (myOriginalTerrain) {
            case STREET:
            case LIGHT:
                while (newTerrain != Terrain.STREET && newTerrain != Terrain.LIGHT) {
                    newDirection = Direction.random();
                    newTerrain = theNeighbors.get(newDirection);
                }
                break;
            case WALL:
            case GRASS:
            case TRAIL:
                while (!(newTerrain.equals(myOriginalTerrain))) {
                    newDirection = Direction.random();
                    newTerrain = theNeighbors.get(newDirection);
                }
                break;
            default:
                break;
        }
        return newDirection;
    }
}