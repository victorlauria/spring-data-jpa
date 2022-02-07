package br.com.esocial.dominio.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.esocial.dominio.PessoaFisica;
import br.com.esocial.dominio.services.PessoaFisicaService;


@RestController
@RequestMapping(value = "/pessoas-fisicas")
public class PessoaFisicaResource {
	
	@Autowired
	private PessoaFisicaService pfService;
	

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PessoaFisica>> pesquisarPessoasFisicas() {	
		List<PessoaFisica> listaPessoasFisicas = pfService.pesquisarPessoasFisicas();//pessoaFisicaDAO.pesquisarPessoasFisicas();
		return ResponseEntity.ok().body(listaPessoasFisicas);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PessoaFisica> buscaPorId(@PathVariable Integer id) {
		PessoaFisica pessoaFisica = pfService.buscarPorId(id);
		return ResponseEntity.ok().body(pessoaFisica);
		
	}
	
	@RequestMapping(value = "/pagina", method = RequestMethod.GET)
	public ResponseEntity<Page<PessoaFisica>> buscarPorPagina(@RequestParam(value = "nome", defaultValue = "0") String nome,
															  @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
															  @RequestParam(value = "linhaPorPagina", defaultValue = "24") Integer linhaPorPagina,
															  @RequestParam(value = "ordenadoPor", defaultValue = "id") String ordenadoPor,
															  @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		
		Page<PessoaFisica> listaPessoasFisicas = pfService.buscaPorPagina(nome, pagina, linhaPorPagina, direcao, ordenadoPor);
		return ResponseEntity.ok().body(listaPessoasFisicas);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@Valid @RequestBody PessoaFisica pessoaFisica) {
		pessoaFisica = pfService.inserir(pessoaFisica);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  							 .path("/{id}").buildAndExpand(pessoaFisica.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@Valid @RequestBody PessoaFisica pessoaFisica, @PathVariable Integer id) {
		pessoaFisica.setId(id);
		pessoaFisica = pfService.atualizar(pessoaFisica);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<PessoaFisica> excluir(@PathVariable Integer id) {
		pfService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}