const payBtn = document.getElementById('payBtn');
payBtn.addEventListener('click', payFetch);

async function payFetch(photo) {
    console.log("get pay fetch")
    const url = "http://localhost:8080/btc/pay";
    try {
        const response = await fetch(url, {
            method: "GET",
        });
        //closeBtn();
        const html = await response.text();
        document.getElementById("actionWindow").innerHTML = html;
        // initPayu();
    } catch (error) {

        return [];
    }
}

//
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
// console.log("pplatnosc");
document.addEventListener('click', function (e) {
    if (e.target && e.target.id === 'pay__send') {
        payF();
    }
});

function payF() {
    let name = document.getElementById('pay__name');
    let email = document.getElementById('pay__email');
    let amount = document.getElementById('pay__amount');
    let currency = document.getElementById('pay__select');
    let checkEmail = document.getElementById('pay__checkEmail');
    getPayLink(name,email,amount,currency, checkEmail)

}

async function getPayLink(name, email, amount,currency,checkEmail) {
    const url = "http://localhost:8080/btc/payu";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: name.value,
                email: email.value,
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