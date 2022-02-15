package br.com.esocial.comum.dao.daogenerico;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Consulta javadoc da classe DAOGenerico
 * 
 * @author Felipe Regalgo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tabela {
    
    String nomeDaTabelaNoBanco() default "";
    
}
