package webapp;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tim on 24.02.2017.
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/graphen/**").addResourceLocations("file:" + Application.GRAPH_DIR);
        super.addResourceHandlers(registry);
    }
}