package br.com.caju.authorization;

import br.com.caju.authorization.adapter.in.web.AccountController;
import br.com.caju.authorization.application.port.in.AccountUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = AccountController.class)
@ComponentScan(basePackageClasses = {AccountController.class, AccountUseCase.class})
@EntityScan("br.com.caju.authorization.domain.model")
@EnableJpaRepositories("br.com.caju.authorization.application.repository")
public class AuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
