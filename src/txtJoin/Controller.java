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


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    protected void startSearch(ActionEvent event) {

        File pathToRootDir = new File(rootDir.getText());
        if (!pathToRootDir.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, choose correct root directory!");
            alert.showAndWait();
        }


        File pathToResultDir = new File(resultDir.getText());
        if (!pathToResultDir.isFile()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Result file is incorrect");
            alert.showAndWait();
        }

        FileFinder folderEntry = new FileFinder();
        folderEntry.pullFilesFromFolder(pathToRootDir);
        ArrayList<File> txtFiles = folderEntry.getTxtFilesList();
        TxtFileWriter txtFileWriter = new TxtFileWriter(resultDir.getText());
        txtFileWriter.writeTXTFiles(txtFiles);


        List<String> txtLines = new ArrayList<>();
        try{
            txtLines = Files.readAllLines(Paths.get(pathToResultDir.getPath()));
        }
        catch (IOException ex){

        }

        for(String line:txtLines){
            textArea.appendText(line+"\n");
        }


    }



}
