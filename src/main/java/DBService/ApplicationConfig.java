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

    public static void main(String[] args) {

        service = applicationContext.getBean(AppService.class);
        //service.test1();
    }

    public AppService getService() {
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        return applicationContext.getBean(AppService.class);
    }

}
