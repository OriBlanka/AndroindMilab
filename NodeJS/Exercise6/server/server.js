const express = require('express');
let app = express();
const request = require('request');
const admin = require("firebase-admin");

const apiKey = "6A28OH5H6W92YMY8";
let token = "";

const serviceAccount = require('D:/Ori-Studies/miLab/mobile/NodeJS/Exercise6/server/stock-quotes-85d2e-3e684f8e1075.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://stock-quotes-85d2e-default-rtdb.firebaseio.com"
});


app.get('/stocks', (req, res, next) => {
    console.log('getting request');
    let stockName = req.body.stock;
    token = req.body.token;

    console.log (`Goe the token: ${token}, and the stock: ${stockName}`);

    var url = `https://www.alphavantage.co/query?function=Global_Quote&symbol=${stockName}&interval=1min&outputsize=full&apikey=${apiKey}`;
    request(url, function(err, res, body){
        console.log(body);
    })

    var firstTime = 1;
    //var tempPrice = 0;
    var price = 0;

    function timeout () {
        setTimeout(function() {
            firstTime = 0;
            request(url, function(err, response, body) {
                if (err) { //Something went wrong went trying to connect to AlphaVantage
                    console.log('error:', error);
                    response.status(400).json({err:"Failed sending request to AlphaVantage"});
                } else {
                    let stockInformation = JSON.parse(body);
                    let globalQuote = stockInformation["Global Quote"];
                    if (globalQuote != undefined) { //A valid value was obtained
                        price = globalQuote["05. price"];
                    }    
                    console.log(`The current price of ${stockName} stock is: ${updatePrice}`);

                    sendToFCM(registrationToken, stockName, price);
                }
            })
            timeout();
        }, firstTime ? 1 : 15000)
    }
    timeout();
});

app.listen(8080,() => {
    console.log('Listening on port 8080!')});

    
/**********************************************************************
    Taken the stock's name and price and send them to the FCM     
***********************************************************************/
function sendToFCM(token, stock, price) {
    var payload = {
        data: {
            symbol: stock,
            price: price
        },
        notification: {
            body: `The price of ${stock} is ${price}`
        }
    };
    admin.messaging().sendToDevice(token, payload)
        .then(function(response) {
            console.log("Successfully sent message:", response);
        })
        .catch(function(error) {
            console.log("Error sending message:", error);
        });
}