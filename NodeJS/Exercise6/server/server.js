const express = require('express');
let app = express();
const request = require('request');

app.get('/stocks', (req, res) => {
    console.log('getting');
    url = url_1 + 'IBM' + url_2;
    request(url, function(err, res, body){
        console.log(body);
    })
});
