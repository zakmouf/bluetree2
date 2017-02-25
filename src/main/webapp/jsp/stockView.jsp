<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/stock/edit?stock=${param.stock}"/>">Edit</a></td>
    <td><a href="<c:url value="/stock/delete?stock=${param.stock}"/>">Delete</a></td>
    <td><a href="<c:url value="/stock/clear?stock=${param.stock}"/>">Clear</a></td>
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
</table>

<table>
  <tr>
    <td><img src="<c:url value="/chart?name=stock&width=400&height=200"/>" width="400" height="200" /></td>
  </tr>
</table>

<c:import url="/jsp/common/footer.jsp" />
