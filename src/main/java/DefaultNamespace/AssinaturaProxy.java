package DefaultNamespace;

public class AssinaturaProxy implements DefaultNamespace.Assinatura {
	private String _endpoint = null;
	private DefaultNamespace.Assinatura assinatura = null;

	public AssinaturaProxy() {
		_initAssinaturaProxy();
	}

	public AssinaturaProxy(String endpoint) {
		_endpoint = endpoint;
		_initAssinaturaProxy();
	}

	private void _initAssinaturaProxy() {
		try {
			assinatura = (new DefaultNamespace.AssinaturaServiceLocator()).getAssinatura();
			if (assinatura != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) assinatura)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) assinatura)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (assinatura != null)
			((javax.xml.rpc.Stub) assinatura)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public DefaultNamespace.Assinatura getAssinatura() {
		if (assinatura == null)
			_initAssinaturaProxy();
		return assinatura;
	}

	public java.lang.String getAssinatura(java.lang.String xml) throws java.rmi.RemoteException {
		if (assinatura == null)
			_initAssinaturaProxy();
		return assinatura.getAssinatura(xml);
	}
}
