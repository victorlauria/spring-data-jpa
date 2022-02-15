package br.com.esocial.comum.dao.daogenerico;

/**
 * <pre>
 * Esta classe deve ser utilizado quando um busca retorna apenas uma coluna. 
 * Por exemplo:
 * 
 * String query = "select nome from tb_pessoa";
 * List< String> result = DAOGenerico.executeQuery("", new ResultTransformerForUniqueAlias());
 *  
 * </pre>
 * 
 * @author Felipe Regalgo
 */
public class ResultTransformerForUniqueAlias implements ResultTransformer<String> {
    
    TipoDaColuna tipoDaColuna;
    
    public ResultTransformerForUniqueAlias() {
    	this(TipoDaColuna.TEXTO);
    }
    
    public ResultTransformerForUniqueAlias(TipoDaColuna tipoDaColuna) {
    	super();
		this.tipoDaColuna = tipoDaColuna;
    }
    
    public String createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
    	return tipoDaColuna.converterValorDaStringVindaDoBancoParaObjeto(conn, aliases[0]);
    }
    
    public static ResultTransformer<String> RESULT_TRANSFORMER = new ResultTransformerForUniqueAlias();
}
