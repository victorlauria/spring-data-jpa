package br.com.esocial.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.esocial.dominio.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Integer>, JpaSpecificationExecutor<PessoaFisica> {

	@Query(value = "SELECT * FROM TB_VICTOR_LAURIA", nativeQuery = true)
	List<PessoaFisica> pesquisarPessoasFisicas();
	
	@Query(value = "SELECT * FROM TB_VICTOR_LAURIA WHERE id = ?", nativeQuery = true)
	Optional<PessoaFisica> buscarPorId(Integer id);
	
	@Query(value = "SELECT * FROM TB_VICTOR_LAURIA", nativeQuery = true)
	Page<PessoaFisica> buscaPorPagina(@Param("nome") String nome, Pageable pageRequest);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE TB_VICTOR_LAURIA SET nome = ?, idade = ? WHERE id = ?", nativeQuery = true)
	PessoaFisica atualizar(PessoaFisica pessoaFisica);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TB_VICTOR_LAURIA WHERE id = ?", nativeQuery = true)
	void excluir(@Param("id") Integer id);
}
