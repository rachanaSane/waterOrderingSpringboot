create table IF NOT EXISTS WATER_ORDER(ID BIGINT identity primary key , DURATION BIGINT, END_DATE_TIME TIMESTAMP, ORDER_STATUS VARCHAR(255), START_DATE_TIME TIMESTAMP, FARM_NAME VARCHAR(255));
INSERT INTO WATER_ORDER(ID,DURATION, END_DATE_TIME, ORDER_STATUS,START_DATE_TIME,FARM_NAME) VALUES (10000200,3600000, sysdate(), 'REQUESTED', sysdate(), 'testfarm')