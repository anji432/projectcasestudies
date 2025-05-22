import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// It is equivalent to using @Configuration,
// @EnableAutoConfiguration and @ComponentScan with their
// default attributes:
public class CurrencyExchangeServiceSampleApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(
			CurrencyExchangeServiceSampleApplication.class,
			args);
	}
}
