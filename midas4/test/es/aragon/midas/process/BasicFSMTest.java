/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.aragon.midas.process;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carlos
 */
public class BasicFSMTest {
    
    BasicFSMItem it;
    public BasicFSMTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
  
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        it = new BasicFSMItem();        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadFSM method, of class BasicFSM.
     */
    @Test
    public void testLoadFSM() {
        System.out.println("loadFSM");
        BasicFSM instance = new BasicFSM();
        instance.loadFSM();
        it = new BasicFSMItem();
        it.setState(1);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(instance.isAllowed(it, 1), true);
        assertEquals(instance.isAllowed(it, 2), false);
        instance.doEvent(it, 1);
        assertEquals(instance.isAllowed(it, 1), false);
        assertEquals(instance.isAllowed(it, 2), true);
        instance.doEvent(it, 2);
        assertEquals(instance.isAllowed(it, 2), false);
        assertEquals(instance.isAllowed(it, 3), true);
        instance.doEvent(it, 3);
        assertEquals(instance.isAllowed(it, 3), false);
        assertEquals(instance.isAllowed(it, 1), true);

    }
}
