package br.com.esocial.comum.dao.daogenerico;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import br.com.atlantic.comum.utils.Contexto;
import br.com.atlantic.comum.utils.Log;
import br.com.atlantic.comum.utils.MsgExcept;
import br.com.atlantic.comum.utils.Utils;

/**
 * Classe de acesso &agrave; tabela <tt>TB_ERROS</tt>.
 *
 * @author BeanGen - Gerado automaticamente
 * @version $Revision: 1.1 $ $Date: 2007/01/27 16:24:16 $
 */
public class ErrosDAOBase {
    //Nome da classe.
    private final String CLASS_NAME =
        getClass().getName().replaceFirst(getClass().getPackage().getName() + ".", "");

   /** Objeto de log. */
   private Log log = new Log(CLASS_NAME);

   protected Contexto contexto = null;

   public ErrosDAOBase(){}

   public ErrosDAOBase(Contexto contexto){
       this.contexto = contexto;
   }

   /**
    * Obt&eacute;m o nome do m�todo <em>getter</em> de um campo.
    *
    * @param name Nome do campo.
    * @return Nome do m�todo <em>getter</em>.
    */
   String getterName(String name) {
      switch (name.length()) {
         case 0: return "get";
         case 1: return "get" + name.toUpperCase();
      }
      return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
   }

   /**
    * Obt&eacute;m nome da coluna associado ao nome do campo.
    *
    * @param name Nome do campo de uma classe DTO.
    * @return Nome da coluna.
    */
   String columnName(String name) {
      String col = new String();

      for (int i = 0; i < name.length(); i++) {
         if (Character.isUpperCase(name.charAt(i)))
            col += '_';
         col += Character.toUpperCase(name.charAt(i));
      }
      return col;
   }

   /**
    * L&ecirc; registros da tabela <tt><tabela></tt> atendendo
    * &agrave;s restri&ccedil;&otilde;es passadas.
    *
    * @param dto Objeto DTO com restri&ccedil;&otilde;es preenchidas.
    * @param list Lista com nome dos campos do objeto DTO.
    * @return <tt>Collection</tt> com resultados obtidos.
    * @throws MsgExcept Erro de execu&ccedil;&atilde;o SQL.
    * @throws Exception
    */
    public Collection find(Erros dto, String list[])
       throws MsgExcept, Exception {
        DataBaseTX conn = null;

        try {
            conn = new DataBaseTX(CLASS_NAME);
            if (!conn.connect()) {
                log.log("Erro na conex\u00E3o...");
                throw new MsgExcept(1000);
            }
            return find(dto, list, conn);
        }
        catch (MsgExcept e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
                log.log("Desconectei...");
            }
        }
    }

   /**
    * L&ecirc; registros da tabela <tt><tabela></tt> atendendo
    * &agrave;s restri&ccedil;&otilde;es passadas.
    *
    * @param dto Objeto DTO com restri&ccedil;&otilde;es preenchidas.
    * @param list Lista com nome dos campos do objeto DTO.
    * @return <tt>Collection</tt> com resultados obtidos.
    * @throws MsgExcept Erro de execu&ccedil;&atilde;o SQL.
    * @throws Exception
    */
   public Collection find(Erros bean, String list[], DataBaseTXInterface conn)
   throws MsgExcept, Exception {
      Vector v = new Vector();
      String query = "SELECT * FROM TB_ERROS WHERE ";

      if (contexto != null){
          bean.setCodIns(contexto.getCodIns());
          bean.setNomUsuUltAtu(contexto.getLogin());
      }

      for (int i = 0; i < list.length; i++) {
         query += columnName(list[i]) + " = ? ";
         if (i < list.length - 1)
            query += "AND ";
      }

      conn.prepareStatement(query);
      for (int i = 0; i < list.length; i++) {
         final String getter = getterName(list[i]);
         Method m = Erros.class.getDeclaredMethod(getter, null);

         conn.setType("P", "%s", i + 1, m.invoke(bean, null).toString());
      }

      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do SELECT...");
         throw new MsgExcept(1002);
      }

      while (conn.next()) {
         Erros dto = new Erros();

         dto.setCodIns(conn.getColumn("COD_INS"));
         dto.setCodErro(conn.getColumn("COD_ERRO"));
         dto.setDesErro(conn.getColumn("DES_ERRO"));
         dto.setDatIng(conn.getColumnDate("DAT_ING") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ING").getTime())));
         dto.setDatUltAtu(conn.getColumnDate("DAT_ULT_ATU") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ULT_ATU").getTime())));
         dto.setNomUsuUltAtu(conn.getColumn("NOM_USU_ULT_ATU"));
         dto.setNomProUltAtu(conn.getColumn("NOM_PRO_ULT_ATU"));

         v.add(dto);
      }

      return v;
   }

   /**
    * L&ecirc; registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO com chave preenchida.
    * @return DTO com dados da chave.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    */
   public Erros findByPrimaryKey(Erros dto) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         Erros found = findByPrimaryKey(dto, conn);
         conn.commit();
         return found;
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * L&ecirc; registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO com chave preenchida.
    * @param conn Objeto de conex&atilde;o.
    * @return DTO com dados da chave.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public Erros findByPrimaryKey(Erros dto, DataBaseTXInterface conn) throws MsgExcept, Exception {
      String query = "SELECT * FROM TB_ERROS " +
        " WHERE COD_ERRO = ? " +
        "  AND COD_INS = ? ";

      conn.prepareStatement(query);

      if (contexto != null){
          dto.setCodIns(contexto.getCodIns());
          dto.setNomUsuUltAtu(contexto.getLogin());
      }

      conn.setType("P", "%s", 1, dto.getCodErro());
      conn.setType("P", "%s", 2, dto.getCodIns());

      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do SELECT...");
         throw new MsgExcept(1002);
      }

      if (conn.next()) {
         dto.setCodIns(conn.getColumn("COD_INS"));
         dto.setCodErro(conn.getColumn("COD_ERRO"));
         dto.setDesErro(conn.getColumn("DES_ERRO"));
         dto.setDatIng(conn.getColumnDate("DAT_ING") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ING").getTime())));
         dto.setDatUltAtu(conn.getColumnDate("DAT_ULT_ATU") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ULT_ATU").getTime())));
         dto.setNomUsuUltAtu(conn.getColumn("NOM_USU_ULT_ATU"));
         dto.setNomProUltAtu(conn.getColumn("NOM_PRO_ULT_ATU"));

         return dto;
      }

      return null;
   }

   /**
    * L&ecirc; todos os registros da tabela <tt>TB_ERROS</tt>.
    *
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    * @return <tt>Collection</tt> com todos os registros da tabela.
    */
   public Collection findAll() throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         Collection coll = findAll(conn);
         conn.commit();
         return coll;
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * L&ecirc; todos os registros da tabela <tt>TB_ERROS</tt>.
    *
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    * @return <tt>Collection</tt> com todos os registros da tabela.
    */
   public Collection findAll(DataBaseTXInterface conn) throws MsgExcept, Exception {
      String query = "SELECT * FROM TB_ERROS";
      Vector v = new Vector();

      conn.prepareStatement(query);
      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do SELECT...");
         throw new MsgExcept(1002);
      }

      while (conn.next()) {
         Erros dto = new Erros();

         dto.setCodIns(conn.getColumn("COD_INS"));
         dto.setCodErro(conn.getColumn("COD_ERRO"));
         dto.setDesErro(conn.getColumn("DES_ERRO"));
         dto.setDatIng(conn.getColumnDate("DAT_ING") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ING").getTime())));
         dto.setDatUltAtu(conn.getColumnDate("DAT_ULT_ATU") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ULT_ATU").getTime())));
         dto.setNomUsuUltAtu(conn.getColumn("NOM_USU_ULT_ATU"));
         dto.setNomProUltAtu(conn.getColumn("NOM_PRO_ULT_ATU"));

         v.add(dto);
      }
      return v;
   }

   protected Collection findWhere(String whereClause) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         Collection coll = findWhere(whereClause, conn);
         conn.commit();
         return coll;
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   protected Collection findWhere(String whereClause, DataBaseTXInterface conn) throws MsgExcept, Exception {
      String query = "SELECT * FROM TB_ERROS WHERE " + whereClause;
      Vector v = new Vector();

      conn.prepareStatement(query);
      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do SELECT...");
         throw new MsgExcept(1002);
      }

      while (conn.next()) {
         Erros dto = new Erros();

         dto.setCodIns(conn.getColumn("COD_INS"));
         dto.setCodErro(conn.getColumn("COD_ERRO"));
         dto.setDesErro(conn.getColumn("DES_ERRO"));
         dto.setDatIng(conn.getColumnDate("DAT_ING") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ING").getTime())));
         dto.setDatUltAtu(conn.getColumnDate("DAT_ULT_ATU") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ULT_ATU").getTime())));
         dto.setNomUsuUltAtu(conn.getColumn("NOM_USU_ULT_ATU"));
         dto.setNomProUltAtu(conn.getColumn("NOM_PRO_ULT_ATU"));

         v.add(dto);
      }
      return v;
   }

   /**
    * L&ecirc; registros da tabela <tt><tabela></tt> atendendo
    * &agrave;s restri&ccedil;&otilde;es passadas.
    *
    * @param dto Objeto DTO com restri&ccedil;&otilde;es preenchidas.
    * @param list Lista com nome dos campos do objeto DTO.
    * @return <tt>Collection</tt> com resultados obtidos.
    * @throws MsgExcept Erro de execu&ccedil;&atilde;o SQL.
    * @throws Exception
    */
    public Collection findLike(Erros dto, String list[], String like)
       throws MsgExcept, Exception {
        DataBaseTX conn = null;

        try {
            conn = new DataBaseTX(CLASS_NAME);
            if (!conn.connect()) {
                log.log("Erro na conex\u00E3o...");
                throw new MsgExcept(1000);
            }
            return findLike(dto, list, like, false, true, conn);
        }
        catch (MsgExcept e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
                log.log("Desconectei...");
            }
        }
    }

   /**
    * L&ecirc; registros da tabela <tt><tabela></tt> atendendo
    * &agrave;s restri&ccedil;&otilde;es passadas.
    *
    * @param dto Objeto DTO com restri&ccedil;&otilde;es preenchidas.
    * @param list Lista com nome dos campos do objeto DTO.
    * @return <tt>Collection</tt> com resultados obtidos.
    * @throws MsgExcept Erro de execu&ccedil;&atilde;o SQL.
    * @throws Exception
    */
    public Collection findLike(Erros dto, String list[], String like, boolean firstPer, boolean lastPer)
       throws MsgExcept, Exception {
        DataBaseTX conn = null;

        try {
            conn = new DataBaseTX(CLASS_NAME);
            if (!conn.connect()) {
                log.log("Erro na conex\u00E3o...");
                throw new MsgExcept(1000);
            }
            return findLike(dto, list, like, firstPer, lastPer, conn);
        }
        catch (MsgExcept e) {
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (conn != null) {
                conn.disconnect();
                log.log("Desconectei...");
            }
        }
    }

   /**
    * L&ecirc; registros da tabela <tt><tabela></tt> atendendo
    * &agrave;s restri&ccedil;&otilde;es passadas.
    *
    * @param dto Objeto DTO com restri&ccedil;&otilde;es preenchidas.
    * @param list Lista com nome dos campos do objeto DTO.
    * @return <tt>Collection</tt> com resultados obtidos.
    * @throws MsgExcept Erro de execu&ccedil;&atilde;o SQL.
    * @throws Exception
    */
   public Collection findLike(Erros bean, String list[], String like, boolean firstPer, boolean lastPer, DataBaseTXInterface conn)
   throws MsgExcept, Exception {
      Vector v = new Vector();

      if (contexto != null){
          bean.setCodIns(contexto.getCodIns());
          bean.setNomUsuUltAtu(contexto.getLogin());
      }

      String query = "SELECT * FROM TB_ERROS WHERE ";

      for (int i = 0; i < list.length; i++) {
         query += columnName(list[i]) + " = ? ";
         if (i < list.length - 1)
            query += "AND ";
      }

      String strFirstPer = firstPer == true ? "%" : "";
      String strLastPer = lastPer == true ? "%" : "";
      String getter = getterName(like);
      Method m = Erros.class.getDeclaredMethod(getter, null);

      query += "AND UPPER(" + columnName(like) + ") LIKE '" + strFirstPer + m.invoke(bean, null).toString().toUpperCase() + strLastPer + "'";
      query += "ORDER BY "+columnName(like);
      conn.prepareStatement(query);
      for (int i = 0; i < list.length; i++) {
         getter = getterName(list[i]);
         m = Erros.class.getDeclaredMethod(getter, null);

         conn.setType("P", "%s", i + 1, m.invoke(bean, null).toString());
      }

      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do SELECT...");
         throw new MsgExcept(1002);
      }

      while (conn.next()) {
         Erros dto = new Erros();

         dto.setCodIns(conn.getColumn("COD_INS"));
         dto.setCodErro(conn.getColumn("COD_ERRO"));
         dto.setDesErro(conn.getColumn("DES_ERRO"));
         dto.setDatIng(conn.getColumnDate("DAT_ING") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ING").getTime())));
         dto.setDatUltAtu(conn.getColumnDate("DAT_ULT_ATU") == null ? "" : Utils.getDateTime(new Timestamp(conn.getColumnDate("DAT_ULT_ATU").getTime())));
         dto.setNomUsuUltAtu(conn.getColumn("NOM_USU_ULT_ATU"));
         dto.setNomProUltAtu(conn.getColumn("NOM_PRO_ULT_ATU"));

         v.add(dto);
      }

      return v;
   }

   /**
    * Insere registro na tabela <tt>TB_ERROS</tt>.
    * @param dto Objeto DTO a ser persistido.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    */
   public void create(Erros dto) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         create(dto, conn);
         conn.commit();
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * Insere registro na tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser persistido.
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public void create(Erros dto, DataBaseTXInterface conn) throws MsgExcept, Exception {

      if (contexto != null){
          dto.setCodIns(contexto.getCodIns());
          dto.setNomUsuUltAtu(contexto.getLogin());
      }

      if (findByPrimaryKey(dto, conn)!=null){
        log.log("Erro de chave duplicada...");
        throw new MsgExcept(1006);
      }

      String sql = "INSERT INTO TB_ERROS " +
        "("+
           " COD_INS," +
           " COD_ERRO," +
           " DES_ERRO," +
           " DAT_ING," +
           " DAT_ULT_ATU," +
           " NOM_USU_ULT_ATU," +
           " NOM_PRO_ULT_ATU)" +
        " VALUES(?,?,?,?,?,?,?)";

      conn.prepareStatement(sql);
      conn.setType("P", "%s", 1, dto.getCodIns());
      conn.setType("P", "%s", 2, dto.getCodErro());
      conn.setType("P", "%s", 3, dto.getDesErro());
      conn.setType("P", "%dh", 4, dto.getDatIng().equals("") ? Utils.strToTimestamp(Utils.getDateTime()) : Utils.strToTimestamp(dto.getDatIng()));
      conn.setType("P", "%dh", 5, Utils.strToTimestamp(Utils.getDateTime()));
      conn.setType("P", "%s", 6, dto.getNomUsuUltAtu());
      conn.setType("P", "%s", 7, CLASS_NAME);

      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do INSERT...");
         throw new MsgExcept(1002);
      }
   }

   /**
    * Insere registro na tabela <tt>TB_ERROS</tt>.
    * @param dto Objeto DTO a ser persistido.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    */
   public void create(Erros dto, ArrayList listPar) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         create(dto, listPar, conn);
         conn.commit();
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * Insere registro na tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser persistido.
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public void create(Erros dto, ArrayList listPar, DataBaseTXInterface conn) throws MsgExcept, Exception {

      for (int i=0; i<listPar.size(); ++i) {
        String list[] = (String[]) listPar.get(i);

        if (!find(dto, list, conn).isEmpty()) {
          log.log("Erro de chave �nica duplicada...");
          throw new MsgExcept(1006);
        }
      }

      create(dto, conn);
   }

   /**
    * Insere registro na tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser persistido.
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public void create(Erros dto, String[] list, DataBaseTXInterface conn) throws MsgExcept, Exception {
      if (!find(dto, list, conn).isEmpty()) {
        log.log("Erro de chave �nica duplicada...");
        throw new MsgExcept(1006);
      }

      create(dto, conn);
   }

   /**
    * Remove registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser removido.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    */
   public void remove(Erros dto) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         remove(dto, conn);
         conn.commit();
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * Remove registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser removido.
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public void remove(Erros dto, DataBaseTXInterface conn) throws MsgExcept, Exception {

      if (contexto != null){
          dto.setCodIns(contexto.getCodIns());
          dto.setNomUsuUltAtu(contexto.getLogin());
      }

      //Verificando Integridades...
      String sql = "DELETE FROM TB_ERROS " +
        " WHERE COD_ERRO = ? " +
        "  AND COD_INS = ? ";

      conn.prepareStatement(sql);
      conn.setType("P", "%s", 1, dto.getCodErro());
      conn.setType("P", "%s", 2, dto.getCodIns());

      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do DELETE...");
         throw new MsgExcept(1002);
      }
   }

   /**
    * Atualiza registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser atualizado.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Erro de conex&atilde;o.
    */
   public void store(Erros dto) throws MsgExcept, Exception {
      DataBaseTX conn = null;

      try {
         conn = new DataBaseTX(CLASS_NAME);
         if (!conn.connect()) {
            log.log("Erro na conex\u00E3o...");
            throw new MsgExcept(1000);
         }
         store(dto, conn);
         conn.commit();
      }
      catch (MsgExcept e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      catch (Exception e) {
         if (conn != null)
            conn.rollback();
         throw e;
      }
      finally {
         if (conn != null) {
            conn.disconnect();
            log.log("Desconectei...");
         }
      }
   }

   /**
    * Atualiza registro da tabela <tt>TB_ERROS</tt>.
    *
    * @param dto Objeto DTO a ser atualizado.
    * @param conn Objeto de conex&atilde;o.
    * @throws MsgExcept Caso ocorram erros de SQL.
    * @throws Exception Caso ocorram erros.
    */
   public void store(Erros dto, DataBaseTXInterface conn) throws MsgExcept, Exception {

      if (contexto != null){
          dto.setCodIns(contexto.getCodIns());
          dto.setNomUsuUltAtu(contexto.getLogin());
      }

      String sql = "UPDATE TB_ERROS " +
        "  SET "+
        "      COD_INS = ?, " +
        "      COD_ERRO = ?, " +
        "      DES_ERRO = ?, " +
        "      DAT_ING = ?, " +
        "      DAT_ULT_ATU = ?, " +
        "      NOM_USU_ULT_ATU = ?, " +
        "      NOM_PRO_ULT_ATU = ? " +
        " WHERE COD_ERRO = ? " +
        "  AND COD_INS = ? ";

      conn.prepareStatement(sql);
      conn.setType("P", "%s", 1, dto.getCodIns());
      conn.setType("P", "%s", 2, dto.getCodErro());
      conn.setType("P", "%s", 3, dto.getDesErro());
      conn.setType("P", "%dh", 4, dto.getDatIng().equals("") ? Utils.strToTimestamp(Utils.getDateTime()) : Utils.strToTimestamp(dto.getDatIng()));
      conn.setType("P", "%dh", 5, Utils.strToTimestamp(Utils.getDateTime()));
      conn.setType("P", "%s", 6, dto.getNomUsuUltAtu());
      conn.setType("P", "%s", 7, CLASS_NAME);
      conn.setType("P", "%s", 8, dto.getCodErro());
      conn.setType("P", "%s", 9, dto.getCodIns());
      if (conn.execute() < 0) {
         log.log("Erro na execu\u00E7\u00E3o do UPDATE...");
         throw new MsgExcept(1002);
      }
   }
}