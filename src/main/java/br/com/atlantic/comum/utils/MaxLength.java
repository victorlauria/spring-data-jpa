package br.com.atlantic.comum.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Consulta javadoc da classe DAOGenerico
 * 
 * @author Felipe Regalgo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
    
    int length() default 1; 
}