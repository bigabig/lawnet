package webapp.models.watson;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;

import com.ibm.watson.developer_cloud.service.exception.InternalServerErrorException;
import webapp.models.Metadata;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by tim on 28.02.2017.
 */
public class WatsonHelper {

    private NaturalLanguageUnderstanding service;
    private String model;

    public WatsonHelper() {
        // Watson API Initialisierung
        service = new NaturalLanguageUnderstanding(NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27);

        Properties properties = new Properties();
        try {
            InputStream is = WatsonHelper.class.getResourceAsStream("/watson.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String user = properties.getProperty("user");
        String pass = properties.getProperty("pass");
        service.setUsernameAndPassword(user, pass);

        model = properties.getProperty("model");
    }

    public String findEntities(String content) {
        // Watson API Input
        EntitiesOptions entities= new EntitiesOptions.Builder()
                .sentiment(false)
                .model(model)
                .limit(250)
                .build();

        Features features = new Features.Builder()
                .entities(entities)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(content)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        return response.toString();
    }

    public Metadata extractMetadata(String jsonString, String alternativAktenzeichen) {
        // Map jsonString to Response Object
        Gson gson = new Gson();
        Response r = gson.fromJson(jsonString, Response.class);

        // Metadaten aus Response extrahieren
        String aktenzeichen = alternativAktenzeichen;
        LocalDate datum = null;
        String typ = "UNDEFINED";

        for(Entity e : r.entities) {
            if(e.getType().equals("ECLI")) {
                aktenzeichen = e.getText();
            }
            if(e.getType().equals("Entscheidungsart") && (e.getText().equals("URTEIL") || e.getText().equals("BESCHLUSS"))) {
                String string = e.getText().toLowerCase();
                typ = Character.toUpperCase(string.charAt(0)) + string.substring(1, string.length());
            }
            if(e.getType().equals("Datum")) {
                String string = e.getText();
                LocalDate date = null;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.GERMAN);
                    date = LocalDate.parse(string, formatter);
                } catch (Exception ex) {
                    ex.printStackTrace();;
                    continue;
                }
                if(date != null && (datum == null || datum.isBefore(date)))
                    datum = date;
            }
        }

        if(datum == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.GERMAN);
            datum = LocalDate.parse("01-01-9999", formatter);
        }

        return new Metadata(aktenzeichen, datum.toString(), typ);
    }

    public List<Metadata> extractVerweis(String jsonString) {
        Gson gson = new Gson();
        Response r = gson.fromJson(jsonString, Response.class);

        List<Metadata> result = new ArrayList<>();

        for(Entity e: r.entities) {
            if(e.getType().equals("Verweis") || e.getType().equals("Norm")) {
                Metadata m = new Metadata();
                m.setAktenzeichen(e.getText());
                m.setTyp(e.getType());
                result.add(m);
            }
        }

        return result;
    }
}
