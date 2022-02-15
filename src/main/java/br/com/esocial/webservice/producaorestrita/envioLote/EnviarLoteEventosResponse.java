
package br.com.esocial.webservice.producaorestrita.envioLote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de anonymous complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EnviarLoteEventosResult" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;any/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "enviarLoteEventosResult"
})
@XmlRootElement(name = "EnviarLoteEventosResponse")
public class EnviarLoteEventosResponse {

    @XmlElement(name = "EnviarLoteEventosResult")
    protected EnviarLoteEventosResponse.EnviarLoteEventosResult enviarLoteEventosResult;

    /**
     * Obtém o valor da propriedade enviarLoteEventosResult.
     * 
     * @return
     *     possible object is
     *     {@link EnviarLoteEventosResponse.EnviarLoteEventosResult }
     *     
     */
    public EnviarLoteEventosResponse.EnviarLoteEventosResult getEnviarLoteEventosResult() {
        return enviarLoteEventosResult;
    }

    /**
     * Define o valor da propriedade enviarLoteEventosResult.
     * 
     * @param value
     *     allowed object is
     *     {@link EnviarLoteEventosResponse.EnviarLoteEventosResult }
     *     
     */
    public void setEnviarLoteEventosResult(EnviarLoteEventosResponse.EnviarLoteEventosResult value) {
        this.enviarLoteEventosResult = value;
    }


    /**
     * <p>Classe Java de anonymous complex type.
     * 
     * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;any/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "any"
    })
    public static class EnviarLoteEventosResult {

        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Obtém o valor da propriedade any.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getAny() {
            return any;
        }

        /**
         * Define o valor da propriedade any.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}
