/* Creation types and tables */

CREATE user_t AS OBJECT (
u_id INT(10),
u_email VARCHAR2(30), 
u_pseudo VARCHAR2(20), 
u_password VARCHAR2(50)
MEMBER FUNCTION get_uemail RETURN VARCHAR, 
MEMBER FUNCTION get_upseudo RETURN VARCHAR
) NOT FINAL ;
/

CREATE TYPE BODY user_t AS 
MEMBER FUNCTION get_uemail RETURN VARCHAR AS
BEGIN RETURN u_email; END;
MEMBER FUNCTION get_upseudo RETURN VARCHAR AS
BEGIN RETURN u_pseudo; END;
END;
/

CREATE TABLE users OF user_t (
CONSTRAINT uc1 PRIMARY KEY(u_id, u_email, u_pseudo),
CONSTRAINT uc2 u_password NOT NULL
);
/

CREATE TYPE hand_t AS OBJECT (
h_value VARCHAR2(10), 
h_color VARCHAR2(10)
) ;
/

CREATE TYPE playerslist_t AS VARRAY(4) OF VARCHAR(20);
/
CREATE TYPE handslist_t AS VARRAY(4) OF hand_t;
/

CREATE game_t AS OBJECT (
g_id INT(10),
g_players playerslist_t, 
g_hands handslist_t
) ;
/

CREATE TABLE games OF game_t (
CONSTRAINT gc1 PRIMARY KEY(g_id),
CONSTRAINT gc2 g_players NOT NULL,
CONSTRAINT gc3 g_hands NOT NULL,
);
/