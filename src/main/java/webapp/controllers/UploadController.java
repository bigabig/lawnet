package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webapp.converter.ConverterRunnable;
import webapp.models.Dokument;
import webapp.models.DokumentDao;
import webapp.models.MetadataDao;
import webapp.models.ZitatDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Bobby on 23.02.2017.
 */
@Controller
public class UploadController {

    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    @Autowired
    private DokumentDao dokumentDao;

    @Autowired
    private MetadataDao metadataDao;

    @Autowired
    private ZitatDao zitatDao;

    //Save the uploaded file to this folder
    //private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Upload\\";
    private static File UPLOADED_FOLDER;

    static{
        try {
            UPLOADED_FOLDER = new File(File.createTempFile("deleteme", "").getParent(), "lawnet-upload");
            UPLOADED_FOLDER.mkdir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("module", "upload");

        if (file.isEmpty()) {
            model.addAttribute("message","Upload fehlgeschlagen!");
            return "upload";
        }

        try {
            String filename = file.getOriginalFilename();
            System.out.println(filename);

            Dokument d = dokumentDao.findByDateiname(filename);
            if(d != null) {
                model.addAttribute("message", "Datei ist bereits vorhanden!");
                model.addAttribute("filename", filename);
                return "upload";
            }

            model.addAttribute("filename", filename);

            File f = new File(UPLOADED_FOLDER, filename);
            f.getParentFile().mkdirs();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(f.getPath());

            Files.write(path, bytes);

            Thread t = new Thread(new ConverterRunnable(filename, f.getPath(), metadataDao, dokumentDao, zitatDao));
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "upload";
    }

    @RequestMapping(value = "/upload")
    public String index(Model model) {
        model.addAttribute("module", "upload");
        return "upload";
    }

}
