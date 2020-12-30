const express = require('express');
const fs = require('fs');
let app = express();
app.use(express.static('./images'));
let path = require('path');

app.get('/files/:name', (req,res) => {
    let fileName = req.params['name'] || null;
    if(!fileName){ // Wants to get a file name not a null object
        res.send("Invalid file name - please try again");
        return; //If there is a problem with the file name, there isn't reason to continue
    }

    //Create full path for the file
    let filePath = path.join('./images/', fileName);

    //Check if this file exists in images folder
    if(!(fs.existsSync(filePath))){ 
        res.send("There isn't such file - please try again");
        return; //If there isn't such file, there isn't reason to continue
    }
    //Reading the file from images folder and piping it
    fs.createReadStream(filePath).pipe(res);
});

app.listen(3000,() => {
    console.log('Listening on port 3000!')});