
var http = require('http');
var fs = require('fs');
var test = require('tes1')

var server = http.createServer(function(req, res){
    res.writeHead(200, {'Content-type' : 'text/json'});
    fs.readFile('big.txt', 'utf-8', function(err, data){
        //res.write('"Title" : "Hello Json web API" "text" : Text fra min API');
        res.write(data);
        res.end();
        
    });
        
    
    
});

server.listen(process.env.Port || 3003);