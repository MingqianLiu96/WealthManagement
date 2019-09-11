DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS records CASCADE;

DROP SEQUENCE IF EXISTS customer_id_seq;
DROP SEQUENCE IF EXISTS account_id_seq;
DROP SEQUENCE IF EXISTS record_id_seq;

CREATE SEQUENCE customer_id_seq START WITH 1;
CREATE SEQUENCE account_id_seq START WITH 1;
CREATE SEQUENCE record_id_seq START WITH 1;


create table customers(
  /* id                INTEGER NOT NULL default nextval('customer_id_seq'), */
   id                BIGSERIAL NOT NULL,
   name              VARCHAR(30) not null,
   password          VARCHAR(50)
);

alter table customers add constraint customer_pk primary key(id);

create table accounts(
/*   id                INTEGER NOT NULL default nextval('account_id_seq'),*/
   id                BIGSERIAL NOT NULL,
   balance           FLOAT not null default(0.00),
   account_type       VARCHAR(50),
   customer_id          BIGINT
);
alter table accounts  add constraint account_pk primary key(id);

create table records(
/*  id     		    INTEGER NOT NULL default nextval('record_id_seq'),*/
  id                BIGSERIAL NOT NULL,
  type 			    VARCHAR(50),
  amount 		    FLOAT not null,
  date			    timestamp not null,
  description       VARCHAR(100),
  account_id        BIGINT
);
alter table records add constraint record_pk primary key(id);
alter table accounts
    add constraint account_customer_fk foreign key (customer_id)
        references customers(id);
alter table records
    add constraint record_account_fk foreign key (account_id)
        references accounts(id);


