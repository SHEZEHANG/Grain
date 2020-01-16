package trade.gdgrain.grain_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("trade.gdgrain.grain_test.mapper")
@SpringBootApplication
public class GrainTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrainTestApplication.class, args);
	}

}
