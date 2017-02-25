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
  </tr>
  <c:forEach items="${markets}" var="market">
    <tr>
      <td><a href="<c:url value="/market/view?market=${market.id}"/>"><c:out value="${market.name}" /></a></td>
      <td><c:out value="${market.riskless}" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />