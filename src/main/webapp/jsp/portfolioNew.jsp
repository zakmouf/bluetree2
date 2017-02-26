<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
  <tr>
    <td>New portfolio</td>
  </tr>
</table>

<form:form action="/portfolio/new" method="post" modelAttribute="form">

  <table>
    <tr>
      <td>Name :</td>
      <td><form:input path="name" /></td>
    </tr>
    <tr>
      <td>Market :</td>
      <td><form:select path="marketId">
          <c:forEach items="${form.markets}" var="market">
            <form:option value="${market.id}">
              <c:out value="${market.name}" />
            </form:option>
          </c:forEach>
        </form:select></td>
    </tr>
    <tr>
      <td>From date :</td>
      <td><form:input path="fromDateStr" /></td>
    </tr>
    <tr>
      <td>To date :</td>
      <td><form:input path="toDateStr" /></td>
    </tr>
    <tr>
      <td>Beta :</td>
      <td><form:input path="beta" /></td>
    </tr>
    <tr>
      <td>Size :</td>
      <td><form:input path="size" /></td>
    </tr>
    <tr>
      <td colspan="2"><input type="submit" value="Ok" /></td>
    </tr>
  </table>

</form:form>

<c:import url="/jsp/common/footer.jsp" />
