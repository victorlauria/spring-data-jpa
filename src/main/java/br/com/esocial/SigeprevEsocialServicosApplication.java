package br.com.esocial;

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
	public static void main(String[] args) {
		SpringApplication.run(SigeprevEsocialServicosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		
		PessoaFisica pessoaFisica1 = new PessoaFisica(null, "VICTOR AUGUSTO LAURIA", "28");
		PessoaFisica pessoaFisica2 = new PessoaFisica(null, "MURILO CARVALO", "19");
		PessoaFisica pessoaFisica3 = new PessoaFisica(null, "GABRIEL GOMES", "18");
		PessoaFisica pessoaFisica4 = new PessoaFisica(null, "DIRCEU PELEGRINI", "34");
		PessoaFisica pessoaFisica5 = new PessoaFisica(null, "MARIA LUIZA R DE AZEVEDO", "39");
		PessoaFisica pessoaFisica6 = new PessoaFisica(null, "JOSE PEREIRA BUENO", "58");
		PessoaFisica pessoaFisica7 = new PessoaFisica(null, "DOMINGOS MARTINES VILLARINHO", "68");
		PessoaFisica pessoaFisica8 = new PessoaFisica(null, "WALMIS GARCIA GUIMARAES", "78");
		PessoaFisica pessoaFisica9 = new PessoaFisica(null, "MARIA APARECIDO GUEREDO PINTO", "88");
		PessoaFisica pessoaFisica10 = new PessoaFisica(null, "LOURDES DE FREITAS OLIVEIRA", "108");
		PessoaFisica pessoaFisica11 = new PessoaFisica(null, "SONIA VENTURI TEBALDI", "48");
		PessoaFisica pessoaFisica12 = new PessoaFisica(null, "ORLANDO TANGANELLI", "38");
		PessoaFisica pessoaFisica13 = new PessoaFisica(null, "ELVIRA MIRABELLI FIORENTINO", "57");
		PessoaFisica pessoaFisica14 = new PessoaFisica(null, "RUBENS FAGUNDES FILHO", "65");
		PessoaFisica pessoaFisica15 = new PessoaFisica(null, "LUCIO RODRIGUES DE OLIVEIRA", "45");
		PessoaFisica pessoaFisica16 = new PessoaFisica(null, "GABRIEL JUNQUEIRA FRANCO", "22");
		PessoaFisica pessoaFisica17 = new PessoaFisica(null, "PAULO FERRIANI BARRADAS", "30");
		PessoaFisica pessoaFisica18 = new PessoaFisica(null, "BENEDITO FOUYER", "98");
		PessoaFisica pessoaFisica19 = new PessoaFisica(null, "COSME DE GUARNIERI NETTO", "45");
		PessoaFisica pessoaFisica20 = new PessoaFisica(null, "EDUARDO KUHN", "55");
		PessoaFisica pessoaFisica21 = new PessoaFisica(null, "CLARA LEITE CARAM", "58");
		PessoaFisica pessoaFisica22 = new PessoaFisica(null, "ANTONIO BOLSONARO", "58");
		PessoaFisica pessoaFisica23 = new PessoaFisica(null, "STELLA ALVES FERREIRA PRADO", "25");
		PessoaFisica pessoaFisica24 = new PessoaFisica(null, "MARIANO RIBEIRO", "67");
		PessoaFisica pessoaFisica25 = new PessoaFisica(null, "JOSE DO ESPIRITO SANTO", "28");
		PessoaFisica pessoaFisica26 = new PessoaFisica(null, "VANDERLEI MARCAL VIEIRA", "48");
		PessoaFisica pessoaFisica27 = new PessoaFisica(null, "ANTENOR ARMANDO", "56");
		PessoaFisica pessoaFisica28 = new PessoaFisica(null, "PEDRO LOPES DE SOUZA", "33");
		PessoaFisica pessoaFisica29 = new PessoaFisica(null, "JULIAO LOURENCO DA SILVA", "32");
		PessoaFisica pessoaFisica30 = new PessoaFisica(null, "MIGUEL ANJO DA GUARDA", "34");
		PessoaFisica pessoaFisica31 = new PessoaFisica(null, "JURACY MARIZ DA SILVA", "65");
		PessoaFisica pessoaFisica32 = new PessoaFisica(null, "JOSE GERALDO DOS SANTOS FERNANDES", "72");
		PessoaFisica pessoaFisica33 = new PessoaFisica(null, "GLYCERIO CARVALHEIRA", "36");
		

		pfRepository.saveAll(Arrays.asList(pessoaFisica1, pessoaFisica2, pessoaFisica3,
				pessoaFisica4, pessoaFisica5, pessoaFisica6, pessoaFisica7, pessoaFisica8,
				pessoaFisica9, pessoaFisica10, pessoaFisica11, pessoaFisica12, pessoaFisica13, 
				pessoaFisica14, pessoaFisica15, pessoaFisica16, pessoaFisica17, pessoaFisica18, 
				pessoaFisica19, pessoaFisica20, pessoaFisica21, pessoaFisica22, pessoaFisica23, 
				pessoaFisica24, pessoaFisica25, pessoaFisica26, pessoaFisica27, pessoaFisica28, 
				pessoaFisica29, pessoaFisica30, pessoaFisica31, pessoaFisica32, pessoaFisica33));
*/				
	}
}
