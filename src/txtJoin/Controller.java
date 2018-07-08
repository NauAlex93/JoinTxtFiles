package txtJoin;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Controller implements Initializable {
    @FXML private TextField rootDir;
    @FXML private TextField resultDir;
    @FXML private TextArea textArea;

    public TextField getRootDir() {
        return rootDir;
    }

    public TextField getResultDir() {
        return resultDir;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setRootDir(TextField rootDir) {
        this.rootDir = rootDir;
    }

    public void setResultDir(TextField resultDir) {
        this.resultDir = resultDir;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    private Task task=null;


    public ArrayList<File> txtFilesList = new ArrayList<>();

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

    public static void ifPathExists(File path){

        if (!path.exists()){
            System.out.println("This path does not exist! Please, new it up.");
            System.exit(1);
        }
    }

    private File file;


    public void writeTXTFiles(ArrayList<File> files, File file) {


        try {

            for (File txtFile : files) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(txtFile.getAbsolutePath()), Charset.forName("utf-8")));
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file.getPath(),true)));
                String s;
                while ((s = reader.readLine()) != null)
                    out.println(s);
                out.close();
                reader.close();
            }

        } catch (IOException e) {
            e.getMessage();
        }

    }

    public File getFile() {
        return file;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//
    }

    @FXML
    protected void startSearch(ActionEvent event) {

        final List<File> list=new ArrayList<>();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Please, choose txt file!");


        File path = new File(rootDir.getText());
        ifPathExists(path);

        File path1 = new File(resultDir.getText());
        //ifPathExists(path1);

        Controller controller = new Controller();
        controller.pullFilesFromFolder(path);
        ArrayList<File> txtFiles = controller.getTxtFilesList();
        controller.writeTXTFiles(txtFiles, path1);

        List<String> txtLines = new ArrayList<>();
        try{
            txtLines = Files.readAllLines(Paths.get(path1.getPath()));
        }
        catch (IOException ex){

        }

        for(String line:txtLines){
            textArea.appendText(line+"\n");
        }


    }



}
