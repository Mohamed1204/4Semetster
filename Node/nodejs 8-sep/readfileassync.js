var fs = require('fs');

fs.readFile('big.txt', 'utf-8', function(err, data){
    console.log('big');
});

fs.readFile('small.txt', 'utf-8', function(err, data){
    console.log('small');
});

fs.readFile('medium.txt', 'utf-8', function(err, data){
    console.log('medium');
});