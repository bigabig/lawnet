package webapp.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp.graph.Draw;
import webapp.graph.Link;
import webapp.graph.Node;
import webapp.models.Metadata;
import webapp.models.MetadataDao;
import webapp.models.Zitat;
import webapp.models.ZitatDao;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by tim on 23.02.2017.
 */
@Controller
public class TestController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private ZitatDao zitatDao;

    private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Graph\\";

    @RequestMapping("/graph")
    public String find(@RequestParam(value="datum", required=false, defaultValue="2016-01-01") String datum, Model model) {
        List<Metadata> meta = null;
        try {
            meta = metadataDao.findByDatum(datum);

            File f = new File(UPLOADED_FOLDER + "datum-"+datum+"-suche.json");
            f.getParentFile().mkdirs();

            List<Node> nodes = new ArrayList<>();
            List<Link> links = new ArrayList<>();
            for(Metadata m : meta) {
                Node n = new Node(m.getAktenzeichen(), 1);

                List<Zitat> zitate = zitatDao.findByAktenzeichen1(m.getAktenzeichen());

                for(Zitat z : zitate) {
                    Link l = new Link(z.getAktenzeichen1(), z.getAktenzeichen2(), 5);
                    links.add(l);

                    Metadata metadata = metadataDao.findByAktenzeichen(z.getAktenzeichen2());
                    Node node = new Node(metadata.getAktenzeichen(), 1);
                    if(!nodes.contains(node))
                        nodes.add(node);
                }

                nodes.add(n);
            }
            Draw d = new Draw(nodes, links);

            Gson g = new Gson();
            String data = g.toJson(d);

            FileWriter fw = new FileWriter(f.getPath());
            fw.write(data);
            fw.close();

            String jsPath = f.getPath();
            model.addAttribute("graph", jsPath);
        }
        catch (Exception ex) {
            return "Error";
        }
        return "graph";
    }

}
