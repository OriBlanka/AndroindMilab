const express = require('express');
const bodyParser = require('body-parser');
const fs = require('fs');
let app = express();
app.use(bodyParser.json());

//See all tasks in the TODO list
app.get('/tasks', (req, res) => {
    res.send(readJsonFile());
    console.log("tasks.js is displayed");
});

//Add a new task to the TODO list
app.get('/tasks/new',(req, res) => {
    
    let tasksList = JSON.parse(readJsonFile());
    let id = req.query.id || "NaN";
    let taskDescription = req.query.task || "None";

    if(isNaN(id)){ //There is no ID number
        res.send("Invalid ID number");
    } else {
        if(!tasksList.hasOwnProperty(id)){ //There is no task with the same ID in the JSON file
            tasksList[id] = taskDescription; 
            //Adding the new task in the JSON file
            writeToJson(tasksList);
    
            console.log("Added new task number " + id + " to your TO DO list");
            res.send("Added new task \n id: " + id + "\n task: " + taskDescription);
        } else {
            res.send("The ID that was chosen already exists, please choose a new one");
        }  
    }
});

//Delete task from the TODO list
app.get('/tasks/remove',(req, res) => {
    
    let tasksList = JSON.parse(readJsonFile());
    let id = req.query.id;

    if(isNaN(id)){ //There is no ID number
        res.send("Invalid ID number");
    } else {
        if(tasksList.hasOwnProperty(id)){ //There is a task with the same ID in the JSON file
            //Delete the task from the String
            delete tasksList[id]; 

            console.log("Delete task number " + id + " from your TO DO list");
            res.send("Delete the task with id: " + id + " from JSON file");

            //Update the JSON file after the deleting
            writeToJson(tasksList);
        } else {
            res.send("There is no task with such ID, please try again");
        }
    }
});

//Delete all tasks from the TODO list
app.get('/tasks/removeAll',(req, res) => {
    let tasksList = { };

    console.log("Delete all tasks from your TO DO list");
    res.send("Delete all tasks from JSON file");

    //Update the JSON file after the deleting
    writeToJson(tasksList);
});


app.listen(3000,() => {
  console.log('Listening on port 3000!')});

/***********************************************************************************************
 * Read the JSON file using readFileSync, abd return the file content that read before.
 * If there is any problem, throw error message.
 ***********************************************************************************************/
function readJsonFile() {
    let jsonFile = fs.readFileSync('tasks.json', 'utf-8', (err) => {
        if (err){
            console.log("File reading failed");
            throw err;
        }
    });
    return jsonFile;
}

/***********************************************************************************************
 * The function gets JS object and create a String object from it,
 * and writes it in the JSON file. If there is any problem, throw error message.
 ***********************************************************************************************/
function writeToJson(file){
    let jsonString = JSON.stringify(file, null, 4);
    fs.writeFile('tasks.json', jsonString, (err) => {
        if (err) {
            console.log("Error writing file", err);
            throw err;
        } else {
            console.log("Successfully wrote file");
        }
    });
}