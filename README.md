# java-play-websocket
Akka Stream WebSocket Application Using Java Play Framework

# Steps to compile and run the project

#### Compilation
```
sbt compile 
```

#### Below command starts webSocket server at port 9000 
```
sbt run
```

#### Install wscat 
```
npm install -g wscat 
```

#### Connect to webSocket Server
```
 wscat -c ws://localhost:9000/ws
``` 

#### Below query will give you OHLC chart data for symbol `XXBTZUSD` 
```
{"event": "subscribe", "symbol": "XXBTZUSD", "interval": 15}
```