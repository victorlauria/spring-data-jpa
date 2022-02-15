package br.com.esocial.comum.dao.daogenerico;

import br.com.atlantic.comum.utils.Contexto;

public class ValoresParametrosSistemaDAO extends DAOGenerico<ValoresParametrosSistema>{

	public ValoresParametrosSistemaDAO() {
	}

	public ValoresParametrosSistemaDAO(Contexto contexto) {
		super(contexto);
	}

	public ValoresParametrosSistema findByCodNumAndCodPar(String codNum, String codPar) throws Exception {

		String query = "select * from TB_VALORES_PARAMETROS where cod_num = ? and cod_par = ?";

		SetTypes setTypes = SetTypesBuilder.iniciarCriacaoObjetoSetTypes()
				.addBindingParameter(codNum)
				.addBindingParameter(codPar)
				.finalizarCriacao();

		return findUniqueValue(query, setTypes);
	}

}
