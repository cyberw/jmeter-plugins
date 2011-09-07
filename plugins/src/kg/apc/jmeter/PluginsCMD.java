package kg.apc.jmeter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class used to handle all command-line stuff
 * like parameter processing etc. All real work
 * made by PluginsCMDWorker
 * @author undera
 * @see PluginsCMDWorker
 */
public class PluginsCMD extends AbstractCMDTool {

    public int processParams(String[] args) {
        if (args == null) {
            args = new String[]{"--help"};
        }

        return processParams(argsArrayToListIterator(args));
    }

    public static ListIterator<String> argsArrayToListIterator(String[] args) {
        List<String> arrayArgs = Arrays.asList(args);
        return new LinkedList<String>(arrayArgs).listIterator();
    }

    @Override
    protected int processParams(ListIterator<String> args) throws UnsupportedOperationException, IllegalArgumentException {
        AbstractCMDTool tool = null;

        while (args.hasNext()) {
            String arg = (String) args.next();
            if (arg.equals("-?") || arg.equals("--help")) {
                showHelp(System.out);
                return 0;
            } else if (arg.equals("--version")) {
                showVersion(System.out);
                return 0;
            } else if (arg.equals("")) {
                args.remove();
            } else if (arg.equals("--tool")) {
                args.remove();
                if (!args.hasNext()) {
                    throw new IllegalArgumentException("No tool name passed");
                }
                arg = (String) args.next();
                if (arg.equals("Reporter")) {
                    tool = new CMDReporterTool();
                } else if (arg.equals("PerfMonAgent")) {
                    //tool = new PerfMonAgentTool();
                }
                args.remove();
            }
        }

        if (tool == null) {
            throw new IllegalArgumentException("No suitable tool mode provided in params");
        }

        while (args.hasPrevious()) {
            args.previous();
        }
        return tool.processParams(args);
    }

    private void showVersion(PrintStream os) {
        os.println("JP@GC Tools v. " + JMeterPluginsUtils.PLUGINS_VERSION);
    }

    @Override
    protected void showHelp(PrintStream os) {
        os.println("JMeter Plugins at Google Code Command-Line Tools");
        os.println("For help and support please visit " + JMeterPluginsUtils.WIKI_BASE + "JMeterPluginsCMD");
        os.println("Usage:\n JMeterPluginsCMD "
                + "--tool < Reporter | PerfMonAgent > [--help --version]");
        CMDReporterTool tool = new CMDReporterTool();
        tool.showHelp(os);

        //PerfMonAgentTool agent = new PerfMonAgentTool();
        //agent.showHelp(os);
    }
}
