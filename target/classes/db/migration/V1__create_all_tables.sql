DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS accountInfo CASCADE;
DROP TABLE IF EXISTS record CASCADE;

DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS account_id_seq;
DROP SEQUENCE IF EXISTS record_id_seq;

CREATE SEQUENCE user_id_seq START WITH 1;
CREATE SEQUENCE account_id_seq START WITH 1;
CREATE SEQUENCE record_id_seq START WITH 1;


create table users(
   id                INTEGER NOT NULL default nextval('user_id_seq'),
   name              VARCHAR(30) not null,
   password          VARCHAR(50)
);

alter table users add constraint users_pk primary key(id);

create table accountInfo(
   id                INTEGER NOT NULL default nextval('account_id_seq'),
   balance           FLOAT not null default(0.00),
   accountType       VARCHAR(50),
   users_id          INTEGER
);
alter table accountInfo  add constraint accountInfo_pk primary key(id);

create table record(
  id     		    INTEGER NOT NULL default nextval('record_id_seq'),
  type 			    VARCHAR(50),
  amount 		    FLOAT not null,
  date			    timestamp not null,
  description       VARCHAR(100),
  accountInfo_id    INTEGER
);
alter table record add constraint record_pk primary key(id);
alter table accountInfo
    add constraint accountInfo_users_fk foreign key (users_id)
        references users(id);
alter table record
    add constraint record_accountInfo_fk foreign key (accountInfo_id)
        references accountInfo(id);


