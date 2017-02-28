package webapp.models.watson;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.alchemy.v1.AlchemyLanguage;
import com.ibm.watson.developer_cloud.alchemy.v1.model.Entities;
import webapp.models.Metadata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tim on 28.02.2017.
 */
public class WatsonHelper {

    private static String API_KEY = "da7ac0bb06b2486850487c777387f1b7c3f6fe85";
    private static String MODEL_ID = "rb:05cecb13-8336-45ad-93c2-1a417c6dcd99";

    private AlchemyLanguage service;

    public WatsonHelper() {
        // Watson API Initialisierung
        service = new AlchemyLanguage();
        service.setApiKey(WatsonHelper.API_KEY);
    }

    public String findEntities(String content) {
        // Watson API Input
        Map<String,Object> params = new HashMap<String, Object>();
        params.put(AlchemyLanguage.MODEL_ID, WatsonHelper.MODEL_ID);
        params.put(AlchemyLanguage.TEXT, content);

        return service.getEntities(params).execute().toString();
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
                typ = e.getText();
            }
            if(e.getType().equals("Datum")) {
                String string = e.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.GERMAN);
                LocalDate date = LocalDate.parse(string, formatter);
                System.out.println(date); // 2010-01-02
                if(datum == null || datum.isBefore(date))
                    datum = date;
            }
        }

        return new Metadata(aktenzeichen, datum.toString(), typ);
    }
}
