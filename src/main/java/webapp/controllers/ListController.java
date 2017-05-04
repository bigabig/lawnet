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
import webapp.models.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tim on 23.02.2017.
 */
@Controller
public class ListController {

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

    @RequestMapping("/list")
    public String list(@RequestParam(value="typ", required=false, defaultValue="") String typ, Model model) {
        model.addAttribute("module", "list");
        model.addAttribute("typ", typ);

        if(typ == null || typ.equals("")) {
            return "list";
        }

        String message = "Sie haben nach dem Typ "+typ.substring(0, 1).toUpperCase() + typ.substring(1)+" gesucht. Hier sind Ihre Ergebnisse:";
        model.addAttribute("message", message);
        System.out.println(message);

        List<Metadata> meta = metadataDao.findByTyp(typ);
        List<String> messages = new ArrayList<>();

        for(Metadata m : meta) {
            messages.add(m.getAktenzeichen());
        }

        model.addAttribute("messages", messages);
        return "list";
    }
}
