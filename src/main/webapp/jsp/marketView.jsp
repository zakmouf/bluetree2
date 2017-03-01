<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/market/${market.id}/edit"/>">Edit</a></td>
    <td><a href="<c:url value="/market/${market.id}/delete"/>">Delete</a></td>
    <td><a href="<c:url value="/market/${market.id}/setstocks"/>">Set stocks</a></td>
    <td><a href="<c:url value="/market/${market.id}/delprices"/>">Delete all prices</a></td>
    <td><a href="<c:url value="/market/${market.id}/updnames"/>">Update names</a></td>
    <td><a href="<c:url value="/market/${market.id}/updprices"/>">Update prices</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Name :</td>
    <td><c:out value="${market.name}" /></td>
  </tr>
  <tr>
    <td>Riskless :</td>
    <td><c:out value="${market.riskless}" /></td>
  </tr>
</table>

<table>
  <tr>
    <td><img src="<c:url value="/chart?name=market&width=400&height=200"/>" width="400" height="200" /></td>
  </tr>
</table>

<table>
  <tr>
    <td>Stocks</td>
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
  <tr>
    <td><a href="<c:url value="/stock/${market.indice.id}/view"/>"><c:out value="${market.indice.symbol}" /></a></td>
    <td><c:out value="${market.indice.name}" /></td>
    <td><c:out value="${market.indice.dateCount}" /></td>
    <td><fmt:formatDate value="${market.indice.firstDate}" pattern="yyyy-MM-dd" /></td>
    <td><fmt:formatDate value="${market.indice.lastDate}" pattern="yyyy-MM-dd" /></td>
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