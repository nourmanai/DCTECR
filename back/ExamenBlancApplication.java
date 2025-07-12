package tn.esprit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAspectJAutoProxy
@EnableScheduling
@ComponentScan("tn.esprit.spring")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ExamenBlancApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ExamenBlancApplication.class, args);
	}

}
