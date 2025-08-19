function getDonut(summary) {
    console.log("donut statruje __-__")
    var root = am5.Root.new("Info__donut");
    root.setThemes([
        am5themes_Animated.new(root)
    ]);
    var chart = root.container.children.push(
        am5percent.PieChart.new(root, {
            startAngle: 160, endAngle: 380
        })
    );
    var series0 = chart.series.push(
        am5percent.PieSeries.new(root, {
            valueField: "value",
            categoryField: "crypto",
            startAngle: 160,
            endAngle: 380,
            radius: am5.percent(70),
            innerRadius: am5.percent(65)
        })
    );
    series0.ticks.template.set("forceHidden", true);
    series0.labels.template.set("forceHidden", true);

    var label = chart.seriesContainer.children.push(
        am5.Label.new(root, {
            textAlign: "center",
            centerY: am5.p50,
            centerX: am5.p30,
            text: "[fontSize:18px]total[/]:\n[bold fontSize:30px]" + getTotal(summary) + "[/]"
        })
    );
    label.set("fill", am5.color(0xff0000));
    label.set("fillOpacity", 0.6);

    var data = getDonutData(summary);
    //         [
    // {
    //     crypto: "Lithuania",
    //     value: 501.9,
    //
    // },
    // {
    //     crypto: "Czech Republic",
    //     value: 301.999999999,
    //
    // }];
    //let g = getDonutData(summary)
    series0.data.setAll(data);
}

function getDonutData(summary) {
    let data = [];
    for (const summaryKey in summary) {
        let crypto = summary[summaryKey];
        let cryptoObj = {crypto: crypto.nameCurrency, value: crypto.approximateSellPrice};
        data.push(cryptoObj);

    }
    return data;
}

function getTotal(summary) {
    let total = 0;
    for (const summaryKey in summary) {
        let cryptoPrice = summary[summaryKey].approximateSellPrice;
        total = total + cryptoPrice;
    }
    return Math.round(total * 100) / 100;
}