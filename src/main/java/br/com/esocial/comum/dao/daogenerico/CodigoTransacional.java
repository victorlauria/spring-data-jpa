package br.com.esocial.comum.dao.daogenerico;

public interface CodigoTransacional {
    public void execute(DataBaseTXInterface conn) throws Exception;
}
