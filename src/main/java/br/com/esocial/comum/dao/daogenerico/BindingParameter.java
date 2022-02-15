package br.com.esocial.comum.dao.daogenerico;

public class BindingParameter {

    TipoDaColuna tipoDaColuna;
    String valor;

    public BindingParameter() {
    }

    public BindingParameter(TipoDaColuna tipoDaColuna, String valor) {
	super();
	this.tipoDaColuna = tipoDaColuna;
	this.valor = valor;
    }

    public TipoDaColuna getTipoDaColuna() {
	return tipoDaColuna;
    }

    public void setTipoDaColuna(TipoDaColuna tipoDaColuna) {
	this.tipoDaColuna = tipoDaColuna;
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

}
