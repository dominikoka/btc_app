<article class="swap" id="swap">
  <section class="swap__box">
    <a class="swap__close close-modal-button" id="swap__close"></a>
  </section>
  <section class="payout">
    <h3 class="payout__title">payout money</h3>
    <div class="account-form">
      <form class="account-form__form">
        <label class="account-form__label" for="account-form__number">Number account</label>
        <input class="account-form__input" type="text" id="payout__account" placeholder="Number account" maxlength="26">

        <label class="account-form__label" for="account-form__currency" id="payout__currency">Currency</label>
        <select class="account-form__select" id="account-form__currency">
          <option value="PLN">PLN</option>
          <option value="USD">USD</option>
          <option value="EUR">EUR</option>
        </select>

        <label class="account-form__label" for="account-form__amount">Amount</label>
        <input class="account-form__input" type="number" id="payout__amount" placeholder="amount" min="0" step="0.01">

        <button class="account-form__button"  id="payout__send">send</button>
        <section class="pay__emailBox">
          <input type="checkbox" id="pay__checkEmail">
          <p class="pay__text">send email</p>
        </section>
      </form>
    </div>
  </section>
</article>