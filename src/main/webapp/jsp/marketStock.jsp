<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td>Stocks market</td>
  </tr>
</table>

<form:form action="/market/stock?market=${param.market}" method="post" modelAttribute="form">

  <table>
    <tr>
      <td><form:textarea path="text" rows="15" cols="40" /></td>
    </tr>
    <tr>
      <td><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />
