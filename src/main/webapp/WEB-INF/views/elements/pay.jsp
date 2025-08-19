<article class="swap" id="swap">
  <section class="swap__box">
    <a class="swap__close close-modal-button" id="swap__close"></a>
    <section class="pay">
      <h3 class="pay__title">add money</h3>
      <input type="pay__name" class="swap__input pay__input" placeholder="name" id="pay__name">
      <input type="pay__email" class="swap__input pay__input" placeholder="email" id="pay__email">
      <input type="pay__amount" class="swap__input pay__input" placeholder="amount" id="pay__amount">
      <select id="pay__select" class="swap__input pay__input">
        <option value="USD">USD</option>
        <option value="EURO">EURO</option>
        <option value="PLN">PLN</option>
      </select>
      <section class="pay__emailBox">
      <input type="checkbox" id="pay__checkEmail">
      <p class="pay__text">send email</p>
      </section>
      <button class="pay__send btn neon-btn neon-purple" id="pay__send">Pay</button>
    </section>

    <%--    iam paying box--%>
    <%--    <section class="container">--%>
    <%--      <div class="card-container">--%>
    <%--        <aside>Card Number</aside>--%>
    <%--        <div class="payu-card-form" id="payu-card-number"></div>--%>

    <%--        <div class="card-details clearfix">--%>
    <%--          <div class="expiration">--%>
    <%--            <aside>Valid Thru</aside>--%>
    <%--            <div class="payu-card-form" id="payu-card-date"></div>--%>
    <%--          </div>--%>

    <%--          <div class="cvv">--%>
    <%--            <aside>CVV</aside>--%>
    <%--            <div class="payu-card-form" id="payu-card-cvv"></div>--%>
    <%--          </div>--%>
    <%--        </div>--%>
    <%--      </div>--%>
    <%--      <button id="tokenizeButton">Tokenize</button>--%>

    <%--      <div id="responseTokenize"></div>--%>


  </section>
</article>
<script src="js/pay.js" defer></script>

