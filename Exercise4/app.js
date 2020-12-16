const http = require('http');

let server=http.createServer(function(request,response) {
    response.writeHead(200);
    response.end("Hello EveryOne!\n");
});

server.listen(8080);