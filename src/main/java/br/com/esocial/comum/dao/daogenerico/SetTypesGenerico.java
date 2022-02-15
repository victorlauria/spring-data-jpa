package br.com.esocial.comum.dao.daogenerico;

/**
 * 
 * @author Felipe Regalgo
 */
public class SetTypesGenerico implements SetTypes {

    SetTypesBean[] setTypesBeans;
    
    public SetTypesGenerico(SetTypesBean... setTypesBeans) {
	this.setTypesBeans = setTypesBeans;
    }
    
    public SetTypesGenerico(String... valores) {
	
	setTypesBeans = new SetTypesBean[valores.length];
	
	for (int i = 0; i < valores.length; i++) {
	    setTypesBeans[i] = new SetTypesBean(TipoDaColuna.TEXTO, valores[i]);
	}
    }
    
    public void setTypes(DataBaseTXInterface conn) throws Exception {
	
	 for (int i = 0; i < setTypesBeans.length; i++) {
	    conn.setType("P", setTypesBeans[i].getTipo().getCodigoPara_SetType(), i + 1, setTypesBeans[i].getValor());
	}
    }

}
