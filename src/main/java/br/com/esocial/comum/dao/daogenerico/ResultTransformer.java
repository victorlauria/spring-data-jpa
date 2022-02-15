package br.com.esocial.comum.dao.daogenerico;

/**
<pre>

Essa classe � utilizada quando � necessario fazer uma consulta 
e o retorno for um objeto especifico.

Exemplo:  
    String query =  " select pes.nome, pes.idade, pes.sexo, rg, end.rua, end.cidade " +
    		    " from tb_pessoa pes, tb_endereco end" +
    		    " where pes.nome like '%felipe%' and pes.id_endereco = end.id"; 

Supondo que fosse necessario popular o seguinte objeto com o retorno.
    public class PessoaEndereco {
    	String nomePessoa;
    	String idadePessoa;
    	String sexoPessoa;
    	String rua;
    	String cidade;
    	    
    	(.. metodos set e get omitidos...)
    }

essa busca poderia ser feita da seguinte forma:

	ResultTransformer< PessoaEndereco> resultTransformer = new ResultTransformer< PessoaEndereco>() {
	    
	    public PessoaEndereco createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
		PessoaEndereco p = new PessoaEndereco();
		
		p.setNomePessoa(conn.getColumn("nome"));
		p.setIdadePessoa(conn.getColumn("idade"));
		p.setSexoPessoa(conn.getColumn("sx"));
		p.setRua(conn.getColumn("rua"));
		p.setCidade(conn.getColumn("cidade"));
		
		return p;
	    }
	};
	
	String query =  " select pes.nome, pes.idade, pes.sexo sx, rg, end.rua, end.cidade " +
        		" from tb_pessoa pes, tb_endereco end" +
        		" where pes.nome like '%felipe%' and pes.id_endereco = end.id";
	
	List< PessoaEndereco> listaDePessoas = DAOGenerico.executeQuery(query, resultTransformer);

existem tb alguns ResultTransformer personalizados, como 'ResultTransformerForUniqueAlias', 
'ResultTransformerForMapResult' e 'ResultTransformerForDtoResult'

</pre>

 * @author Felipe Regalgo
 */
public interface ResultTransformer<T> {

    public T createObject(DataBaseTXInterface conn, String[] aliases) throws Exception;

}
