package takeaway.client.gameofthree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import takeaway.client.gameofthree.util.RandomPortUtil;

@SpringBootApplication
public class GameOfThreeApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	application.registerShutdownHook(true);
        return application.sources(GameOfThreeApplication.class);
    }

    public static void main(String[] args) {
    	RandomPortUtil.setRandomPort();
        SpringApplication.run(GameOfThreeApplication.class, args);
    }

}
