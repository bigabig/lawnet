package webapp.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 27.02.2017.
 */
public class FileList {

    private List<String> filelist = new ArrayList<String>();

    public FileList(String path)
    {
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {

                filelist.add(line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String> getFileList() {
        return filelist;
    }

}
