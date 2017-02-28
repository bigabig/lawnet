package webapp.importer;

import org.apache.commons.io.IOUtils;
import webapp.models.Dokument;
import webapp.models.DokumentDao;
import webapp.models.Metadata;
import webapp.models.MetadataDao;

import webapp.models.watson.WatsonHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by tim on 28.02.2017.
 */
public class ImportRunnable implements Runnable {

    private static String FILE_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Files\\";
    private List<String> files;
    private MetadataDao metadataDao;
    private DokumentDao dokumentDao;

    public ImportRunnable(String path, MetadataDao metadataDao, DokumentDao dokumentDao) {
        this.files = new FileList(path).getFileList();
        this.metadataDao = metadataDao;
        this.dokumentDao = dokumentDao;
    }

    @Override
    public void run() {

        for(String filename : files) {

            FileInputStream fis = null;
            String content = null;
            try {
                fis = new FileInputStream(FILE_FOLDER + filename);
                content = IOUtils.toString(fis, "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WatsonHelper watsonHelper = new WatsonHelper();

            // Watson API Call
            String jsonString = watsonHelper.findEntities(content);

            // Extract Metadata from JSON String
            Metadata m = watsonHelper.extractMetadata(jsonString, filename);
            Dokument d = new Dokument(m.getAktenzeichen(), filename, content);

            metadataDao.save(m);
            dokumentDao.save(d);
        }

    }
}
