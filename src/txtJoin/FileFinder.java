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

public class FileFinder extends Task<Void> {

    final private String path;

    final private List<File> list=new ArrayList<>();

    public FileFinder(String path) {
        this.path = path;
    }


    public void findIn(File fi) {
        File[] files=fi.listFiles((f)->f.getName().endsWith(".txt") || f.isDirectory());

        if (files != null) {
            for (File f : files)
                if (f.isDirectory()) {
                    findIn(f);
                } else {
                    list.add(f);
                }
        }
    }

    @Override
    protected Void call() throws Exception {

        File file = new File(path);
        if (file.isDirectory()) {
            findIn(file);
        }

        Collections.sort(list);
        Controller controller = new Controller();
        TxtFileWriter txtFileWriter = new TxtFileWriter(controller.getResultDir().getText());
        txtFileWriter.write(list);

        TextArea textArea = controller.getTextArea();
        List<String> txtLines = new ArrayList<>();
        txtLines = Files.readAllLines(Paths.get(txtFileWriter.getFile().getPath()));

        for(String line:txtLines){
            textArea.appendText(line);
        }

        System.out.println(controller.getResultDir().getText());
        return null;
    }

}
