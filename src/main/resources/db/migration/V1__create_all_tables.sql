DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS record CASCADE;

DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS account_id_seq;
DROP SEQUENCE IF EXISTS record_id_seq;

CREATE SEQUENCE user_id_seq START WITH 1;
CREATE SEQUENCE account_id_seq START WITH 1;
CREATE SEQUENCE record_id_seq START WITH 1;


create table users(
  /* id                INTEGER NOT NULL default nextval('user_id_seq'), */
   id                BIGSERIAL NOT NULL,
   name              VARCHAR(30) not null,
   password          VARCHAR(50)
);

alter table users add constraint users_pk primary key(id);

create table account(
/*   id                INTEGER NOT NULL default nextval('account_id_seq'),*/
   id                BIGSERIAL NOT NULL,
   balance           FLOAT not null default(0.00),
   account_type       VARCHAR(50),
   users_id          BIGINT
);
alter table account  add constraint account_pk primary key(id);

create table record(
/*  id     		    INTEGER NOT NULL default nextval('record_id_seq'),*/
  id                BIGSERIAL NOT NULL,
  type 			    VARCHAR(50),
  amount 		    FLOAT not null,
  date			    timestamp not null,
  description       VARCHAR(100),
  account_id        BIGINT
);
alter table record add constraint record_pk primary key(id);
alter table account
    add constraint account_users_fk foreign key (users_id)
        references users(id);
alter table record
    add constraint record_account_fk foreign key (account_id)
        references account(id);


