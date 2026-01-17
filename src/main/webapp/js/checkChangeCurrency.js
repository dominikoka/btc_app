function checkCurrency(symbolToChange, amount, symbolFromChange) {
    let symbol = symbolToChange;

    async function checkCurrencyFetch() {
        //const url = "http://localhost:8080/btc/changeCheckCrypto";
        const url = "/btc/changeCheckCrypto";
        try {
            const response = await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    symbolToChange: symbolToChange,
                    symbolFromChange: symbolFromChange,
                    amount: amount,
                })
            });
            if (!response.ok) throw new Error(`Response status: ${response.status}`);
            const data = await response.json();
            var infoChange = document.getElementById("approximateChange");
            infoChange.innerHTML = "";
            infoChange.appendChild(getCheckCrypto(data));
            console.log(data)
            return data;
        } catch (error) {
            console.error(error.message);
            return [];
        }
    }

    checkCurrencyFetch();

    function getCheckCrypto(data) {
        let currencyFrom = document.createElement("p");
        currencyFrom.innerHTML = data.currencyFrom;
        currencyFrom.className = "CheckCrypto__name";

        let currencyTo = document.createElement("p");
        currencyTo.innerHTML = data.currencyTo;
        currencyTo.className = "CheckCrypto__currencyTo";

        let message = document.createElement("p");
        message.innerHTML = data.message;
        message.className = "CheckCrypto__message";

        let amount = document.createElement("p");
        amount.innerHTML = data.amount;
        amount.className = "CheckCrypto__amount";

        let box = document.createElement("section");
        box.className = "CheckCrypto"

        if (data.success) {
            let extraMess = document.createElement("p");
            extraMess.innerHTML = "YOU RECEIVEs: "
            extraMess.className = "CheckCrypto_extraMess";

            box.appendChild(message);
            box.appendChild(extraMess);
            box.appendChild(amount);
            box.appendChild(currencyTo);

        } else {
            box.appendChild(message);
        }
        return box;
    }
}


const swapWindow = document.getElementById('actionWindow');
swapWindow.addEventListener('click', function (e) {
    let changeBtn = document.getElementById('swap__changeBtn');
    let symbolFromChange;
    let amount;
    let symbolToChange;
    if (e.target.classList.contains('swap__changeBtn')) {
        console.log("kliknales change btn");
        symbolToChange = getSymbolToChange()
        amount = document.getElementById('swap__amount').value
        symbolFromChange = document.getElementById('swap__select').value
        changeCurrencyFetch();
    }
})

async function changeCurrencyFetch() {
    //const url = "http://localhost:8080/btc/changeCrypto";
    const url = "/btc/changeCrypto";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Typelocal": "application/json"
            },
            body: JSON.stringify({
                symbolToChange: symbolToChange,
                symbolFromChange: symbolFromChange,
                amount: amount,
            })
        });
        const div = document.getElementById('swap');
        div.style.display = "none";
        let assets = document.getElementById('assets');
        let paragraph = assets.querySelectorAll('p');
        for (let i = 0; i < paragraph.length; i++) {
            paragraph[i].remove();
        }
        document.getElementById("approximateChange").innerHTML = data;
    } catch (error) {
        console.error(error.message);
        return [];
    }
}

