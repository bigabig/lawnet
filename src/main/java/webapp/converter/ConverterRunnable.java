package webapp.converter;

import org.apache.commons.io.IOUtils;
import webapp.models.Dokument;
import webapp.models.DokumentDao;
import webapp.models.Metadata;
import webapp.models.MetadataDao;
import webapp.models.watson.WatsonHelper;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by tim on 27.02.2017.
 */
public class ConverterRunnable implements Runnable {

    private String filename;
    private String path;
    private DokumentDao dokumentDao;
    private MetadataDao metadataDao;

    public ConverterRunnable(String filename, String path, MetadataDao metadataDao, DokumentDao dokumentDao)
    {
        this.filename = filename;
        this.path = path;
        this.metadataDao = metadataDao;
        this.dokumentDao = dokumentDao;
    }

    @Override
    public void run() {
        try {
            // PDF in Text umwandeln
            ProcessBuilder pb = new ProcessBuilder(System.getProperty("user.dir") + "\\helper\\pdftotext.exe", "-enc", "UTF-8", path);
            Process p = pb.start();
            p.waitFor();

            String textPath = path.replace(".pdf", ".txt");

            // Text in cleanText umwandeln
            pb = new ProcessBuilder("java", "-jar", System.getProperty("user.dir")+"\\helper\\converter.jar", textPath, textPath.replace(".txt", "_clean.txt"));
            p = pb.start();
            p.waitFor();

            String cleanPath = textPath.replace(".txt", "_clean.txt");

            // cleanText einlesen
            FileInputStream fis = new FileInputStream(cleanPath);
            String content = IOUtils.toString(fis, "UTF-8");

            WatsonHelper watsonHelper = new WatsonHelper();
            // Watson API Call
            String jsonString = watsonHelper.findEntities(content);

            // Extract Metadata from JSON String
            Metadata m = watsonHelper.extractMetadata(jsonString, filename);
            Dokument d = new Dokument(m.getAktenzeichen(), filename, content);

            // Push Data to the Database
            metadataDao.save(m);
            dokumentDao.save(d);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

