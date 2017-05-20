package radio.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by loicd on 20/05/2017.
 */
@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EntityScan(basePackages = {"music.manager.model"})
@EnableJpaRepositories(basePackages = {"music.manager.repository"})
@ComponentScan({"external.api", "music.manager", "converter.container"})
public class RadioApplication {

    public static void main(String[] argc){
        SpringApplication.run(RadioApplication.class, argc);
    }

}
