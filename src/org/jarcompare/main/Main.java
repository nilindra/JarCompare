package org.jarcompare.main;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.jarcompare.util.BeanUtils;
import org.jarcompare.util.JApiBridge;
import org.jarcompare.to.FileStructure;
import org.jarcompare.util.FileUtils;
import org.jarcompare.util.PerfUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Nilindra.Fernando
 */
public class Main {

    public static final String WORKSPACE_DIR = "/workspace/";
    public static final String DIST_DIR = WORKSPACE_DIR + "dist/";
    public static final String COMPATIBILITY_DIR = DIST_DIR + "compatibility/";
    public static final String TMP_DIR = DIST_DIR + "tmp/";

    private static Properties loadConfig() throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader(WORKSPACE_DIR + "config.properties"));
        return prop;
    }

    private static void setup() {
        new File(DIST_DIR).mkdirs();
        new File(COMPATIBILITY_DIR).mkdirs();
        new File(TMP_DIR).mkdirs();
    }

    private static void cleanup() throws IOException {
        FileUtils.deleteIfExists(new File(Main.TMP_DIR));
    }

    private static void execute(String path1, String path2) throws IOException, InterruptedException {
        setup();
        File dir1 = new File(path1);
        File dir2 = new File(path2);

        List<FileStructure> lst1 = FileUtils.listJARFiles(dir1);
        List<FileStructure> lst2 = FileUtils.listJARFiles(dir2);

        Map<String, FileStructure> map1 = FileUtils.getJarMapContents(lst1);
        Map<String, FileStructure> map2 = FileUtils.getJarMapContents(lst2);

        if (map1.size() != map2.size()) {
            MapDifference<String, FileStructure> mapDifference = Maps.difference(map1, map2);
            Map<String, FileStructure> keysOnlyInSource = mapDifference.entriesOnlyOnLeft();
            Map<String, FileStructure> keysOnlyInTarget = mapDifference.entriesOnlyOnRight();

            System.out.println("Sizes diff between packages v1[" + lst1.size() + "], v2[" + lst2.size() + "]");
            System.out.println("Added.txt with [" + keysOnlyInTarget.size() + "] entries");
            System.out.println("Removed.txt with [" + keysOnlyInSource.size() + "] entries");
            FileUtils.writeFile(DIST_DIR + "Added.txt" , FileUtils.getFilePaths(keysOnlyInTarget.values()));
            FileUtils.writeFile(DIST_DIR + "Removed.txt" , FileUtils.getFilePaths(keysOnlyInSource.values()));
        }

        List<String> commonKeys = FileUtils.getCommonsKeys(map1, map2);
        if (!commonKeys.isEmpty()) {
            for (String key : commonKeys) {
                FileStructure fs1 = map1.get(key);
                FileStructure fs2 = map2.get(key);

                boolean status = FileUtils.isEqual(fs1.getFile(), fs2.getFile());
                if (!status) {
                    System.out.println("");
                    System.out.println("File difference exists for {" + fs1.getFileNameWithExtension() + "}");
                    JApiBridge.execute(fs1, fs2);
                }
            }
        }

        cleanup();;
    }

    public static void main(String[] args) throws Exception {
        Date startDateTime = new Date();
        Properties p = loadConfig();
        System.out.println("JarCompare Started");

        String v1Path = BeanUtils.maskNull(p.getProperty("v1.path"));
        String v2Path = BeanUtils.maskNull(p.getProperty("v2.path"));
        System.out.println("V1.PATH [" + v1Path + "] ");
        System.out.println("V2.PATH [" + v2Path + "] ");

        execute(v1Path, v2Path);

        Date endDateTime = new Date();
        System.out.println("JarCompare Completed " + PerfUtils.getExecutionTime(startDateTime, endDateTime));
    }
}