package app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twitter.Extractor;

@Configuration
public class BeanConfiguration {
    @Bean
    public Extractor extractor() {
        return new Extractor();
    }
}
