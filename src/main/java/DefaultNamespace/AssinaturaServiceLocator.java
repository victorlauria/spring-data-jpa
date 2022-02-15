package DefaultNamespace;

public class AssinaturaServiceLocator extends org.apache.axis.client.Service implements DefaultNamespace.AssinaturaService {

    public AssinaturaServiceLocator() {
    }


    public AssinaturaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AssinaturaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Assinatura
    private java.lang.String Assinatura_address = "http://localhost:8080/AssianturaWS/services/Assinatura";

    public java.lang.String getAssinaturaAddress() {
        return Assinatura_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AssinaturaWSDDServiceName = "Assinatura";

    public java.lang.String getAssinaturaWSDDServiceName() {
        return AssinaturaWSDDServiceName;
    }

    public void setAssinaturaWSDDServiceName(java.lang.String name) {
        AssinaturaWSDDServiceName = name;
    }

    public DefaultNamespace.Assinatura getAssinatura() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Assinatura_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAssinatura(endpoint);
    }

    public DefaultNamespace.Assinatura getAssinatura(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            DefaultNamespace.AssinaturaSoapBindingStub _stub = new DefaultNamespace.AssinaturaSoapBindingStub(portAddress, this);
            _stub.setPortName(getAssinaturaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAssinaturaEndpointAddress(java.lang.String address) {
        Assinatura_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (DefaultNamespace.Assinatura.class.isAssignableFrom(serviceEndpointInterface)) {
                DefaultNamespace.AssinaturaSoapBindingStub _stub = new DefaultNamespace.AssinaturaSoapBindingStub(new java.net.URL(Assinatura_address), this);
                _stub.setPortName(getAssinaturaWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Assinatura".equals(inputPortName)) {
            return getAssinatura();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://DefaultNamespace", "AssinaturaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://DefaultNamespace", "Assinatura"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Assinatura".equals(portName)) {
            setAssinaturaEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }
}
