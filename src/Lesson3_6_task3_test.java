import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class Lesson3_6_task3_test {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {true, new int[]{2,1,4,4,1,1}},
                {true, new int[]{4,4,4,4,4}},
                {true, new int[]{1,1}},
                {true, new int[]{1,1,4,4,4,4,1,1}},
        });
    }

    private Lesson3_6_Task2 t;
    private boolean a;
    private int[] b;

    public Lesson3_6_task3_test(boolean a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Before
    public void init() {
        t = new Lesson3_6_Task2();
    }

    @Test
    public void testTask2() {
        Assert.assertEquals(a, t.doTask2(b));
    }
}
