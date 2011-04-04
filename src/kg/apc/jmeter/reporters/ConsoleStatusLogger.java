package kg.apc.jmeter.reporters;

import java.io.PrintStream;
import java.io.Serializable;
import org.apache.jmeter.JMeter;
import org.apache.jmeter.engine.event.LoopIterationEvent;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.reporters.AbstractListenerElement;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleListener;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 *
 * @author undera
 */
public class ConsoleStatusLogger extends AbstractListenerElement
        implements SampleListener, Serializable,
        NoThreadClone, TestListener {
    private static final Logger log = LoggingManager.getLoggerForClass();

    private PrintStream out;
    private long cur=0;
    private int count;
    private int threads;
    private int sumRTime;
    private int sumLatency;
    private int errors;

    private class JMeterLoggerOutputStream extends PrintStream {

        public JMeterLoggerOutputStream(Logger log) {
            super(System.out);
        }

        @Override
        public void println(String msg) {
            log.info(msg);
        }
    }

    public void sampleOccurred(SampleEvent se) {
        long sec = System.currentTimeMillis() / 1000;
        if (sec!=cur && count>0) {
            flush();
            cur=sec;
        }
        SampleResult res=se.getResult();
        
        count++;
        sumRTime+=res.getTime();
        sumLatency+=res.getLatency();
        errors+=res.isSuccessful()?0:1;
        threads=res.getAllThreads();
    }

    private void flush() {
        String msg="Threads: "+threads+'/'+JMeterContextService.getTotalThreads()+'\t';
        msg+="Samples: "+count+'\t';
        msg+="Latency: "+sumLatency/count+'\t';
        msg+="Resp.Time: "+sumRTime/count+'\t';
        msg+="Errors: "+100*errors/count+'%';
        out.println(msg);

        count=0;
        sumRTime=0;
        sumLatency=0;
        errors=0;
    }
    
    public void sampleStarted(SampleEvent se) {
    }

    public void sampleStopped(SampleEvent se) {
    }

    public void testStarted() {
        if (JMeter.isNonGUI()) {
            out = System.out;
        } else {
            out = new JMeterLoggerOutputStream(log);
        }
    }

    public void testStarted(String string) {
        testStarted();
    }

    public void testEnded() {
    }

    public void testEnded(String string) {
    }

    public void testIterationStart(LoopIterationEvent lie) {
    }
}
