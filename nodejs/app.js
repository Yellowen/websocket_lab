var WebSocket = require('ws');
var ws = new WebSocket('ws://localhost:4000/');

ws.on('open', function() {
  console.log('connect');
  ws.send('something111111111111111');
});
ws.on('message', function(message) {
  console.log('received: %s', message);
});
