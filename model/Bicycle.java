/*
 * TCSS 305 - Autumn 2015
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * Bicycle class states the behavior and properties of the bicycle.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class Bicycle extends AbstractVehicle {
    
    /**Number of turns to skip when dead. */
    public static final int MY_DEATH_TIME = 25;
      
    /**
     * Constructor for a bicycle, taking a x, y, direction.
     * 
     * @param theX theX coordinate.
     * @param theY theY coordinate.
     * @param theDir theDir in which the bike is located.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, MY_DEATH_TIME);
    }
  
    /**
     * Lets the bike object know if it can move onto the given terrain from the
     * light color passed in.
     * 
     * @param theTerrain theTerrain passed in.
     * @param theLight theLight color passed in.
     * @return boolean whether or not the bike can move onto the given terrain
     * from the light color.
     */
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        final boolean result;
        
        if (theLight == Light.GREEN) {
            if (theTerrain != Terrain.GRASS && theTerrain != Terrain.WALL) {
                result = true;
            } else {
                result = false;
            } 
        } else {
            if (theTerrain != Terrain.GRASS && theTerrain != Terrain.WALL 
                            && theTerrain != Terrain.LIGHT) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }
    
    /**
     * Tells the bike what direction to go from the light color and terrain from its neighbors.
     * 
     * @param theNeighbors theNeighbors is the map of the neighboring terrain.
     * @return Direction in which the bike must go.
     */
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction dir = getDirection();
        Direction newDirection = dir;
        final Terrain terrainAhead = theNeighbors.get(dir);
        final Terrain terrainLeft = theNeighbors.get(dir.left());
        final Terrain terrainRight = theNeighbors.get(dir.right());
        final Terrain terrainReverse = theNeighbors.get(dir.reverse());
        
        switch (terrainAhead) {     
            case GRASS:
            case WALL:
                //If left is available, go left--
                if (canGoLeftorRight(terrainLeft)) {
                    newDirection = dir.left();  
                    break;
                }

                //If right is available, go right--
                if (canGoLeftorRight(terrainRight)) {
                    newDirection = dir.right();   
                    break;
                }
                   
                //If neither left or right, turn around--
                if (canGoLeftorRight(terrainReverse)) {
                    newDirection = dir.reverse();  
                    break;
                }
                break;
            case STREET:
                if (terrainLeft == Terrain.TRAIL) {
                    newDirection = dir.left();
                    break;
                }
                
                if (terrainRight == Terrain.TRAIL) {
                    newDirection = dir.right();
                    break;
                }
                break;
            default:
                break;
        }
        return newDirection;
    }
    
    /**
     * Helper method to determine if bike can turn.
     * 
     * @param theTerrain theTerrain the bike is at.
     * @return true/false
     */
    private boolean canGoLeftorRight(final Terrain theTerrain) {
        boolean ret = false;
        // CC was over 10-- helps CC < 10
        if (theTerrain != Terrain.GRASS && theTerrain != Terrain.WALL) {
            ret = true;
        }
        
        return ret;
    }
}