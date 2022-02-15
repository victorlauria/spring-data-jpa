package br.com.esocial.comum.dao.daogenerico;

public class ValoresParametrosSistema {
	
	@Coluna(pk = true, tipoDaColuna = TipoDaColuna.NUMERO_INTEIRO)
    String codIns;
	@Coluna(pk = true)
	String codNum;
    String codPar;
    String valPar;
    
	public String getCodIns() {
		return codIns;
	}
	public void setCodIns(String codIns) {
		this.codIns = codIns;
	}
	public String getCodNum() {
		return codNum;
	}
	public void setCodNum(String codNum) {
		this.codNum = codNum;
	}
	public String getCodPar() {
		return codPar;
	}
	public void setCodPar(String codPar) {
		this.codPar = codPar;
	}
	public String getValPar() {
		return valPar;
	}
	public void setValPar(String valPar) {
		this.valPar = valPar;
	}
	  
	  
}
