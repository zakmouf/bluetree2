<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/portfolio/new"/>">New portfolio</a></td>
  </tr>
</table>

<table>
  <tr>
    <th>Name</th>
    <th>Market</th>
    <th>From date</th>
    <th>To date</th>
    <th>Beta</th>
    <th>Size</th>
  </tr>
  <c:forEach items="${portfolios}" var="portfolio">
    <tr>
      <td><a href="<c:url value="/portfolio/${portfolio.id}/view"/>"><c:out value="${portfolio.name}" /></a></td>
      <td><a href="<c:url value="/market/${portfolio.market.id}/view"/>"><c:out value="${portfolio.market.name}" /></a></td>
      <td><fmt:formatDate value="${portfolio.fromDate}" pattern="yyyy-MM-dd" /></td>
      <td><fmt:formatDate value="${portfolio.toDate}" pattern="yyyy-MM-dd" /></td>
      <td><c:out value="${portfolio.beta}" /></td>
      <td><c:out value="${portfolio.size}" /></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />
