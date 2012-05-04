package org.sphinx4j;

public class Install extends Sphinx4j {

    @Override
    public String getExecScript(String[] args) {
        return null;
    }

    @Override
    public void run(String[] args) {
        try {
            new SphinxInstaller(getInstallDir()).install();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
