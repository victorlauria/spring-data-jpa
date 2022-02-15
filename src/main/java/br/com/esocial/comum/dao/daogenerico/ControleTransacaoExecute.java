package br.com.esocial.comum.dao.daogenerico;

/**
 *
 * @deprecated Utilizar chamada com clousure DAOGenerico.executarTransacao(conn -> ....)
 * @author Felipe Regalgo
 */
@Deprecated
public abstract class ControleTransacaoExecute {

    public void execute(DataBaseTXInterface conn) throws Exception {

    }

    public Object executeComRetorno(DataBaseTXInterface conn) throws Exception {
	return null;
    }

}
