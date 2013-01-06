delete from KSMG_MESSAGE where ID = 'cc0a5e8f-ecd0-4865-ab44-a5ce11e44e67'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('cc0a5e8f-ecd0-4865-ab44-a5ce11e44e67','clusetSisCode', 'en', 'clusetmanagement', 'SIS Code', 'cc0a5e8f-ecd0-4865-ab44-a5ce11e44e67', 1)
/
delete from KSMG_MESSAGE where ID = 'a48d4dab-897d-4e11-8eeb-133b3a1a9679'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('a48d4dab-897d-4e11-8eeb-133b3a1a9679','clusetSisCode-instruct', 'en', 'clusetmanagement', 'If this is a Gened or CORE cluset, enter in the code that is used to identify this set in SIS so that the course sync will pull those courses into the set correctly', 'a48d4dab-897d-4e11-8eeb-133b3a1a9679', 1)
/

UPDATE KSMG_MESSAGE SET MSG_VALUE='Select by Proposed Course Number' WHERE MSG_ID='selectByProposedCourse'
/
-- KSLAB 2583 Retire By Proposal Labels
-- retireCourseProposalTitle
delete from KSMG_MESSAGE where ID = '644E21680A474D5BA3E829514EB91BDD'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21680A474D5BA3E829514EB91BDD', 'courseProposedProposalTitle', 'en', 'course', 'Proposal Title', 'AF6D3266E1004ACC90AA44F1D08E97F0', 1) 
/
delete from KSMG_MESSAGE where ID = '37B066108559462F8580112E7165F111'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('37B066108559462F8580112E7165F111', 'courseProposedProposalTitle-instruct', 'en', 'course', 'This title is used for identifying the proposal through the approval process.', '723107AC3E54436A82AEF4775DDBD79D', 1)
/
-- courseProposedRetireInformation - Summary Section Label KSCM-977 KSCM-1744
delete from KSMG_MESSAGE where ID = '83079F0F61B3490F98F9FA327CA36FB8'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('83079F0F61B3490F98F9FA327CA36FB8', 'courseProposedRetireInformation', 'en', 'course', 'Retire Proposal Information', '98B2750755234ADFB7A5BDA611D613FD', 1)
/
-- courseProposedEndTerm
delete from KSMG_MESSAGE where ID = '7EF0BBBAEE6E44CB992932AFE4B196CA'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('7EF0BBBAEE6E44CB992932AFE4B196CA', 'courseProposedEndTerm', 'en', 'course', 'End Term', 'CF8B9C56F75E4EF398699808C165DCE7', 1)
/
delete from KSMG_MESSAGE where ID = '57CAC53F828B47738003FC599543BECE'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('57CAC53F828B47738003FC599543BECE', 'courseProposedEndTerm-instruct', 'en', 'course', 'The last term in which the course can be offered.', '1F37720DC13D4AF9AF50E94CCE6EB8A0', 1)
/
-- courseProposedLastTermOffered
delete from KSMG_MESSAGE where ID = '0C92DCA81A0F49F586AD57E8C12BA480'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('0C92DCA81A0F49F586AD57E8C12BA480', 'courseProposedLastTermOffered', 'en', 'course', 'Last Term Offered', 'A892230858174D28A007F8B4493A68C9', 1)
/
delete from KSMG_MESSAGE where ID = '710EE53064BA4B46854C6642446620D6'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('710EE53064BA4B46854C6642446620D6', 'courseProposedLastTermOffered-instruct', 'en', 'course', 'Select the last term this course was or will be offered, if known.', 'E809074C0FAC48AA89D947CA780B27DB', 1)
/
-- courseProposedLastCatalogYear
delete from KSMG_MESSAGE where ID = '9196B525BD614463AE79C6B3806667A3'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('9196B525BD614463AE79C6B3806667A3', 'courseProposedLastCatalogYear', 'en', 'course', 'Last Course Catalog Publication Year', '16A3A11FF6E54FF5AB55BE5F18B0B813', 1)
/
delete from KSMG_MESSAGE where ID = '488D129E24624EFFAEEF03064B09C77C'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('488D129E24624EFFAEEF03064B09C77C', 'courseProposedLastCatalogYear-instruct', 'en', 'course', 'Last academic year for which this course will be published in the course catalog.', '8C18BFD3190041B295F6C287A2C930FB', 1)
/
-- otherComments
delete from KSMG_MESSAGE where ID = 'CAF783BB01C846298C681EB1FCD4B0B9'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('CAF783BB01C846298C681EB1FCD4B0B9', 'courseOtherComments', 'en', 'course', 'Other Comments', '381EDA4D3A5D4EF5B2ACE26D1CFAD576', 1)
/

-- KSCM-983 KSLAB-2578 Wire in RetireCourseByProposal, button/widget labels
delete from KSMG_MESSAGE where ID = '3D4442F02C504923AEBCC82DA20FA245'
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('3D4442F02C504923AEBCC82DA20FA245', 'retireCourseSubTitle', 'en', 'course', 'Which type of retire action do you want?', 'C0C3601B6BD84A048B31DEDE8250F4FE', 1)
/
delete from KSMG_MESSAGE where ID = 'B8A65FA79D1D4A758C873A28E04966D5'
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('B8A65FA79D1D4A758C873A28E04966D5', 'retireCourseAdmin', 'en', 'course', 'Administrative Retire', '639B27CC80F54C91A038EC34B5073FA9', 1)
/
delete from KSMG_MESSAGE where ID = '7DC0CDB8E21E46539AE8D8A1576FF0F7'
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('7DC0CDB8E21E46539AE8D8A1576FF0F7', 'retireCourseByProposal', 'en', 'course', 'Retire by Proposal', '11A4D017C52C4DE8A69A542967BD2B77', 1)
/
delete from KSMG_MESSAGE where ID = '8AE6CFE94E8A45E89B217C8CA300B0EA'
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) values ('8AE6CFE94E8A45E89B217C8CA300B0EA', 'retireCourseWidgetTitle', 'en', 'course', 'Retire Course', 'AAEBFD9E504646B4886B69483F9565C2', 1)
/
-- KSCM-1685
delete from KSMG_MESSAGE where id = 'd073bc38-8c4d-48b8-b240-35ecea572c79'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d073bc38-8c4d-48b8-b240-35ecea572c79', 'crossListedAndJoints', 'en', 'course', 'Cross Listed and Jointly Offered Courses', '64fcbc4a-fe76-4ed7-b334-ff31ade5a87f', 1)
/
--preventSubmission of more than one retire proposal
--message is customized for UMD based on Mike C text in KSCM-1838
delete from KSMG_MESSAGE where id = '305fb5d3-5f53-456b-9069-3241f57b988e'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('305fb5d3-5f53-456b-9069-3241f57b988e', 'courseProposeRetireSingleProposal', 'en', 'course', 'Unable to submit your proposal to retire a course into workflow. Another user is already working on a proposal to retire this course and only one proposal to retire a course is allowed at a time. To proceed with this retire course proposal, you will need to identify who is working on the proposal and have them cancel it.  Contact the Office of Academic Planning and Programs for more information.', '45ba8f8d-2b9b-4201-8b0d-1eac4585d178', 1)
/
--KSCM-1326
delete from KSMG_MESSAGE where ID = '86A313740B364D2C879D825A0C2E3ABD'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('86A313740B364D2C879D825A0C2E3ABD', 'kuali.lu.lookup.findProgram.advanced.approvedonly:lu.queryParam.luOptionalCode-name', 'en', 'course', 'Program Code', '9D231ED698DF43F08B0452BD95542825', 1)
/

delete from KSMG_MESSAGE where ID = 'ED419EDE0D1641A3A49E16FED6B09684'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ED419EDE0D1641A3A49E16FED6B09684', 'kuali.lu.lookup.findProgram.advanced.proposalsonly:lu.queryParam.luOptionalCode-name', 'en', 'course', 'Program Code', 'C076CBC87643461588B057C0F2A59E71', 1)
/