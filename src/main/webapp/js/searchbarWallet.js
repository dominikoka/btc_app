let symbol = "";
function searchBarWallet() {




    console.log("asd")
    // let symbolHtml = document.getElementById("chartInfor");
    // symbolHtml.textContent = symbol;

    async function getSymbolFetch() {

        fetch('https://api.binance.com/api/v3/exchangeInfo')
        try {
            const response = await fetch('https://api.binance.com/api/v3/exchangeInfo');
            if (!response.ok) {
                throw new Error("not ok")
            }
            const data = await response.json();
            return data;
        } catch (e) {
            throw new Error(e);
        }
    }

    const items = [];

    async function getSymbol() {
        let symbolFetch = await getSymbolFetch();
        let data;
        symbolFetch.symbols.forEach(s => {
            //console.log(s);
            let asset = s.quoteAsset;
            if (!items.includes(s.quoteAsset)) {
                items.push(s.quoteAsset)
            }
        })
        console.log(symbolFetch);
        items.forEach(s => {
            const a = document.createElement('a');
            const li = document.createElement('li');
            const p = document.createElement('p');
            p.textContent = s;
            a.append(li);
            li.append(p);
            resultlist.append(a);
        })
    }

    getSymbol();


    var searchbar = document.getElementById("searchbar");
    var searchbarinput = document.getElementById("searchbarinput");
    var dropdown = document.getElementById("dropdown");

    var resultlist = document.getElementById("resultlist");

    var lis = resultlist.getElementsByTagName("li");


    resultlist.addEventListener('click', function (e) {
        let text
        let li = e.target.closest('li');
        if (li) {
            text = li.querySelector('p').textContent;
            symbol = text;
            value = symbol;
            //symbolHtml.textContent = symbol;
            //loadDataFromInputs(fromDate, toDate, "1d",symbol);
            searchbarinput.value = "";
            searchbarinput.placeholder = symbol;
            console.log("symbol z event listener" + symbol)
        }
    })

    function darksoulsearch() {

        searchbarinput.style.borderRadius = "25px 25px 0 0";

        resultlist.style.display = "flex";

        dropdown.style.animation = "height 0.5s 1 linear forwards";
        dropdown.style.height = "fit-content";
        dropdown.style.maxHeight = "200px";
        dropdown.style.overflowX = "hidden";
        dropdown.style.overflowY = "scroll";
        dropdown.style.transition = "all 0.5s";

    }

    function closesearch() {

        searchbarinput.style.borderRadius = "25px";

        dropdown.style.animation = "revheight 0.5s 1 linear forwards";
        dropdown.style.height = "fit-content";
        dropdown.style.maxHeight = "0px";
        dropdown.style.overflowX = "hidden";
        dropdown.style.overflowY = "scroll";
        dropdown.style.transition = "all 0.5s";

        resultlist.style.display = "none";
    }

    window.addEventListener("click", function (event) {
        if (event.target != searchbarinput) {
            closesearch();
            console.log("you clicked " + event);
        }

    });



    searchbarinput.addEventListener
    ("input", function () {

            var searchValue = searchbarinput.value.toLowerCase();

            for (let i = 0; i < lis.length; i++) {
                var li = lis[i];
                var liName = li.textContent.toLowerCase();

                if (liName.includes(searchValue)) {
                    darksoulsearch();
                    li.style.display = "flex";
                } else {
                    li.style.display = "none";
                }
            }
        }
    );
    return symbol;
}
function getSymbolToChange()
{
    console.log("symbol z symbol to change" + symbol)
    return symbol;
}