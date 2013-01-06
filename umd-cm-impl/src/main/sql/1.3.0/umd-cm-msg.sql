-- KSCM-1945 Change 'Course Prefix' to 'Course Prefix and/or Number' on course/proposal lightboxes
delete from KSMG_MESSAGE where MSG_ID = 'selectByCourseCode' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','d17d3010-ca8f-455f-bb74-80442529fcce' ,'en' ,'selectByCourseCode' ,'Select by Course Prefix and/or Number',1)
/

delete from KSMG_MESSAGE where MSG_ID = 'selectByProposedCourse' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','a420434b-9a72-4679-b9ab-243bb3c9a51c' ,'en' ,'selectByProposedCourse' ,'Select by Proposed Course Prefix and/or Number',1)
/

-- KSCM-1708
-- Change Code to Prefix on Course Results
delete from KSMG_MESSAGE where MSG_ID = 'lu.resultColumn.luOptionalCode-name' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','a420424b-9072-4679-b9ab-243bb3c9a51c' ,'en' ,'lu.resultColumn.luOptionalCode-name' ,'Course Number',1)
/
-- KSCM-1294
-- SQL code to override "title" property tag to read "Find a Course Proposal"
DELETE from KSMG_MESSAGE where MSG_ID = 'kuali.proposal.lookup.course.advanced-title'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','b07aa879-1329-4e4b-8ef6-d50b7cc5cf47' ,'en' ,'kuali.proposal.lookup.course.advanced-title' ,'Find a Course Proposal',1)
/
 
-- KSCM-526
-- SQL code to override "Name" to "Title" in .xml file for course name. Fixes UI Lightbox result table for finding a course.
DELETE from KSMG_MESSAGE where MSG_ID = 'lu.resultColumn.luOptionalLongName-name'
/

-- while doing KSCM-722, Modified GUID, duplicate from above violates constraint
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','586a0d48-9966-4a56-83b7-803cc100279c' ,'en' ,'lu.resultColumn.luOptionalLongName-name' ,'Title',1)
/

-- KSCM-722
update 	ksmg_message 
set msg_value = 'Select one or more categories...' 
where  	msg_id = 'cluLOCategoryBrowsePopup' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

-- KSCM-1623 part 1
update ksem_enum_val_t
set VAL = 'Student Eligibility and Prerequisite'
-- where id = '5fcd4c7c-e638-4028-8287-68d7bd1f4988'
where cd = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
and enum_key = 'kuali.dependency.course.types'
/

-- KSCM-1623 part 2
update ksem_enum_val_t
set VAL = 'Cross-Listed Courses'
-- where id = '850a28c9-ebfb-4199-8769-6693404ce4af'
where cd = 'crossListed'
and enum_key = 'kuali.dependency.course.types'
/

-- KSCM-524 Overrides .xml title for Find a Course lightbox
DELETE from KSMG_MESSAGE where MSG_ID = 'kuali.lu.lookup.findCourse.advanced-title'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','586a0d48-9966-4a56-83b7-803fc100279c' ,'en' ,'kuali.lu.lookup.findCourse.advanced-title' ,'Find a Course',1)
/

-- KSCM-1974 Overrides .xml property name for title in Find a Proposal lightbox
DELETE from KSMG_MESSAGE where MSG_ID = 'proposal.resultColumn.proposalOptionalName-title'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','586a1d48-9966-4a56-83b7-803fc100279c' ,'en' ,'proposal.resultColumn.proposalOptionalName-title' ,'Title',1)
/

-- KSCM-1974 Overrides .xml property name for course in Find a Proposal lightbox
DELETE from KSMG_MESSAGE where MSG_ID = 'proposal.resultColumn.proposalOptionalCourseCode-name'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','586a1x48-9966-4a56-83b7-803fc100279c' ,'en' ,'proposal.resultColumn.proposalOptionalCourseCode-name' ,'Course Number',1)
/

-- KSCM-2012 Change Text Edit Tools Message
DELETE from KSMG_MESSAGE where MSG_ID = 'toolsDesc'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','ab5ced11-3eaa-4cea-8b0b-6d63348f00b7' ,'en' ,'toolsDesc','Watch here for future course information and analysis tools.' ,1)
/

-- KSCM-2015 Change text on Learning Outcomes Page
DELETE from KSMG_MESSAGE where MSG_ID = 'cluLearningObjectives-instruct'
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','cb56162e-8292-4997-ba75-c54ea0bf47b9' ,'en' ,'cluLearningObjectives-instruct','Use this screen to enter your learning outcomes. Each outcome should be in its own row. Use the large text box for text similar to what you would include on a syllabus (maximum 1500 characters per learning outcome). Learning outcomes are required for new courses (except for courses such as special topics courses where learning outcomes would vary). You have the ability to categorize your learning outcomes with keywords by using the box on the right. You can select existing categories and add them to the learning outcome, or you can create new categories, which will add them to the learning outcome and will also add them to the system''s inventory of learning outcome categories. Please note that the use of learning outcome categories is optional.<br><br>When creating new categories, you will be asked to classify them as related to a skill, subject, or accreditation. Skills are general and unrelated to a specific discipline (e.g., critical thinking, oral communication, mathematical computation). Subjects are discipline related and should be preceded by your department prefix (e.g., ECON - micro, HIST - European, ENGL - Victorian Literature). Accreditation should be used when a category relates to an external accreditation process or agency and should include the accreditor''s abbreviation as well as additional descriptive information (e.g. NCATE - Field Experiences, ABET - Multidisciplinary Teams). Because standards change from time to time, we recommend that you do not use the standard number as the only identifier. <br><br> Click on the link below to search for existing learning outcome text.' ,1)
/
