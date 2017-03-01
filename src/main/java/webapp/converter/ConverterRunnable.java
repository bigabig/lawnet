package webapp.converter;

import org.apache.commons.io.IOUtils;
import webapp.models.*;
import webapp.models.watson.WatsonHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by tim on 27.02.2017.
 */
public class ConverterRunnable implements Runnable {

    private String filename;
    private String path;
    private DokumentDao dokumentDao;
    private MetadataDao metadataDao;
    private ZitatDao zitatDao;

    public ConverterRunnable(String filename, String path, MetadataDao metadataDao, DokumentDao dokumentDao, ZitatDao zitatDao)
    {
        this.filename = filename;
        this.path = path;
        this.metadataDao = metadataDao;
        this.dokumentDao = dokumentDao;
        this.zitatDao = zitatDao;
    }

    @Override
    public void run() {
        try {
            // PDF in Text umwandeln
            ProcessBuilder pb = new ProcessBuilder(System.getProperty("user.dir") + "\\helper\\pdftotext.exe", "-enc", "UTF-8", path);
            Process p = pb.start();
            p.waitFor();

            String textPath = path.replace(".pdf", ".txt");
            String cleanPath = textPath.replace(".txt", "_clean.txt");

            // Text in cleanText umwandeln
            pb = new ProcessBuilder("java", "-jar", System.getProperty("user.dir")+"\\helper\\converter.jar", textPath, cleanPath, System.getProperty("user.dir")+"\\helper\\shortcuts.txt");
            p = pb.start();
            p.waitFor();

            // cleanText einlesen
            FileInputStream fis = new FileInputStream(cleanPath);
            String content = IOUtils.toString(fis, "UTF-8");

            WatsonHelper watsonHelper = new WatsonHelper();
            // Watson API Call
            String jsonString = watsonHelper.findEntities(content);
            System.out.println(jsonString);

            // Extract Data from JSON String
            Metadata m = watsonHelper.extractMetadata(jsonString, filename);
            Dokument d = new Dokument(m.getAktenzeichen(), filename, content);
            List<Metadata> verweise = watsonHelper.extractVerweis(jsonString);

            // Push Data to the Database
            metadataDao.save(m);
            dokumentDao.save(d);
            for(Metadata meta : verweise) {
                metadataDao.save(meta);
                Zitat z = new Zitat(m.getAktenzeichen(), meta.getAktenzeichen());
                zitatDao.save(z);
            }

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

