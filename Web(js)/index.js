
var mainUrl = "http://localhost:8092/";

var clickedDB = null;
var clickedTable = null;

function createDBTable(data) {

    $("#tableInstanceAddButton").hide();
    var thead = null;
    var tbody = null;

    thead = "<thead><tr><th>#</th><th>DB</th></tr></thead>";
    tbody = "<tbody>"
    for (var i in data) {
        var index = parseInt(i) + 1;
        tbody += "<tr>";
        tbody += "<th scope=\"row\">" + index + "</th>";
        tbody += "<td class=\"dbClass\">" + data[i]["dbName"] + "</td>";
        tbody += "</tr>";
    }
    $("#dbTable").html(thead + tbody);
}

function createTableTable(data) {

    $("#tableInstanceTable").html("");
    $("#tableInstanceAddButton").hide();
    var thead = null;
    var tbody = null;

    thead = "<thead><tr><th>#</th><th>Table</th></tr></thead>";
    tbody = "<tbody>"
    for (var i in data) {
        var index = parseInt(i) + 1;
        tbody += "<tr>";
        tbody += "<th scope=\"row\">" + index + "</th>";
        tbody += "<td class=\"tableClass\">" + data[i]["tableName"] + "</td>";
        tbody += "</tr>";
    }
    $("#tableTable").html(thead + tbody);
}

function createTableInstanceTable(data) {
    var thead = null;
    var tbody = null;

    $("#tableInstanceAddButton").show();

    // create header
    var tableBaseFields = data["tableBaseFields"];
    thead = "<thead><tr><th>#</th>"
    for (var i in tableBaseFields) {

        var tableFieldName = tableBaseFields[i]["tableFieldName"];
        var type = tableBaseFields[i]["type"]
        var column = tableFieldName + "(" + type + ")";
        thead += "<th>" + column + "</th>"
    }
    thead += "</tr></thead>"

    // create rows
    tbody = "<tbody>"
    var tableInstances = data["tableInstances"];
    console.log(tableInstances);
    for (var i in tableInstances) {
        var index = parseInt(i) + 1;
        tbody += "<tr>";
        tbody += "<th scope=\"row\">" + index + "</th>";

        var baseFields = tableInstances[i]["baseFields"];
        console.log(baseFields);
        for (var j in baseFields) {
            var dataIn = baseFields[j]["data"];
            tbody += "<td class=\"tableInstanceClass\">" + dataIn + "</td>";
        }
        tbody += "</tr>";
    }

    // create input row
    tbody += "<tr id=\"tableInstanceInputTr\">";
    tbody += "<th scope=\"row\">***</th>";
    for (var i in tableBaseFields) {
        var tableFieldName = tableBaseFields[i]["tableFieldName"];
        var type = tableBaseFields[i]["type"];
        type
        tbody += "<td><input id=\"" + tableFieldName + "\" class=\"" + type + "\"></input></td>";
    }
    tbody += "</tr>";

    $("#tableInstanceTable").html(thead + tbody);
}

function addTableInstance(data) {
    var req = {};
    req["baseFields"] = data;

    var st = JSON.stringify(req);
    console.log(st);

    $.ajax({
        method: "POST",
        contentType: "application/x-www-form-urlencoded",
        url: mainUrl + "db/" + clickedDB + "/table/" + clickedTable,
        data: {
            "tableInstanceJson": JSON.stringify(req)
        },
        success: function (msg) {
            alert('Success');
        },
        error: function (err) {
            alert('Error');
        }
    });

}

function tableInsAjax() {
    $.ajax({
        url: mainUrl + "db/" + clickedDB + "/table/" + clickedTable,
        method: 'GET',
        success: function (data) {
            console.log(data);
            createTableInstanceTable(data)
        },
    });
}

$(document).ready(function () {
    $("#tableInstanceAddButton").hide();

    // load DBs 
    $.ajax({
        url: mainUrl + "db",
        method: 'GET',
        success: function (data) {
            console.log(data);
            createDBTable(data)
        },
    });


    $('body').on('click', '.dbClass', function () {
        clickedDB = $(this).text();

        console.log(clickedDB);
        $.ajax({
            url: mainUrl + "db/" + clickedDB + "/table/",
            method: 'GET',
            success: function (data) {
                console.log(data);
                createTableTable(data)
            },
        });
    });

    $('body').on('click', '.tableClass', function () {
        clickedTable = $(this).text();
        console.log(clickedTable);
        tableInsAjax();
    });

    $('body').on('click', '#tableInstanceAddButton', function () {
        console.log("Add buttton clicked");
        var reqData = [];
        $("#tableInstanceInputTr").children('td').each(function () {
            var input = $(this).find("input");
            var tableFieldName = input.attr('id');
            var type = input.attr('class');
            var data = input.val();
            reqData.push({
                tableFieldName: tableFieldName,
                type: type,
                data: data
            })

        });
        addTableInstance(reqData);
        tableInsAjax();
    });
});