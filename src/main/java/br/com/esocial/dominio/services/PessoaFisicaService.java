package br.com.esocial.dominio.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.esocial.dominio.PessoaFisica;
import br.com.esocial.dominio.services.exception.DataIntegrityException;
import br.com.esocial.repositories.PessoaFisicaRepository;

@Service
public class PessoaFisicaService {

	@Autowired
	private PessoaFisicaRepository pfRepository;
	
	public List<PessoaFisica> pesquisarPessoasFisicas() {
		return pfRepository.pesquisarPessoasFisicas();
	}
	
	public PessoaFisica buscarPorId(Integer id) {
		Optional<PessoaFisica> pessoaFisica = pfRepository.buscarPorId(id);
		return pessoaFisica.orElseThrow(() -> new ObjectNotFoundException(id, PessoaFisica.class.getName()));
	
	}
	
	public Page<PessoaFisica> buscaPorPagina(String nome, Integer pagina, Integer linhasPorPagina, String direcao, String ordenadoPor) {
		PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenadoPor);
		return pfRepository.buscaPorPagina(nome, pageRequest);
	}

	public PessoaFisica inserir(PessoaFisica pessoaFisica) {
		pessoaFisica = pfRepository.save(pessoaFisica);
		return pessoaFisica;
	}

	public PessoaFisica atualizar(PessoaFisica pessoaFisica) {
		PessoaFisica novaPessoaFisica = buscarPorId(pessoaFisica.getId());
		atualizarAuxiliar(novaPessoaFisica, pessoaFisica);
		return pfRepository.save(novaPessoaFisica);
	}
	
	public void excluir(Integer id) {
		buscarPorId(id);
		try {
			pfRepository.excluir(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir.");
		}
	}
	
	private void atualizarAuxiliar(PessoaFisica novaPessoaFisica, PessoaFisica pessoaFisica) {
		novaPessoaFisica.setNome(pessoaFisica.getNome());
		novaPessoaFisica.setIdade(pessoaFisica.getIdade());
	}		
}
