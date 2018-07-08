package txtJoin;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileFinder {

    private ArrayList<File> txtFilesList = new ArrayList<>();


    public void pullFilesFromFolder(File folder) {

        File[] filesList = folder.listFiles();
        txtFilesList.addAll(Arrays.asList(folder.listFiles((dir, name) ->  name.endsWith(".txt"))));

        if (filesList.length != 0) {

            for (File entry : filesList) {
                if (entry.isDirectory()) {
                    pullFilesFromFolder(entry);
                }
            }
        }
    }

    public ArrayList<File> getTxtFilesList() {

        Comparator<File> fileNameComparator
                = Comparator.comparing(
                File::getName, String.CASE_INSENSITIVE_ORDER);

        Collections.sort(txtFilesList, fileNameComparator);

        return txtFilesList;
    }

}
