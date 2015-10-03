var WebSocket = require('ws');
var ws = new WebSocket('ws://localhost:5000/');

ws.on('open', function() {
  console.log('connect');
  ws.send('something');
});
ws.on('message', function(message) {
  console.log('received: %s', message);
});
