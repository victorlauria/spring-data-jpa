package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
<pre>
Essa classe popula um dto utilizando os alias no resultado do select. 
Preenchendo os campos do Dto q possuam os mesmos nomes dos alias no resultado da busca.

Supondo a seguinte classe

public class Pessoa {
	String nome;
	String idade;
	
	public String getNome() {
	    return nome;
	}
	public void setNome(String nome) {
	    this.nome = nome;
	}
	public String getIdade() {
	    return idade;
	}
	public void setIdade(String idade) {
	    this.idade = idade;
	}
}

e a seguinte busca:
String query = "select nome, idade from tb_pessoa";

essa busca poderia ser feita da seguinte maneira
List< Pessoa> resultado = DAOGenerico.executeQuery(query, new ResultTransformerForDtoResult< Pessoa>(){});

obs: � necessario colocar o '{}' depois da instancia\u00E7\u00E3o da classe ResultTransformerForDtoResult

</PRE> 

 * 
 * @author Felipe Regalgo
 *
 * @param <T> Deve ser mostrado a classe que ser\u00E1 o resultado da busca 
 */
public class ResultTransformerForDtoResult<T> implements ResultTransformer<T> {
    
    Class<T> dtoClass;
    Map<String, Field> mapOfFields = new HashMap<String, Field>();
    Map<Field, Method> mapOfSetMethods = new HashMap<Field, Method>();
    boolean converterAliasParaPadraoJava;
    private Class dtoClasseParametroConstrutor;
    
    public ResultTransformerForDtoResult() {
	this(false);
    }
    
    public ResultTransformerForDtoResult(Class klass) {
	dtoClasseParametroConstrutor = klass;
	construtor(false);
    }
    
    public ResultTransformerForDtoResult(Class klass, boolean converterAliasParaPadraoJava) {
	dtoClasseParametroConstrutor = klass;
	construtor(converterAliasParaPadraoJava);
    }
    
    public ResultTransformerForDtoResult(boolean converterAliasParaPadraoJava) {
	construtor(converterAliasParaPadraoJava);
    }

    private void construtor(boolean converterAliasParaPadraoJava) {
	this.converterAliasParaPadraoJava = converterAliasParaPadraoJava;
	this.dtoClass = getClasseDoDto();
	
	for (Field field : dtoClass.getDeclaredFields()) {
	    
	    // ------------------
	    if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
		continue;
	    }
	    
	    if (field.isAnnotationPresent(Coluna.class) && field.getAnnotation(Coluna.class).gravarCampoNaTabela() == false) {
		continue;
	    } 
	    
	    if (field.getType().equals(String.class) == false 
		    && field.getType().equals(BigDecimal.class) == false
		    && field.getType().equals(Integer.class) == false
		    && field.getType().equals(int.class) == false) {
		continue;
	    }
	    
	    // ------------------
	    String keyForMapFields = field.getName();
	    if (field.isAnnotationPresent(Coluna.class)) {
		if (field.getAnnotation(Coluna.class).nomeDaColunaNoBanco().length() > 0) {
		    keyForMapFields = field.getAnnotation(Coluna.class).nomeDaColunaNoBanco();
		}
	    }
	    mapOfFields.put(keyForMapFields.toLowerCase(), field);
	    
	    // ------------------
	    String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	    try {
		mapOfSetMethods.put(field, dtoClass.getMethod(setMethodName, field.getType()));
	    } catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException("N\u00E3o foi possivel obter metodo " + setMethodName + " do campo " + field.getName() + ", da classe " + dtoClass.getSimpleName());
	    }
	}
    }
    
    public T createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
	
	// ---------------------
	T t = dtoClass.newInstance();
	
	// ---------------------
	for (String alias : aliases) {
	    
	    // ---------------------
	    String fieldName = converterAliasParaPadraoJava ? alias.replaceAll("_", "").toLowerCase() : alias.toLowerCase(); 
	    Field field = mapOfFields.get(fieldName);
	    
	    if (field == null && converterAliasParaPadraoJava) {
		// tenta sem converter
		field = mapOfFields.get(alias.toLowerCase());
	    }
	    
	    // ---------------------
	    if (field != null) {
		String valorAsString = getTipoDaColuna(field).converterValorDaStringVindaDoBancoParaObjeto(conn, alias);
		
		Object valorFinal;
		if (field.getType() == Integer.class || field.getType() == int.class) {
		    valorFinal = Integer.parseInt(valorAsString);
		} else if (field.getType() == BigDecimal.class) {
		    valorFinal = new BigDecimal(valorAsString);
		} else {
		    valorFinal = valorAsString;
		}
		mapOfSetMethods.get(field).invoke(t, valorFinal);
	    }
	}
	
	// ---------------------
	return t;
    }

    private TipoDaColuna getTipoDaColuna(Field field) {
	
	TipoDaColuna tipoDaColuna;
	
	if (field.isAnnotationPresent(Coluna.class)) {
	    tipoDaColuna = field.getAnnotation(Coluna.class).tipoDaColuna();
	} else {
	    tipoDaColuna = TipoDaColuna.TEXTO;
	}
	
	return tipoDaColuna;
    }
    
    private Class<T> getClasseDoDto() {
	
	if (dtoClasseParametroConstrutor != null) {
	    return dtoClasseParametroConstrutor;
	}
	
	if(getClass().getSuperclass().equals(Object.class)){
		throw new RuntimeException("� necess�rio instanciar desta forma 'new ResultTransformerForDtoResult<T>(T.class);'");
	}
	
	// ---------------------
	Class directSubclass = getClass();
	while (directSubclass.getSuperclass().equals(ResultTransformerForDtoResult.class) == false) {
	    directSubclass = directSubclass.getSuperclass();
	}

	// ---------------------
	return (Class<T>) ((ParameterizedType) directSubclass.getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
}
