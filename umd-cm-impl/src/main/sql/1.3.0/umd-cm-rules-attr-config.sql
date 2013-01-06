-- KSCM-765 
-- Delete Children of unwanted rules
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='<reqCompFieldType=kuali.reqComponent.field.type.gradePoint;reqCompFieldLabel=grade point> in <reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=course>'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='Must have earned a minimum grade point value of 2.0 in math140'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()'
/
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE REQ_COM_TYPE_ID='kuali.reqComponent.type.course.course.no.completed.min'
/
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID='kuali.reqComponent.type.course.course.no.completed.min'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseCluSet.getCluSetAsCode()#{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='<reqCompFieldType=kuali.reqComponent.field.type.gradePoint;reqCompFieldLabel=Grade Point> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Course>'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='Must have earned a minimum grade point value of 2.0 in each of (MATH115, MATH220, MATH140)'
/
DELETE FROM KSST_REQ_COM_TYPE_NL_TMPL WHERE TEMPLATE='minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end'
/
DELETE FROM KSST_STMT_TYP_JN_RC_TYP WHERE REQ_COM_TYPE_ID='kuali.reqComponent.type.course.courseset.no.completed.min'
/
DELETE FROM KSST_RCTYP_JN_RCFLDTYP WHERE REQ_COMP_TYPE_ID='kuali.reqComponent.type.course.courseset.no.completed.min'
/
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY='kuali.reqComponent.type.course.courseset.no.completed.min'
/
-- Delete Unwanted Rules
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY='kuali.reqComponent.type.course.course.no.completed.min'
/
DELETE FROM KSST_REQ_COM_TYPE WHERE TYPE_KEY='kuali.reqComponent.type.course.courseset.no.completed.min'
/
-- Change Wording for Math Eligibility
UPDATE KSST_REQ_COM_TYPE 
SET TYPE_DESC='Must have math eligibility of <eligibility> or higher'
WHERE TYPE_KEY='kuali.reqComponent.type.math.elig'
/

-- Adds the order the rules are going to be sorted in the rules drop down on the Course Proposal Page
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.completed'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('bf61abde-bd4f-4c2a-ba17-f3ae16e2a34d','SORT_ORDER','1','kuali.reqComponent.type.course.completed',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.all'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('ac8f53df-9940-4d14-b33f-5afce884eaa9','SORT_ORDER','2','kuali.reqComponent.type.course.courseset.completed.all',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.one'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('9bcdc55c-e313-4520-a4f9-22d17fd02c1d','SORT_ORDER','3','kuali.reqComponent.type.course.courseset.completed.one',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.grade.min'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('32d410fb-e7ff-4ffe-a99c-cc3fdc8fd3a4','SORT_ORDER','4','kuali.reqComponent.type.course.courseset.grade.min',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.nof.grade.min'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('460bb709-9d57-4849-a95e-f9985fd6f17c','SORT_ORDER','5','kuali.reqComponent.type.course.courseset.nof.grade.min',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.prefix.credits.completed.nof'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('e3b85b4b-a5f0-469d-bb42-05a8ad7ebcdb','SORT_ORDER','6','kuali.reqComponent.type.course.prefix.credits.completed.nof',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.prefix.courses.completed.nof'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('1cccb55d-99be-4934-889c-a918a80c4be3','SORT_ORDER','7','kuali.reqComponent.type.course.prefix.courses.completed.nof',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.credits.completed.nof'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('2bfc7ce3-8d15-4ccf-a304-76de9e7ae93e','SORT_ORDER','8','kuali.reqComponent.type.course.courseset.credits.completed.nof',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.nof'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('db0c8d98-9d98-4c2f-bc64-230c17714e13','SORT_ORDER','9','kuali.reqComponent.type.course.courseset.completed.nof',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.completed.enrolled'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('69a8d2ca-3f86-44d9-a6a3-47141ccd2e13','SORT_ORDER','10','kuali.reqComponent.type.course.completed.enrolled',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.enrolled.all'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('b9788cfa-9514-496b-beb3-b5863cd91652','SORT_ORDER','11','kuali.reqComponent.type.course.courseset.completed.enrolled.all',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.enrolled.one'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('ec859ae2-5b7f-4370-83ea-dc6fad5b4021','SORT_ORDER','12','kuali.reqComponent.type.course.courseset.completed.enrolled.one',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.math.elig'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('ec859ae2-5b7f-4370-k3ea-dc6fad5b4021','SORT_ORDER','13','kuali.reqComponent.type.math.elig',null,'1')
/
-- KSCM-765 changing sort_order from 19 to 14
--
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.enrolled'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('88e5f98b-8fb2-4049-b7a3-24c0ef926d55','SORT_ORDER','14','kuali.reqComponent.type.course.enrolled',null,'1')
/
-- KSCM-765 changing sort_order from 20 to 15
--
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.enrolled.all'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('5519ab8d-9e85-4d92-bee3-02754dfdd057','SORT_ORDER','15','kuali.reqComponent.type.course.courseset.enrolled.all',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.enrolled.nof'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('d342e1b6-01ee-40fc-a245-139437e6461c','SORT_ORDER','21','kuali.reqComponent.type.course.courseset.enrolled.nof',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.permission.org.required.preco'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('a10d1000-a4c1-414a-9063-9a0b79146eb3','SORT_ORDER','22','kuali.reqComponent.type.course.permission.org.required.preco',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.permission.instructor.required.preco'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('843a5946-cbf3-47cf-bd74-13efb77b39ee','SORT_ORDER','23','kuali.reqComponent.type.course.permission.instructor.required.preco',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.content.dept.contact'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('8af7e5dd-f5f4-477c-a60a-74d3a6d14990','SORT_ORDER','24','kuali.reqComponent.type.course.content.dept.contact',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.recommended.completed'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('e175a5d0-ad95-42fb-86ae-af542182330a','SORT_ORDER','25','kuali.reqComponent.type.course.recommended.completed',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.recommended.completed.all'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('2bc43d13-ca00-45a8-b3bc-e9a2f2e22b7d','SORT_ORDER','26','kuali.reqComponent.type.course.courseset.recommended.completed.all',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.recommended.completed.one'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('9d133c10-d248-4575-a44a-cf3776d1db4d','SORT_ORDER','27','kuali.reqComponent.type.course.courseset.recommended.completed.one',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.program.admitted'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('44b7cfae-9b7c-414d-b326-5273b2740d5f','SORT_ORDER','28','kuali.reqComponent.type.course.program.admitted',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.major.org'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('4aa699f4-c805-4283-a087-098375a1eb9f','SORT_ORDER','29','kuali.reqComponent.type.major.org',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.standing'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('4c7abe27-c417-4b08-89b4-934bdf8ef235','SORT_ORDER','30','kuali.reqComponent.type.course.standing',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.credits.min'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('dda1937f-746d-4ea7-bd62-26ac30245d5b','SORT_ORDER','31','kuali.reqComponent.type.course.credits.min',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.cumulative.gpa.min'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('82425c03-3109-4468-9a53-aa139992fb77','SORT_ORDER','32','kuali.reqComponent.type.course.cumulative.gpa.min',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.program.cumulative.gpa.min'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('f73ee8a1-e01c-4cd4-b679-9f5afebc7bc0','SORT_ORDER','33','kuali.reqComponent.type.program.cumulative.gpa.min',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.notcompleted'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('aa082566-4db8-4c1e-b04e-993920a94962','SORT_ORDER','34','kuali.reqComponent.type.course.notcompleted',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.completed.none'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('74df30cb-3ed1-4874-afdd-444a3b31c98b','SORT_ORDER','35','kuali.reqComponent.type.course.courseset.completed.none',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.program.notadmitted'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('e937a08a-0bb6-4fc1-8f24-5c14bf2d8e2f','SORT_ORDER','36','kuali.reqComponent.type.course.program.notadmitted',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.courseset.grade.max'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('d05ba6f6-6e8b-474b-a7e7-4f9c5822d83a','SORT_ORDER','37','kuali.reqComponent.type.course.courseset.grade.max',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.permission.org.required'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('47d95db6-45df-4ac6-81a1-3e3be845e56b','SORT_ORDER','38','kuali.reqComponent.type.course.permission.org.required',null,'1')
/
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.permission.instructor.required'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('bbb3bdb5-c3f9-4759-93a1-a0011048decb','SORT_ORDER','39','kuali.reqComponent.type.course.permission.instructor.required',null,'1')
/
-- Free form option must stay at 100 as it will always be on the bottom of the dropdown
DELETE FROM KSST_REQ_COM_TYPE_ATTR WHERE OWNER='kuali.reqComponent.type.course.freeform.text'
/
INSERT INTO KSST_REQ_COM_TYPE_ATTR (ID,ATTR_NAME,ATTR_VALUE,OWNER,OBJ_ID,VER_NBR)
VALUES ('b9b02511-7cb9-495a-a6cf-b925f6033a4c','SORT_ORDER','100','kuali.reqComponent.type.course.freeform.text',null,'1')
/
