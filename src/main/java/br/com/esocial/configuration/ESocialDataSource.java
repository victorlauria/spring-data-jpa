package br.com.esocial.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ESocialDataSource extends HikariConfig {
	
	@Value("${spring.datasource.username}")
	private String usuario;
	
	@Value("${spring.datasource.password}")
	private String senha;
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setUsername("ljunior");
		hikariConfig.setPassword("lrosahmg");
		hikariConfig.setJdbcUrl("jdbc:oracle:thin:@10.32.249.2:1521/PDBSPPREVHML.br1.ocm.s7071866.oraclecloudatcustomer.com");
		
		return new HikariDataSource(hikariConfig);
	}

}
