/* Creation types and tables */

CREATE user_t AS OBJECT (
u_pseudo VARCHAR2(20), 
u_email VARCHAR2(30) unique, 
u_password VARCHAR2(64),
MEMBER FUNCTION get_uemail RETURN VARCHAR, 
MEMBER FUNCTION get_upseudo RETURN VARCHAR
) NOT FINAL ;
/

CREATE TABLE users OF user_t (
CONSTRAINT uc1 PRIMARY KEY(u_pseudo),
CONSTRAINT uc3 u_email NOT NULL,
CONSTRAINT uc2 u_password NOT NULL
);
/

CREATE SEQUENCE users_seq
START WITH 1
INCREMENT BY 1;
/

--CREATE OR REPLACE TRIGGER users_trigger
--BEFORE INSERT
--ON users
--REFERENCING NEW AS NEW
--FOR EACH ROW
--BEGIN
--SELECT users_seq.NEXTVAL INTO :NEW.u_id FROM dual;
--END;
--/

CREATE TYPE BODY user_t AS 
MEMBER FUNCTION get_uemail RETURN VARCHAR AS
BEGIN RETURN u_email; END;
MEMBER FUNCTION get_upseudo RETURN VARCHAR AS
BEGIN RETURN u_pseudo; END;
END;
/

CREATE TYPE card_t AS OBJECT (
h_value VARCHAR2(10), 
h_color VARCHAR2(10)
) ;
/

CREATE TYPE playerslist_t AS VARRAY(4) OF VARCHAR(20);
/
CREATE TYPE hand_t AS VARRAY(20) OF card_t;
/
CREATE TYPE handslist_t AS VARRAY(4) OF hand_t;
/

CREATE game_t AS OBJECT (
g_name VARCHAR(50),
g_players playerslist_t, 
g_hands handslist_t
) ;
/

CREATE TABLE games OF game_t (
CONSTRAINT gc1 PRIMARY KEY(g_name),
CONSTRAINT gc2 g_players NOT NULL,
CONSTRAINT gc3 g_hands NOT NULL
);
/

CREATE SEQUENCE games_seq
START WITH 1
INCREMENT BY 1;
/

--CREATE OR REPLACE TRIGGER games_trigger
--BEFORE INSERT
--ON games
--REFERENCING NEW AS NEW
--FOR EACH ROW
--BEGIN
--SELECT games_seq.NEXTVAL INTO :NEW.g_id FROM dual;
--END;
--/

