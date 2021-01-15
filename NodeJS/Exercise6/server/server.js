"use strict";

const express = require('express');
const bodyParser = require('body-parser');
const FCM = require('fcm-push');
const fetch = require("node-fetch");

let app = express();
app.use(bodyParser.json());

const apiKey = "6A28OH5H6W92YMY8";
const fcmKey = "AAAAFl4dOQA:APA91bFUtMvcBx7sj9TxAm0xROyAq9l1ODvIDzpYZOebG3oUKcPmmiw7-8GmdgZgMRKUIjTmoJUbNHetgTlL8f0TAIGvcr-QY3Sru2Nqe5AJ7UDbyDwBSJIC3FcfQN89IW4POQmrEFF9";

let token = "";
let fcm = new FCM(fcmKey);


app.post('/token/:token', (req, res) => {
    token = req.params['token'];
    console.log("Got new token: " + token);
    res.json({result: "success"});
});


app.post('/stocks/:stock', (req, res) => {
    let stockName = req.params['stock'];
    token = req.body.token;
    console.log (`Got the token: ${token}, and the stock: ${stockName}`);

    setInterval(() => {
        fetchData(stockName, (price, error) => {
            if(error){
                console.log("Error sending message:", error);
            } else {
                sendToFCM(token, stockName, price);
            }
        })
    }, 15000);
    res.json({result: "Success!"});
});

app.listen(8080,() => {
    console.log('Listening on port 8080!')});

    
    
/**********************************************************************
    Fetches the stock data from the Alphavantage API related to the 
    given symbol     
***********************************************************************/
function fetchData (symbol, cb) {
    //build url address
    let url = new URL('https://www.alphavantage.co/query');
    let params = {function: "GLOBAL_QUOTE", symbol: symbol, apikey: apiKey};
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
    // fetch data
    fetch(url , {method: 'GET'})
    .then(response => response.json())
    .then((data) => {
        cb(data["Global Quote"]["05. price"]);
    })
    .catch((error) => {
        console.error('ERROR: ', error);
        cb("", error);
    });
}

/**********************************************************************
    Taken the stock's name and price and send them to the FCM     
***********************************************************************/
function sendToFCM(token, stock, price) {
    fcm.send({
        to: token,
        data: {
            symbol: stock,
            price: price
        },
        notification: {
            title: `Updating  ${stock}`,
            body: `The price of ${stock} is ${price}`
        }
    }, (error, response) => {
        console.log("Error sending message:", error);
    });
}