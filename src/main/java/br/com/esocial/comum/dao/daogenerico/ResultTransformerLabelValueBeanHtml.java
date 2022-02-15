package br.com.esocial.comum.dao.daogenerico;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Felipe Regalgo
 */
public class ResultTransformerLabelValueBeanHtml implements ResultTransformer<LabelValueBean> {

    public static ResultTransformer<LabelValueBean> RESULT_TRANSFORMER = new ResultTransformerLabelValueBeanHtml();
    
    @Override
    public LabelValueBean createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
	String codigo = conn.getColumn(1);
	String labelAsHtml = StringEscapeUtils.escapeHtml4(conn.getColumn(2));
		return new LabelValueBean(labelAsHtml, codigo);	
    }

}