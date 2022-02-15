package DefaultNamespace;

public interface AssinaturaService extends javax.xml.rpc.Service {
    public java.lang.String getAssinaturaAddress();

    public DefaultNamespace.Assinatura getAssinatura() throws javax.xml.rpc.ServiceException;

    public DefaultNamespace.Assinatura getAssinatura(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
