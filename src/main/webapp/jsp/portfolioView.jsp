<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/portfolio/${portfolio.id}/delete"/>">Delete</a></td>
    <td><a href="<c:url value="/portfolio/${portfolio.id}/optimize"/>">Optimize</a></td>
    <td><a href="<c:url value="/portfolio/${portfolio.id}/project"/>">Project</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Name :</td>
    <td><c:out value="${portfolio.name}" /></td>
  </tr>
  <tr>
    <td>Market :</td>
    <td><a href="<c:url value="/market/${portfolio.market.id}/view"/>"> <c:out value="${portfolio.market.name}" /></a></td>
  </tr>
  <tr>
    <td>From date :</td>
    <td><fmt:formatDate value="${portfolio.fromDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>To date :</td>
    <td><fmt:formatDate value="${portfolio.toDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>Beta :</td>
    <td><c:out value="${portfolio.beta}" /></td>
  </tr>
  <tr>
    <td>Size :</td>
    <td><c:out value="${portfolio.size}" /></td>
  </tr>
</table>

<table>
  <tr>
    <td>Holdings</td>
  </tr>
</table>

<table>
  <tr>
    <th>Name</th>
    <th>Weight</th>
  </tr>
  <c:forEach items="${holdings}" var="holding">
    <tr>
      <td><a href="<c:url value="/stock/${holding.stock.id}/view"/>"><c:out value="${holding.stock.symbol}" /></a> (<c:out value="${holding.stock.name}" />)</td>
      <td><fmt:formatNumber value="${holding.weight}" pattern="0.00%" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />
