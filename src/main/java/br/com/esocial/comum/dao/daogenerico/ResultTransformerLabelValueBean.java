package br.com.esocial.comum.dao.daogenerico;

/**
 * @author Felipe Regalgo
 */
public class ResultTransformerLabelValueBean implements ResultTransformer<LabelValueBean> {

    public static ResultTransformer<LabelValueBean> RESULT_TRANSFORMER = new ResultTransformerLabelValueBean();
    
    @Override
    public LabelValueBean createObject(DataBaseTXInterface conn, String[] aliases) throws Exception {
    	return new LabelValueBean(conn.getColumn(2), conn.getColumn(1));
    }

}