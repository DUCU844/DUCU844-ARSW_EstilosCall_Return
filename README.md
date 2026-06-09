# ARSW - Call and Return Style: Networks and Java

This laboratory explores how programs communicate over a network using Java.
We use the **Call and Return** architectural style, where one program (client) calls another program (server) and waits for a response.

---

## Topics Covered

- URLs (addresses to find resources on the internet)
- Sockets (TCP connections between client and server)
- Datagrams (UDP messages without connection)
- RMI (calling methods on a remote object)

---

## Project Structure

```
├── URL/              # Exercises 1 and 2 - Working with URLs
├── Sockets/          # Exercises 3 to 6 - TCP Sockets and Web Server
├── Datagramas/       # Exercise 7 - UDP Datagrams
└── RMI/              # Exercise 8 - Remote Method Invocation
```

---

## Section 1: URLs

A URL (Uniform Resource Locator) is an address that points to a resource on the internet.

**Format:**
```
<protocol>://<server>:<port>/<path>
```
**Example:** `http://www.escuelaing.edu.co:80/index.html`

A URL object in Java has 8 parts you can read:

| Method | What it returns |
|---|---|
| `getProtocol()` | The protocol (http, https, ftp...) |
| `getAuthority()` | Host + port together |
| `getHost()` | The server name |
| `getPort()` | The port number (-1 if not set) |
| `getPath()` | The path to the resource |
| `getQuery()` | Parameters after `?` |
| `getFile()` | Path + query together |
| `getRef()` | Fragment after `#` |

### Exercise 1 - Print URL parts
**File:** `URL/src/createURL.java`

Creates a URL object and prints all 8 parts.

**How to run:**
```bash
javac URL/src/createURL.java -d out/URL
java -cp out/URL createURL
```

**Expected output:**
```
Protocol: https
Host: escuelaing.s3.amazonaws.com
Port: 443
...
```

---

### Exercise 2 - Browser that saves HTML
**File:** `URL/src/ResultadoHTML.java`

This program asks the user for a URL, reads the content, and saves it to a file called `resultado.html`.

**How to run:**
```bash
javac URL/src/ResultadoHTML.java -d out/URL
java -cp out/URL ResultadoHTML
```

**Example input:**
```
Write address to consult: http://example.com
```

After running, open `resultado.html` in a browser to see the page.

---

## Section 2: Sockets (TCP)

A socket is a connection point between two programs on a network.
TCP sockets guarantee that data arrives in order and without errors.

To connect, the **server** waits on a port.
The **client** connects to that port and sends messages.

```
Client  ──────── connect ────────>  Server (port 35000)
Client  ──────── message ───────>  Server
Client  <─────── response ──────   Server
```

### Exercise 3 - Square Server (4.3.1)
**Files:** `Sockets/src/SquareServer.java` | `Sockets/src/SquareClient.java`

The client sends a number. The server responds with the square of that number.

**How to run (open two terminals):**

Terminal 1 - Server:
```bash
javac Sockets/src/SquareServer.java -d out/Sockets
java -cp out/Sockets SquareServer
```

Terminal 2 - Client:
```bash
javac Sockets/src/SquareClient.java -d out/Sockets
java -cp out/Sockets SquareClient
```

**Example:**
```
Input:  5
Output: 25
```

---

### Exercise 4 - Trigonometry Server (4.3.2)
**File:** `Sockets/src/TrigServer.java`

The server receives a number and responds with a trigonometric function result.
By default the function is **cosine**. You can change the function by sending `fun:sin`, `fun:cos`, or `fun:tan`.

**How to run:**

Terminal 1 - Server (port 35001):
```bash
javac Sockets/src/TrigServer.java -d out/Sockets
java -cp out/Sockets TrigServer
```

Terminal 2 - Client (use telnet or netcat):
```bash
telnet localhost 35001
```

**Example session:**
```
> 0
  1.0           (cos(0) = 1)
> fun:sin
  OK: function is now sin
> 0
  0.0           (sin(0) = 0)
> fun:tan
  OK: function is now tan
> 0.7853981633
  1.0           (tan(pi/4) = 1)
```

---

### Exercise 5 - Simple Web Server (4.4)
**File:** `Sockets/src/HttpServer.java`

A basic HTTP server that accepts **one request** and responds with an HTML page.

**How to run:**
```bash
javac Sockets/src/HttpServer.java -d out/Sockets
java -cp out/Sockets HttpServer
```

Then open a browser and go to: `http://localhost:35000`

The server prints every request line it receives and responds with a simple HTML page.

---

### Exercise 6 - Multi-Request Web Server (4.5.1)
**File:** `Sockets/src/MultiHttpServer.java`

This server handles **multiple requests in a loop** (not at the same time, but one after another).
It can serve any file: HTML pages and images (PNG, JPG, GIF...).

The server reads files from the directory where you run it.

**How to run:**
```bash
javac Sockets/src/MultiHttpServer.java -d out/Sockets
java -cp out/Sockets MultiHttpServer
```

**Test it:**
- Put an `index.html` file in the same directory you run the server from.
- Open `http://localhost:35000` in a browser.
- The server will return the file with the correct content type.

**Supported content types:**

| Extension | Content-Type |
|---|---|
| `.html` | text/html |
| `.css` | text/css |
| `.js` | application/javascript |
| `.png` | image/png |
| `.jpg` | image/jpeg |
| `.gif` | image/gif |

---

## Section 3: Datagrams (UDP)

A datagram is a message sent over the network **without a connection**.
UDP does not guarantee that messages arrive or arrive in order.
It is useful when speed is more important than reliability (for example: clocks, games, video).

```
Client  ──── packet ────>  Server
Client  <─── packet ────   Server  (or no response at all)
```

### Exercise 7 - Time Server and Client (5.2.1)
**Files:** `Datagramas/src/DatagramTimeServer.java` | `Datagramas/src/DatagramTimeClient.java`

The **server** waits for a message and responds with the current date and time.
The **client** asks for the time every 5 seconds. If the server does not respond, it keeps showing the last time it received.

**How to run:**

Terminal 1 - Server (port 4445):
```bash
javac Datagramas/src/DatagramTimeServer.java -d out/Datagramas
java -cp out/Datagramas DatagramTimeServer
```

Terminal 2 - Client:
```bash
javac Datagramas/src/DatagramTimeClient.java -d out/Datagramas
java -cp out/Datagramas DatagramTimeClient
```

**Expected output (client):**
```
Server time: Mon Jun 09 10:00:00 COT 2026
Server time: Mon Jun 09 10:00:05 COT 2026
Server not responding. Last known time: Mon Jun 09 10:00:05 COT 2026
Server time: Mon Jun 09 10:00:15 COT 2026
```

**Test the resilience:** stop the server while the client runs. The client keeps the last time. Start the server again and the client updates automatically.

---

## Section 4: RMI (Remote Method Invocation)

RMI lets a Java program call a method on an object that lives in **another Java program**, possibly on another computer.
The programmer writes the code as if the object is local, but the call goes over the network.

```
Client  ──── registry lookup ────>  rmiregistry
Client  <─── remote reference ──    rmiregistry
Client  ──── method call ────────>  Server object
Client  <─── return value ──────    Server object
```

**Key concepts:**
- **Interface:** defines what methods the remote object has.
- **Stub:** a local proxy that handles network communication for the client.
- **Registry:** a name server where the server publishes its object.

### Exercise 8 - RMI Chat (6.4.1)
**Files:**
- `RMI/src/rmiexample/IChatService.java` — remote interface
- `RMI/src/rmiexample/ChatServiceImpl.java` — server implementation
- `RMI/src/rmiexample/ChatApp.java` — main app (both sides)

Each user runs `ChatApp`. Each program publishes its own chat service and connects to the other.

**How to compile:**
```bash
javac -d out/RMI RMI/src/rmiexample/IChatService.java RMI/src/rmiexample/ChatServiceImpl.java RMI/src/rmiexample/ChatApp.java
```

**How to run (two terminals, same machine):**

Terminal 1 (User A):
```bash
java -cp out/RMI rmiexample.ChatApp
Your name: Alice
Port to publish your service on: 5000
Remote IP to connect to: 127.0.0.1
Remote port to connect to: 5001
```

Terminal 2 (User B):
```bash
java -cp out/RMI rmiexample.ChatApp
Your name: Bob
Port to publish your service on: 5001
Remote IP to connect to: 127.0.0.1
Remote port to connect to: 5000
```

**Important:** start both terminals before sending messages. Type a message and press Enter to send. Press Enter on an empty line to exit.

---

## Summary of Exercises

| # | Section    | Exercise                         | File(s)                                  | Status   |
|---|------------|----------------------------------|------------------------------------------|----------|
| 1 | URLs       | Print 8 URL parts                | `URL/src/createURL.java`                 | Complete |
| 2 | URLs       | Browser → resultado.html         | `URL/src/ResultadoHTML.java`             | Complete |
| 3 | Sockets    | Square server                    | `Sockets/src/SquareServer.java` + Client | Complete |
| 4 | Sockets    | Trig server (sin/cos/tan)        | `Sockets/src/TrigServer.java`            | Complete |
| 5 | Sockets    | Simple web server (1 request)    | `Sockets/src/HttpServer.java`            | Complete |
| 6 | Sockets    | Multi-request web server + files | `Sockets/src/MultiHttpServer.java`       | Complete |
| 7 | Datagramas | Time server + resilient client   | `Datagramas/src/DatagramTime*.java`      | Complete |
| 8 | RMI        | Two-way chat                     | `RMI/src/rmiexample/Chat*.java`          | Complete |

---

## Requirements

- Java 11 or newer (tested with Java 24)
- No external libraries needed

## Author

Adrian — ARSW 2026-i, Escuela Colombiana de Ingeniería
