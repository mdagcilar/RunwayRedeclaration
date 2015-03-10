import Model.Obstacle;
import Model.Runway;
import Model.Strip;
import Model.Values;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 9/3/2015
 */
public class MathTestRecValues {

    //Strip 1
    private Values str1Values;
    private Strip str1;
    //Strip2
    private Values str2Values;
    private Strip str2;
    //Runway and obstacle
    private Runway runway;
    private Obstacle obstacle;
    private Values expectedValues;
    private static final int BLAST_ALLOWANCE = 300;
    private static final int DEFAULT_VALUE = 10;

    @Before
    public void setUp() {
    }

    //Landing tests

    //Land over, if the obstacle is before the middle and short.
    @Test
    public void landOverBeforeMiddle() {
        //runway specs
        str1Values = new Values(3902, 3902, 3902, 3595);
        str1 = new Strip("09L", 9, "L", str1Values, 306);
        str2Values = new Values(3884, 3884, 3962, 3884);
        str2 = new Strip("27R", 27, "R", str2Values, 0);
        runway = new Runway("09L/27R", str1, str2);
        //obstacle to test
        obstacle = new Obstacle("plane", 4, 12, 5, "");
        runway.addObstacle(obstacle, -50, 3646, 0);
        runway.recalculateValues(BLAST_ALLOWANCE);

        //expected values
        expectedValues = new Values(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, 2982);
        expectedValues.setLanding("Land over");
        System.out.println(runway.getStrip1().getRecVal());
        System.out.println(runway.getStrip1().getRecVal().getLda());
        assertTrue(runway.getStrip1().getRecVal().getLda() == expectedValues.getLda());
        assertTrue(runway.getStrip1().getRecVal().getLanding().equals(expectedValues.getLanding()));
    }

    @Test
    public void landTowardsAfterMiddle() {
        //runway specs
        str1Values = new Values(3902, 3902, 3902, 3595);
        str1 = new Strip("09L", 9, "L", str1Values, 306);
        str2Values = new Values(3884, 3884, 3962, 3884);
        str2 = new Strip("27R", 27, "R", str2Values, 0);
        runway = new Runway("09L/27R", str1, str2);
        //obstacle to test
        obstacle = new Obstacle("plane", 4, 12, 10, "");
        runway.addObstacle(obstacle, 2853, 500, 0);
        runway.recalculateValues(BLAST_ALLOWANCE);

        //expected values
        expectedValues = new Values(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, 2548);
        expectedValues.setLanding("Land towards");
        System.out.println(runway.getStrip1().getRecVal());
        System.out.println(runway.getStrip1().getRecVal().getLda());
        assertTrue(runway.getStrip1().getRecVal().getLda() == expectedValues.getLda());
        assertTrue(runway.getStrip1().getRecVal().getLanding().equals(expectedValues.getLanding()));
    }

    /* If the obstacle is before the middle. We want to land before it, if
       the obstacle is tall enough to give us less available landing distance after it.*/
    @Test
    public void landTowardsBeforeMiddle() {
        //runway specs
        str1Values = new Values(3902, 3902, 3902, 3595);
        str1 = new Strip("09L", 9, "L", str1Values, 306);
        str2Values = new Values(3884, 3884, 3962, 3884);
        str2 = new Strip("27R", 27, "R", str2Values, 0);
        runway = new Runway("09L/27R", str1, str2);
        //obstacle to test
        obstacle = new Obstacle("Tall", 4, 50, 10, "");
        runway.addObstacle(obstacle, 669, 2927, 0);
        runway.recalculateValues(BLAST_ALLOWANCE);

        //expected values
        expectedValues = new Values(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, 364);
        expectedValues.setLanding("Land towards");
        System.out.println(runway.getStrip1().getRecVal());
        System.out.println(runway.getStrip1().getRecVal().getLda());
        assertTrue(runway.getStrip1().getRecVal().getLda() == expectedValues.getLda());
        assertTrue(runway.getStrip1().getRecVal().getLanding().equals(expectedValues.getLanding()));
    }

    //landingOverBeforeMiddle edge case for the above test.
    @Test
    public void landOverBeforeMiddleEdgeCase() {
        //runway specs
        str1Values = new Values(3902, 3902, 3902, 3595);
        str1 = new Strip("09L", 9, "L", str1Values, 306);
        str2Values = new Values(3884, 3884, 3962, 3884);
        str2 = new Strip("27R", 27, "R", str2Values, 0);
        runway = new Runway("09L/27R", str1, str2);
        //obstacle to test
        obstacle = new Obstacle("Tall", 4, 50, 10, "");
        runway.addObstacle(obstacle, 667, 2929, 0);
        runway.recalculateValues(BLAST_ALLOWANCE);

        //expected values
        expectedValues = new Values(DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, 363);
        expectedValues.setLanding("Land over");
        System.out.println(runway.getStrip1().getRecVal());
        System.out.println(runway.getStrip1().getRecVal().getLda());
        assertTrue(runway.getStrip1().getRecVal().getLda() == expectedValues.getLda());
        assertTrue(runway.getStrip1().getRecVal().getLanding().equals(expectedValues.getLanding()));
    }


    //Take off tests
    @Test
    public void takeOffAwayBeforeMiddle() {
        //runway specs
        str1Values = new Values(1723,1723,1831,1650);
        str1 = new Strip("02", 2, "", str1Values, 73);
        str2Values = new Values(1650,1650,1805, 1605);
        str2 = new Strip("20", 20, "", str2Values, 45);
        runway = new Runway("09L/27R", str1, str2);
        //obstacle to test
        obstacle = new Obstacle("plane", 7, 12, 20, "");
        runway.addObstacle(obstacle, 106, 1544, 20);
        runway.recalculateValues(BLAST_ALLOWANCE);

        //expected values
        expectedValues = new Values(1234, 1234, 1342, DEFAULT_VALUE);
        expectedValues.setTakeoff("TakeOff Away");
        System.out.println(runway.getStrip1().getRecVal());
        System.out.println(runway.getStrip1().getRecVal().getTora());
        System.out.println(runway.getStrip1().getRecVal().getAsda());
        System.out.println(runway.getStrip1().getRecVal().getToda());
        assertTrue(runway.getStrip1().getRecVal().getTora() == expectedValues.getTora());
        assertTrue(runway.getStrip1().getRecVal().getAsda() == expectedValues.getAsda());
        assertTrue(runway.getStrip1().getRecVal().getToda() == expectedValues.getToda());
        assertTrue(runway.getStrip1().getRecVal().getTakeoff().equals(expectedValues.getTakeoff()));
    }
}
