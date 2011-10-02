package math.complexity;

import junit.framework.TestCase;

/**
 * The DensityMapTest class covers the cartesian to screen space
 * conversion.
 *
 * @author Pablo Mayrgundter
 */
public class DensityMapTest extends TestCase {

  DensityMap map;

  public void setUp() {
    map = new DensityMap(10, 10, 17);
  }

  public void tearDown() {
    map = null;
  }

  public void testMap() {
    map.map(3, 4.2);
    map.map(3, 4.2);
    map.map(3, 5.2);
    assertEquals(2, map.getDensity(3, 4.2));
    assertEquals(1, map.getDensity(3, 5.2));
    assertEquals(0, map.getDensity(3, 6.2));
    assertEquals(2, map.getMaxDensity());
  }

  /**
   * Runnable as:
   *
   *   java math.algs.DensityMapTest
   */
  public static void main(String [] args) {
    junit.textui.TestRunner.run(DensityMapTest.class);
  }
}