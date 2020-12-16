const http = require('http');

let server=http.createServer(function(request,response) {
    response.writeHead(200);
    response.end("Hello World!\n");
});

server.listen(8080);