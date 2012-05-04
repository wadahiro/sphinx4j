package org.sphinx4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

/**
 * sphinx installer.
 * 
 * @author Hiroyuki Wada
 * 
 */
public class SphinxInstaller {

    private String installDir;

    public SphinxInstaller(String installDir) {
        this.installDir = installDir;
    }

    public void install() throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("sphinx4j");

        List<String> urls = new ArrayList<String>();
        urls.add(bundle.getString("shpinx.sourceUrl"));
        urls.add(bundle.getString("docutils.sourceUrl"));
        urls.add(bundle.getString("jinja2.sourceUrl"));
        urls.add(bundle.getString("roman.sourceUrl"));

        Download download = new Download();

        for (String url : urls) {
            File destFile = getDestFile(url);

            System.out.println("Downloading to " + destFile);

            download.download(new URI(url), destFile);

            verify(url, destFile);

            System.out.println("Uncompress to " + installDir);

            uncompress(destFile);
        }
    }

    private void verify(String url, File destFile)
            throws NoSuchAlgorithmException, IOException {
        if (url.contains("#md5=")) {
            String md5 = url.substring(url.lastIndexOf("#md5=") + 5,
                    url.length());
            String md5Hex = DigestUtils.md5Hex(new FileInputStream(destFile));
            if (!md5.equals(md5Hex)) {
                throw new IllegalStateException("MD5 check error! " + destFile);
            }
            System.out.println("MD5 check OK.");
        }
    }

    private void uncompress(File file) throws IOException {
        if (!file.getName().endsWith(".tar.gz")) {
            throw new IllegalArgumentException("extension must be tar.gz.");
        }
        TarInputStream tin = null;
        try {
            tin = new TarInputStream(new GZIPInputStream(new FileInputStream(
                    file)));

            for (TarEntry tarEnt = tin.getNextEntry(); tarEnt != null; tarEnt = tin
                    .getNextEntry()) {
                if (tarEnt.isDirectory()) {
                    new File(installDir, tarEnt.getName()).mkdir();
                } else {
                    FileOutputStream fos = new FileOutputStream(new File(
                            installDir, tarEnt.getName()));
                    tin.copyEntryContents(fos);
                }
            }
        } finally {
            if (tin != null) {
                tin.close();
            }
        }
    }

    private File getDestFile(String url) {
        return new File(installDir, getFileName(url));
    }

    private String getFileName(String url) {
        url = url.substring(url.lastIndexOf('/'), url.length());
        if (url.contains("#md5=")) {
            return url.substring(0, url.lastIndexOf('#'));
        }
        return url;
    }
}
