console.log("payout")
const payoutBtn = document.getElementById('payoutBtn');
payoutBtn.addEventListener('click', payFetch);

async function payFetch(photo) {
    console.log("get pay fetch")
    const url = "http://localhost:8080/btc/payout";
    try {
        const response = await fetch(url, {
            method: "GET",
        });
        const html = await response.text();
        document.getElementById("actionWindow").innerHTML = html;
        // initPayu();
        //closeBtn();
    } catch (error) {

        return [];
    }
}

// function initPayu() {
//     var optionsForms = {
//         cardIcon: true,
//         style: {
//             basic: {
//                 fontSize: '24px',
//             },
//         },
//         placeholder: {
//             number: '',
//             date: 'MM/YY',
//             cvv: '',
//         },
//         lang: 'pl',
//     }
//
//     var renderError = function (element, errors) {
//         element.className = 'response-error'
//         var messages = []
//         errors.forEach(function (error) {
//             messages.push(error.message)
//         })
//         element.innerText = messages.join(', ')
//     }
//
//     var renderSuccess = function (element, msg) {
//         element.className = 'response-success'
//         element.innerText = msg
//     }
//
// //inicjalizacja SDK poprzez podanie POS ID oraz utworzenie obiektu secureForms
//     var payuSdkForms = PayU('393823')
//     var secureForms = payuSdkForms.secureForms()
//
// //utworzenie formularzy podając ich typ oraz opcje
//     var cardNumber = secureForms.add('number', optionsForms)
//     var cardDate = secureForms.add('date', optionsForms)
//     var cardCvv = secureForms.add('cvv', optionsForms)
//
// //renderowanie formularzy
//     cardNumber.render('#payu-card-number')
//     cardDate.render('#payu-card-date')
//     cardCvv.render('#payu-card-cvv')
//
//     var tokenizeButton = document.getElementById('tokenizeButton')
//     var responseElement = document.getElementById('responseTokenize')
//
//     tokenizeButton.addEventListener('click', function () {
//         responseElement.innerText = ''
//
//         try {
//             //tokenizacja karty (komunikacja z serwerem PayU)
//             payuSdkForms.tokenize('SINGLE').then(function (result) {
//                 // przykład dla tokenu typu SINGLE
//                 result.status === 'SUCCESS'
//                     ? renderSuccess(responseElement, result.body.token) //tutaj wstaw przekazanie tokena do back-endu
//                     : renderError(responseElement, result.error.messages) //sprawdź typ błędu oraz komunikaty i wyświetl odpowiednią informację użytkownikowi
//             })
//         } catch (e) {
//             console.log(e) // błędy techniczne
//         }
//     })
// }
document.addEventListener('click', function (e) {
    if (e.target && e.target.id === 'payout__send') {
        payoutF();
    }
});

function payoutF()
{

    console.log("kliknales wyplatem");
    let account = document.getElementById('payout__account');
    // let email = document.getElementById('pay__email');
     let amount = document.getElementById('payout__amount');
    let currency = document.getElementById('account-form__currency');
     let checkEmail = document.getElementById('pay__checkEmail');
    getPayOut(account,amount,currency,checkEmail)
    //getPayLink(name,email,amount,currency, checkEmail)
}
async function getPayOut(account,amount,currency,checkEmail) {
    const url = "http://localhost:8080/btc/sendMoney";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                account:account.value,
                amount: amount.value,
                currency: currency.value,
                checkEmail: checkEmail.checked
            })
        });
        if (!response.ok) throw new Error(`Response status: ${response.status}`);
        const data = await response.json();
        window.open(data.redirectLink, '_blank');

        return data;
    } catch (error) {
        console.error(error.message);
        return [];
    }
}
// function closeBtn() {
//     const closeBtn = document.getElementById('swap__close');
//     closeBtn.addEventListener('click', function () {
//         let div = document.getElementById('swap');
//         div.style.display = "none";
//         console.log("zamykanie okkna");
//     });
// }