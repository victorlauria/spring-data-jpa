
package br.com.atlantic.esocial.enviarLoteProducao;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.atlantic.esocial.enviarLoteProducao package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.atlantic.esocial.enviarLoteProducao
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnviarLoteEventos }
     * 
     */
    public EnviarLoteEventos createEnviarLoteEventos() {
        return new EnviarLoteEventos();
    }

    /**
     * Create an instance of {@link EnviarLoteEventosResponse }
     * 
     */
    public EnviarLoteEventosResponse createEnviarLoteEventosResponse() {
        return new EnviarLoteEventosResponse();
    }

    /**
     * Create an instance of {@link EnviarLoteEventos.LoteEventos }
     * 
     */
    public EnviarLoteEventos.LoteEventos createEnviarLoteEventosLoteEventos() {
        return new EnviarLoteEventos.LoteEventos();
    }

    /**
     * Create an instance of {@link EnviarLoteEventosResponse.EnviarLoteEventosResult }
     * 
     */
    public EnviarLoteEventosResponse.EnviarLoteEventosResult createEnviarLoteEventosResponseEnviarLoteEventosResult() {
        return new EnviarLoteEventosResponse.EnviarLoteEventosResult();
    }

}
