import socket

HOST = '127.0.0.1'
PORT = 65432

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    print("Connected to server")

    # Receive welcome message
    data = s.recv(1024)
    print(f"Received: {data.decode()}")

    # Send a test message
    message = "Hello server!"
    s.sendall(message.encode())

    # Receive response
    data = s.recv(1024)
    print(f"Received: {data.decode()}")
