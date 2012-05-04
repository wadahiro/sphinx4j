package org.sphinx4j;

public class Help extends Sphinx4j {

    @Override
    public String getExecScript(String[] args) {
        StringBuilder code = new StringBuilder();
        code.append("print 'Usage: sphinx4j commands'\n");
        code.append("print 'Commands:'\n");
        code.append("print '    install            -- install sphinx into your home directory'\n");
        code.append("print '    sphinx-quickstart  -- run sphinx-quickstart'\n");
        code.append("print '    sphinx-build       -- run sphinx-build'\n");

        return code.toString();
    }

}
