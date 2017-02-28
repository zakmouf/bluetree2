<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/stock/new"/>">New stock</a></td>
  </tr>
</table>

<table>
  <tr>
    <th>Symbol</th>
    <th>Name</th>
    <th>Date count</th>
    <th>First date</th>
    <th>Last date</th>
  </tr>
  <c:forEach items="${stocks}" var="stock">
    <tr>
      <td><a href="<c:url value="/stock/${stock.id}/view"/>"><c:out value="${stock.symbol}" /></a></td>
      <td><c:out value="${stock.name}" /></td>
    <td><c:out value="${stock.dateCount}" /></td>
    <td><fmt:formatDate value="${stock.firstDate}" pattern="yyyy-MM-dd" /></td>
    <td><fmt:formatDate value="${stock.lastDate}" pattern="yyyy-MM-dd" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />
