
package br.com.esocial.webservice.producao.consultarLote;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.esocial.webservice.producao.consultarLote package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.esocial.webservice.producao.consultarLote
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarLoteEventos }
     * 
     */
    public ConsultarLoteEventos createConsultarLoteEventos() {
        return new ConsultarLoteEventos();
    }

    /**
     * Create an instance of {@link ConsultarLoteEventosResponse }
     * 
     */
    public ConsultarLoteEventosResponse createConsultarLoteEventosResponse() {
        return new ConsultarLoteEventosResponse();
    }

    /**
     * Create an instance of {@link ConsultarLoteEventos.Consulta }
     * 
     */
    public ConsultarLoteEventos.Consulta createConsultarLoteEventosConsulta() {
        return new ConsultarLoteEventos.Consulta();
    }

    /**
     * Create an instance of {@link ConsultarLoteEventosResponse.ConsultarLoteEventosResult }
     * 
     */
    public ConsultarLoteEventosResponse.ConsultarLoteEventosResult createConsultarLoteEventosResponseConsultarLoteEventosResult() {
        return new ConsultarLoteEventosResponse.ConsultarLoteEventosResult();
    }

}
