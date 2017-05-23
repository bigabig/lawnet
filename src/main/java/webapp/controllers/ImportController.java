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
import webapp.models.watson.WatsonHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by tim on 28.02.2017.
 */
@Controller
public class ImportController {

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private ZitatDao zitatDao;

    //Save the uploaded file to this folder
    //private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Upload\\";
    private static String UPLOADED_FOLDER;

    static {
        Properties properties = new Properties();
        try {
            InputStream is = WatsonHelper.class.getResourceAsStream("/watson.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UPLOADED_FOLDER = properties.getProperty("uploadpath");
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("module", "import");

        if (file.isEmpty()) {
            model.addAttribute("messsage","Fehlgeschlagen!");
            return "import";
        }

        try {
            String filename = file.getOriginalFilename();

            File f = new File(UPLOADED_FOLDER, filename);
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
