delete from KSLR_RESCOMP_TYPE where type_key = 'kuali.resultComponentType.credit.degree.multiple'
/

update	KSLR_RESCOMP_TYPE 
set 	name = 'Fixed' 
where 	type_key = 'kuali.resultComponentType.credit.degree.fixed'
/

update 	KSLR_RESCOMP_TYPE 
set 	name = 'Variable' 
where 	type_key = 'kuali.resultComponentType.credit.degree.range'
/

delete from KSLR_RESULT_VALUE where ID = 'A+'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('A+', 'A+', 'kuali.resultComponent.grade.letter', 'cf61cd2e-23ac-45c3-85e3-efc59d3325c8', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'A-'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('A-', 'A-', 'kuali.resultComponent.grade.letter', '8e4fff94-480e-4679-a688-8257c1225936', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'B+'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('B+', 'B+', 'kuali.resultComponent.grade.letter', '41b0f599-b143-413a-83fa-50e5c5a516e2', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'B-'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('B-', 'B-', 'kuali.resultComponent.grade.letter', '7536105c-a3f8-4169-a26d-58f077d32feb', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'C+'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('C+', 'C+', 'kuali.resultComponent.grade.letter', '20bc5f9b-a68c-411c-9e56-3b6c5b615459', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'C-'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('C-', 'C-', 'kuali.resultComponent.grade.letter', 'd6ff18c3-f4bf-4cd6-9d2b-71444049e878', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'D+'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('D+', 'D+', 'kuali.resultComponent.grade.letter', '9cce6742-7620-4e20-9431-3e19a7a3332b', 0)
/
delete from KSLR_RESULT_VALUE where ID = 'D-'
/
insert into KSLR_RESULT_VALUE (ID, VALUE, RSLT_COMP_ID, OBJ_ID, VER_NBR) values ('D-', 'D-', 'kuali.resultComponent.grade.letter', '1c1080b3-46a5-4133-821a-aec91a5a52d1', 0)
/