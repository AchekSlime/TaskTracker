import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public String string(){
        return new String("33");
    }

    public static void main(String[] args) {
        ApplicationContext ap = new AnnotationConfigApplicationContext(Config.class);
        String s = ap.getBean("string", String.class);
        System.out.println(s);
    }
}
