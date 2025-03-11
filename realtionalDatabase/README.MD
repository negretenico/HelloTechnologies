# Relational Database

This is a very simple implementation of a RDBMS.
<br/>

* Reading in data by use of a `Select` service
* Inserting by using an `Insert` service
* Creating tables and the metadata included by using a `Create` service
* Schema validation in order to properly insert the data into the table

Thoughts for future improvements

* WHERE Clause Support – Implement filtering capabilities for queries, allowing users to retrieve specific rows based on
  conditions (e.g., SELECT * FROM users WHERE age > 30).
* Query Parsing & Execution Plan – Develop a proper SQL query parser to handle more complex queries efficiently,
  potentially using an Abstract Syntax Tree (AST) for structured interpretation.
* Indexing & Performance Optimization – Introduce indexing mechanisms to improve query performance, reducing the need
  for full table scans on every SELECT.
* Foreign Keys & Relationships – Implement referential integrity to support relationships between tables, enabling true
  relational capabilities like JOIN operations.
* Transaction Support – Introduce ACID-compliant transactions to ensure consistency when multiple operations are
  performed together.
* Concurrency Control & Locking – Implement mechanisms like row-level locking or optimistic concurrency control to allow
  multiple operations on the database without conflicts.