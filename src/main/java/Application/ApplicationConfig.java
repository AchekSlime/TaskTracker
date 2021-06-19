package Application;

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
        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        service = applicationContext.getBean(AppService.class);
        service.test1();
    }

}
