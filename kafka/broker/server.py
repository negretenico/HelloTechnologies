import os
import socket
import threading
from logging import info as log_info
import logging
from json import loads
from Message import Message
from collections import defaultdict

logging.basicConfig(level=logging.INFO)

HOST = 'localhost'
PORT = 65_432
connection_map = defaultdict(
    set)  # shared across threads, likely won't scale well if we enhance this in the future think of ways to improve, som sorta semaphore mayeb?

NUM_PARTITIONS = 10


def handle_appending_to_log(message: Message, topic_name: str):
    partition = hash(message.key) ** 8 % NUM_PARTITIONS
    file_name = os.path.join('partitions', topic_name, str(partition))
    os.makedirs(os.path.dirname(file_name), exist_ok=True)
    with open(file_name, 'wb') as f:
        f.write(bytes(message.value, 'utf-8'))


def handle_producer(message: Message, topic_name: str, producer):
    handle_appending_to_log(message, topic_name)
    for connection in connection_map[topic_name]:
        connection.send(bytes(message.value, 'utf-8'))
        connection.settimeout(2)  # Set a timeout
        try:
            data = connection.recv(1024)
            if data:
                recieved_msg = data.decode('utf-8')
                producer.send(f"Acknowledgment from consumer: {recieved_msg}")
        except socket.timeout:
            producer.send("No acknowledgment received from a consumer")


def handle_client_connection(conn, add):
    try:
        log_info(F"New connection with {add}")
        schema = """
            header: Dict[str, Any]
    key: str
    value: Any
    timestamp: datetime

        """
        conn.send(
            f"Connected to broker, data will be seralized and deseralized in a json fromat, follwoing {schema} format".encode(
                'utf-8'))
        while True:
            data = conn.recv(1024)
            if not data:
                break
            metadata = loads(data.decode('utf-8'))
            if metadata['client'] == 'producer':
                handle_producer(metadata['message'], metadata['topic_name'], conn)
                conn.send(f"Messages have been sent to consumers".encode('utf-8'))
                break
            connection_map[metadata['topic_name']].add(conn)
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
