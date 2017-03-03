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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tim on 26.02.2017.
 */
@Controller
public class DetailController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    private static String GRAPH_FOLDER = System.getProperty("user.dir")+"\\graph\\";

    @RequestMapping("/detail")
    public String details(@RequestParam(value="id", required=false, defaultValue="") String aktenzeichen, Model model) {

        // Daten aus der Datenbank holen
        Metadata metadata = metadataDao.findByAktenzeichen(aktenzeichen);
        Dokument dokument = dokumentDao.findByAktenzeichen(aktenzeichen);

        if(metadata == null || dokument == null) {
            model.addAttribute("aktenzeichen", aktenzeichen);
            model.addAttribute("message", "Kein Volltext zu diesem Eintrag vorhanden!");
            return "detail";
        }

        // Daten an das Template übergeben
        model.addAttribute("aktenzeichen", metadata.getAktenzeichen());
        model.addAttribute("datum", metadata.getDatum());
        model.addAttribute("typ", metadata.getTyp());
        model.addAttribute("text", dokument.getText());

        // Darstellung der Entscheidung mit allen relevanten Informationen
        return "detail";
    }
}
