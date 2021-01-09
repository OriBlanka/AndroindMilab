const express = require('express');
let app = express();
const request = require('request');

const apiKey = "6A28OH5H6W92YMY8";

app.get('/stocks/:stockName', (req, res) => {
    console.log('getting')
    let stockName = req.params['stockName'];
    let url = `https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${stockName}&interval=1min&outputsize=full&apikey=${apiKey}`;
     
    request(url, function(err, res, body){
        console.log(body);
    })
});

app.listen(3000,() => {
    console.log('Listening on port 3000!')});