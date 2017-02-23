package webapp.controllers;

import com.sun.javafx.runtime.SystemProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Bobby on 23.02.2017.
 */
@Controller
public class UploadController {

    //Save the uploaded file to this folder

    private static String UPLOADED_FOLDER = System.getProperty("user.home")+"\\Desktop\\Webapp\\Upload\\";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Model model) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            model.addAttribute("filename","Fehlgeschlagen");
            return "upload";
        }

        try {

            model.addAttribute("filename", file.getOriginalFilename());

            File f = new File(UPLOADED_FOLDER + file.getOriginalFilename());
            f.getParentFile().mkdirs();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(f.getPath());

            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "upload";
}
}
