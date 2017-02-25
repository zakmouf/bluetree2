<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
	<tr>
		<td>Update market</td>
	</tr>
</table>

<form:form action="/market/update?market=${param.market}" method="post" modelAttribute="form">

  <table>
    <tr>
      <td>Start date :</td>
      <td><form:input path="startDate" /></td>
    </tr>
    <tr>
      <td>Increment :</td>
      <td><form:input path="increment" /></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />
