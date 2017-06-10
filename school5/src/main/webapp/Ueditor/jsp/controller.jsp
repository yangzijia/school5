<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	/** 项目根路径 **/
	String rootPath = application.getRealPath( "/" );
	 /** 调用后端的ActionEnter类，并执行exec方法 **/
	out.write( new ActionEnter( request, rootPath ).exec() );
	
%>