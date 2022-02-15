package br.com.esocial.comum.dao.daogenerico;

public interface CodigoTransacionalComRetorno<T> {
    public T execute(DataBaseTXInterface conn) throws Exception;
}
