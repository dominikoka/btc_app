<html>
<meta charset="utf-8">
<style>
  <%@include file="../../css/style.css" %>
</style>

<body>
<%--<%@ include file="../../WEB-INF/views/elements/searchbar.jsp" %>--%>
<%@ include file="../../WEB-INF/views/elements/navBtns.jsp" %>
<section class="swap__section">
  <h1 class="swap__h1title"> WALLET</h1>
</section>
<article class="Info">
  <section class="Info__switchCrypto">

    <%--    <select id="switchCrypto" class="switchCrypto">--%>
    <%--      <option value="volvo">Volvo</option>--%>
    <%--      <option value="saab">Saab</option>--%>
    <%--      <option value="opel">Opel</option>--%>
    <%--      <option value="audi">Audi</option>--%>
    <%--    </select>--%>
    <div class="Info__donut" id="Info__donut"></div>
  </section>
  <%--  <section class="Info__TotalAmount">--%>
  <%--    <p class="Info__Money"> 1280</p>--%>
  <%--  </section>--%>
  <%--  <section class="Info__changeTotalAmount"> price changed $900 &#8593 2,5%</section>--%>
  <%--  <section>tutaj bedzie wykres jak zmienia sie cena</section>--%>
</article>
<article class="WalletBtns">
  <section class="WalletBtn">
    <button class="darkMode_Btn btn neon-btn neon-orange padding" id="payoutBtn">receive</button>
  </section>
  <section class="WalletBtn">
    <button class="darkMode_Btn btn neon-btn neon-blue swapBtn padding" id="swapBtn">swap</button>
  </section>
  <section class="WalletBtn">
    <button class="darkMode_Btn btn neon-btn neon-purple padding">earn</button>
  </section>
  <section class="WalletBtn">
    <button class="darkMode_Btn btn neon-btn neon-green padding" id="payBtn">add</button>
  </section>
</article>
<article class="tabss">
  <ul class="tabs">
    <li data-tab-target="#home" class="active tab">Assets</li>
    <li data-tab-target="#news" class="tab">History</li>
  </ul>
  <div class="tab-content">
    <div id="home" data-tab-content class="active">
      <%@ include file="../../WEB-INF/views/elements/Assets.jsp" %>
    </div>
    <div id="news" data-tab-content>
      <%@ include file="../../WEB-INF/views/elements/History.jsp" %>
    </div>
  </div>
</article>
<script type="text/javascript" src="https://secure.snd.payu.com/javascript/sdk" defer></script>
<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
<script src="https://cdn.amcharts.com/lib/5/percent.js"></script>
<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
<article id="actionWindow"></article>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.30.1/moment.min.js"></script>
<script src="js/tabs.js"></script>
<script src="js/wallet.js" defer></script>
<script src="js/searchbarWallet.js" defer></script>
<script src="js/checkChangeCurrency.js" defer></script>

<script src="js/chartDonut.js" defer></script>
<script src="js/darkMode.js" defer></script>
<script src="js/pay.js" defer></script>
<script src="js/payout.js" defer></script>


</body>

</html>
