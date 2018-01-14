var fs = require('fs');

console.log("hello start");
var text = fs.readFileSync('text.txt', 'utf-8');
console.log("hello middle");
console.log(text);