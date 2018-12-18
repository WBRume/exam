<%--
  Created by IntelliJ IDEA.
  User: zhang.peng03
  Date: 2018/11/26
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>选择</title>
</head>
<body>
<p>
    ######格式说明：（文件需要以UTF-8格式编码，推荐notepad++编辑和转换编码）<br/>
    ######每道题第一行应以6个#号开头（"######"），<br/>
    ######第二行用3个*号开头("***")表示该题所属章节<br/>
    ######第三行开始用数字开头（数字表示题号，是多少无所谓）表示选择题题干描述，可以为多行<br/>
    ######以A. B. C. D. E. ...这些字母开头的行表示选项，每个选项可以为多行，但行之间不能有空行; 每题最多8个选项（A~H）<br/>
    ###### 注意，每个选项的第二行不能再用字母或数字（因已经被选项、题干占用）<br/>
    ######选项行结束后的一行，以三个>号开头（">>>"）表示答案，多选题答案用逗号隔开，如A,B,C<br/>
    ######所有题结束后最后一行应以6个#号开头（"######"），后续不能有空行！<br/>
    ######<br/>
    ***Java集合类***<br/>
    1,下列哪些方法是ArrayList和LinkedList集合中都定义的（多选）（     ）<br/>
    A. add(Object o)<br/>
    B. removeFirst()<br/>
    C. remove(Object o)<br/>
    D. add(int index,Object o)<br/>
    >>>A,C,D<br/>
    ######<br/>
    ***Java集合类***<br/>
    2，不是迭代器接口（Iterator）所定义的方法是（     ）。<br/>
    A．hasNext()<br/>
    B．next()<br/>
    C．remove()<br/>
    D．nextElement()<br/>
    >>>D<br/>
    ######<br/>
    ***Java集合类***<br/>
    3，下面那个方法不是接口Collection中已声明的方法（      ）<br/>
    A．添加元素的add(Object  obj) 方法<br/>
    B．删除元素的remove(Object obj)方法<br/>
    C．得到元素个数的length()方法<br/>
    D．返回迭代器的iterator()方法，迭代器用于元素遍历<br/>
    >>>C<br/>
    ######<br/>
    ***Java集合类***<br/>
    4，分析如下程序运行结果：<br/>
    [[[program001.jpg]]]<br/>
    A. [Hello, World, Hello, Learn]<br/>
    B. [Learn]<br/>
    C. [Hello, Learn]<br/>
    D. [World, Hello, Learn]<br/>
    >>>C<br/>
    ######<br/>
</p>
</body>
</html>
