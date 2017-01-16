package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;

import org.junit.Test;

/**
 * Unit tests for class Truck.
 * 
 * @author Jake Knowles
 * @version 27 October 2015
 */

public class TruckTest {
    
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;

    /**
     * Test method for {@link Truck#testConstructor()}.
     */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(99, 100, Direction.NORTH);   
        
        assertEquals("Truck x coordinate not initialized correctly!", 99, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 100, t.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, t.getDirection());
    }
    
    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
        // start from each terrain type except WALL
        for (final Terrain testTerrain : Terrain.values()) {
            if (testTerrain != Terrain.WALL) { // Trucks do not start on Walls
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                // go to each terrain type
                for (final Terrain t : Terrain.values()) {
                    // try the test under each light condition
                    for (final Light l : Light.values()) {
                        if ((t == testTerrain)
                            || (t == Terrain.LIGHT && testTerrain == Terrain.STREET)
                            || (t == Terrain.STREET && testTerrain == Terrain.LIGHT)) {
                            // trucks can pass the terrain they start on under any light
                            // conditions, and can also pass lights if they start on
                            // streets and vice-versa

                            assertTrue("Truck started on " + testTerrain
                                                       + " should be able to pass " + t
                                                       + ", with light " + l,
                                       truck.canPass(t, l));
                        } else {
                            // trucks can't leave their terrain

                            assertFalse("Truck started on " + testTerrain
                                         + " should NOT be able to pass " + t
                                         + ", with light " + l, truck.canPass(t, l));
                        }
                    }
                } 
            }
        }
    }

    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirection() {
        // There is an assumption that there will always be at least one valid choice
        // for the chooseDirection() method to return. No test for surrounded by WALLs!
        
        // trucks need to stay on their own terrain
        // (STREET and LIGHT are considered the same Terrain for this purpose)
        // Trucks choose randomly from among directions which lead to STREET or LIGHT
        
        // If a Truck starts on STREET it should also be willing to move to LIGHT
        // If a Truck starts on LIGHT it should also be willing to move to STREET
        
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.WALL);
        neighbors.put(Direction.NORTH, Terrain.WALL);
        /*   W
         * W ? ?
         *   ?
         */

        for (final Terrain t : Terrain.values()) {
            if (t == Terrain.WALL) {
                continue; // Trucks don't start on WALLs
            }
            final Truck truck = new Truck(0, 0, Direction.NORTH);
            neighbors.put(Direction.EAST, t);
            
            // provide both STREET and LIGHT options
            if (t == Terrain.STREET) {
                neighbors.put(Direction.SOUTH, Terrain.LIGHT);
            } else if (t == Terrain.LIGHT) {
                neighbors.put(Direction.SOUTH, Terrain.STREET);
            } else {
                neighbors.put(Direction.SOUTH, t);
            }
            /*   W
             * W t t
             *   ?         LIGHT, STREET, or t depending on t
             */
            int tries = 0;
            boolean seenSouth = false;
            boolean seenEast = false;
            while (tries < TRIES_FOR_RANDOMNESS) {
                tries = tries + 1;
                final Direction dir = truck.chooseDirection(neighbors);
                assertTrue("on " + t + ", should choose east or south, was " + dir,
                           dir == Direction.EAST || dir == Direction.SOUTH);                   
                    
                seenSouth = seenSouth || dir == Direction.SOUTH;
                seenEast = seenEast || dir == Direction.EAST;
            }
            assertTrue("truck randomness", seenSouth && seenEast);
            
            // now check with only one valid option    
            neighbors.put(Direction.EAST, Terrain.WALL);
            /*   W
             * W t W
             *   ?         LIGHT, STREET, or t depending on t
             */
            tries = 0;
            while (tries < TRIES_FOR_RANDOMNESS) {
                tries = tries + 1;
                final Direction dir = truck.chooseDirection(neighbors);
                assertSame("invalid dir chosen, should be south, was " + dir,
                           Direction.SOUTH, dir);
            }
            
            // for STREET and LIGHT also check with only the opposite type available 
            neighbors.put(Direction.EAST, t);
            if (t == Terrain.STREET || t == Terrain.LIGHT) {
                neighbors.put(Direction.SOUTH, t);
                /*   W
                 * W t t
                 *   t
                 */
                tries = 0;
                seenSouth = false;
                seenEast = false;
                while (tries < TRIES_FOR_RANDOMNESS) {
                    tries = tries + 1;
                    final Direction dir = truck.chooseDirection(neighbors);
                    assertTrue("on " + t + ", should choose east or south, was " + dir,
                               dir == Direction.EAST || dir == Direction.SOUTH);
                    seenSouth = seenSouth || dir == Direction.SOUTH;
                    seenEast = seenEast || dir == Direction.EAST;
                }
                assertTrue("truck randomness issue! SOUTH : " + seenSouth
                                               + "; EAST : " + seenEast,
                           seenSouth && seenEast);
            }
        }
    }
}