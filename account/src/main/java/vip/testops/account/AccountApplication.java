package vip.testops.account;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public OpenAPI springDemoOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpringCloud Demo Account API")
                        .description("SpringCloud Demo Account service application")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                ).externalDocs(new ExternalDocumentation()
                        .description("SpringCloud Demo")
                );
    }
}
