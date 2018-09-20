package th.co.gosoft.onl.efile;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import th.co.gosoft.onl.efile.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EntityScan(basePackageClasses = { 
		EFileApplication.class,
		Jsr310JpaConverters.class 
})
public class EFileApplication {
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
    public static void main(String[] args) {
        SpringApplication.run(EFileApplication.class, args);
    }
}