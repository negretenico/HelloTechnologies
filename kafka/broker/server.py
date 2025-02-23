import socket
import threading
from logging import info as log_info
import logging

logging.basicConfig(level=logging.INFO)

HOST = 'localhost'
PORT = 65_432


def handle_client_connection(conn, add):
    try:
        log_info(F"New connection with {add}")
        conn.sendall("Connected to broker".encode('utf-8'))
        while True:
            data = conn.recv(1024)
            if not data:
                break
            log_info(f"Broker received {data.decode('utf-8')} from {add}")
            conn.send("Broker received your message".encode('utf-8'))
    except Exception as e:
        log_info(f"WE ran into an error")
    finally:
        conn.close()
        log_info(f"Connection closed for {add}")


def run_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind((HOST, PORT))
    server_socket.listen()
    log_info(f"Broker created")
    try:
        while True:
            conn, add = server_socket.accept()
            client_thread = threading.Thread(
                target=handle_client_connection,
                args=(conn, add)
            )
            client_thread.start()
            log_info(f"Active connections: {threading.active_count() - 1}")
    except KeyboardInterrupt:
        log_info("Server shutting down")
    finally:
        server_socket.close()


if __name__ == "__main__":
    run_server()
