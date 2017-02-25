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
  </tr>
  <c:forEach items="${stocks}" var="stock">
    <tr>
      <td><a href="<c:url value="/stock/view?stock=${stock.id}"/>"><c:out value="${stock.symbol}" /></a></td>
      <td><c:out value="${stock.name}" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />
