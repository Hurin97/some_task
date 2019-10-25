create table par_value (
 id number GENERATED AS IDENTITY PRIMARY key,
 value varchar2(20 CHAR)
 );
 create table par(
 id number GENERATED AS IDENTITY PRIMARY KEY,
 step integer,
 name varchar2(20 CHAR),
 fullname varchar2(30 CHAR),
 comment_ varchar2(150 CHAR),
 isEditable NUMBER(1) /*NOT NULL*/ CHECK (isEditable in (0,1)),
 isScannable NUMBER(1)/*NOT NULL*/ CHECK  (isScannable in (0,1)),
 isVisible NUMBER(1) /*NOT NULL*/ CHECK  (isVisible in (0,1)),
 isRequired NUMBER(1) /*NOT NULL*/ CHECK  (isRequired in (0,1)),
 isPrinted  NUMBER(1) /*NOT NULL*/ CHECK  (isPrinted in (0,1)),
 isValidateOnLine NUMBER(1) /*NOT NULL*/ CHECK  (isValidateOnLine in (0,1)),
 type VARCHAR2(10 char),
 min_length integer,
 max_length integer,
 double_input FLOAT(126),
 value VARCHAR2(20 CHAR),
 reg_exp varchar2(100 CHAR),
 from_debt FLOAT(126)
);
 create table serv (
 serv_id NUMBER GENERATED AS IDENTITY primary key,
 isClosed NUMBER(1) /*NOT NULL*/ CHECK  (isClosed in (0,1)),
 bic INTEGER,
 schet VARCHAR2(20 CHAR),
 corr_schet INTEGER,
 pars number,
 sys_message varchar2(300 CHAR),
 CONSTRAINT fk_par FOREIGN KEY (pars) REFERENCES par(id)
);
create  TABLE  order_ (
 id number GENERATED AS IDENTITY    ,
 services number,
 summa INTEGER,
 CONSTRAINT fk_serv foreign key (services) 
 REFERENCES serv(serv_id)
);




--  drop table order_;
--  drop table serv;
-- drop table par;
--drop table par_value;