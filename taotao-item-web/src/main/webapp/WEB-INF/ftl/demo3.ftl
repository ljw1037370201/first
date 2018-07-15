<html>
    <head>测试</head>
    <body>
        <table cellpadding="0" cellspacing="0" border="1">
            <tr>
               <td>学号</td>
               <td>姓名</td>
               <td>年龄</td>
            </tr>
            <#--循环-->
            <#list students as student>
            <#--if条件判断-->
             <#if student_index%2==0>
             <tr style="background: #2A8CFA">
             <#--else不需要写完整，else的条件写在<#else>后面即可-->
             <#else >
             <tr style="background: pink">
             </#if>

                    <td>${student.id}</td>
                    <td>${student.usrname}</td>
                    <td>${student.age}</td>
                </tr>
            </#list>
        </table>
    <br>
        <#--默认日期格式-->
    <#--日期格式:${date?date} <br>-->
    <#--日期格式:${date?datetime} <br>-->
    <#--日期格式:${date?time}-->
        <#--自定义日期格式-->
    日期格式：${date?string('yyyy-MM-dd HH:mm:ss')}<br>
        <#--null处理，!后面有值显示值，!没有值不显示，但不会报错-->
    null值的处理${val!"默认初始值"}<br>
    null值的处理${val!}<br>
    判断null是否为null:
    <#if sss??>
        sss只不会空
        <#else >
        sss值为空
    </#if>
    页面包含:
    <#include "demo1.ftl">
    </body>
</html>