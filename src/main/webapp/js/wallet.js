let summary;

function fetchCryptoInfo() {
    return fetch('https://api.binance.com/api/v3/exchangeInfo')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .catch(error => {
            // Obsługa błędów
            console.error('There has been a problem with your fetch operation:', error);
        });
}

async function getCryptoInfo() {
    var cryptoInfo = [];
    var cryptoFetch = await fetchCryptoInfo();
    for (let i = 0; i < cryptoFetch.symbols.length; i++) {
        let symbol = cryptoFetch.symbols[i];

        cryptoInfo.push(symbol.baseAsset);
    }
    console.log("aaa");
}

getCryptoInfo();

function getSummary(summary, assetsName, assetsAvgPrice, assetsAmount, assetsPrice, assetsChange) {
    for (let i = 0; i < summary.length; i++) {
        let nameCurrency = summary[i].nameCurrency;
        let approximatePrice = summary[i].approximateSellPrice;
        let dayHistory = summary[i].dayHistory;
        let totalAmount = summary[i].totalAmount;
        let averageBuyPrice = summary[i].averageBuyPrice;
        let newElementName = document.createElement('p');
        let newElementAvgPrice = document.createElement('p');
        let newElementTotal = document.createElement('p');
        let newElementApproximate = document.createElement('p');
        let history = document.createElement('p');
        newElementName.textContent = nameCurrency;
        assetsName.appendChild(newElementName);
        newElementAvgPrice.textContent = averageBuyPrice;
        assetsAvgPrice.appendChild(newElementAvgPrice);
        newElementTotal.textContent = totalAmount;
        assetsAmount.appendChild(newElementTotal);
        newElementApproximate.textContent = approximatePrice;
        assetsPrice.appendChild(newElementApproximate);
        history.textContent = dayHistory;
        assetsChange.appendChild(history);
        console.log(nameCurrency);
    }
}

function getHistory(history, historyName, historyAmount, historyPrice, historyData, historyAction) {
    for (let i = 0; i < history.length; i++) {
        let nameCurrency = history[i].Currency;
        let amount = history[i].amount;
        let actualPrice = history[i].actual_price;
        let created_at = history[i].created_at;
        let action = history[i].action;
        let newElementName = document.createElement('p');
        let newElementAmount = document.createElement('p');
        let newElementActualPrice = document.createElement('p');
        let newElementCreatedAt = document.createElement('p');
        let actionElement = document.createElement('p');
        newElementName.textContent = nameCurrency;
        historyName.appendChild(newElementName);
        newElementAmount.textContent = amount;
        historyAmount.appendChild(newElementAmount);
        newElementActualPrice.textContent = actualPrice;
        historyPrice.appendChild(newElementActualPrice);
        newElementCreatedAt.textContent = created_at;
        historyData.appendChild(newElementCreatedAt);

        actionElement.textContent = action;
        historyAction.appendChild(actionElement);
        console.log(nameCurrency);
    }
}

function getSwapCurrency(summary) {
    const selectCryptoSwap = document.getElementById('swap__select');
    for (let i = 0; i < summary.length; i++) {
        console.log("dodawanie elementow option");
        let nameCurrency = summary[i].nameCurrency;
        let totalAmount = summary[i].totalAmount;
        let elementName = document.createElement('option');


        elementName.textContent = nameCurrency + " (" + totalAmount + ")";
        elementName.value = nameCurrency;
        selectCryptoSwap.appendChild(elementName);
        //await selectCryptoSwap.appendChild(elementTotal);

    }
}

async function ww() {
    const assetsName = document.getElementById('assets__name');
    const assetsAvgPrice = document.getElementById('assets__avgPrice');
    const assetsAmount = document.getElementById('assets__Amount');
    const assetsPrice = document.getElementById('assets__Price');
    const assetsTotal = document.getElementById('assets__Total');
    const assetsChange = document.getElementById('assets__Change');

    const historyName = document.getElementById('history__name');
    const historyAmount = document.getElementById('history__Amount');
    const historyPrice = document.getElementById('history__Price');
    const historyData = document.getElementById('history__Total');
    const historyAction = document.getElementById('history__Change');


    async function getSymbolFetch() {
        try {
            const response = await fetch('http://localhost:8080/btc/walletJSON');
            if (!response.ok) {
                throw new Error("not ok")
            }
            const data = await response.json();
            console.log(data);

            return data;
        } catch (e) {
            throw new Error(e);
        }
    }

    let data = await getSymbolFetch();
    summary = data.summary;
    let history = data.history;
    getSummary(summary, assetsName, assetsAvgPrice, assetsAmount, assetsPrice, assetsChange);
    getHistory(history, historyName, historyAmount, historyPrice, historyData, historyAction);
    getDonut(summary);

    //dataSummary= getSymbolFetch().summary;

}

ww();
const swapBtn = document.getElementById('swapBtn');
swapBtn.addEventListener('click', getClickedSwapBtn);

function getClickedSwapBtn() {
    console.log("u clicked swap btn")
    getSwap();
}

let div;

async function getSwap(photo) {
    console.log("get swap fetch")
    const url = "http://localhost:8080/btc/swap";
    try {
        const response = await fetch(url, {
            method: "GET",
        });
        const html = await response.text();
        document.getElementById("actionWindow").innerHTML = html;

        // const result = await summary;
        // console.log(result);
        if (summary !== undefined) {
            console.log("dodawanie elementow");
            getSwapCurrency(summary);
        } else {
            await new Promise(resolve => setTimeout(resolve, 3000));
            console.log("nie dodawanie elementow")
            getSwapCurrency(summary);

        }
        let symbolToChange = searchBarWallet();

        const checkChangeCurrencyBtc = document.getElementById('swap__check');
        checkChangeCurrencyBtc.addEventListener('click', () =>
            checkCurrency(getSymbolToChange(), document.getElementById('swap__amount').value, document.getElementById('swap__select').value)
        );

        //closeBtn();
    } catch (error) {
        console.error(error.message);
        return [];
    }
}

function closeBtn() {
    const closeBtn = document.getElementById('swap__close');
    if (closeBtn) {
        closeBtn.addEventListener('click', function () {
            let div = document.getElementById('swap');
            if (div) {
                div.style.display = "none";
            }
        })
    }
}

document.getElementById("actionWindow").addEventListener('click', function (e) {
    if (e.target && e.target.id === 'swap__close') {
        let div = document.getElementById('swap');
        if (div) {
            div.style.display = "none";
        }
    }
})