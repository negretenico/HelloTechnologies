import json
import socket


def handle_consumer():
    HOST = '127.0.0.1'
    PORT = 65432
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        print("Connected to server")

        # Receive welcome message
        welcome = s.recv(1024)
        print("Welcome:", welcome.decode('utf-8'))

        # Send subscription request
        subscription = json.dumps({'topic_name': 'some_topic'})
        s.sendall(subscription.encode('utf-8'))

        # Enter message listening loop
        while True:
            data = s.recv(1024)
            if not data:
                break
            message = data.decode('utf-8')
            print("Received:", message)

            # Process the message here and send ack if needed
            ack = {"ack": True, "info": "Processed"}
            s.send(json.dumps(ack).encode('utf-8'))


if __name__ == "__main__":
    handle_consumer()
