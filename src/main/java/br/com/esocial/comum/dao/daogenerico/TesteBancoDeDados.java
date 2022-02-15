package br.com.esocial.comum.dao.daogenerico;

import java.util.Map;

public class TesteBancoDeDados {

	public static void main(String[] args) throws Exception {
		Map<String, String> map = DAOGenerico.executeQueryForUniqueValue("SELECT NUM_CPF AS RESULTADO FROM TB_PESSOA_FISICA PF WHERE ROWNUM = 1 ");
		System.out.println("Resultado: " + map.get("RESULTADO"));
	}

}
