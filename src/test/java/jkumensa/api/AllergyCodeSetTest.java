package jkumensa.api;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AllergyCodeSetTest {
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSizeAndIsEmpty() {
        AllergyCodeSet s = new AllergyCodeSet();
        Assert.assertEquals(0, s.size());
        Assert.assertTrue(s.isEmpty());
        s.add('A');
        Assert.assertEquals(1, s.size());
        Assert.assertFalse(s.isEmpty());
        s.add(new Character('C'));
        Assert.assertEquals(2, s.size());
        Assert.assertFalse(s.isEmpty());
        s.remove('B');
        Assert.assertEquals(2, s.size());
        Assert.assertFalse(s.isEmpty());
        s.remove('A');
        Assert.assertEquals(1, s.size());
        Assert.assertFalse(s.isEmpty());
        s.remove(new Character('C'));
        Assert.assertEquals(0, s.size());
        Assert.assertTrue(s.isEmpty());
    }

    @Test
    public void testContains_Object() {
        AllergyCodeSet s = new AllergyCodeSet();
        Character a = 'A';
        Assert.assertFalse(s.contains(a));
        s.add('A');
        Assert.assertTrue(s.contains(a));
        s.add('B');
        Assert.assertTrue(s.contains(a));
        s.remove('A');
        Assert.assertFalse(s.contains(a));
    }

    @Test
    public void testContains_char() {
        AllergyCodeSet s = new AllergyCodeSet();
        char a = 'A';
        Assert.assertFalse(s.contains(a));
        s.add('A');
        Assert.assertTrue(s.contains(a));
        s.add('B');
        Assert.assertTrue(s.contains(a));
        s.remove('A');
        Assert.assertFalse(s.contains(a));
    }

    @Test
    public void testIteratorEmpty() {
        AllergyCodeSet s = new AllergyCodeSet();
        Assert.assertFalse(s.iterator().hasNext());
    }

    @Test
    public void testIteratorSingle_Object() {
        AllergyCodeSet s = new AllergyCodeSet();
        s.add('G');
        AllergyCodeSet.CharIterator it = s.iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals((Character) 'G', it.next());
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorSingle_char() {
        AllergyCodeSet s = new AllergyCodeSet();
        s.add('G');
        AllergyCodeSet.CharIterator it = s.iterator();
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals('G', it.nextChar());
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorSingleMulti() {
        AllergyCodeSet s = new AllergyCodeSet();
        s.add('B');
        s.add('C');
        s.add('G');
        AllergyCodeSet.CharIterator it = s.iterator();
        
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals('B', it.nextChar());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals((Character)'C', it.next());
        Assert.assertTrue(it.hasNext());
        Assert.assertEquals('G', it.nextChar());
        Assert.assertFalse(it.hasNext());
    }

    /*    @Test
    public void testRemove_Object() {
        System.out.println("remove");
        Object o = null;
        AllergyCodeSet instance = new AllergyCodeSet();
        boolean expResult = false;
        boolean result = instance.remove(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testRemove_char() {
        System.out.println("remove");
        char c = ' ';
        AllergyCodeSet instance = new AllergyCodeSet();
        boolean expResult = false;
        boolean result = instance.remove(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testContainsAll() {
        System.out.println("containsAll");
        Collection c = null;
        AllergyCodeSet instance = new AllergyCodeSet();
        boolean expResult = false;
        boolean result = instance.containsAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    @Test
    public void testAddAll() {
        AllergyCodeSet s = new AllergyCodeSet();
        s.add('F');
        s.addAll(Arrays.asList('C', 'G', 'H'));
        Assert.assertEquals(4, s.size());
        Assert.assertTrue(s.contains('F'));
        Assert.assertTrue(s.contains('C'));
        Assert.assertTrue(s.contains('G'));
        Assert.assertTrue(s.contains('H'));
    }

    /*@Test
    public void testRemoveAll() {
        System.out.println("removeAll");
        Collection c = null;
        AllergyCodeSet instance = new AllergyCodeSet();
        boolean expResult = false;
        boolean result = instance.removeAll(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    @Test
    public void testClear() {
        AllergyCodeSet s = new AllergyCodeSet();
        s.add('E');
        Assert.assertTrue(s.contains('E'));
        s.clear();
        Assert.assertFalse(s.contains('E'));
        Assert.assertTrue(s.isEmpty());
    }
}
