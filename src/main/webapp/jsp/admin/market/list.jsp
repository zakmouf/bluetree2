<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/market/new"/>">New market</a></td>
	</tr>
</table>

<display:table name="markets" requestURI="/admin/market" pagesize="20">
	<display:column property="name" title="Name" url="/admin/market/view" paramId="market" paramProperty="id" />
	<display:column property="riskless" title="Riskless" />
</display:table>

<c:import url="/jsp/admin/common/footer.jsp" />

</body>
</html>