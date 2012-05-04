package org.sphinx4j;

/**
 * sphinx-quickstart.py wrapper.
 * 
 * @author Hiroyuki Wada
 * 
 */
public class SphinxBuild extends Sphinx4j {

    public String getExecScript(String[] args) {
        String code = "from sphinx import main\n" + "main(" + toPyList(args)
                + ")";
        return code;
    }
}
