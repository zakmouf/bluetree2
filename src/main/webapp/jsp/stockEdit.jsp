<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
	<tr>
		<td>Edit stock</td>
	</tr>
</table>

<form:form action="/stock/edit?stock=${param.stock}" method="post" modelAttribute="form">

<table>
	<tr>
		<td>Symbol : </td>
		<td><form:input path="symbol" disabled="true"/></td>
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

<c:import url="/jsp/common/footer.jsp" />
