addEventListener("click", (event) => {
    console.log("clicked");
});


function color()
{
    if(document.body.classList.contains("dark-mode"))
    {
        root.interfaceColors.set("background", am5.color(0xfafafa));
        root.interfaceColors.set("grid", am5.color(0xfafafa));
        root.interfaceColors.set("text", am5.color(0xb13c3c));
        root.interfaceColors.set("primaryButtonText", am5.color(0x000000));
        root.interfaceColors.set("fill", am5.color(0xb13c3c));
        root.interfaceColors.set("secondaryButton", am5.color(0xb13c3c));
        root.interfaceColors.set("positive", am5.color(0x48418c));
        root.interfaceColors.set("negative", am5.color(0x8c4144));
        //test
        console.log("darkmode enabled");
        // root.interfaceColors.set("alternativeText", am5.color(0xb13c3c));
        // root.interfaceColors.set("legendLabel", am5.color(0xb13c3c));
        // root.interfaceColors.set("legendValueLabel", am5.color(0xb13c3c));


    }
    else
    {
        root.interfaceColors.set("background", am5.color(0xffffff));
        root.interfaceColors.set("grid", am5.color(0x000000));
        root.interfaceColors.set("text", am5.color(0x000000));
        root.interfaceColors.set("primaryButtonText", am5.color(0xffffff));

        root.interfaceColors.set("fill", am5.color(0xf3f3f3));
        root.interfaceColors.set("secondaryButton", am5.color(0xd9d9d9));
        root.interfaceColors.set("positive", am5.color(0x50b300));
        root.interfaceColors.set("negative", am5.color(0xb30000));
    }
}
