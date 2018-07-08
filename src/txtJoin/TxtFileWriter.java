package txtJoin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TxtFileWriter {

    private File file;

    public TxtFileWriter(String pathname) {
        this.file = new File(pathname);
    }

    public void writeTXTFiles(ArrayList<File> files) {


        try {

            for (File txtFile : files) {
                BufferedReader in = new BufferedReader(new FileReader(txtFile.getAbsolutePath()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
                String s;
                while ((s = in.readLine()) != null)
                    out.println(s);
                out.close();
                in.close();
            }

        } catch (IOException e) {
            e.getMessage();
        }

    }

    public File getFile() {
        return file;
    }
}
