package br.com.esocial;

import java.io.IOException;

//import java.util.Arrays;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*
import br.com.esocial.dominio.PessoaFisica;
import br.com.esocial.repositories.PessoaFisicaRepository;
*/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, 
scanBasePackages={"br.com.esocial.SigeprevEsocialServicosApplication", "br.com.esocial.repositories.PessoaFisicaRepository"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = {"br.com.esocial"})
@EntityScan("br.com.esocial.dominio")
@EnableJpaRepositories("br.com.esocial.repositories")
public class SigeprevEsocialServicosApplication implements CommandLineRunner {
/*
	@Autowired
	private PessoaFisicaRepository pfRepository;
*/	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(SigeprevEsocialServicosApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
				
	}
}
