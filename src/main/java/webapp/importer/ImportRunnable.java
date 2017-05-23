package webapp.importer;

import com.ibm.watson.developer_cloud.service.exception.InternalServerErrorException;
import org.apache.commons.io.IOUtils;
import webapp.models.*;

import webapp.models.watson.WatsonHelper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Created by tim on 28.02.2017.
 */
public class ImportRunnable implements Runnable {

    private static String FILE_FOLDER;
    private static String JSON_FOLDER;
    private List<String> files;
    private MetadataDao metadataDao;
    private DokumentDao dokumentDao;
    private ZitatDao zitatDao;

    //Save the uploaded file to this folder
    //private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Upload\\";
    private static String UPLOADED_FOLDER;

    static {
        Properties properties = new Properties();
        try {
            InputStream is = WatsonHelper.class.getResourceAsStream("/watson.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FILE_FOLDER = properties.getProperty("importpath");
        JSON_FOLDER = properties.getProperty("jsonpath");
    }

    public ImportRunnable(String path, MetadataDao metadataDao, DokumentDao dokumentDao, ZitatDao zitatDao) {
        this.files = new FileList(path).getFileList();
        this.metadataDao = metadataDao;
        this.dokumentDao = dokumentDao;
        this.zitatDao = zitatDao;
    }

    @Override
    public void run() {

        for(String filename : files) {

            Dokument de = dokumentDao.findByDateiname(filename);
            if(de != null) {
                System.out.println(filename + " - Datei bereits vorhanden!");
                continue;
            }

            FileInputStream fis = null;
            String content = null;
            try {
                fis = new FileInputStream(new File(FILE_FOLDER, filename));
                content = IOUtils.toString(fis, "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            if(content == null || content.equals("") || content.isEmpty()) {
                continue;
            }

            System.out.println("Processing file: "+filename);
            System.out.println("Content: "+content);

            WatsonHelper watsonHelper = new WatsonHelper();

            // Watson API Call
            String jsonString = "";
            try {
                jsonString = watsonHelper.findEntities(content);
                System.out.println("JSON: " + jsonString);
            } catch (InternalServerErrorException e) {
                System.out.println("Error processing file "+filename);
                writeToJsonFolder("ERROR_"+filename, "Error processing file "+filename);
                continue;
            }

            if(jsonString.isEmpty()) {
                continue;
            }

            // Save JSON String
            writeToJsonFolder(filename+".json", jsonString);

            // Extract Metadata from JSON String
            Metadata m = watsonHelper.extractMetadata(jsonString, filename);
            Dokument d = new Dokument(m.getAktenzeichen(), filename, content);
            List<Metadata> verweise = watsonHelper.extractVerweis(jsonString);

            metadataDao.save(m);
            dokumentDao.save(d);
            for(Metadata meta : verweise) {
                metadataDao.save(meta);
                Zitat z = new Zitat(m.getAktenzeichen(), meta.getAktenzeichen());
                zitatDao.save(z);
            }
        }

    }

    private void writeToJsonFolder(String filename, String content) {
        File f = new File(JSON_FOLDER, filename);
        f.getParentFile().mkdirs();

        try(  PrintWriter out = new PrintWriter( f.getPath())  ){
            out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
