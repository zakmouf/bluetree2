<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/market/edit?market=${param.market}"/>">Edit</a></td>
    <td><a href="<c:url value="/market/delete?market=${param.market}"/>">Delete</a></td>
    <td><a href="<c:url value="/market/stock?market=${param.market}"/>">Stocks</a></td>
    <td><a href="<c:url value="/market/update?market=${param.market}"/>">Update</a></td>
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
  <tr>
    <td>Indice :</td>
    <td><a href="<c:url value="/stock/view?stock=${market.indice.id}"/>"><c:out value="${market.indice.symbol}" /></a> ( <c:out value="${market.indice.name}" /> )</td>
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
  </tr>
  <c:forEach items="${stocks}" var="stock">
    <tr>
      <td><a href="<c:url value="/stock/view?stock=${stock.id}"/>"><c:out value="${stock.symbol}" /></a></td>
      <td><c:out value="${stock.name}" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />