<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>New stock</td>
	</tr>
</table>

<form:form action="/admin/stock/new" method="post" modelAttribute="form">

<table>
	<tr>
		<td>Symbol : </td>
		<td><form:input path="symbol"/></td>
	</tr>
	<tr>
		<td>Name : </td>
		<td><form:input path="name"/></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form:form>

<c:import url="/jsp/admin/common/footer.jsp" />
