<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>成績管理</h2>

    <form method="get" action="TestRegist.action">
    <label>入学年度</label>
    <select name="f1">
        <option value="0">--------</option>
        <c:forEach var="year" items="${ent_year_set}">
            <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
        </c:forEach>
    </select>

    <label>クラス</label>
    <select name="f2">
        <option value="0">--------</option>
        <c:forEach var="num" items="${class_num_set}">
            <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
        </c:forEach>
    </select>

    <label>科目</label>
    <select name="f3">
        <option value="0">--------</option>
        <c:forEach var="subject" items="${subject_set}">
            <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
        </c:forEach>
    </select>

    <label>回数</label>
    <select name="f4">
        <option value="0">--------</option>
        <c:forEach var="num" items="${no_set}">
            <option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
        </c:forEach>
    </select>

    <button>検索</button>

    <div>${errors.get("f1")}</div>
    </form>

    <c:choose>
        <c:when test="${tests.size()>0}">
            <h2>科目：${subject.name}（${f4}回）</h2>
            <form method="get" action="TestRegistExecute.action">
                <table class="table table-hover">
                <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>点数</th>
            </tr>
            <c:forEach var="test" items="${tests}">
            <tr>
                <td>${test.student.entYear}</td>
                <td>${test.classNum}</td>
                <td>${test.student.no}</td>
                <td>${test.student.name}</td>
                <td>
                    <c:choose>
                        <c:when test="${test.point=='-1'}">
                            <input type="number" name="point_${test.student.no}" value="" min="0" max="100"/>
                        </c:when>
                        <c:otherwise>
                            <input type="number" name="point_${test.student.no}" value="${test.point}" min="0" max="100"/>
                        </c:otherwise>
                    </c:choose>
                    <!-- 点数を入力するフィールドに0～100の数字制限を追加 -->
                    <div class="error">${errors.get("test_error")}</div>
                </td>
            </tr>
            </c:forEach>
            </table>
            <input type="hidden" name="f1" value="${f1}"/>
            <input type="hidden" name="f2" value="${f2}"/>
            <input type="hidden" name="f3" value="${f3}"/>
            <input type="hidden" name="f4" value="${f4}"/>
            <input type="submit" value="登録して終了">
            </form>
        </c:when>
    </c:choose>

</body>
</html>
