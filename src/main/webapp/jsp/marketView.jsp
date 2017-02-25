<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/market/edit?market=${param.market}"/>">Edit</a></td>
		<td><a href="<c:url value="/market/delete?market=${param.market}"/>">Delete</a></td>
	</tr>
</table>

<table>
	<tr>
		<td>Name :</td>
		<td><c:out value="${market.name}" /></td>
	</tr>
	<tr>
		<td>Riskless :</td>
		<td><c:out value="${market.riskless}" /></td>
	</tr>
	<tr>
		<td>Indice :</td>
		<td><a href="<c:url value="/stock/view?stock=${indice.id}"/>"><c:out value="${indice.symbol}" /></a> ( <c:out value="${indice.name}" /> )</td>
	</tr>
</table>

<table>
	<tr>
		<td><a href="<c:url value="/market/stock?market=${param.market}"/>">Stocks</a></td>
	</tr>
</table>

<c:if test="${not empty stocks}">

	<display:table name="stocks" requestURI="/market/view" pagesize="20">
		<display:column property="symbol" title="Symbol" url="/admin/stock/view" paramId="stock" paramProperty="id" />
		<display:column property="name" title="Name" />
	</display:table>

</c:if>

<c:import url="/jsp/common/footer.jsp" />