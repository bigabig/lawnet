package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webapp.importer.ImportRunnable;
import webapp.models.DokumentDao;
import webapp.models.MetadataDao;
import webapp.models.ZitatDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by tim on 28.02.2017.
 */
@Controller
public class ImportController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Import\\";

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private ZitatDao zitatDao;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("messsage","Fehlgeschlagen!");
            return "import";
        }

        try {
            String filename = file.getOriginalFilename();

            File f = new File(UPLOADED_FOLDER + filename);
            f.getParentFile().mkdirs();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(f.getPath());

            Files.write(path, bytes);

            Thread t = new Thread(new ImportRunnable(f.getPath(), metadataDao, dokumentDao, zitatDao));
            t.run();

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("message", "Import erfolgreich!");
        return "import";
    }

    @RequestMapping(value = "/import")
    public String index(Model model) {
        return "import";
    }

}
