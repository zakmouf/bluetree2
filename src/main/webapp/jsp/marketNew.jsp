<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td>New market</td>
  </tr>
</table>

<form:form action="/market/new" method="post" modelAttribute="form">

  <table>
    <tr>
      <td>Name :</td>
      <td><form:input path="name" /></td>
    </tr>
    <tr>
      <td>Riskless :</td>
      <td><form:input path="riskless" /></td>
    </tr>
    <tr>
      <td>Indice :</td>
      <td><form:select path="indiceId">
          <c:forEach items="${form.stocks}" var="stock">
            <form:option value="${stock.id}"><c:out value="${stock.symbol}" /> ( <c:out value="${stock.name}" /> )</form:option>
          </c:forEach>
        </form:select></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />