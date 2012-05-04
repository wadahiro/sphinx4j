package org.sphinx4j;

import org.python.core.Py;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

/**
 * Base for SphinxCommand.
 * 
 * @author Hiroyuki Wada
 * 
 */
public abstract class Sphinx4j {

    private String installDir = System.getProperty("user.home")
            + "/.sphinx4j/Lib";

    private PythonInterpreter interp;

    public static void main(String[] args) {
        String command = "help";
        if (args.length > 0) {
            command = args[0];
        }
        Sphinx4j.instance(command).run(args);
    }

    public static Sphinx4j instance(String command) {
        if (command.equalsIgnoreCase("install")) {
            return new Install();

        } else if (command.equalsIgnoreCase("sphinx-build")) {
            return new SphinxBuild();

        } else if (command.equalsIgnoreCase("sphinx-quickstart")) {
            return new SphinxQuickStart();

        }
        return new Help();
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir;
    }

    public String getInstallDir() {
        return installDir;
    }

    public Sphinx4j() {
        interp = new PythonInterpreter(null, new PySystemState());

        PySystemState sys = Py.getSystemState();
        // sys.path.append(new SyspathArchive(installDir +
        // "/Sphinx-1.1.3.zip"));
        // sys.path.append(new SyspathArchive(installDir +
        // "/docutils-0.8.1.zip"));
        // sys.path.append(new SyspathArchive(installDir + "/roman-1.4.0.zip"));
        // sys.path.append(new SyspathArchive(installDir + "/Jinja2-2.6.zip"));

        sys.path.append(new PyString(installDir + "/Sphinx-1.1.3"));
        sys.path.append(new PyString(installDir + "/docutils-0.8"));
        sys.path.append(new PyString(installDir + "/roman-1.4.0/src"));
        sys.path.append(new PyString(installDir + "/Jinja2-2.6"));
    }

    public void run(String[] args) {
        interp.exec(getExecScript(args));
    }

    public abstract String getExecScript(String[] args);

    protected String toPyList(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append("'");
            sb.append(s);
            sb.append("'");
        }
        return '[' + sb.toString() + ']';
    }
}
