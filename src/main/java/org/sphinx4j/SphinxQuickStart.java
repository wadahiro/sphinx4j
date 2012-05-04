package org.sphinx4j;

/**
 * sphinx-build.py wrapper.
 * 
 * @author Hiroyuki Wada
 * 
 */
public class SphinxQuickStart extends Sphinx4j {

    public String getExecScript(String[] args) {
        String code = "from sphinx.quickstart import main\n" + "main("
                + toPyList(args) + ")";
        return code;
    }
}
