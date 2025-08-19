<html>
<style>
  <%@include file="css/style.css" %>
</style>
<body>
<%--<main class="main">--%>
<%--  <div class="title"> btc_price</div>--%>
<%--</main>--%>


<div class="option">
  <div class="date">
    <div class="date__startDate">
      <label for="date__startLabel">start date:</label>

      <input
        class="date__startInput"
        type="date"
        id="date__startLabel"
        name="trip-start"
        value="2018-07-22"
        min="2018-01-01"
        max="2018-12-31"/>
    </div>
    <div class="date__endDate">
      <label for="date__endLabel">End date:</label>

      <input
        class="date__endInput"
        type="date"
        id="date__endLabel"
        name="trip-start"
        value="2018-07-22"
        min="2018-01-01"
        max="2018-12-31"/>
    </div>
  </div>
</div>

<%@ include file="../WEB-INF/views/elements/navBtns.jsp" %>
<section class="swap__section">
  <h1 class="swap__h1title"> CRYPTOCURRENCY CHART</h1>
</section>
<%@ include file="../WEB-INF/views/elements/searchbar.jsp" %>
<div id="chartcontrols" style="padding-left:15px"></div>

<div id="chartdiv">
  <p class="chartInfor" id="chartInfor">asd</p>
</div>
<%@ include file="../WEB-INF/views/elements/scripts.jsp" %>
<script src="js/wallet.js" defer></script>
</body>

</html>
