package webapp.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ListUtils;
import webapp.graph.Draw;
import webapp.graph.Link;
import webapp.graph.Node;
import webapp.models.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by tim on 23.02.2017.
 */
@Controller
public class GraphController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private ZitatDao zitatDao;

    private static String GRAPH_FOLDER = System.getProperty("user.dir")+"\\graphen\\";

    @RequestMapping("/graph")
    public String find(@RequestParam(value="filename", required=false, defaultValue="") String dateiname,
                       @RequestParam(value="aktenzeichen", required=false, defaultValue="") String aktenzeichen,
                       @RequestParam(value="datum", required=false, defaultValue="") String datum,
                       @RequestParam(value="typ", required=false, defaultValue="") String typ,
                       @RequestParam(value="zitat", required=false, defaultValue="active") String zitat, Model model) {

        String message = "Aktenzeichen: "+aktenzeichen+"\nDatum: "+datum+"\nTyp: "+typ+"\nZitat: "+zitat;
        model.addAttribute("message", message);
        System.out.println(message);
        String filename = "";

        // MySQL-Abfrage abhängig vom Request
        List<Metadata> meta = null;

        if(dateiname.equals("") && aktenzeichen.equals("") && datum.equals("") && typ.equals("") ) {
            return "graph";
        } else if (!dateiname.equals("")) {
            Dokument d = dokumentDao.findByDateiname(dateiname);
            meta = new ArrayList<Metadata>();
            Metadata m = metadataDao.findByAktenzeichen(d.getAktenzeichen());
            if(m != null)
                meta.add(m);
            filename = "dateiname-"+dateiname+"-suche.json";
        } else if (aktenzeichen.equals("") && datum.equals("") && typ.equals("") ) {
            return "graph";
        } else if(!aktenzeichen.equals("")) {
            meta = metadataDao.findByAktenzeichenStartingWith(aktenzeichen);
            filename = "aktenzeichen-"+aktenzeichen.replace(":","_")+"-suche.json";
        } else if(!datum.equals("") && !typ.equals("")) {
            meta = metadataDao.findByDatumAndTyp(datum, typ);
            filename = "datumtyp-"+datum+typ+"-suche.json";
        } else if(!datum.equals("")) {
            meta = metadataDao.findByDatum(datum);
            filename = "datum-"+datum+"-suche.json";
        } else if(!typ.equals("")) {
            meta = metadataDao.findByTyp(typ);
            filename = "typ-"+typ+"-suche.json";
        }

        if(meta == null || meta.isEmpty() || !(zitat.equals("active") || zitat.equals("passiv"))) {
            model.addAttribute("fehler", "Zu dieser Suchanfrage gibt es leider keine Ergebnisse!");
            return "graph";
        }

        // Metadaten nach JSON-String umwandeln
        String data = metaToJSON(meta, zitat, model);

        // JSON-String als JSON-Datei abspeichern
        saveJSONFile(filename, data);

        // Übergeben des Filenames an Template
        model.addAttribute("filename", filename);

        // Darstellung der JSON-Datei als Graph
        return "graph";
    }

    private String metaToJSON(List<Metadata> meta, String zitat, Model model) {
        List<Metadata> metaStatistik = new ArrayList<>();
        metaStatistik.addAll(meta);

        // Umwandlung der MySQL-Anfrage in JSON
        List<Node> nodes = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        for(Metadata m : meta) {
            Node n = new Node(m.getAktenzeichen(), getColor(m.getTyp()), 7);
            nodes.add(n);
        }

        for(Metadata m : meta) {
            List<Zitat> zitate = null;
            if (zitat.equals("active")) {
                zitate = zitatDao.findByAktenzeichen1(m.getAktenzeichen());
            } else if (zitat.equals("passiv")) {
                zitate = zitatDao.findByAktenzeichen2(m.getAktenzeichen());
            }

            if (zitate != null) {
                for (Zitat z : zitate) {
                    Link l = new Link(z.getAktenzeichen1(), z.getAktenzeichen2(), 5);
                    links.add(l);

                    Metadata metadata = null;
                    if (zitat.equals("active")) {
                        metadata = metadataDao.findByAktenzeichen(z.getAktenzeichen2());
                    } else if (zitat.equals("passiv")) {
                        metadata = metadataDao.findByAktenzeichen(z.getAktenzeichen1());
                    }

                    if (metadata != null) {
                        metaStatistik.add(metadata);
                        Node node = new Node(metadata.getAktenzeichen(), getColor(metadata.getTyp()), 4);
                        if (!nodes.contains(node))
                            nodes.add(node);
                    }
                }
            }
        }

        Metadata[] statistik =  test(metaStatistik);
        List<String> result = new ArrayList<>();
        for(Metadata stat : statistik) {
            if(stat != null) {
                result.add("Top " + stat.getTyp() + ": " + stat.getAktenzeichen());
            }
        }

        model.addAttribute("statistik", result);

        Draw d = new Draw(nodes, links);

        Gson g = new Gson();
        return g.toJson(d);
    }

    private void saveJSONFile(String filename, String jsonString) {
        // Speicherort für das Ergebnis (JSON Datei)
        File f = new File(GRAPH_FOLDER + filename);
        f.getParentFile().mkdirs();

        // Schreiben des Ergebnis nach /graph
        try {
            FileWriter fw = new FileWriter(f.getPath());
            fw.write(jsonString);
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private int getColor(String typ) {
        switch(typ) {
            case "Urteil":
                return 1;
            case "Beschluss":
                return 2;
            case "Verweis":
                return 3;
            case "Norm":
                return 4;
        }
        return 0;
    }

    private Metadata[] test(List<Metadata> meta) {
        Metadata[] top = new Metadata[4]; // Urteil = 0, Beschluss = 1, Verweis = 2, Norm = 3

        int tu = 0;
        int tb = 0;
        int tv = 0;
        int tn = 0;

        for(Metadata m : meta) {
            List<Zitat> zitierungen = zitatDao.findByAktenzeichen2(m.getAktenzeichen());
            int anzahl = 0;

            for(Zitat z : zitierungen) {
                Metadata lel = metadataDao.findByAktenzeichen(z.getAktenzeichen1());
                if(meta.contains(lel))
                    anzahl = anzahl + 1;
            }

            if(anzahl < 2)
                continue;
            switch(m.getTyp()) {
                case "Urteil":
                    if(anzahl > tu) {
                        tu = anzahl;
                        top[0] = m;
                    }
                    break;
                case "Beschluss":
                    if(anzahl > tb) {
                        tb = anzahl;
                        top[1] = m;
                    }
                    break;
                case "Verweis":
                    if(anzahl > tv) {
                        tv = anzahl;
                        top[2] = m;
                    }
                    break;
                case "Norm":
                    if(anzahl > tn) {
                        tn = anzahl;
                        top[3] = m;
                    }
                    break;
            }
        }

        return top;
    }
}
