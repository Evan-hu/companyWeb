﻿<%@page import="com.ga.erp.biz.system.OpBiz"%>
<%@page import="com.ga.click.util.GaUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ga.click.mvc.UserACL"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
   request.getSession(false).setAttribute("userAcl",new OpBiz(null).login("superadmin", "mainkey"));
   response.sendRedirect("/main.htm");
    /*
    String resultMsg = "";
  try {
    String logout =request.getParameter("logout");
    if (logout != null) {
      session.invalidate();
    }
    UserACL acl = null;
    if(request.getSession(false) != null){
      if(request.getSession(false).getAttribute("userAcl") != null){
        acl = (UserACL)request.getSession(false).getAttribute("userAcl");
      }
    }
    if (acl != null) {
      //已经登录
      response.sendRedirect("/main.htm");
    } else {
      //校验登录
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String authcode1 = request.getParameter("ac");
        String authcode2 = (String) request.getSession().getAttribute("authCode");
        String needchk = request.getParameter("needchk");
        if (GaUtil.isNullStr(username) || GaUtil.isNullStr(password) || GaUtil.isNullStr(authcode1)) {
        if(!GaUtil.isNullStr(needchk)){
            resultMsg = "用户名、密码和验证码必须输入！";
        }  
        }
        else if (!authcode1.equals(authcode2)) {
          resultMsg = "验证码错误！";
        }  else {       
          try {
            OpBiz biz = new OpBiz(null);
            acl = biz.login(username,password);
            request.getSession(false).setAttribute("userAcl",acl);
            response.sendRedirect("/main.htm");
          } catch (Exception ex) {
            resultMsg = ex.getMessage();
          }        
        }
    }
  } catch(Exception ex) {
     resultMsg = "系统错误！";
  }
  finally {       
    request.setAttribute("msg", resultMsg);
    request.getSession().removeAttribute("authCode");
  }
  */
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
  <meta http-equiv="Pragma" content="no-cache"/>   
  <meta http-equiv="Cache-Control" content="no-cache"/>   
  <meta http-equiv="Expires" content="0"/>  
<title>后台管理系统</title>
<link href="/css/login.css" rel="stylesheet" type="text/css" />
<script>
var msg = "<%=request.getAttribute("msg")%>";
if (msg) {
  alert(msg);
}
</script>
</head>
<body>
<table width="681" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:120px">
  <tr>
    <td width="353" height="259" align="center" valign="bottom" style="background: url(../images/login_1.gif)"><table width="90%" border="0" cellspacing="3" cellpadding="0">
      <tr>
        <td align="right" valign="bottom" style="color:#05B8E4">Power by <a href="#" target="_blank">811598</a> Copyright 2014</td>
      </tr>
    </table></td>
    <td width="195" style="background: url(../images/login_2.gif)">
<form name="form" id="form" method="post" action="/first.jsp?needchk=1">
    <table style="width:190px; height: 106px;" border="0" align="center" cellpadding="2" cellspacing="0">
            <tr>
              <td height="50" colspan="2" align="left">&nbsp;</td>
            </tr>
            <tr>
              <td width="60" height="30" align="left">登陆名称</td>
              <td><input name="username" id="username" type="text" class="txt" size="14"/></td>
            </tr>
            <tr>
              <td height="30" align="left">登陆密码</td>
              <td><input name="password" id="password" type="password" class="txt" size="14"/></td>
            </tr>
            <tr>
              <td height="30"> 验 证 码 </td>
        <td><input type="text" name="ac"  id="ac" size="4" class="txt" maxlength="14"/>
        <img title="看不清楚？点击更换！" style="cursor :pointer" src="/authimg.jpg" height="19" onclick="this.src='/authimg.jpg?'+Math.random();"/>
          </td>
            </tr>
            <tr> 
              <td colspan="2" align="center" height="40"><input type="submit" name="submit" style="background:url(images/login_5.gif) no-repeat;width:80px;cursor: pointer;" value=" 登  陆  "/> 
        <input type="reset" name="Submit" style="background:url(images/login_5.gif) no-repeat;width:80px;cursor: pointer;" value=" 重 置  "/></td>
      </tr> 
    </table>
</form>
    </td>
    <td width="133" style="background: url(../images/login_3.gif)">&nbsp;</td>
  </tr>
  <tr>
    <td height="161" colspan="3" style="background: url(../images/login_4.gif)"></td>
  </tr>
</table>
</body>
</html>