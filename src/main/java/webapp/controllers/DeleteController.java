package webapp.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author Tim Fischer
 */
@Controller
public class DeleteController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private ZitatDao zitatDao;


    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="aktenzeichen", required=false, defaultValue="") String aktenzeichen, Model model) {
        List<String> messages = new ArrayList<>();

        if(aktenzeichen.equals("")) {
            return "delete";
        }

        Metadata metadata = metadataDao.findByAktenzeichen(aktenzeichen);

        if(metadata == null) {
            model.addAttribute("message", "Dieses Aktenzeichen existiert nicht in der Datenbank!");
            return "delete";
        }

        Dokument dokument = dokumentDao.findByAktenzeichen(aktenzeichen);
        List<Zitat> meineZitate = zitatDao.findByAktenzeichen1(aktenzeichen);
        List<Zitat> meineErwähnungen = zitatDao.findByAktenzeichen2(aktenzeichen);

        messages.add("Metadata " + aktenzeichen);
        if(dokument != null)
            messages.add("Dokument " + dokument.getDateiname());

        for(Zitat zitat : meineZitate) {
            String aktenzeichen2 = zitat.getAktenzeichen2();

            messages.add("Zitat " + aktenzeichen + " -> " + aktenzeichen2);
            zitatDao.delete(zitat);

            if(zitatDao.findByAktenzeichen1(aktenzeichen2).size() > 0) {
                //nicht löschen! zitiert noch andere dokumente
                continue;
            }
            if(zitatDao.findByAktenzeichen2(aktenzeichen2).size() > 0) {
                // nicht löschen! wird noch von anderen dokumenten zitiert
                continue;
            }

            Metadata m = metadataDao.findByAktenzeichen(aktenzeichen2);
            messages.add("zitierte Metadaten " + aktenzeichen2);
            metadataDao.delete(m);
        }

        for(Zitat zitat : meineErwähnungen) {
            messages.add("Erwähnung " + zitat.getAktenzeichen1() + " -> " + zitat.getAktenzeichen2());
            zitatDao.delete(zitat);
        }

        if(dokument != null)
            dokumentDao.delete(dokument);
        metadataDao.delete(metadata);

        model.addAttribute("message", "Löschen erfolgreich!");
        model.addAttribute("messages", messages);
        return "delete";
    }

} // class DeleteController
