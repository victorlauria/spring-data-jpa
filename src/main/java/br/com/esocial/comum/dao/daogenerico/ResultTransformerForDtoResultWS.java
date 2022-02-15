package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @see ResultTransformerForDtoResult
 * 
 */
public abstract class ResultTransformerForDtoResultWS<T> implements ResultTransformer<T> {

    Class<T> dtoClass;
    Map<String, Field> mapOfFields = new HashMap<String, Field>();
    Map<Field, Method> mapOfSetMethods = new HashMap<Field, Method>();
    boolean converterAliasParaPadraoJava;

    public ResultTransformerForDtoResultWS() {
	this(false);
    }

    public ResultTransformerForDtoResultWS(boolean converterAliasParaPadraoJava) {

	this.converterAliasParaPadraoJava = converterAliasParaPadraoJava;
	this.dtoClass = getClasseDoDto();

	for (Field field : dtoClass.getDeclaredFields()) {

	    mapOfFields.put(field.getName().toLowerCase(), field);
	    String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
	    try {
		mapOfSetMethods.put(field, dtoClass.getMethod(setMethodName, String.class));
	    } catch (Exception e) {
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

	    // ---------------------
	    if (field != null) {
		String valor = getTipoDaColuna(field).converterValorDaStringVindaDoBancoParaObjeto(conn, alias);
		mapOfSetMethods.get(field).invoke(t, valor);
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

	Class directSubclass = getClass();
	while (directSubclass.getSuperclass().equals(ResultTransformerForDtoResultWS.class) == false) {
	    directSubclass = directSubclass.getSuperclass();
	}
	return (Class<T>) ((ParameterizedType) directSubclass.getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
