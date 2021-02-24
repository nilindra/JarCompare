package org.jarcompare.util;

import com.google.common.io.Files;
import org.jarcompare.to.FileStructure;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Nilindra.Fernando
 */
public class FileUtils {

    private FileUtils() {
    }

    public static List<FileStructure> listJARFiles(File rootDir) {
        List<FileStructure> lst = new ArrayList<>();
        for (File file : Files.fileTreeTraverser().breadthFirstTraversal(rootDir)) {
            String fileNameWithExtension = file.getName();
            String fileNameWithExtensionWithLowerCase = fileNameWithExtension.toLowerCase();

            if (file.isFile() && fileNameWithExtensionWithLowerCase.indexOf(".jar") != -1) {
                String dirName = file.toString().substring(0, file.toString().indexOf(fileNameWithExtension));
                String fileName = fileNameWithExtension.substring(0, fileNameWithExtensionWithLowerCase.indexOf(".jar"));
                lst.add(new FileStructure(fileName, fileNameWithExtension, dirName, file));
            }
        }
        return lst;
    }

    public static File jarFileCopy(File org, String prefix) throws IOException {
        File copied = new File(prefix + org.getName());
        Files.copy(org, copied);
        return copied;
    }

    public static Map<String, FileStructure> getJarMapContents(List<FileStructure> lst) {
        Map<String, FileStructure> map = new HashMap();
        for (FileStructure fs : lst) {
            map.put(fs.getFileName(), fs);
        }
        return map;
    }

    public static List<String> getFilePaths(Collection<FileStructure> fsCol) {
        List<String> list = new ArrayList<>();
        for (FileStructure o : fsCol) {
            list.add(o.getFile().toString());
        }
        return list;
    }

    public static boolean isEqual(File src, File dest) throws IOException {
        return Files.equal(src, dest);
    }

    public static boolean deleteIfExists(File file) throws IOException {
        return java.nio.file.Files.deleteIfExists(file.toPath());
    }

    public static List<String> getCommonsKeys(Map<String, FileStructure> map1, Map<String, FileStructure> map2) {
        List<String> commonKeys = new ArrayList<>();

        for (Map.Entry<String, FileStructure> entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey()) && !commonKeys.contains(entry.getKey())) {
                commonKeys.add(entry.getKey());
            }
        }

        for (Map.Entry<String, FileStructure> entry : map2.entrySet()) {
            if (map1.containsKey(entry.getKey()) && !commonKeys.contains(entry.getKey())) {
                commonKeys.add(entry.getKey());
            }
        }

        return commonKeys;
    }

    public static void writeFile(String fileName, List<String> contents) {
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;

        try {
            File file = new File(fileName);
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);

            for (String s : contents) {
                pr.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        } finally {
            if (pr != null) {
                try {
                    pr.close();
                } catch (Exception x) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (Exception x) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception x) {
                }
            }
            ;
        }
    }
}