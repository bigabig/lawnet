package webapp.converter;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.AlchemyService;
import org.apache.commons.io.IOUtils;
import webapp.models.Dokument;
import webapp.models.DokumentDao;
import webapp.models.Metadata;
import webapp.models.MetadataDao;
import webapp.watson.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

            // Watson API Initialisierung
            AlchemyLanguage service = new AlchemyLanguage();
            service.setApiKey("da7ac0bb06b2486850487c777387f1b7c3f6fe85");

            // Watson API Input
            Map<String,Object> params = new HashMap<String, Object>();
            params.put(AlchemyLanguage.TEXT, "IBM Watson won the Jeopardy television show hosted by Alex Trebek");

            // Watson API Call
            Entities entities = service.getEntities(params).execute();
            String jsonString = entities.toString();

            Gson gson = new Gson();
            Response r = gson.fromJson(jsonString, Response.class);
            System.out.println(r.language +" "+ r.entities[0].text);

            // TEXT gegen die Watson API schicken und Metadaten extrahieren
            String aktenzeichen = filename;
            String datum = "2017-02-27";
            String typ = "Beschluss";

            Metadata m = new Metadata(aktenzeichen, datum, typ);
            metadataDao.save(m);

            Dokument d = new Dokument(aktenzeichen, filename, content);
            dokumentDao.save(d);

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

