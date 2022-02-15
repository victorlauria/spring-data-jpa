package br.com.esocial.comum.dao.daogenerico;

import java.util.ArrayList;
import java.util.List;

/**
 * SetTypes setTypes = SetTypesBuilder.iniciarCriacaoObjetoSetTypes()
 *     				.addBindingParameter(TipoDaColuna.TEXTO, "ss")
 *     				.addBindingParameter(TipoDaColuna.TEXTO, "ss")
 *     				.addBindingParameter(TipoDaColuna.NUMERO_INTEIRO, "1")
 *     				.addBindingParameter(TipoDaColuna.DATA, "11/11/2011")
 *     				.addBindingParameter(TipoDaColuna.TEXTO, "ss")
 *     				.addBindingParameter(TipoDaColuna.TEXTO, "ss")
 *     				.finalizarCriacao();
 * @author fregalgo
 */
public class SetTypesBuilder {
    
    private SetTypesBuilder() {
	
    }
    
    List<BindingParameter> parameters = new ArrayList<BindingParameter>();
    
    public SetTypesBuilder addBindingParameter(TipoDaColuna tipoDaColuna, String valor) {
	parameters.add(new BindingParameter(tipoDaColuna, valor));
	return this;
    }
    
    public SetTypesBuilder addBindingParameter(String valor) {
	parameters.add(new BindingParameter(TipoDaColuna.TEXTO, valor));
	return this;
    }

    public SetTypesBuilder addBindingParameter(String... valores) {
	for (String valor: valores) {
	    addBindingParameter(valor);
	}
	return this;
    }
    
    public static SetTypesBuilder iniciarCriacaoObjetoSetTypes() {
	return new SetTypesBuilder();
    }
    
    public SetTypes finalizarCriacao() {
	
	// ------------------
	SetTypes setTypes = new SetTypes() {
	    @Override 
	    public void setTypes(DataBaseTXInterface conn) throws Exception {

		for (int i = 0; i < parameters.size(); i++) {
		    BindingParameter bindingParameter = parameters.get(i);

		    int indice = i + 1;
		    Object param = bindingParameter.getTipoDaColuna().converterValorDoObjetoParaGravarNoBanco(bindingParameter.getValor());

		    conn.setType("P", bindingParameter.getTipoDaColuna().getCodigoPara_SetType(), indice, param);
		}

	    }
	};
	
	// ------------------
	return setTypes;
    }
    
    public static void main(String[] args) {
	SetTypes setTypes = SetTypesBuilder.iniciarCriacaoObjetoSetTypes()
        			.addBindingParameter(TipoDaColuna.TEXTO, "ss")
        			.addBindingParameter(TipoDaColuna.TEXTO, "ss")
        			.addBindingParameter(TipoDaColuna.NUMERO_INTEIRO, "1")
        			.addBindingParameter(TipoDaColuna.DATA, "11/11/2011")
        			.addBindingParameter(TipoDaColuna.TEXTO, "ss")
        			.addBindingParameter(TipoDaColuna.TEXTO, "ss")
        			.finalizarCriacao();
    }
    
}
