package org.jarcompare.to;

import java.io.File;

/**
 * @author Nilindra.Fernando
 */
public class FileStructure {

    private String fileNameWithExtension;
    private String fileName;
    private String directory;
    private File file;

    public FileStructure(String fileName, String fileNameWithExtension, String directory, File file) {
        this.setFileName(fileName);
        this.setFileNameWithExtension(fileNameWithExtension);
        this.setDirectory(directory);
        this.setFile(file);
    }

    public String toString() {
        return "FileNameWithExtension[" + this.getFileNameWithExtension() + "], Directory[" + this.getDirectory() + "], File[" + this.getFile() + "]";
    }

    public String getFileName() {
        return fileName;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectory() {
        return directory;
    }

    private void setDirectory(String directory) {
        this.directory = directory;
    }

    public File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    public String getFileNameWithExtension() {
        return fileNameWithExtension;
    }

    private void setFileNameWithExtension(String fileNameWithExtension) {
        this.fileNameWithExtension = fileNameWithExtension;
    }
}
