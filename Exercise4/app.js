const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
let app = express();
app.use(bodyParser.json());


app.get('/tasks', (req, res) => {
    res.send(readJsonFile());
    console.log("tasks.js is displayed");
});

app.get('/tasks/new',(req, res) => {
    
    let tasksList = JSON.parse(readJsonFile());
    let id = req.query.id || "NaN";
    let taskDescription = req.query.task || "None";

    if(isNaN(id)){
        res.send("Invalid ID number");
    } else {
        if(!tasksList.hasOwnProperty(id)){
            tasksList[id] = taskDescription;
            writeToJson(tasksList);
    
            console.log("Added new task number " + id + " to your TO DO list");
            res.send("Added new task \n id: " + id + "\n task: " + taskDescription);
        } else {
            res.send("The ID that was chosen already exists, please choose a new one");
        }  
    }
});

app.listen(3000,() => {
  console.log('Listening on port 3000!')});

/**
 * 
 */
function readJsonFile() {
    let jsonFile = fs.readFileSync('tasks.json', 'utf-8', (err) => {
        if (err){
            console.log("File reading failed");
            throw err;
        }
    });
    return jsonFile;
}

/**
 * 
 */
function writeToJson(file){
    let jsonString = JSON.stringify(file, null, 4);
    fs.writeFile('tasks.json', jsonString, (err) => {
        if (err) {
            console.log("Error writing file", err);
        } else {
            console.log("Successfully wrote file");
        }
    });
}