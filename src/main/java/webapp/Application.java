package webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import java.io.File;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  static String GRAPH_DIR;

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  public static void main(String[] args) {
    try {
      GRAPH_DIR = new File(".").getCanonicalPath() + "/graphen/";
      SpringApplication.run(Application.class, args);
    } catch (Exception e) {

    }
  }

}
