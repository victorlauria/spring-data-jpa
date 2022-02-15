package br.com.esocial.comum.dao.daogenerico;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Quando classe essa classe � utilizada o resultado retornado, � um map com o alias como chave 
 * e o value do map seria o valor.
 * 
 * Exemplo: 
 * 
 * String query = "select nome, idade, sexo from tb_pessoa"
 * List< Map< String, String>> resultado = DAOGenerico.executeQuery(query, new ResultTransformerForMapResult());
 * 
 * String idade;
 * idade = resultado.get(0).get("idade");
 * idade = resultado.get(0).get("idadE");
 * idade = resultado.get(0).get("iDaDe");
 * 
 * String nome = resultado.get(0).get("nome");
 * (...)
 * 
 * obs: repare q o map retornado possue a chave Case Insensitive
 * 
 * </pre>
 * @author Felipe Regalgo
 */
public class ResultTransformerForMapResult implements ResultTransformer<Map<String, String>> {
    
    Map<String, TipoDaColuna> mapAliasTipos;
    
    public ResultTransformerForMapResult() {
	this(new HashMap<String, TipoDaColuna>());
    }

    /**
     * @param mapAliasTipos Seria um map mostrando o tipo de um alias em especifico
     */
    public ResultTransformerForMapResult(Map<String, TipoDaColuna> mapAliasTipos) {
	super();
	
	this.mapAliasTipos = criarMapComChavesLowercase(mapAliasTipos);
    }

    private Map<String, TipoDaColuna> criarMapComChavesLowercase(Map<String, TipoDaColuna> mapParaTransformar) {
	
	Map<String, TipoDaColuna> map = new HashMap<String, TipoDaColuna>();
	
	for (String key : mapParaTransformar.keySet()) {
	    TipoDaColuna tipoDaColuna = mapParaTransformar.get(key);
	    map.put(key.toLowerCase(), tipoDaColuna);
	}
	
	return map;
    }
    
    public Map<String, String> createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
	
	// ---------------------
	Map<String, String> map = new MapForCaseInsensitiveKey();
	
	// ---------------------
	for (String alias : aliases) {
	    
	    TipoDaColuna tipoDaColuna = getTipoDaColuna(alias);
	    map.put(alias, tipoDaColuna.converterValorDaStringVindaDoBancoParaObjeto(conn, alias));
	}
	
	// ---------------------
	return map;
    }

    private TipoDaColuna getTipoDaColuna(String alias) {
	
	TipoDaColuna tipoDaColuna = mapAliasTipos.get(alias.toLowerCase());
	
	if (tipoDaColuna == null) {
	    tipoDaColuna = TipoDaColuna.TEXTO;
	}
	
	return tipoDaColuna;
    }
    
}









