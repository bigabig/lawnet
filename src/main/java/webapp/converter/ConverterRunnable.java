package webapp.converter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import webapp.models.Dokument;
import webapp.models.DokumentDao;
import webapp.models.Metadata;
import webapp.models.MetadataDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by tim on 27.02.2017.
 */
public class ConverterRunnable implements Runnable {

    private String path;
    private DokumentDao dokumentDao;
    private MetadataDao metadataDao;

    public ConverterRunnable(String path, MetadataDao metadataDao, DokumentDao dokumentDao)
    {
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

            // TEXT gegen die Watson API schicken und Metadaten extrahieren
            String aktenzeichen = "testdemo3";
            String datum = "2017-02-27";
            String typ = "Beschluss";

            Metadata m = new Metadata(aktenzeichen, datum, typ);
            metadataDao.save(m);

            Dokument d = new Dokument(aktenzeichen, content);
            dokumentDao.save(d);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

