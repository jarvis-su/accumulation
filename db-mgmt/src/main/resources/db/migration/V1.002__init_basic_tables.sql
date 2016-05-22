---USER MANAGEMENT
INSERT INTO STATUS VALUES (1,'CREATED','USER MANAGEMENT',NOW());
INSERT INTO STATUS VALUES (2,'ACTIVE','USER MANAGEMENT',NOW());
INSERT INTO STATUS VALUES (3,'INACTIVE','USER MANAGEMENT',NOW());
INSERT INTO STATUS VALUES (4,'LOCKED','USER MANAGEMENT',NOW());
---ROLE MANAGEMENT
INSERT INTO STATUS VALUES (11,'ACTIVE','ROLE MANAGEMENT',NOW());
INSERT INTO STATUS VALUES (12,'INACTIVE ROLE','ROLE MANAGEMENT',NOW());
INSERT INTO STATUS VALUES (13,'LOCKED ROLE','ROLE MANAGEMENT',NOW());


INSERT INTO RESPONSE_TYPE VALUES (0,'APPROVED','ALL',NOW());
INSERT INTO RESPONSE_TYPE VALUES (1,'USER EXISTED','USERS',NOW());
INSERT INTO RESPONSE_TYPE VALUES (2,'USER LOCKED','USERS',NOW());
INSERT INTO RESPONSE_TYPE VALUES (3,'USER NOT EXISTED','USERS',NOW());

INSERT INTO TRANSACTION_TYPE VALUES (0,'DEFAULT','DEFAULT',NOW());
INSERT INTO TRANSACTION_TYPE VALUES (1,'CREATE ROLE','ROLE MGMT',NOW());
INSERT INTO TRANSACTION_TYPE VALUES (2,'UPDATE ROLE','ROLE MGMT',NOW());
INSERT INTO TRANSACTION_TYPE VALUES (3,'CREATE USERS','USERS MGMT',NOW());
INSERT INTO TRANSACTION_TYPE VALUES (4,'UPDATE USERS','USERS MGMT',NOW());