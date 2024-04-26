<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>成績参照</h2>

	<form action = "TestListSubjectExecute.action" method="get">
		<label>入学年度</label>
		<select name="ent_year">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたent_yearが一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>
		<div>${errors.get("ent_year")}</div>

		<label>クラス</label>
		<select name="class_num">
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="sub_name">
			<option value="0">--------</option>
			<c:forEach var="sub" items="${subject_set}">
				<%-- 表記は科目名が出ているけど実際に送られるものは科目コードが送られる --%>
				<option value="${sub.cd}" <c:if test="${sub.name==sub_name}">selected</c:if>>${sub.name}</option>
			</c:forEach>
		</select>

		<div>${errors.get("sub_name")}</div>

		<input type="submit" value="検索">
	</form>
	<c:choose>
		<c:when test="${scores.size()>0}">
			<div>科目：${sub.getName()}</div>

			<table class="table table-hover">
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th>１回</th>
					<th>２回</th>
					<th></th>
					<th></th>
				</tr>
				<c:forEach var="score" items="${scores}">
					<tr>
						<td>${score.entYear}</td>
						<td>${score.classNum}</td>
						<td>${score.studentNo}</td>
						<td>${score.studentName}</td>
						<td>${score.points.get('1')}</td>
						<td>${score.points.get('2')}</td>
						<td class="text-center">
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div>成績情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>

	<a href="#">戻る</a>

</body>
</html>