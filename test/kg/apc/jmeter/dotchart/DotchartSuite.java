/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kg.apc.jmeter.dotchart;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author apc
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DotChartModelTest.class, DotChartAverageValuesTest.class, DotChartVisualizerTest.class, DotChartTest.class, DotChartGuiPanelTest.class, DotChartSampleResultTest.class, DotChartColoredRowTest.class})
public class DotchartSuite {

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

}