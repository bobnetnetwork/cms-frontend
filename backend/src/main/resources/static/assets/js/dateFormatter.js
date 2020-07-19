dateFormatter()

function dateFormatter(){
    for (i = 0; i < document.getElementsByClassName("date").length; i++){
        document.getElementsByClassName("date").item(i).innerHTML = formatter(document.getElementsByClassName("date").item(i).innerHTML)
    }
}

function formatter(mysqlDate){
    const d = Date.parse(mysqlDate)
    const date = new Date(d)

    return date.toLocaleDateString("en-US")
}