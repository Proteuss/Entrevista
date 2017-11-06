package carlos.entrevistatecnicacotalker;

import android.support.v4.widget.TextViewCompat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Testing {

    private  MainActivity mainActivity;

    @Before
    public void setup() {
        mainActivity = new MainActivity();
        System.out.println("Testing Started");
    }
    @After
    public void cleanup() {
        System.out.println("Testing Ended");
    }

    @Test
    public void replaceAsteriskTest() {
        mainActivity = new MainActivity();
        //Long test
        List<String> output = mainActivity.replaceAsterisk("1*0*0*1");
        List<String> expectedOutput=new Vector<>();
        expectedOutput.add("1101011");
        expectedOutput.add("1101001");
        expectedOutput.add("1100011");
        expectedOutput.add("1100001");
        expectedOutput.add("1001011");
        expectedOutput.add("1001001");
        expectedOutput.add("1000011");
        expectedOutput.add("1000001");
        assertEquals("ReplaceAsterisk error", expectedOutput, output);
        //Border case test
        output = mainActivity.replaceAsterisk("*1*");
        expectedOutput=new Vector<>();
        expectedOutput.add("111");
        expectedOutput.add("110");
        expectedOutput.add("011");
        expectedOutput.add("010");
        assertEquals("ReplaceAsterisk error", expectedOutput, output);
        //Border case test
        output = mainActivity.replaceAsterisk("***");
        expectedOutput=new Vector<>();
        expectedOutput.add("111");
        expectedOutput.add("110");
        expectedOutput.add("101");
        expectedOutput.add("100");
        expectedOutput.add("011");
        expectedOutput.add("010");
        expectedOutput.add("001");
        expectedOutput.add("000");
        assertEquals("ReplaceAsterisk error", expectedOutput, output);
    }
}
