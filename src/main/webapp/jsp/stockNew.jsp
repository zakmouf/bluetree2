<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td>New stock</td>
  </tr>
</table>

<form:form action="/stock/new" method="post" modelAttribute="form">

  <table>
    <tr>
      <td>Symbol :</td>
      <td><form:input path="symbol" /></td>
    </tr>
    <tr>
      <td>Name :</td>
      <td><form:input path="name" /></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />
