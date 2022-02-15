package br.com.esocial.comum.dao.daogenerico;

import java.sql.Timestamp;
import java.util.Date;

import br.com.atlantic.comum.utils.Utils;

/**
 * 
 * @author Felipe Regalgo
 */
public enum TipoDaColuna {

    TEXTO("%s") {

	@Override
	public Object converterValorDoObjetoParaGravarNoBanco(String valor) {
	    return valor;
	}

	@Override
	public String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception {
	    return transformarEmStringVaziaCasoParamSejaNulo(conn.getColumn(nomeDaColuna));
	}
	
	@Override
	public String getTipoNoBanco(double length) {
	    return "VARCHAR2(" + ((int) length) + ")";
	}
	
    }, NUMERO_REAL("%f") {

	@Override
	public Object converterValorDoObjetoParaGravarNoBanco(String valor) {
	    return valor;
	}

	@Override
	public String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception {
	    return transformarEmStringVaziaCasoParamSejaNulo(conn.getColumn(nomeDaColuna));
	}

	@Override
	public String getTipoNoBanco(double length) {
	    String newLength = (length + "").replace('.', ',');
	    return "NUMBER(" + newLength + ")";
	}
	
    }, NUMERO_INTEIRO("%f") {

	@Override
	public Object converterValorDoObjetoParaGravarNoBanco(String valor) {
	    return valor;
	}

	@Override
	public String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception {
	    return transformarEmStringVaziaCasoParamSejaNulo(conn.getColumn(nomeDaColuna));
	}

	@Override
	public String getTipoNoBanco(double length) {
	    return "NUMBER(" + ((int) length) + ")";
	}
	
    }, DATA("%dh") {

	@Override
	public Object converterValorDoObjetoParaGravarNoBanco(String valor) throws Exception {

	    if (valor == null) {
		return null;
	    }
	    
	    return Utils.strToTimestamp(valor);
	}

	@Override
	public String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception {
	    Date columnDate = conn.getColumnDate(nomeDaColuna);
	    if (columnDate == null) {
		return "";
	    }
	    
	    return Utils.getDate(new Timestamp(columnDate.getTime()));
	}

	@Override
	public String getTipoNoBanco(double length) {
	    return "DATE";
	}
	
    }, DATA_E_HORA("%dh") {

	@Override
	public Object converterValorDoObjetoParaGravarNoBanco(String valor) throws Exception {
	    
	    if (valor == null) {
		return null;
	    }
	    
	    return Utils.strToTimestamp(valor);
	}

	@Override
	public String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception {
	    Date columnDate = conn.getColumnDate(nomeDaColuna);
	    if (columnDate == null) {
		return "";
	    }
	    return Utils.getDateTime(new Timestamp(columnDate.getTime()));
	}

	@Override
	public String getTipoNoBanco(double length) {
	    return "DATE";
	}
    };

    String codigoPara_SetType;
    
    private TipoDaColuna(String codigoPara_SetType) {
	this.codigoPara_SetType = codigoPara_SetType;
    }

    public static String transformarEmStringVaziaCasoParamSejaNulo(String valor) {
	return valor == null ? "" : valor;
    }

    public String getCodigoPara_SetType() {
	return codigoPara_SetType;
    }

    public abstract Object converterValorDoObjetoParaGravarNoBanco(String value) throws Exception;

    public abstract String converterValorDaStringVindaDoBancoParaObjeto(DataBaseTXInterface conn, String nomeDaColuna) throws Exception;

    public abstract String getTipoNoBanco(double length);
    
}
