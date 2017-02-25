<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/stock/new"/>">New stock</a></td>
	</tr>
</table>

<display:table name="stocks" requestURI="/admin/stock/list" pagesize="20">
	<display:column property="symbol" title="Symbol" url="/admin/stock/view" paramId="stock" paramProperty="id" />
	<display:column property="name" title="Name" />
</display:table>

<c:import url="/jsp/admin/common/footer.jsp" />
