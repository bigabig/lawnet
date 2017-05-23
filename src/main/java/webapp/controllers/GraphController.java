package webapp.controllers;

import com.google.gson.Gson;
import org.apache.commons.collections4.bag.HashBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.graph.Draw;
import webapp.graph.Link;
import webapp.graph.Node;
import webapp.models.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
    private ServletContext servletContext;

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private ZitatDao zitatDao;

    // private static String GRAPH_FOLDER = System.getProperty("user.dir")+"\\graphen\\";

    @RequestMapping(value={"/","/graph"})
    public String find(@RequestParam(value="filename", required=false, defaultValue="") String dateiname,
                       @RequestParam(value="aktenzeichen", required=false, defaultValue="") String aktenzeichen,
                       @RequestParam(value="datum", required=false, defaultValue="") String datum,
                       @RequestParam(value="typ", required=false, defaultValue="") String typ,
                       @RequestParam(value="zitat", required=false, defaultValue="active") String zitat, Model model) {

        model.addAttribute("module", "graph");

        String message = "Aktenzeichen: "+aktenzeichen+"\nDatum: "+datum+"\nTyp: "+typ+"\nZitat: "+zitat;
        model.addAttribute("aktenzeichen", aktenzeichen);
        model.addAttribute("datum", datum);
        model.addAttribute("typ", typ);
        model.addAttribute("zitat", zitat);
        model.addAttribute("message", message);
        System.out.println(message);
        String filename = "error.json";

        // MySQL-Abfrage abhängig vom Request
        // Generierung des Dateinamens abhängig vom Request
        List<Object[]> result = null;
        if(dateiname.equals("") && aktenzeichen.equals("") && datum.equals("") && typ.equals("") ) {
            return "graph";
        } else if (!dateiname.equals("")) {
            result = metadataDao.documentQuery(dateiname);
            filename = "dateiname-"+dateiname+"-suche.json";
        } else if (aktenzeichen.equals("") && datum.equals("") && typ.equals("") ) {
            return "graph";
        } else if(!aktenzeichen.equals("")) {
            if(zitat.equals("active")) {
                result = metadataDao.activeAktQuery(aktenzeichen);
                filename = "aktenzeichen-active-"+aktenzeichen.replace(":","_")+"-suche.json";
            } else if (zitat.equals("passiv")) {
                result = metadataDao.passivAktQuery(aktenzeichen);
                filename = "aktenzeichen-passiv-"+aktenzeichen.replace(":","_")+"-suche.json";
            } else if (zitat.equals("actpas")) {
                result = metadataDao.activeAktQuery(aktenzeichen);
                result.addAll(metadataDao.passivAktQuery(aktenzeichen));
                filename = "aktenzeichen-actpas-"+aktenzeichen.replace(":","_")+"-suche.json";
            }
        } else if(!datum.equals("") && !typ.equals("")) {
            if(zitat.equals("active")) {
                result = metadataDao.activeDatTypQuery(datum, typ);
                filename = "datumtyp-active-"+datum+typ+"-suche.json";
            } else if (zitat.equals("passiv")) {
                result = metadataDao.passivDatTypQuery(datum, typ);
                filename = "datumtyp-passiv-"+datum+typ+"-suche.json";
            } else if (zitat.equals("actpas")) {
                result = metadataDao.activeDatTypQuery(datum, typ);
                result.addAll(metadataDao.passivDatTypQuery(datum, typ));
                filename = "datumtyp-actpas-"+datum+typ+"-suche.json";
            }
        } else if(!datum.equals("")) {
            if(zitat.equals("active")) {
                result = metadataDao.activeDatQuery(datum);
                filename = "datum-active-"+datum+"-suche.json";
            } else if (zitat.equals("passiv")) {
                result = metadataDao.passivDatQuery(datum);
                filename = "datum-passiv-"+datum+"-suche.json";
            } else if (zitat.equals("actpas")) {
                result = metadataDao.activeDatQuery(datum);
                result.addAll(metadataDao.passivDatQuery(datum));
                filename = "datum-actpas-"+datum+"-suche.json";
            }
        } else if(!typ.equals("")) {
            if(zitat.equals("active")) {
                result = metadataDao.activeTypQuery(typ);
                filename = "typ-active-"+typ+"-suche.json";
            } else if (zitat.equals("passiv")) {
                result = metadataDao.passivTypQuery(typ);
                filename = "typ-passiv-"+typ+"-suche.json";
            } else if (zitat.equals("actpas")) {
                result = metadataDao.activeTypQuery(typ);
                result.addAll(metadataDao.passivTypQuery(typ));
                filename = "typ-actpas-"+typ+"-suche.json";
            }
        }

        if(result == null || result.isEmpty() || !(zitat.equals("active") || zitat.equals("passiv") || zitat.equals("actpas"))) {
            model.addAttribute("fehler", "Zu dieser Suchanfrage gibt es leider keine Ergebnisse!");
            return "graph";
        }

        HashSet<Node> nodeSet = new HashSet<Node>();
        List<Link> links = new ArrayList<Link>();
        HashBag<Node> bag = new HashBag<>();

        // 0 = Ak1 | 1 = Dat1 | 2 = Typ1 | 3 = Ak2 | 4 = Dat2 | 5 = Typ2
        for(Object[] objects : result) {

            String ak1 = (String) objects[0];
            String ak2 = (String) objects[3];
            String typ1 = (String) objects[2];
            String typ2 = (String) objects[5];

            Node n1 = new Node(ak1, getColor(typ1), 7);
            Node n2 = new Node(ak2, getColor(typ2), 4);

            nodeSet.add(n1);
            nodeSet.add(n2);

            if(zitat.equals("active")) {
                bag.add(n2);
            } else if (zitat.equals("passiv")) {
                bag.add(n1);
            } else if (zitat.equals("actpas")) {
                bag.add(n1);
                bag.add(n2);
            }

            links.add(new Link(ak1, ak2, 5));
        }

        List<Node> nodes = new ArrayList<>();
        for(Node n : nodeSet) {
            nodes.add(n);
        }

        Node[] topNodes = new Node[5]; // Urteil = 1, Beschluss = 2, Verweis = 3, Norm = 4
        int[] topCount = new int[5];
        for(Node n : bag) {
            int group = n.getGroup();
            int count = bag.getCount(n);

            if(count > 1 && count > topCount[group]) {
                topCount[group] = count;
                topNodes[group] = n;
            }
        }

        List<String> statistik = new ArrayList<>();
        for(Node node : topNodes) {
            if(node != null) {
                statistik.add("Top " + getTypName(node.getGroup()) + ": " + node.getId());
            }
        }

        Draw graph = new Draw(nodes, links);
        Gson g = new Gson();
        String data = g.toJson(graph);
        System.out.println(data);

        /*



        List<Metadata> allMetadata = findAllMetadata(meta, zitat);

        // Metadaten nach JSON-String umwandeln
        String data = metaToJSON(meta, allMetadata, zitat);


        // JSON-String als JSON-Datei abspeichern
        saveJSONFile(filename, data);

        */

        // Statistik erheben
        model.addAttribute("statistik", statistik);

        // JSON-String als JSON-Datei abspeichern
        saveJSONFile(filename, data);

        // Übergeben des Filenames an Template
        model.addAttribute("filename", filename);

        // Darstellung der JSON-Datei als Graph
        return "graph";
    }


    // ALL METADATA

    private List<Zitat> getZitate(Metadata metadata, String zitat) {
        List<Zitat> zitate = null;
        if (zitat.equals("actpas")) {
            zitate = zitatDao.findByAktenzeichen1(metadata.getAktenzeichen());
            zitate.addAll(zitatDao.findByAktenzeichen2(metadata.getAktenzeichen()));
        } else if (zitat.equals("active")) {
            zitate = zitatDao.findByAktenzeichen1(metadata.getAktenzeichen());
        } else if (zitat.equals("passiv")) {
            zitate = zitatDao.findByAktenzeichen2(metadata.getAktenzeichen());
        }
        return zitate;
    }

    private List<Metadata> findAllMetadata(List<Metadata> meta, String zitat)  {
        List<Metadata> result = new ArrayList<>();
        result.addAll(meta);

        for(Metadata m : meta) {

            List<Zitat> zitate = getZitate(m, zitat);
            if(zitate == null)
                continue;

            for(Zitat z : zitate) {

                Metadata[] newMeta = new Metadata[2];
                if (zitat.equals("actpas")) {
                    newMeta[0] = metadataDao.findByAktenzeichen(z.getAktenzeichen2());
                    newMeta[1] = metadataDao.findByAktenzeichen(z.getAktenzeichen1());
                } else if (zitat.equals("active")) {
                    newMeta[0] = metadataDao.findByAktenzeichen(z.getAktenzeichen2());
                } else if (zitat.equals("passiv")) {
                    newMeta[0] = metadataDao.findByAktenzeichen(z.getAktenzeichen1());
                }

                for(Metadata nw : newMeta) {
                    if (nw == null)
                        continue;

                    if(!result.contains(nw))
                        result.add(nw);
                }
            }
        }
        return result;
    }

    // STATISTICS

    private List<String> makeStatistic(List<Metadata> allMetadata) {
        Metadata[] statistik =  test(allMetadata);

        List<String> result = new ArrayList<>();
        for(Metadata stat : statistik) {
            if(stat != null) {
                result.add("Top " + stat.getTyp() + ": " + stat.getAktenzeichen());
            }
        }

        return result;
    }

    private String metaToJSON(List<Metadata> meta, List<Metadata> allMetadata, String zitat) {
        List<Node> nodes = new ArrayList<>();
        List<Link> links = new ArrayList<>();

        for(Metadata m : allMetadata) {
            Node n = null;
            if(meta.contains(m))
                n = new Node(m.getAktenzeichen().toLowerCase(), getColor(m.getTyp()), 7);
            else
                n = new Node(m.getAktenzeichen().toLowerCase(), getColor(m.getTyp()), 4);
            nodes.add(n);

            List<Zitat> zitate = zitatDao.findByAktenzeichen1(m.getAktenzeichen());
            for(Zitat z : zitate) {
                if(allMetadata.contains(new Metadata(z.getAktenzeichen2()))) {
                    Link l = new Link(z.getAktenzeichen1().toLowerCase(), z.getAktenzeichen2().toLowerCase(), 5);
                    links.add(l);
                }
            }
        }
        Draw d = new Draw(nodes, links);
        Gson g = new Gson();
        return g.toJson(d);
    }

    private void saveJSONFile(String filename, String jsonString) {
        // Speicherort für das Ergebnis (JSON Datei)
        File f = new File(servletContext.getRealPath("graphen"), filename);
        f.getParentFile().mkdirs();
        System.out.println(f.getAbsolutePath());

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

    private String getTypName(int i) {
        switch(i) {
            case 1:
                return "Urteil";
            case 2:
                return "Beschluss";
            case 3:
                return "Verweis";
            case 4:
                return "Norm";
        }
        return "error";
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
