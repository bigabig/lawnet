package webapp.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.graph.Draw;
import webapp.graph.Link;
import webapp.graph.Node;
import webapp.models.Metadata;
import webapp.models.MetadataDao;
import webapp.models.Zitat;
import webapp.models.ZitatDao;

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
    private ZitatDao zitatDao;

    private static String GRAPH_FOLDER = System.getProperty("user.dir")+"\\graph\\";

    @RequestMapping("/graph")
    public String find(@RequestParam(value="aktenzeichen", required=false, defaultValue="") String aktenzeichen,
                       @RequestParam(value="datum", required=false, defaultValue="") String datum,
                       @RequestParam(value="typ", required=false, defaultValue="") String typ,
                       @RequestParam(value="zitat", required=false, defaultValue="active") String zitat, Model model) {

        // MySQL-Anfrage: Alle Metadaten mit dem Datum datum
        List<Metadata> meta = metadataDao.findByDatum(datum);

        String data = metaToJSON(meta, zitat);

        String filename = "datum-"+datum+"-suche.json";
        saveJSONFile(filename, data);

        // Übergeben des Filenames an Template
        model.addAttribute("filename", filename);

        return "graph";
    }

    private String metaToJSON(List<Metadata> meta, String zitat) {
        // Umwandlung der MySQL-Anfrage in JSON
        List<Node> nodes = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        for(Metadata m : meta) {
            Node n = new Node(m.getAktenzeichen(), 1);
            nodes.add(n);

            List<Zitat> zitate = null;
            if(zitat.equals("active")) {
                zitate = zitatDao.findByAktenzeichen1(m.getAktenzeichen());
                System.out.println("active!");
            } else if (zitat.equals("passiv")) {
                zitate = zitatDao.findByAktenzeichen2(m.getAktenzeichen());
                System.out.println("passiv!");
            }
            System.out.println(zitate);

            if(zitate != null) {
                for(Zitat z : zitate) {
                    Link l = new Link(z.getAktenzeichen1(), z.getAktenzeichen2(), 5);
                    links.add(l);

                    Metadata metadata = null;
                    if(zitat.equals("active")) {
                        metadata = metadataDao.findByAktenzeichen(z.getAktenzeichen2());
                    } else if(zitat.equals("passiv")) {
                        metadata = metadataDao.findByAktenzeichen(z.getAktenzeichen1());
                    }

                    Node node = new Node(metadata.getAktenzeichen(), 1);
                    if(!nodes.contains(node))
                        nodes.add(node);
                }
            }
        }
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
}
