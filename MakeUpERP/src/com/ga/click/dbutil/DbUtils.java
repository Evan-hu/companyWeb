package com.ga.click.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.json.JSONObject;

import com.ga.click.control.DbField;
import com.ga.click.control.GaConstant;
import com.ga.click.exception.BizException;
import com.ga.click.util.GaUtil;
import com.sun.rowset.CachedRowSetImpl;

public class DbUtils {

  public DbUtils() {
  }
  private static DataSource datasource;
  private static ThreadLocal<Connection> threadConnect = new ThreadLocal<Connection> ();
  private static TreeSet<String> MYSQL_KEY = new TreeSet<String>(){
    {
      add("STATE");
      add("TYPE");
    }
  };

  /**
   * 获取数据库连接
   *
   * @return
   * @throws Exception
   */
  public static Connection getConnection() throws BizException {
    Connection conn = (Connection) threadConnect.get();
    if (null == conn) {
      try {
        if (datasource == null) {
          Context initCtx = new InitialContext();
          Context envCtx = (Context) initCtx.lookup("java:comp/env");
          datasource = (DataSource) envCtx.lookup("jdbc/mysql");
          if (datasource == null) {
            throw new BizException(BizException.DB, "Loopup DataSource FAILED!");
          }
        }
        conn = datasource.getConnection();
        //conn.setAutoCommit(true);
        threadConnect.set(conn);
      }catch (Exception ex) {
        ex.printStackTrace();
        throw new BizException(BizException.DB, "数据库连接获取失败");
      }
    }
    return conn;
  }
  
  public static Connection CreateNewConnect() throws BizException {
    Connection conn = null;
      try {
        if (datasource == null) {
          Context initCtx = new InitialContext();
          Context envCtx = (Context) initCtx.lookup("java:comp/env");
          datasource = (DataSource) envCtx.lookup("jdbc/mysql");
          if (datasource == null) {
            throw new BizException(BizException.DB, "Loopup DataSource FAILED!");
          }
        }
        conn = datasource.getConnection();
        conn.setAutoCommit(true);
      }catch (Exception ex) {
        throw new BizException(BizException.DB, "数据库连接获取失败");      
      }
    return conn;
  }

  /**
   * 获取连接并且设置为手动提交事务
   * @throws BizException 
   */
  public static void begin() throws BizException {
    Connection conn = getConnection();
    try {
      conn.setAutoCommit(false);
    }catch (SQLException e) {
      throw new BizException(BizException.DB, "设置手动提交事务失败");
    }
  }

  public static void commit() throws BizException {
    Connection conn = (Connection) threadConnect.get();
    if (null != conn)  {
      try {
    	  if(!conn.getAutoCommit()){
    		  conn.commit();
    		  conn.setAutoCommit(true);
    	  }
      }catch (SQLException e) {
    	  e.printStackTrace();
        throw new BizException(BizException.DB, "手动提交事务失败");
      }
    }
  }

  public static void rollback() throws BizException {
    Connection conn = (Connection) threadConnect.get();
    if (null != conn) {
      try {
        if (!conn.getAutoCommit()) {
          conn.rollback();
          conn.setAutoCommit(true);
        }
      }catch (SQLException e) {
        throw new BizException(BizException.DB, "手动回滚事务失败");
      }
    }
  }

  public static void close() throws BizException {
    Connection conn = (Connection) threadConnect.get();
    try {
      if (null != conn) {
        if(!conn.getAutoCommit()) {//mysql中自动提交模式要先判断
          conn.commit();
        }
        conn.close();
        threadConnect.remove();
      }
    }catch (SQLException e) {
      e.printStackTrace();
      throw new BizException(BizException.DB, "Connection关闭失败");
    }
  }

  public static void close(ResultSet rs) throws BizException {
    try {
      if (null != rs) {
        rs.close();
      }
    }catch (SQLException e) {
      throw new BizException(BizException.DB, "ResultSet关闭失败");
    }
  }

  public static void close(PreparedStatement preStatement) throws BizException {
    try {
      if (null != preStatement) {
        preStatement.close();
      }
    }catch (SQLException e) {
      throw new BizException(BizException.DB, "PreparedStatement关闭失败");
    }
  }

  /**
   * 执行添加处理(事务单独控制),所添加的字段为valuemap和funcfieldmap中的所有key
   * @param table 表名
   * @param valuesMap 值MAP
   * @param funcFieldMap 函数字段MAP(如ID、日期),KEY:字段名;vlaue:函数表达式。
   * @return
   */
  public static int add(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap) {
    StringBuffer updateFieldList = new StringBuffer();
    if (valuesMap != null && valuesMap.keySet() != null) {
      boolean haveField = false;
      for(String key : valuesMap.keySet()) {
        if (!(table+"_ID").equals(key)) {//与oracle不同mysql为自增长
          if (haveField) {
            updateFieldList.append(",");
          }
          updateFieldList.append(key);
          haveField = true;
        }
      }
      if (funcFieldMap != null) {
        for(String key : funcFieldMap.keySet()) {
          if (!valuesMap.containsKey(key)) {
            updateFieldList.append(",");
            updateFieldList.append(key);
          }
        }
      }
    }
    return add(table,valuesMap,funcFieldMap,updateFieldList.toString());
  }
  
  /**
   * 执行添加处理(事务单独控制)【肖宇舟添加自动审核流程判断2014-06-10】
   * @param table 表名
   * @param valuesMap 值对象
   * @param funcFieldMap 函数字段map
   * @param updateFieldStr 所添加字段列表,以逗号分割
   * @return -1:参数不合法;其他:更新行数
 * @throws SQLException 
 * @throws BizException 
   */
  public static int add(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap,String updateFieldStr){
	  if(GaUtil.haveCheckTables.contains(table)){//如果表格包含审核字段,自动启用事物
		begin();
	  }
    PreparedStatement preStatement = null; 
    try {
       //判断空值
       if (GaUtil.isNullStr(table) || valuesMap == null || valuesMap.isEmpty()) {
         return -1;
       }
       //字段列表
       List<String> fieldList = new ArrayList<String>();
       if (!GaUtil.isNullStr(updateFieldStr)) {
         fieldList = Arrays.asList(updateFieldStr.split(","));
       } else {
         fieldList.addAll(valuesMap.keySet());
       }     
       StringBuffer insertSql = new StringBuffer();
       StringBuffer valueSql = new StringBuffer();
       List<Object> valueList = new ArrayList<Object>();
       insertSql.append("insert into ");
       insertSql.append(table);
       insertSql.append("(");        
       valueSql.append(" values(");
       boolean haveField = false;
       for (String key : fieldList) {        
         //处理函数字段
         if (haveField) {
           insertSql.append(",");
           valueSql.append(",");
         }
         haveField = true;
         if (funcFieldMap !=null && !GaUtil.isNullStr(funcFieldMap.get(key))) {
            //函数字段,运行只加入字段,而不加入值(如ID\当前日期等)
             insertSql.append(key.toUpperCase());
             valueSql.append(funcFieldMap.get(key));
             if (funcFieldMap.get(key).indexOf("?") > -1) {              
               if (valuesMap.get(key) != null) {
                     if(valuesMap.get(key) instanceof String && GaUtil.isNullStr(valuesMap.get(key).toString())){
                         //如果类型为String且值为""，则结束本次循环
                         continue;
                     }
                     valueList.add(valuesMap.get(key));
                 }
             }
         } else {
           //普通字段,字段和值必须一一对应,MAP中如对应为空也需加入
           insertSql.append(key.toUpperCase());
           valueSql.append("?");
           valueList.add(valuesMap.get(key));
         }         
       }
       String sql = insertSql.append(")").toString() + valueSql.append(")".toString());
       //执行更新
       preStatement = getConnection().prepareStatement(sql);
       if (valueList != null) {
         int i = 1;
         for (Object value : valueList) {
//           if (value instanceof BigDecimal) {
//             preStatement.setObject(i,((BigDecimal)value).longValue());
//           } else {
           
             preStatement.setObject(i,value);
//           }

           i ++;
         }
       }
       int rowCnt =  preStatement.executeUpdate();
       if(GaUtil.haveCheckTables.contains(table)){//如果表格包含审核字段
    	  //判断该业务是否配置了审核流程
         Map<String,Object> map = DbUtils.queryMap(getConnection(), "select A.*,MAX(STEP) MAX_STEP from (select AC.AUDITING_CONFIG_ID,AC.SYS_CODE_ID,SYS_CODE_NAME,AR.ROLE_ID ,AC.STATE,AR.STEP\n" +
        		 "from  AUDITING_CONFIG AC join AUDITING_ROLE AR on AC.AUDITING_CONFIG_ID = AR.AUDITING_CONFIG_ID  ) a\n" +
        		 " where a.SYS_CODE_ID =  ( select SYS_CODE_ID from SYS_CODE where SYS_CODE_VALUE = ?  and STATE_PATH not like '/0/' limit 0,1) and a.STATE = 1 order by a.STEP  LIMIT 0,1", table);
         if(map != null && !map.isEmpty() && map.get("AUDITING_CONFIG_ID") != null){
           String paramValue = (String)valuesMap.get("PARAM_VALUE");
           Long objectId = GaUtil.getLastId();
           
           JSONObject json = new JSONObject();
           json.put(""+table+"_ID", objectId);
           if(!GaUtil.isNullStr(paramValue)){
        	   try{
        		   json = new JSONObject(paramValue);
        		   json.put(""+table+"_ID", objectId);
        	   }catch (Exception e) {
        		   
        	   }
           }
           String objectName = (String)valuesMap.get("OBJECT_NAME");
           if(GaUtil.isNullStr(objectName)){
        	   throw new BizException(BizException.DB, "该业务需要审核，需设置业务名称（OBJECT_NAME）！");
           }
           //objectName = GaUtil.isNullStr(objectName) ? "暂无" : objectName;
           paramValue = (paramValue == null || "".equals(paramValue)) ? "{'"+table+"_ID':'"+GaUtil.getLastId()+"'}" : paramValue;
           
           DbUtils.execute("insert into AUDITING_EXAMPLE(SYS_CODE_ID,BIZ_ID,SYS_CODE_NAME,ALL_STEP,NOW_STEP,CREATE_TIME,PARAM_VALUE,OBJECT_NAME,STATE,AUDITING_CONFIG_ID) values(?,?,?,?,1,now(),?,?,0,?)", map.get("SYS_CODE_ID"),objectId,map.get("SYS_CODE_NAME"),map.get("MAX_STEP"),paramValue,objectName,map.get("AUDITING_CONFIG_ID"));
           DbUtils.execute("insert into AUDITING(AUDITING_EXAMPLE_ID,STEP,RESULT_CODE,ROLE_ID)  values(?,1,0,?)", GaUtil.getLastId(),map.get("ROLE_ID"));
         }
       }
       commit();
       return rowCnt;
     } catch(Exception ex) {
    	 rollback();
       throw new BizException(BizException.DB, "添加失败:"+ex.getMessage());
     }
     finally {
       close(preStatement);
     }
  }
  
  /**
   * 排除更新，排除MAP中指定字段名进行更新
   * @param table 表名
   * @param valuesMap 值
   * @param funcFieldMap 函数字段
   * @param excludeFields 排除字段，以，号分隔
   * @return
   */
  public static int addExclude(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap,String excludeFields) {
    String[] fieldArray = excludeFields.split(",");
    List<String> excludeList = Arrays.asList(fieldArray);
    String updateFieldList = ",";
    if (valuesMap != null && valuesMap.keySet() != null) {
      for(String key : valuesMap.keySet()) {
        if (!excludeList.contains(key)) {
          updateFieldList = updateFieldList + key +",";  
        }        
      }
      if (funcFieldMap != null) {
        for(String key : funcFieldMap.keySet()) {
          if (!valuesMap.containsKey(key)) {
            if (!excludeList.contains(key)) {
              updateFieldList = updateFieldList + key +",";  
            }            
          }
        }
      }
    }
    //更新字段
    if (updateFieldList.indexOf(",") == 0) updateFieldList = updateFieldList.substring(1);
    if (updateFieldList.endsWith(",")) updateFieldList = updateFieldList.substring(0,updateFieldList.length() -1);
    return add(table,valuesMap,funcFieldMap,updateFieldList);
  }
  
  
  public static int update(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap,String whereFieldList) {
    StringBuffer updateFieldList = new StringBuffer();
    if (valuesMap != null && valuesMap.keySet() != null) {
      boolean haveField = false;
      for(String key : valuesMap.keySet()) {
        if (haveField) {
          updateFieldList.append(",");
        }
        updateFieldList.append(key);
        haveField = true;
      }
    }
    return update(table,valuesMap,funcFieldMap,updateFieldList.toString(),whereFieldList);
  } 
  
  /**
   * 排除更新,指定MAP中排除的字段名
   * @param table
   * @param valuesMap
   * @param funcFieldMap
   * @param excludeFields
   * @param whereFieldList
   * @return
   */
  public static int updateExclude(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap,String excludeFields,String whereFieldList) {
    String[] fieldArray = excludeFields.split(",");
    List<String> excludeList = Arrays.asList(fieldArray);
    String updateFieldList = ",";
    if (valuesMap != null && valuesMap.keySet() != null) {
      for(String key : valuesMap.keySet()) {
        if (!excludeList.contains(key)) {
          updateFieldList = updateFieldList +  key +",";  
        }        
      }
      if (funcFieldMap != null) {
        for(String key : funcFieldMap.keySet()) {
          if (!valuesMap.containsKey(key)) {
            if (!excludeList.contains(key)) {
              updateFieldList = updateFieldList + key +",";  
            }            
          }
        }
      }
    }
    //更新字段
    if (updateFieldList.indexOf(",") == 0) updateFieldList = updateFieldList.substring(1);
    if (updateFieldList.endsWith(",")) updateFieldList = updateFieldList.substring(0,updateFieldList.length() -1);
    return update(table,valuesMap,funcFieldMap,updateFieldList,whereFieldList);
  }
  
  /**
   * 执行表更新
   * @param table 表名称
   * @param valuesMap 值MAP
   * @param funcFieldMap 函数字段列表(key:字段名;value:函数表达式)
   * @param updateFieldList 要更新的字段列表(用,号分隔)
   * @param whereFieldList 更新的条件字段列表(用,号分隔)
   * @return
   */
  public static int update(String table,Map<String,Object> valuesMap,Map<String,String> funcFieldMap,String updateFieldList,String whereFieldList) {
    PreparedStatement preStatement = null; 
    try {
    	  if(GaUtil.haveCheckTables.contains(table)){//如果表格包含审核字段,自动启用事物
    		    begin();
    		    long objCheckState = DbUtils.queryLong("select IFNULL(CHECK_STATE,0) CHECK_STATE from " +table + " where "+ table+"_ID = ? ",valuesMap.get(table+"_ID"));
    		    Map<String,Object> map = DbUtils.queryMap(getConnection(),"select AC.AUDITING_CONFIG_ID,PARAM_VALUE,OBJECT_NAME,AC.CHECK_IN_TYPE,CHECK_LOSE_TYPE,CHECK_SUCCESS_TYPE from AUDITING_EXAMPLE AU join AUDITING_CONFIG AC on AU.AUDITING_CONFIG_ID =AC.AUDITING_CONFIG_ID where AU.SYS_CODE_ID = (select SYS_CODE_ID from SYS_CODE where SYS_CODE_VALUE = ? limit 0,1) and BIZ_ID = ? limit 0,1", table,valuesMap.get(table+"_ID"));
	    	 	long objectId = (Long)valuesMap.get(table+"_ID");
	    	 	Integer configId = 0;
		    	String paramValue = "";
		    	String objectName = "";
		    	Integer inType = 0;
		    	Integer loseType = 0;
		    	Integer successType = 0;
	    	    if(map != null && !map.isEmpty()){//获取上次审核的基本信息
    		    	configId = (Integer)map.get("AUDITING_CONFIG_ID");
    		    	paramValue = (String)map.get("PARAM_VALUE");
    		    	objectName = (String)map.get("OBJECT_NAME");
    		    	inType =  (Integer)map.get("CHECK_IN_TYPE");
    		    	loseType =  (Integer)map.get("CHECK_LOSE_TYPE");
    		    	successType =  (Integer)map.get("CHECK_SUCCESS_TYPE");
			    	if(objCheckState == -1 ){//审核不通过，并且允许再次审核
			    		switch (loseType) {
							case 0://不允许
							  throw new BizException("对不起，该对象审核不通过，禁止修改！");
							case 1://允许
			    		    	Map<String,Object> auditMap = DbUtils.queryMap(getConnection(), "select A.*,MAX(STEP) MAX_STEP from (select AC.AUDITING_CONFIG_ID,AC.SYS_CODE_ID,SYS_CODE_NAME,AR.ROLE_ID ,AC.STATE,AR.STEP\n" +
			   		           		 "from  AUDITING_CONFIG AC join AUDITING_ROLE AR on AC.AUDITING_CONFIG_ID = AR.AUDITING_CONFIG_ID  ) a\n" +
			   		           		 " where a.SYS_CODE_ID =  ( select SYS_CODE_ID from SYS_CODE where SYS_CODE_VALUE = ?  and STATE_PATH not like '/0/' limit 0,1) and a.STATE = 1 order by a.STEP  LIMIT 0,1", table);
			   		            if(auditMap != null && !auditMap.isEmpty() && auditMap.get("AUDITING_CONFIG_ID") != null){
			   		              DbUtils.execute("insert into AUDITING_EXAMPLE(SYS_CODE_ID,BIZ_ID,SYS_CODE_NAME,ALL_STEP,NOW_STEP,CREATE_TIME,PARAM_VALUE,OBJECT_NAME,STATE,AUDITING_CONFIG_ID) values(?,?,?,?,1,now(),?,?,0,?)", auditMap.get("SYS_CODE_ID"),objectId,auditMap.get("SYS_CODE_NAME"),auditMap.get("MAX_STEP"),paramValue,objectName,configId);
			   		              DbUtils.execute("insert into AUDITING(AUDITING_EXAMPLE_ID,STEP,RESULT_CODE,ROLE_ID)  values(?,1,0,?)", GaUtil.getLastId(),auditMap.get("ROLE_ID"));
			   		            }
						}
			    	}else if(objCheckState == 0){//待审核状态，并且
			    		switch (inType) {
							case 0://不允许
								throw new BizException("对不起，该对象正在审核，禁止修改！");
			    		}
			    	}else {//审核通过
			    		switch (successType) {
							case 0://不允许
								throw new BizException("对不起，该对象已经审核通过，禁止修改！");
			    		}
			    	}
	    	    }
    	  }
       //判断空值
       if (GaUtil.isNullStr(table) || valuesMap == null || valuesMap.isEmpty() || GaUtil.isNullStr(whereFieldList)) {
         return -1;
       }
       //条件字段列表
       List<String> whereField = Arrays.asList(whereFieldList.split(","));
       //字段列表
       List<String> updateField = new ArrayList<String>();
       //如未设置更新字段，则默认为所有值字段
       if (!GaUtil.isNullStr(updateFieldList)) {
         updateField = Arrays.asList(updateFieldList.split(","));
       } else {
         updateField.addAll(valuesMap.keySet());
       }
       StringBuffer whereSql = new StringBuffer();
       StringBuffer valueSql = new StringBuffer();
       List<Object> valueList = new ArrayList<Object>();
       valueSql.append("update ");
       valueSql.append(table);
       valueSql.append(" set ");        
       boolean haveField = false;
       for (String key : updateField) {
         //在where条件中出现的则不更新.
         boolean isWhereField = false;
         for(String field : whereField) {
           if (field.equals(key)) {
             isWhereField = true;
             break;
           }
         }
         if (isWhereField) {
           continue;
         }
         //处理函数字段
         if (haveField) {
           valueSql.append(",");
         }
         //处理函数字段
         if (funcFieldMap != null && !GaUtil.isNullStr(funcFieldMap.get(key))) {
            //函数字段,运行只加入字段,而不加入值(如ID\当前日期等)
           valueSql.append(key.toUpperCase());
           valueSql.append("=");
           valueSql.append(funcFieldMap.get(key));
           if (funcFieldMap.get(key).indexOf("?") > -1) {
             if (valuesMap.containsKey(key)) {
               valueList.add(valuesMap.get(key));
             }
           }
         } else {
           //普通字段,字段和值必须一一对应,MAP中如对应为空也需加入
           valueSql.append(key.toUpperCase());
           valueSql.append("=");
           valueSql.append("?");
           valueList.add(valuesMap.get(key));
         }         
         haveField = true;
       }
       //组合where语句
       haveField = false;
       for (String key : whereField) {        
         //处理函数字段
         if (haveField) {
           whereSql.append(" and ");
         }
         if (funcFieldMap !=null && !GaUtil.isNullStr(funcFieldMap.get(key))) {
            //函数字段,运行只加入字段,而不加入值(如ID\当前日期等)
           whereSql.append(key.toUpperCase());
           whereSql.append("=");
           whereSql.append(funcFieldMap.get(key));
           if (funcFieldMap.get(key).indexOf("?") >-1) {
             if (valuesMap.containsKey(key)) {
               valueList.add(valuesMap.get(key));
             }
           }
         } else {
           //普通字段,字段和值必须一一对应,MAP中如对应为空也需加入
           whereSql.append(key.toUpperCase());
           whereSql.append("=");
           whereSql.append("?");
           valueList.add(valuesMap.get(key));
         }         
         haveField = true;
       }
       String sql = valueSql.toString() +" where "+ whereSql.toString();
       //执行更新       
//       System.out.println(sql);
       preStatement = getConnection().prepareStatement(sql);
       if (valueList != null) {
         int i = 1;
         for (Object value : valueList) {
//           if (value instanceof BigDecimal) {
//             preStatement.setObject(i,((BigDecimal)value).longValue());
//           } else {
             preStatement.setObject(i,value);
//           }
           i ++;
         }
       }
       commit();
       return preStatement.executeUpdate();
     } catch(Exception ex) {
       ex.printStackTrace();
       rollback();
       throw new BizException(BizException.DB, "更新失败-"+ex.getMessage());
     }
  }
  
  /**
   * 执行删除处理
   * @param tableName
   * @param idField
   * @param value
   */
  public static int del(String tableName,String idField,Object value) {
    PreparedStatement preStatement = null; 
    try {
       //判断空值
       if (GaUtil.isNullStr(tableName) || GaUtil.isNullStr(idField) || value == null){
         return -1;
       }
       StringBuffer sb =  new StringBuffer();
       sb.append("delete from ");
       sb.append(tableName.toUpperCase());
       sb.append(" where ");
       sb.append(idField.toUpperCase());
       sb.append("=?");
       preStatement = getConnection().prepareStatement(sb.toString());
       preStatement.setObject(1,value);
       return preStatement.executeUpdate();
     } catch(Exception ex) {
       throw new BizException(BizException.DB, "删除失败-"+ex.getMessage());
     }
     finally {
       close(preStatement);
     }
  }
  
  /**
   * 查询离线记录集
   * @param sql
   * @param params
   * @return
   * @throws BizException
   */
  public static  CachedRowSet query(String sql, Object ...params) throws BizException {
    ResultSet rs = null;
    PreparedStatement preStatement = null;
    try {
      preStatement = getConnection().prepareStatement(sql);
      for (int i = 0, en = params.length; i < en; i++) {
        preStatement.setObject(i + 1,params[i]);
      }
      rs = preStatement.executeQuery();
      CachedRowSet crs = new CachedRowSetImpl();
      crs.populate(rs);
      return crs;
    }catch (Exception ex) {ex.printStackTrace();
      throw new BizException(BizException.DB, "查询失败");
    }finally {
      close(rs);
      close(preStatement);
    }
  }
  
  /**
   * 执行查询并直接转为同dbField定义相符合的MAP对象列表
   * @param conn
   * @param sql
   * @param dbFieldList
   * @param params
   * @return
   */
  public static List<Map<String, Object>> queryMapList(List<DbField> dbFieldList,String sql,Object... params) {
    ResultSet rs = null;
    PreparedStatement preStatement = null;
    try {
      preStatement = getConnection().prepareStatement(sql);
      for (int i = 0, en = params.length; i < en; i++) {
          preStatement.setObject(i + 1,params[i]);
      }
      rs = preStatement.executeQuery();
      return DbUtils.getDataMapList(dbFieldList, rs);
    }catch (Exception ex) {ex.printStackTrace();
      throw new BizException(BizException.DB, "查询失败");
    }finally {
      close(rs);
      close(preStatement);
    }
  }
  
  /**
   * 查询数据并返回第一条记录转换的MAP
   * @param sql
   * @param dbFieldList
   * @param params
   * @return
   */
  public static Map<String, Object> queryMap(List<DbField> dbFieldList,String sql, Object... params) {
    ResultSet rs = null;
    PreparedStatement preStatement = null;
    try {
      preStatement = getConnection().prepareStatement(sql.toUpperCase());
      for (int i = 0, en = params.length; i < en; i++) {
        preStatement.setObject(i + 1,params[i]);
      }
      rs = preStatement.executeQuery();
      return DbUtils.getDataMap(dbFieldList, rs);
    }catch (Exception ex) {ex.printStackTrace();
      throw new BizException(BizException.DB, "查询失败");
    }finally {
      close(rs);
      close(preStatement);
    }
  }
  

  /**
   * 利用连接执行更新处理(需外部手动控制事务)
   * @param conn
   * @param sql
   * @param params
   * @return
   * @throws BizException
   */
  public static int execute(String sql, Object ...params) throws BizException {
    PreparedStatement preStatement = null;
    try {
      preStatement = getConnection().prepareStatement(sql.toUpperCase());
      if (params != null) {
        for (int j = 0; j < params.length; j++) {
          preStatement.setObject(j + 1,params[j]);
        }
      }
      return preStatement.executeUpdate();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new BizException(BizException.DB, "更新失败");
    }finally {
      close(preStatement);
    }
  }
   
  
  /**
   * 获取查询对象(不依赖dbField定义)
   * 注意:本查询的MAP中的值对象同DbField中的定义是有所差异,比如ID类型此函数返回DECIMAL类型，
   * 而DbField是作为LONG对象处理的
   * @param conn
   * @param sql
   * @param params
   * @return
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> queryMap(Connection conn, String sql, Object... params) {
    ResultSetHandler rsHandler = new MapHandler();
    QueryRunner queryRunner = new QueryRunner();
    Map<String, Object> result = null;
    try {
      result = (Map<String, Object>)queryRunner.query(conn, sql, rsHandler, params);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new BizException(BizException.SYSTEM,"DB查询错误!");
    }
    return result;
  }

  
  /**
   * 查询多条记录,将ResultSet中每一行的数据映射成Map并装入List(不依赖dbField定义).
   * 注意:本查询的MAP中的值对象同DbField中的定义是有所差异,比如ID类型此函数返回DECIMAL类型，
   * @param sql 查询语句
   * @param params 查询条件
   * @return List<Map<字段名, 字段值>>
   * @throws DbUtilException
   */
  @SuppressWarnings("unchecked")
  public static List<Map<String, Object>> queryMapList(Connection conn, String sql, Object... params) {
    ResultSetHandler  rsHandler = new MapListHandler();
    QueryRunner queryRunner = new QueryRunner();
    List<Map<String, Object>> result = null;
    try {
      
      result = (List<Map<String, Object>>)queryRunner.query(conn, sql, rsHandler, params);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new BizException(BizException.SYSTEM,"DB查询错误!");
    }
    return result;
  }
  
 

  public static Long queryLong(String sql, Object ...params) throws BizException {
    CachedRowSet rs = query(sql.toUpperCase(), params);
    try {
      if (rs.next()) {
        Long num = rs.getLong(1);
        return num;
      }
    }catch (SQLException e) {
      throw new BizException(BizException.DB, "查询失败");
    }
    return null;
  }
  
  
  public static void closeConn(Connection conn) {
    try {
      if (!conn.isClosed()) conn.close();
    }
    catch (Exception ex){
      ex.printStackTrace();
    }
  }
  /**
   * 执行INSERT, UPDATE, 或 DELETE. 需要提供连接. 事务需要手动处理.
   * @param conn
   * @param sql 执行的sql
   * @param params sql的参数
   * @return 更新的行数.
   * @throws DbUtilException
   */
  public static int update(Connection conn, String sql, Object... params) {
    PreparedStatement preStatement = null;
    try {
      preStatement = conn.prepareStatement(sql);
      if (params != null) {
        for (int j = 0; j < params.length; j++) {
          preStatement.setObject(j + 1,params[j]);
        }
      }
      return preStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(BizException.DB, "更新失败");
    }finally {
      close(preStatement);
    }
  }
  
  public static int update(String sql, Object... params) {
    PreparedStatement preStatement = null;
    try {
      preStatement = getConnection().prepareStatement(sql);
      if (params != null) {
        for (int j = 0; j < params.length; j++) {
          preStatement.setObject(j + 1,params[j]);
        }
      }
      return preStatement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(BizException.DB, "更新失败");
    }finally {
      close(preStatement);
    }
  }
  
  
  public static String changeSql(String sql) {
    if (sql == null || "".equals(sql)) {
      return "";
    }
    return "";
  }
  
  /**
   * 直接返回第一行记录MAP
   * @param dbField 
   * @param rs
   * @return
   */
  public static Map<String,Object> getDataMap(List<DbField> dbField,ResultSet rs) {
    try {
      if (rs == null || !rs.next()) {
        return null;
      }      
      return getRowDataMap(dbField,rs);
    } catch(Exception ex) {
      throw new BizException(BizException.SYSTEM,"读取数据时异常");
    }

  }
  
  /**
   * 获取当前行的数据(调用前必须先移动记录集)
   * @param dbField
   * @param rs
   * @throws SQLException 
   */
  public static Map<String,Object> getRowDataMap(List<DbField> dbField,ResultSet rs) throws SQLException {
  Map<String,Object> dataMap = new HashMap<String,Object>();

  for(DbField field : dbField) {
    String fieldCode = field.getFieldCode(false);
    try {
      if (rs.getObject(fieldCode) == null) {
        dataMap.put(fieldCode, null);
        continue;
      }
      switch (field.getDataType()) 
      {
      case GaConstant.DT_DATETIME:
        dataMap.put(fieldCode,rs.getTimestamp(fieldCode));
        break;
      case GaConstant.DT_DOUBLE:
        dataMap.put(fieldCode,rs.getDouble(fieldCode));
        break;
      case GaConstant.DT_INT:
        dataMap.put(fieldCode,rs.getInt(fieldCode));
        break;
      case GaConstant.DT_LONG:
        dataMap.put(fieldCode,rs.getLong(fieldCode));           
        break;
      case GaConstant.DT_MONEY:
        dataMap.put(fieldCode,rs.getBigDecimal(fieldCode));           
        break;
      case GaConstant.DT_PASSWORD:
      case GaConstant.DT_STRING:
      default:
        dataMap.put(fieldCode,rs.getString(fieldCode));
        break;
      }
    } catch(SQLException e) {
      //表示缺少字段定义或类型转换失败
    }
  }
  return dataMap;}
  /**
   * 返回记录集对应的LIST
   * @param dbField
   * @param rs
   * @return
   */
  public static List<Map<String,Object>> getDataMapList(List<DbField> dbField,ResultSet rs) {
    try {
      if (rs == null) {
        return null;
      }
      List<Map<String,Object>> rtnList =new ArrayList<Map<String,Object>>();
      while (rs.next()) {
        rtnList.add(getRowDataMap(dbField,rs));
      }
      return rtnList;
    } catch(Exception ex) {
      throw new BizException(BizException.SYSTEM,"读取数据时异常");
    }
  }
/**
  public static Object changeCharSetUTF8Obj(Object params) throws UnsupportedEncodingException {
    if (params instanceof String) {
      return params.toString().getBytes("UTF-8");
    }
    return params;
  }

  public static String changeCharSetUTF8String(String params) throws UnsupportedEncodingException {
    if (params instanceof String) {
      return new String(params.getBytes("UTF-8"),"UTF-8");
    }
    return params;
  }
  
  public static void changeCharSetUTF8Map(Map<String, Object> map) throws UnsupportedEncodingException {
    for(Map.Entry<String, Object> entry: map.entrySet()) {
      if (entry.getValue() instanceof String) {
        map.put(entry.getKey(), new String(entry.getValue().toString().getBytes("UTF-8"),"UTF-8"));
      }
     }
  }
  public static void changeCharSetUTF8String(Map<String, String> map) throws UnsupportedEncodingException {
    for(Map.Entry<String, String> entry: map.entrySet()) {
      if (entry.getValue() instanceof String) {
        String str = new String(entry.getValue().toString().getBytes(), "UTF-8");
        map.put(entry.getKey(), str);
      }
     }
  }
  **/
}
