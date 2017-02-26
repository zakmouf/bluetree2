<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/portfolio/delete?portfolio=${param.portfolio}"/>">Delete</a></td>
    <td><a href="<c:url value="/portfolio/optimize?portfolio=${param.portfolio}"/>">Optimize</a></td>
    <td><a href="<c:url value="/portfolio/project?portfolio=${param.portfolio}"/>">Project</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Name :</td>
    <td><c:out value="${portfolio.name}" /></td>
  </tr>
  <tr>
    <td>Market :</td>
    <td><c:out value="${market.name}" /></td>
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

<display:table name="positions">
  <display:column property="stock.symbol" title="Symbol" />
  <display:column property="stock.name" title="Name" />
  <display:column property="weight" title="Weight" format="{0,number,0.00%}" />
</display:table>

<c:import url="/jsp/common/footer.jsp" />
