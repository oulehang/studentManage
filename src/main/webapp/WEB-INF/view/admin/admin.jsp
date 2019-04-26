<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>学生成绩管理系统 管理员后台</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
	<link rel="bookmark" href="/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/easyui/themes/icon.css" />
    <script type="text/javascript" src="${basePath}/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='${basePath}/easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {"menus":[
						{"menuid":"1","icon":"","menuname":"成绩统计分析",
							"menus":[
									{"menuid":"11","menuname":"考试列表","icon":"icon-exam","url":"${basePath}/exam/examListView"}
								]
						},
						{"menuid":"2","icon":"","menuname":"学生信息管理",
							"menus":[
									{"menuid":"21","menuname":"学生列表","icon":"icon-user-student","url":"${basePath}/student/studentListView"},
								]
						},
						{"menuid":"3","icon":"","menuname":"教师信息管理",
							"menus":[
									{"menuid":"31","menuname":"教师列表","icon":"icon-user-teacher","url":"${basePath}/teacher/teacherListView"},
								]
						},
						{"menuid":"4","icon":"","menuname":"基础信息管理",
							"menus":[
									{"menuid":"41","menuname":"年级列表","icon":"icon-world","url":"${basePath}/Grade/gradeListView"},
									{"menuid":"42","menuname":"班级列表","icon":"icon-house","url":"${basePath}/clazz/clazzListView"},
									{"menuid":"43","menuname":"课程列表","icon":"icon-book-open","url":"${basePath}/course/courseListView"}
								]
						},
						{"menuid":"5","icon":"","menuname":"系统管理",
							"menus":[
							        {"menuid":"51","menuname":"系统设置","icon":"icon-set","url":"${basePath}/system/adminPersonalView"},
								]
						}
				]};


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background:#0a6999 repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style=" font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="${basePath}/login/loginOut" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">学生成绩管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright &copy;</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="false" border="false">
		<!--  导航内容 -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="/WEB-INF/view/admin/welcome.jsp" />
		</div>
    </div>
	
	<iframe width=0 height=0 src="/refresh.jsp"></iframe>
	
</body>
</html>