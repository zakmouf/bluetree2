<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td><a href="<c:url value="/stock/edit?stock=${param.stock}"/>">Edit</a></td>
    <td><a href="<c:url value="/stock/delete?stock=${param.stock}"/>">Delete</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Symbol :</td>
    <td><c:out value="${form.stock.symbol}" /></td>
  </tr>
  <tr>
    <td>Name :</td>
    <td><c:out value="${form.stock.name}" /></td>
  </tr>
</table>

<table>
  <tr>
    <td><a href="<c:url value="/stock/clear?stock=${param.stock}"/>">Clear</a></td>
  </tr>
</table>

<table>
  <tr>
    <td>Count :</td>
    <td><c:out value="${form.count}" /></td>
  </tr>
  <tr>
    <td>First date :</td>
    <td><fmt:formatDate value="${form.firstDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
  <tr>
    <td>Last date :</td>
    <td><fmt:formatDate value="${form.lastDate}" pattern="yyyy-MM-dd" /></td>
  </tr>
</table>

<table>
  <tr>
    <td><img src="<c:url value="/chart?name=stock&width=400&height=200"/>" width="400" height="200" /></td>
  </tr>
</table>

<table>
  <tr>
    <td>View</td>
  </tr>
</table>

<form:form action="/stock/view?stock=${param.stock}" method="post" modelAttribute="form">

  <table>
    <tr>
      <td>From :</td>
      <td><form:input path="fromDate" /></td>
    </tr>
    <tr>
      <td>To :</td>
      <td><form:input path="toDate" /></td>
    </tr>
  </table>

  <table>
    <tr>
      <td><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />
