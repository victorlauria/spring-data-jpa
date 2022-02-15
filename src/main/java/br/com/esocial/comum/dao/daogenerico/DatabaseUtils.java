/*
 * Criado em 30/03/2004 por Arthur Bellieny
 *
 */
package br.com.esocial.comum.dao.daogenerico;

import br.com.atlantic.comum.utils.Log;
import br.com.atlantic.comum.utils.MsgExcept;
import br.com.atlantic.comum.utils.Utils;

/**
 * @author Arthur Bellieny
 *
 * 30/03/2004
 */
public class DatabaseUtils {
	private static String CLASS_NAME = "DatabaseUtils";

	private static Log log = new Log(CLASS_NAME);

	public static String getSequence(String seq) throws MsgExcept, Exception {
		DataBaseTXInterface conn = null;

		try {
			conn = new DataBaseTX(CLASS_NAME);
			if (!conn.connect()) {
				log.log("Erro na conex\u00E3o...");
				throw new MsgExcept(1000);
			}

			return getSequence(seq, conn);
		} catch (MsgExcept e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
				log.log("Desconectei...");
			}
		}
	}
	
	public static String getSequenceCertidoes(String seq) throws MsgExcept, Exception {
		DataBaseTXInterface conn = null;

		try {
			conn = new DataBaseTX(CLASS_NAME);
			
			if (!conn.connect()) {
				log.log("Erro na conex\u00E3o...");
				throw new MsgExcept(1000);
			}

			return getSequence(seq, conn) + "/" + Utils.getDataAno4Digito(Utils.getDateNow()) ;
			
		} catch (MsgExcept e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
				log.log("Desconectei...");
			}
		}
	}	

	public static String formataSequenceAno(String seq) throws MsgExcept, Exception {

		try {
			
			return seq + "/" + Utils.getDataAno4Digito(Utils.getDateNow()) ;
			
		} catch (MsgExcept e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}	

	public static String formataSequenceAno(String seq, int tamanhoCampo) throws MsgExcept, Exception {

		try {
			
			return Utils.addLeftChar(seq, 6, "0") + "/" + Utils.getDataAno4Digito(Utils.getDateNow()) ;
			
		} catch (MsgExcept e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}		
	
	public static String getSequence(String seq, DataBaseTXInterface conn) throws MsgExcept, Exception {
		String query = "SELECT " + seq + ".NEXTVAL as seq FROM DUAL ";

		conn.prepareStatement(query);
		String retorno = "0";

		if (conn.execute() < 0) {
			log.log("Erro na execu\u00E7\u00E3o do SELECT...");
			throw new MsgExcept(1002);
		}
		if (conn.next()) {
			retorno = conn.getColumn("seq");
		}

		return retorno;
	}

	public static String executeExpressao(String expressao) throws MsgExcept, Exception {
		DataBaseTXInterface conn = null;

		try {
			conn = new DataBaseTX(CLASS_NAME);
			if (!conn.connect()) {
				log.log("Erro na conex\u00E3o...");
				throw new MsgExcept(1000);
			}

			return executeExpressao(expressao, conn);
		} catch (MsgExcept e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
				log.log("Desconectei...");
			}
		}
	}
	public static String executeExpressao(String expressao, DataBaseTXInterface conn) throws MsgExcept, Exception {
		String query = "SELECT " + expressao + " AS RET FROM DUAL ";

		conn.prepareStatement(query);
		String retorno = "0";

		if (conn.execute() < 0) {
			log.log("Erro na execu\u00E7\u00E3o do SELECT...");
			throw new MsgExcept(1002);
		}
		if (conn.next()) {
			retorno = conn.getColumn("RET");
		}

		return retorno;
	}

}
