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
  </tr>
  <c:forEach items="${portfolios}" var="portfolio">
    <tr>
      <td><a href="<c:url value="/portfolio/view?portfolio=${portfolio.id}"/>"><c:out value="${portfolio.name}" /></a></td>
    </tr>
  </c:forEach>
</table>

<c:import url="/jsp/common/footer.jsp" />
