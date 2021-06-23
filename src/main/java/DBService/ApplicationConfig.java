package DBService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApplicationConfig {
    private static ApplicationContext applicationContext;
    private static AppService service;

    public AppService getService() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        return applicationContext.getBean(AppService.class);
    }

}
