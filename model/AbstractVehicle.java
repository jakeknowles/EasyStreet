/*
 * TCSS 305 - Autumn 2015
 * Assignment 3 - Easy Street
 */

package model;

import java.util.Map;

/**
 * AbstractVehicle is an abstract class for the 5 vehicles.
 * (Bicycle, Human, Truck, Car, and ATV)
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public abstract class AbstractVehicle implements Vehicle {
    
    /** The current x coordinate. */
    private int myX;
    
    /** The current y coordinate. */
    private int myY;
    
    /** The current direction. */
    private Direction myDirection;
    
    /** The original x coordinate. */
    private final int myOriginalX;
    
    /** The original y coordinate. */
    private final int myOriginalY;
    
    /** The original direction. */
    private final Direction myOriginalDirection;
     
    /** The death time of this vehicle. */
    private final int myDeathTime;
    
    /** The boolean value of isAlive. */
    private boolean myIsAlive = true;
    
    /** The waiting time of this vehicle until it's done coming back to life. */
    private int myWaitingTime;
      
    /**
    * Constructs an abstract vehicle object with the passed in x, y, and direction. 
    * 
    * @param theX theX is the x coordinate.
    * @param theY theY is the y coordinate.
    * @param theDir theDir is the direction.
    * @param theDeathTime theDeathTime is the deathTime.
    */
    public AbstractVehicle(final int theX,
                           final int theY,
                           final Direction theDir,
                           final int theDeathTime) {
        
        myOriginalX = theX;
        myOriginalY = theY;
        myOriginalDirection = theDir;
        myX = myOriginalX;
        myY = myOriginalY;
        myDirection = myOriginalDirection;
        myDeathTime = theDeathTime;
    }
    
    /**
    * Lets the given object know what terrain and what light color there is to
    * be able to move.
    * 
    * @param theTerrain theTerrain passed in.
    * @param theLight theLight color passed in.
    * @return boolean whether or not the object can move onto the given terrain
    * from the light color.
    */
    public abstract boolean canPass(Terrain theTerrain, Light theLight);
    
    /**
    * Tells the object what direction to go from the light color and terrain from 
    * its neighbors.
    * 
    * @param theNeighbors theNeighbors is the map of the neighboring terrain.
    * @return Direction in which the object must go.
    */
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);

    /**
    * Returns the given vehicles death time and when it can come back and move.
    * 
    * @return the number of updates.
    */
    public int getDeathTime() {
        return myDeathTime;
    }
   
    /**
    * Called when this Vehicle collides with the specified other Vehicle.
    * 
    * @param theOther The other object.
    */
    public void collide(final Vehicle theOther) {
        if (isAlive()) {
            if (theOther.isAlive()) {
                if (myDeathTime <= theOther.getDeathTime()) {
                    myIsAlive = true;
                } else {
                    myIsAlive = false;
                    myWaitingTime = myDeathTime;
                }
            } else {
                myIsAlive = true;
            }
        }
    }
    
    /**
    * Returns the file name of the image for this Vehicle object.
    * "whatever.gif" or "whatever_dead.gif".
    * 
    * @return the file name.
    */
    public String getImageFileName() {
        final String className = getClass().getSimpleName().toLowerCase();
        if (myIsAlive) {
            return className + ".gif";
        } else {
            return className + "_dead.gif";
        }
    }
    
    /**
    * Returns this Vehicle object's direction.
    * 
    * @return the direction.
    */
    public Direction getDirection() {
        return myDirection;
    }
    
    /**
    * Returns this Vehicle object's x-coordinate.
    * 
    * @return the x-coordinate.
    */
    public int getX() {
        return myX;
    }
    
    /**
    * Returns this Vehicle object's y-coordinate.
    * 
    * @return the y-coordinate.
    */
    public int getY() {
        return myY;
    }
    
    /**
    * Returns whether this Vehicle object is alive and should move on the map.
    * 
    * @return true if the object is alive, false otherwise.
    */
    public boolean isAlive() {
        return myIsAlive;
    }
    
    /**
    * Called to notify a dead vehicle that 1 movement round has
    * passed, so that it will become 1 move closer to revival.
    */
    public void poke() {
        myWaitingTime = myWaitingTime - 1;
        if (myWaitingTime == 0) {
            myIsAlive = true;
            setDirection(Direction.random());
        }
    }
    
    /**
    * Moves this vehicle back to its original position.
    */
    public void reset() {
        setX(myOriginalX);
        setY(myOriginalY);
        setDirection(myOriginalDirection);
        myIsAlive = true;
        myWaitingTime = 0;
    }
    
    /**
    * Sets this object's facing direction to the given value.
    * 
    * @param theDir The new direction.
    */
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }
    
    /**
    * Sets this object's x-coordinate to the given value.
    * 
    * @param theX The new x-coordinate.
    */
    public void setX(final int theX) {
        myX = theX;
    }
    
    /**
    * Sets this object's y-coordinate to the given value.
    * 
    * @param theY The new y-coordinate.
    */
    public void setY(final int theY) {
        myY = theY;
    }
}