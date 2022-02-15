package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Method;

/**
 * 
 * @author Felipe Regalgo
 */
public class ColunaDaTabela {
    
    String nomeDaColuna;
    String nomeDoCampoNaClasse;
    TipoDaColuna tipo;
    double length;
    
    String sequenceAssociada;
    
    String metodoGet;
    String metodoSet;
    
    public ColunaDaTabela(String nomeDoCampoNaClasse, TipoDaColuna tipo, double length) {
	this(DAOGenerico.converterPadraoJavaParaPadraoDoBanco(nomeDoCampoNaClasse), nomeDoCampoNaClasse, tipo, length);
    }
    
    public ColunaDaTabela(String nomeDaColuna, String nomeDoCampoNaClasse, TipoDaColuna tipo, double length) {
	super();
	this.nomeDaColuna = nomeDaColuna;
	this.nomeDoCampoNaClasse = nomeDoCampoNaClasse;
	this.tipo = tipo;
	this.length = length;
	
	metodoGet = getMetodoGetAPartirDoNomeDoCampo(nomeDoCampoNaClasse);
	metodoSet = getMetodoSetAPartirDoNomeDoCampo(nomeDoCampoNaClasse);
    }
    
    public ColunaDaTabela(String nomeDoCampoNaClasse, double length) {
	this(nomeDoCampoNaClasse, TipoDaColuna.TEXTO, length);
    }

    private String getMetodoSetAPartirDoNomeDoCampo(String campo) {
	return "set" + Character.toUpperCase(campo.charAt(0)) + campo.substring(1);
    }

    private String getMetodoGetAPartirDoNomeDoCampo(String campo) {
	return "get" + Character.toUpperCase(campo.charAt(0)) + campo.substring(1);
    }

    public String getNomeDaColuna() {
        return nomeDaColuna;
    }

    public void setNomeDaColuna(String nomeDaColuna) {
        this.nomeDaColuna = nomeDaColuna;
    }

    public String getNomeDoCampoNaClasse() {
        return nomeDoCampoNaClasse;
    }

    public void setNomeDoCampoNaClasse(String nomeDoCampoNaClasse) {
        this.nomeDoCampoNaClasse = nomeDoCampoNaClasse;
    }

    public TipoDaColuna getTipo() {
        return tipo;
    }

    public void setTipo(TipoDaColuna tipo) {
        this.tipo = tipo;
    }

    public String getMetodoGet() {
        return metodoGet;
    }

    public void setMetodoGet(String metodoGet) {
        this.metodoGet = metodoGet;
    }

    public String getMetodoSet() {
        return metodoSet;
    }

    public void setMetodoSet(String metodoSet) {
        this.metodoSet = metodoSet;
    }

    public void setValorNoObjeto(Object objeto, DataBaseTXInterface conn) {
	
	String valor = null;
	
	try {
	    
	    valor = tipo.converterValorDaStringVindaDoBancoParaObjeto(conn, nomeDaColuna);
	    setValorNoObjeto(objeto, valor);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException("N\u00E3o foi possivel setar o valor '" + valor + "' na classe '" + objeto.getClass().getName() + "', utilizando o metodo '" + metodoSet + "'");
	}
    }
    
    public void setValorNoObjeto(Object objeto, String valor) {
	
	try {
	    
	    Method method = objeto.getClass().getMethod(metodoSet, String.class);
	    method.invoke(objeto, valor);
	    
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException("N\u00E3o foi possivel setar o valor '" + valor + "' na classe '" + objeto.getClass().getName() + "', utilizando o metodo '" + metodoSet + "'");
	}
    }
    
    public Object getValorDoObjeto(Object objeto) {
	
	try {
	    Method method = objeto.getClass().getMethod(metodoGet);
	    String value = (String) method.invoke(objeto);
	    
	    if (value == null) {
		return null;
	    }
	    
	    return tipo.converterValorDoObjetoParaGravarNoBanco(value);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new RuntimeException("N\u00E3o foi possivel acessar o metodo '" + metodoGet + "' da classe '" + objeto.getClass().getName() + "'");
	}
    }

    @Override
    public String toString() {
	return nomeDoCampoNaClasse + " - " + nomeDaColuna;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getSequenceAssociada() {
        return sequenceAssociada;
    }

    public void setSequenceAssociada(String sequenceAssociada) {
        this.sequenceAssociada = sequenceAssociada;
    }

    public boolean isSequenceCadastradas() {
	return getSequenceAssociada() != null && getSequenceAssociada().length() > 0;
    }
}
















