package br.com.esocial.comum.dao.daogenerico;
import java.io.Serializable;
/**
 * Classe DTO da tabela <tt>TB_ERROS</tt>.
 *
 * @author BeanGen - Gerado automaticamente
 * @version $Revision: 1.1 $ $Date: 2007/01/27 16:24:05 $
 */
public class Erros implements Cloneable, Serializable{

    private String codIns = "";
    private String codErro = "";
    private String desErro = "";
    private String datIng = "";
    private String datUltAtu = "";
    private String nomUsuUltAtu = "";
    private String nomProUltAtu = "";
    /**
    * Obt&eacute;m valor do atributo codIns.
    *
    * @return codIns.
    */
    public String getCodIns() {
        return this.codIns;
    }

    /**
    * Obt&eacute;m valor do atributo codErro.
    *
    * @return codErro.
    */
    public String getCodErro() {
        return this.codErro;
    }

    /**
    * Obt&eacute;m valor do atributo desErro.
    *
    * @return desErro.
    */
    public String getDesErro() {
        return this.desErro;
    }

    /**
    * Obt&eacute;m valor do atributo datIng.
    *
    * @return datIng.
    */
    public String getDatIng() {
        return this.datIng;
    }

    /**
    * Obt&eacute;m valor do atributo datUltAtu.
    *
    * @return datUltAtu.
    */
    public String getDatUltAtu() {
        return this.datUltAtu;
    }

    /**
    * Obt&eacute;m valor do atributo nomUsuUltAtu.
    *
    * @return nomUsuUltAtu.
    */
    public String getNomUsuUltAtu() {
        return this.nomUsuUltAtu;
    }

    /**
    * Obt&eacute;m valor do atributo nomProUltAtu.
    *
    * @return nomProUltAtu.
    */
    public String getNomProUltAtu() {
        return this.nomProUltAtu;
    }

    /**
    * Define valor do atributo codIns.
    *
    * @param codIns coluna <tt>TB_ERROS.COD_INS</tt>.
    */
    public void setCodIns(String codIns) {
        this.codIns = codIns;
    }

    /**
    * Define valor do atributo codErro.
    *
    * @param codErro coluna <tt>TB_ERROS.COD_ERRO</tt>.
    */
    public void setCodErro(String codErro) {
        this.codErro = codErro;
    }

    /**
    * Define valor do atributo desErro.
    *
    * @param desErro coluna <tt>TB_ERROS.DES_ERRO</tt>.
    */
    public void setDesErro(String desErro) {
        this.desErro = desErro;
    }

    /**
    * Define valor do atributo datIng.
    *
    * @param datIng coluna <tt>TB_ERROS.DAT_ING</tt>.
    */
    public void setDatIng(String datIng) {
        this.datIng = datIng;
    }

    /**
    * Define valor do atributo datUltAtu.
    *
    * @param datUltAtu coluna <tt>TB_ERROS.DAT_ULT_ATU</tt>.
    */
    public void setDatUltAtu(String datUltAtu) {
        this.datUltAtu = datUltAtu;
    }

    /**
    * Define valor do atributo nomUsuUltAtu.
    *
    * @param nomUsuUltAtu coluna <tt>TB_ERROS.NOM_USU_ULT_ATU</tt>.
    */
    public void setNomUsuUltAtu(String nomUsuUltAtu) {
        this.nomUsuUltAtu = nomUsuUltAtu;
    }

    /**
    * Define valor do atributo nomProUltAtu.
    *
    * @param nomProUltAtu coluna <tt>TB_ERROS.NOM_PRO_ULT_ATU</tt>.
    */
    public void setNomProUltAtu(String nomProUltAtu) {
        this.nomProUltAtu = nomProUltAtu;
    }

}