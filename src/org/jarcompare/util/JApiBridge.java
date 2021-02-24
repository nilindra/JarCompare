package org.jarcompare.util;

import org.apache.commons.io.FileDeleteStrategy;
import org.jarcompare.main.Main;
import org.jarcompare.to.FileStructure;

import java.io.IOException;
import java.io.File;

/**
 * @author Nilindra.Fernando
 */
public class JApiBridge {

    private static String composePath(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("-report-path " + Main.COMPATIBILITY_DIR + fileName + "/BC_SC_Report.html");
        return sb.toString();
    }

    public static void execute(FileStructure fs1, FileStructure fs2) throws IOException, InterruptedException {
        File v1File = FileUtils.jarFileCopy(fs1.getFile(), Main.TMP_DIR + "V1-");
        File v2File = FileUtils.jarFileCopy(fs2.getFile(), Main.TMP_DIR + "V2-");

        Runtime run = Runtime.getRuntime();
        String command = "japi-compliance-checker -lib "
                + fs1.getFileName() + " " + v1File.toString() + "  " + v2File.toString() + " --v1=V1 --v2=V2 " + composePath(fs1.getFileName());

        Process proc = run.exec(command);
        System.out.println("Waiting to complete for FileV1[" + fs1.getFileNameWithExtension() + "] FileV2[" + fs2.getFileNameWithExtension() + "]");

        int code = proc.waitFor();
        // If it's fully compatible, we will not track them.
        if (code == 0) {
            File successForRemoval = new File(Main.COMPATIBILITY_DIR + fs1.getFileName());
            FileDeleteStrategy.FORCE.delete(successForRemoval);
        }

        FileUtils.deleteIfExists(v1File);
        FileUtils.deleteIfExists(v2File);

        System.out.println("Complete for FileV1[" + fs1.getFileNameWithExtension() + "] FileV2[" + fs2.getFileNameWithExtension() + "]");
    }
}