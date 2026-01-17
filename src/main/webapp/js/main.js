window.root = null;
let startLabel, endLabel;
let data;
let lastLogged = "";
let fromDate = null;
let toDate = null;
let applyBtn = null;
let intervalName = "1 day";
let exporting;
let symbol = "BTCUSDT";
let symbolHtml = document.getElementById("chartInfor");
symbolHtml.textContent = symbol;

document.addEventListener('DOMContentLoaded', function () {
    init();
    setDateInputs(startLabel, endLabel);
    loadDataFromInputs(startLabel, endLabel, "1d");
    applyBtn = document.querySelector('.am5-modal-primary');
    fromDate = startLabel.valueAsDate;
    toDate = endLabel.valueAsDate;
});

function init() {
    startLabel = document.getElementById("date__startLabel");
    endLabel = document.getElementById("date__endLabel");
    if (!startLabel || !endLabel) {
        console.error('Nie znaleziono inputów z id date__startLabel i date__endLabel');
        return;
    }


}

async function setDateInputs(startLabel, endLabel) {
    let actualDay = new Date();
    let oneWeekAgo = new Date(actualDay);
    oneWeekAgo.setDate(actualDay.getDate() - 115);

    startLabel.valueAsDate = oneWeekAgo;
    endLabel.valueAsDate = actualDay;

    startLabel.addEventListener('change', function () {
        loadDataFromInputs(startLabel, endLabel, "1d");
    });
    endLabel.addEventListener('change', function () {
        loadDataFromInputs(startLabel, endLabel, "1d");
    });
}

function dateIso(date) {
    if (typeof date === "string") {
        date = new Date(date);
    }
    let year = date.getUTCFullYear();
    let month = String(date.getUTCMonth() + 1).padStart(2, '0');
    let day = String(date.getUTCDate()).padStart(2, '0');
    let hours = String(date.getUTCHours()).padStart(2, '0');
    let minutes = String(date.getUTCMinutes()).padStart(2, '0');
    let seconds = String(date.getUTCSeconds()).padStart(2, '0');
    let ms = String(date.getUTCMilliseconds()).padStart(3, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${ms}Z`;
}

function getValueAsDate(input) {
    if (typeof input.valueAsDate === "object" && input.valueAsDate instanceof Date) {
        return input.valueAsDate;
    }
    if (input.value && /^\d{4}-\d{2}-\d{2}$/.test(input.value)) {
        let [y, m, d] = input.value.split('-').map(Number);
        return new Date(Date.UTC(y, m - 1, d));
    }
    return null;
}

async function loadDataFromInputs(startLabel, endLabel, intee) {
    console.log("tutaj ladowanie");
    //let dates = await getKlineData(startLabel, endLabel, intee)
    let dates = [];
    drawChart(dates)
}


async function getKlineData(startLabel, endLabel, intee,symbol) {
    //const url = "http://localhost:8080/btc/api";
    const url = "/btc/api";
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                startDate: (startLabel instanceof Date) ? startLabel : startLabel.valueAsDate,
                endDate: (endLabel instanceof Date) ? endLabel : endLabel.valueAsDate,
                Interwal: intee,
                Symbol: symbol
            })
        });
        if (!response.ok) throw new Error(`Response status: ${response.status}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error(error.message);
        return [];
    }
}


async function drawChart(dates) {
    am5.array.each(am5.registry.rootElements,
        function (root) {
            if (root.dom.id == "chartdiv") {
                console.log("Rysuję nowy wykres", dates)
                root.dispose();
            }
        }
    );
    // Create root element
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/getting-started/#Root_element
    root = am5.Root.new("chartdiv");


// Set themes
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/concepts/themes/
    root.setThemes([
        am5themes_Animated.new(root)
    ]);


// Create a stock chart
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock-chart/#Instantiating_the_chart
    var stockChart = root.container.children.push(am5stock.StockChart.new(root, {}));


// Set global number format
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/concepts/formatters/formatting-numbers/
    root.numberFormatter.set("numberFormat", "#,###.00");


// Create a main stock panel (chart)
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock-chart/#Adding_panels
    var mainPanel = stockChart.panels.push(am5stock.StockPanel.new(root, {
        wheelY: "zoomX",
        panX: true,
        panY: true
    }));


// Create value axis
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
    var valueAxis = mainPanel.yAxes.push(am5xy.ValueAxis.new(root, {
        renderer: am5xy.AxisRendererY.new(root, {
            pan: "zoom"
        }),
        extraMin: 0.1, // adds some space for for main series
        tooltip: am5.Tooltip.new(root, {}),
        numberFormat: "#,###.00",
        extraTooltipPrecision: 2
    }));

    var dateAxis = mainPanel.xAxes.push(am5xy.GaplessDateAxis.new(root, {
        baseInterval: {
            timeUnit: "day",
            count: 1
        },
        renderer: am5xy.AxisRendererX.new(root, {
            minorGridEnabled: true
        }),
        tooltip: am5.Tooltip.new(root, {})
    }));


// Add series
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
    var valueSeries = mainPanel.series.push(am5xy.CandlestickSeries.new(root, {
        name: "MSFT",
        clustered: false,
        valueXField: "Date",
        valueYField: "Close",
        highValueYField: "High",
        lowValueYField: "Low",
        openValueYField: "Open",
        //description: "Description",
        tooltip: am5.Tooltip.new(root, {
            labelText: "KR: {Description}"
        }),
        calculateAggregates: true,
        xAxis: dateAxis,
        yAxis: valueAxis,
        legendValueText: "open: [bold]{openValueY}[/] high: [bold]{highValueY}[/] low: [bold]{lowValueY}[/] close: [bold]{valueY}[/]",
        legendRangeValueText: "",
    }));


    function createNewSeries() {
        const newSeries = am5xy.CandlestickSeries.new(root, {
            name: "MSFT",
            clustered: false,
            valueXField: "Date",
            valueYField: "Close",
            highValueYField: "High",
            lowValueYField: "Low",
            openValueYField: "Open",
            description: "Description",
            calculateAggregates: true,
            xAxis: dateAxis,
            yAxis: valueAxis,
            legendValueText: "open: [bold]{openValueY}[/] high: [bold]{highValueY}[/] low: [bold]{lowValueY}[/] close: [bold]{valueY}[/]",
            legendRangeValueText: "",
        });

        const tooltip = am5.Tooltip.new(root, {
            labelText: "KR: {Description}"
        });
        tooltip.get("background").setAll({fillOpacity: 1});

        newSeries.columns.template.set("tooltip", tooltip);
        newSeries.columns.template.set("tooltipText", "ok");

        return newSeries;
    }


// Set main value series
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock-chart/#Setting_main_series
    stockChart.set("stockSeries", valueSeries);


// Add a stock legend
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock-chart/stock-legend/
    var valueLegend = mainPanel.plotContainer.children.push(am5stock.StockLegend.new(root, {
        stockChart: stockChart
    }));


// Create volume axis
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
    var volumeAxisRenderer = am5xy.AxisRendererY.new(root, {
        inside: true
    });

    volumeAxisRenderer.labels.template.set("forceHidden", true);
    volumeAxisRenderer.grid.template.set("forceHidden", true);

    var volumeValueAxis = mainPanel.yAxes.push(am5xy.ValueAxis.new(root, {
        numberFormat: "#.#a",
        height: am5.percent(20),
        y: am5.percent(100),
        centerY: am5.percent(100),
        renderer: volumeAxisRenderer
    }));

// Add series
// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
    var volumeSeries = mainPanel.series.push(am5xy.ColumnSeries.new(root, {
        name: "Volume",
        clustered: false,
        valueXField: "Date",
        valueYField: "Volume",
        xAxis: dateAxis,
        yAxis: volumeValueAxis,
        legendValueText: "[bold]{valueY.formatNumber('#,###.0a')}[/]"
    }));


    volumeSeries.columns.template.setAll({
        strokeOpacity: 0,
        fillOpacity: 0.5
    });

// color columns by stock rules
    volumeSeries.columns.template.adapters.add("fill", function (fill, target) {
        var dataItem = target.dataItem;
        if (dataItem) {
            return stockChart.getVolumeColor(dataItem);
        }
        return fill;
    })


// Set main series
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock-chart/#Setting_main_series
    stockChart.set("volumeSeries", volumeSeries);
    valueLegend.data.setAll([valueSeries, volumeSeries]);


// Add cursor(s)
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/xy-chart/cursor/
    mainPanel.set("cursor", am5xy.XYCursor.new(root, {
        yAxis: valueAxis,
        xAxis: dateAxis,
        snapToSeries: [valueSeries],
        snapToSeriesBy: "y!"
    }));


// Add scrollbar
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/xy-chart/scrollbars/
    var scrollbar = mainPanel.set("scrollbarX", am5xy.XYChartScrollbar.new(root, {
        orientation: "horizontal",
        height: 50
    }));
    stockChart.toolsContainer.children.push(scrollbar);

    var sbDateAxis = scrollbar.chart.xAxes.push(am5xy.GaplessDateAxis.new(root, {
        baseInterval: {
            timeUnit: "day",
            count: 1
        },
        renderer: am5xy.AxisRendererX.new(root, {})
    }));

    var sbValueAxis = scrollbar.chart.yAxes.push(am5xy.ValueAxis.new(root, {
        renderer: am5xy.AxisRendererY.new(root, {})
    }));

    var sbSeries = scrollbar.chart.series.push(am5xy.LineSeries.new(root, {
        valueYField: "Close",
        valueXField: "Date",
        xAxis: sbDateAxis,
        yAxis: sbValueAxis
    }));

    sbSeries.fills.template.setAll({
        visible: true,
        fillOpacity: 0.3
    });
// // Process data (convert dates and values)
// let dateRangeSelector = am5stock.DateRangeSelector.new(root, {
//     stockChart: stockChart
// });
    var dateRangeSelector = am5stock.DateRangeSelector.new(root, {
        stockChart: stockChart
    });
// ...
    dateRangeSelector.getPrivate('dropdown').get('parent').querySelector('input.am5-modal-primary').addEventListener('click', (event) => {
        console.log(dateRangeSelector.getPrivate('fromDate'))
        console.log(dateRangeSelector.getPrivate('toDate'))
    })


// Function that dynamically loads data
    async function loadData(ticker, series, granularity) {
        dates = await getKlineData(startLabel, endLabel, granularity,symbol)
        const parsedData = dates.map(d => ({
            Close: parseFloat(d.Close),
            Open: parseFloat(d.Open),
            High: parseFloat(d.High),
            Low: parseFloat(d.Low),
            Volume: parseFloat(d.Volume),
            Date: Number(d.Date),
            Description: d.Description
        }));
        data = parsedData;
        window[ticker + "_" + granularity] = parsedData
        var data = (window)[ticker + "_" + granularity];
        am5.array.each(series, function (item) {
            item.data.setAll(data);
        });
    }

// Load initial data for the first series
    var currentGranularity = "day";
    loadData("MSFT", [valueSeries, volumeSeries, sbSeries], currentGranularity);

// Set up series type switcher
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock/toolbar/series-type-control/
    var seriesSwitcher = am5stock.SeriesTypeControl.new(root, {
        stockChart: stockChart
    });

    seriesSwitcher.events.on("selected", function (ev) {
        setSeriesType(ev.item.id);
    });

    function getNewSettings(series) {
        var newSettings = [];
        am5.array.each(["name", "valueYField", "highValueYField", "lowValueYField", "openValueYField", "calculateAggregates", "valueXField", "xAxis", "yAxis", "legendValueText", "legendRangeValueText", "stroke", "fill"], function (setting) {
            newSettings[setting] = series.get(setting);
        });
        return newSettings;
    }

    function setSeriesType(seriesType) {
        // Get current series and its settings
        var currentSeries = stockChart.get("stockSeries");
        var newSettings = getNewSettings(currentSeries);

        // Remove previous series
        var data = currentSeries.data.values;
        mainPanel.series.removeValue(currentSeries);

        // Create new series
        var series;
        switch (seriesType) {
            case "line":
                series = mainPanel.series.push(am5xy.LineSeries.new(root, newSettings));
                break;
            case "candlestick":
            case "procandlestick":
                newSettings.clustered = false;
                series = mainPanel.series.push(am5xy.CandlestickSeries.new(root, newSettings));
                if (seriesType == "procandlestick") {
                    series.columns.template.get("themeTags").push("pro");
                }
                break;
            case "ohlc":
                newSettings.clustered = false;
                series = mainPanel.series.push(am5xy.OHLCSeries.new(root, newSettings));
                break;
        }

        // Set new series as stockSeries
        if (series) {
            valueLegend.data.removeValue(currentSeries);
            series.data.setAll(data);
            stockChart.set("stockSeries", series);
            var cursor = mainPanel.get("cursor");
            if (cursor) {
                cursor.set("snapToSeries", [series]);
            }
            valueLegend.data.insertIndex(0, series);
        }
    }

// Interval switcher
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock/toolbar/interval-control/
    var intervalSwitcher = am5stock.IntervalControl.new(root, {
        stockChart: stockChart,
        items: [
            {id: "1 minute", label: "1 minute", interval: {timeUnit: "minute", count: 1}},
            {id: "1 day", label: "1 day", interval: {timeUnit: "day", count: 1}},
            {id: "1 week", label: "1 week", interval: {timeUnit: "week", count: 1}},
            {id: "1 month", label: "1 month", interval: {timeUnit: "month", count: 1}}
        ]
    });

    intervalSwitcher.events.on("selected", function (ev) {
        // Determine selected granularity
        currentGranularity = ev.item.interval.timeUnit;

        // Get series
        var valueSeries = stockChart.get("stockSeries");
        var volumeSeries = stockChart.get("volumeSeries");

        // Set up zoomout
        valueSeries.events.once("datavalidated", function () {
            mainPanel.zoomOut();
        });

        // Load data for all series (main series + comparisons)
        var promises = [];
        promises.push(loadData("MSFT", [valueSeries, volumeSeries, sbSeries], currentGranularity));
        console.log("ladowanie danych 531");
        am5.array.each(stockChart.getPrivate("comparedSeries", []), function (series) {
            promises.push(loadData(series.get("name"), [series], currentGranularity));
        });

        // Once data loading is done, set `baseInterval` on the DateAxis
        Promise.all(promises).then(function () {
            dateAxis.set("baseInterval", ev.item.interval);
            sbDateAxis.set("baseInterval", ev.item.interval);
        });
    });


// Stock toolbar
// -------------------------------------------------------------------------------
// https://www.amcharts.com/docs/v5/charts/stock/toolbar/
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    var toolbar = am5stock.StockToolbar.new(root, {
        container: document.getElementById("chartcontrols"),
        stockChart: stockChart,
        controls: [
            am5stock.IndicatorControl.new(root, {
                stockChart: stockChart,
                legend: valueLegend
            }),
            am5stock.DateRangeSelector.new(root, {
                stockChart: stockChart,
                minDate: new Date(2003, 2, 1),
                maxDate: today
            }),
            am5stock.PeriodSelector.new(root, {
                stockChart: stockChart
            }),
            intervalSwitcher,
            seriesSwitcher,
            am5stock.DrawingControl.new(root, {
                stockChart: stockChart
            }),
            am5stock.ResetControl.new(root, {
                stockChart: stockChart
            }),
            am5stock.SettingsControl.new(root, {
                stockChart: stockChart
            })
        ]
    })
    const container = document.getElementById('chartcontrols');


    intervalSwitcher.events.on("selected", function (ev) {
        const value = ev.item.id;
        if (value && value !== lastLogged) {
            console.log("Wybrany zakres:", value);
            lastLogged = value;
            const toInpute = document.querySelector('input.flatpickr-input[aria-label="To date"]');
            const fromInpute = document.querySelector('input.flatpickr-input[aria-label="From date"]');
            // if (value === "1 minute") {
            //     console.log("kod ktory bedzie wykonywal sie dla jednej minuty");
            //     let ff = getValueAsDate(fromDate);
            //     let dd = getValueAsDate(toDate);
            //     intervalName = "1 minute";
            //     loadDataFromInputs(fromDate, toDate, "11:30");
            //     if (true) {
            //         console.log("load 510 tooltip");
            //         //updateTooltip();
            //
            //     }
            //
            // } else {
            //     console.log("kod ktory bedzie sie wykonywal dla reszty");
            //     loadDataFromInputs(getValueAsDate(fromDate), getValueAsDate(toDate), "1d");
            // }
        }
    });


    const toInput = document.querySelector('input.flatpickr-input[aria-label="To date"]');
    const fromInput = document.querySelector('input.flatpickr-input[aria-label="From date"]');


    setTimeout(() => {
        const applyBtn = document.querySelector('.am5-modal-primary');
        if (applyBtn && toInput && fromInput) {
            applyBtn.addEventListener('click', function () {
                if (fromInput.value && toInput.value) {
                    fromDate = getValueAsDate(fromInput);
                    toDate = getValueAsDate(toInput);
                    startLabel = fromDate;
                    endLabel = toDate;
                    console.log("tutaj ladowanie po zmianie daty");
                    loadDataFromInputs(fromDate, toDate, "1d",symbol);
                }
            });
        } else {
            console.log("blad 258")
        }
    }, 1000);

    exporting = am5plugins_exporting.Exporting.new(root, {
        menu: am5plugins_exporting.ExportingMenu.new(root, {})
    });
//toInput.addEventListener('change', onDateChange);
//fromInput.addEventListener('change', onDateChange);


}

document.addEventListener("keydown", async function (ev) {
    const today = new Date().toISOString();
    if (ev.key === "s") {
        //exporting.set("filePrefix",today);
        //exporting.download("png");
        let photo = await getScreenShot();
        let formData = new FormData();
        formData.append("photo", photo);

        sendPhoto(formData);
    }
})

async function getScreenShot() {
    const screenshotTarget = document.body;
    let screenShot;
    await html2canvas(screenshotTarget).then(canvas => {
        screenShot = canvas.toDataURL();
    });
    return screenShot;
}

async function sendPhoto(photo) {
    //const url = "http://localhost:8080/btc/screenShoot";
    const url = "/btc/screenShoot";
    try {
        const response = await fetch(url, {
            method: "POST",
            body: photo
        });
    } catch (error) {
        console.error(error.message);
        return [];
    }
}

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
        items.push(s.symbol)

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
    let li = e.target.closest('li');
    if (li) {
        let text = li.querySelector('p').textContent;
        console.log(text
            + fromDate + toDate);
        symbol = text;
        symbolHtml.textContent = symbol;
        loadDataFromInputs(fromDate, toDate, "1d",symbol);
        searchbarinput.value="";
        searchbarinput.placeholder = symbol;
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




