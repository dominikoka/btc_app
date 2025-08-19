<article class="swap" id="swap">
  <section class="swap__box">
    <a class="swap__close close-modal-button" id="swap__close"></a>
    <h2 class="swap__title">change your crypto :)</h2>
    <section class="swap__from">
      <h3 class="swap__fromTitle">From</h3>
      <article class="swap__fromBox">
        <select class="swap__select" id="swap__select">
        </select>
        <input type="swap__amount" id="swap__amount" name="swap__amount" class="swap__amount">
        <button class="swap__check" id="swap__check">check</button>
      </article>

    </section>
    <section class="swap__to">
      <h3 class="swap__toTitle">To</h3>
      <%
        request.setAttribute("styleVariant", "primary");
      %>
      <%@ include file="searchbar.jsp" %>
    </section>

    <section class="swap__change">
      <button class="swap__changeBtn" type="button"> change</button>
    </section>
    <section class="swap__approximateChange">
      <h3 class="approximateChange" id="approximateChange"></h3>
    </section>
  </section>
</article>


