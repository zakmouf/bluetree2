<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/stock/${stock.id}/edit"/>">Edit</a></td>
    <td><a href="<c:url value="/stock/${stock.id}/delete"/>">Delete</a></td>
    <td><a href="<c:url value="/stock/${stock.id}/delprices"/>">Delete all prices</a></td>
    <td><a href="<c:url value="/stock/${stock.id}/updnames"/>">Update name</a></td>
    <td><a href="<c:url value="/stock/${stock.id}/updprices"/>">Update prices</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Symbol :</td>
    <td><c:out value="${stock.symbol}" /></td>
  </tr>
  <tr>
    <td>Name :</td>
    <td><c:out value="${stock.name}" /></td>
  </tr>
  <tr>
    <td>Date count :</td>
    <td><c:out value="${stock.dateCount}" /></td>
  </tr>
  <tr>
    <td>First date :</td>
    <td><fmt:formatDate value="${stock.firstDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>Last date :</td>
    <td><fmt:formatDate value="${stock.lastDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
</table>

<table>
  <tr>
    <td><img src="<c:url value="/chart?name=stock&width=400&height=200"/>" width="400" height="200" /></td>
  </tr>
</table>

<c:import url="/jsp/common/footer.jsp" />
