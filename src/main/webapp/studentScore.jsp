<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
    <title>考试成绩</title>
    <meta charset="UTF-8">
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css">
    <link type="text/css" rel="stylesheet" href="css/material_icons.css">
    <link type="text/css" rel="stylesheet" href="css/mobileadaptive.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style type="text/css">
        body {
            font-family: Roboto, "Microsoft YaHei";
        }

        .my-opacity {
            opacity: 0.6;
        }

        .my-opacity:hover {
            opacity: 1;
        }

        a {
            color: #2e7d32;
        }
    </style>
</head>
<body>
   <%-- <%@ include file="include/header.jsp" %>--%>
    <div id="main">
        <div class="container" style="min-height: 350px;">
            <h4 style="text-align: center;" class="light-green-text text-darken-1">已参加考试分数</h4>
            <table class="mytable">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>考试名称</th>
                        <th>交卷时间</th>
                        <th>分数</th>
                    </tr>
                </thead>
                <s:iterator value="studentExamScoreList" status="st" var="item">
                    <tr style="background-color:<s:if test="#st.odd">#efefef</s:if><s:else>#ffffff</s:else>">
                        <td><s:property value="#st.index+1"/></td>
                        <td>
                            <s:property value="exam.name"/>
                        </td>
                        <td>
                           <%-- <s:property value="examEndTime" />--%>
                            <s:date name="examEndTime" format="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <s:property value="score"/>
                        </td>
                    </tr>

                </s:iterator>
            </table>


            <!-- 分页控件 -->
            <form name="form2" method="post">
                <input type="hidden" name="pageIndex" value="<s:property value="pageIndex"/>" />
                <s:if test="totalPage gt 1">
                    <ul class="pagination">
                        <li <s:if test="pageIndex lt 1">class="disabled"</s:if><s:else>class="waves-effect"</s:else>>
                            <a href="#!" <s:if test="pageIndex gt 0">onclick="selectPage(<s:property value="pageIndex"/>)"</s:if>>
                                <i class="fas fa-chevron-left fa-xs"></i></a>
                        </li>
                        <s:iterator var="item" begin="1" end="totalPage">
                            <s:if test="pageIndex==top-1"><li class="active"><a href="#!"><s:property/></a></li></s:if>
                            <s:else><li class="waves-effect"><a href="#!" onclick="selectPage(<s:property/>)"><s:property/></a></li></s:else>
                        </s:iterator>
                        <li <s:if test="pageIndex gt (totalPage-2)">class="disabled"</s:if><s:else>class="waves-effect"</s:else>>
                            <a href="#!" <s:if test="pageIndex lt (totalPage-1)">onclick="selectPage(<s:property value="pageIndex+2"/>)"</s:if>>
                                <i class="fas fa-chevron-right fa-xs"></i></a>
                        </li>
                    </ul>
                </s:if>
            </form>
        </div>
    </div>

    <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>

    <script>
        $(document).ready(function () {
            $('.modal-trigger').leanModal({
                dismissible: true, //是否点模态对话框外面就可以关闭
                opacity: 0.6, //接近1，不透明
            });//使用模态对话框，必须有这句

            $(".button-collapse").sideNav({
                menuWidth: 200, // Default is 240
                edge: 'left', // Choose the horizontal origin
                closeOnClick: true // Closes side-nav on <a> clicks, useful for Angular/Meteor
            });

            $(".collapsible").collapsible({
                accordion: true
            });
        })

    </script>

    <s:if test="hasActionErrors()">
        <script type="text/javascript">
            toast1();

            function toast1() {
                var errorStr = '<s:property value="actionErrors[0]"/>';
                var $toastContent = $('<span class="red-text lighten-5"><h4>' + errorStr + '</h4></span>');
                Materialize.toast($toastContent, 4000, 'rounded');
            }
        </script>
    </s:if>

    <s:if test="hasActionMessages()">
        <script type="text/javascript">
            toast2();

            function toast2() {
                var msgStr = '<s:property value="actionMessages[0]"/>';
                var $toastContent = $('<span class="teal-text lighten-5"><h4>' + msgStr + '</h4></span>');
                Materialize.toast($toastContent, 4000, 'rounded');
            }
        </script>
    </s:if>
    <script>
        function selectPage(page){
            form2.pageIndex.value=page-1;
            form2.action="studentscorelist";
            form2.submit();
        }
    </script>
</body>
</html>
