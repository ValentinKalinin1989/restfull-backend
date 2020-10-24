CREATE TABLE IF NOT EXISTS role
(
  id integer NOT NULL,
  name character varying(20) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS usr
(
  login character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  CONSTRAINT usr_pkey PRIMARY KEY (login)
);

CREATE TABLE IF NOT EXISTS usr_role
(
  usr_id character varying(255) NOT NULL REFERENCES usr (login),
  role_id integer NOT NULL REFERENCES role (id)
);