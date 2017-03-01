<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/market/new"/>">New market</a></td>
  </tr>
</table>

<table>
  <tr>
    <th>Name</th>
    <th>Riskless</th>
    <th>Indice</th>
  </tr>
  <c:forEach items="${markets}" var="market">
    <tr>
      <td><a href="<c:url value="/market/${market.id}/view"/>"><c:out value="${market.name}" /></a></td>
      <td><c:out value="${market.riskless}" /></td>
      <td><a href="<c:url value="/stock/${market.indice.id}/view"/>"><c:out value="${market.indice.symbol}" /></a> ( <c:out value="${market.indice.name}" /> )</td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />