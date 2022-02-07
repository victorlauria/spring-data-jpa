package br.com.esocial.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.esocial.dominio.PessoaFisica;

@Repository
public class PessoaRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<PessoaFisica> pesquisarPessoasFisicas() {
		String query = "SELECT * FROM USER_IPESP.TB_PESSOA_FISICA";
		List<PessoaFisica> pessoaFisica = (List<PessoaFisica>) jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(PessoaFisica.class));
		return pessoaFisica;
	}
}
