package br.com.esocial.comum.dao.daogenerico;

/**
 * 
 * @author Felipe Regalgo
 */
public class SetTypesBean {

    TipoDaColuna tipo;
    String valor;

    public SetTypesBean(TipoDaColuna tipo, String valor) {
	super();
	this.tipo = tipo;
	this.valor = valor;
    }

    public TipoDaColuna getTipo() {
	return tipo;
    }

    public String getValor() {
	return valor;
    }

    public void setTipo(TipoDaColuna tipo) {
	this.tipo = tipo;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

}
