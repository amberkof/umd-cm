--KSCM-1746 Text change requested for LO-instruct message.
update 	ksmg_message 
set 	msg_value = 'Use this screen to enter your learning outcomes. Each outcome should be in its own row. Use the large text box for text similar to what you would include on a syllabus (maximum 1500 characters per learning outcome). You may categorize outcomes with keywords by using the box on the right to: 1) select existing categories and add them; or 2) create new categories and add them to both the inventory and the outcome in your course.<br/><br/>  
When creating new categories, you will be asked to classify them as related to a skill, subject, or accreditation. Skills are general and unrelated to a specific discipline (e.g., critical thinking, oral communication, mathematical computation). Subjects are discipline related and should be preceded by your department prefix (e.g., ECON - micro, HIST - European, ENGL - Victorian Literature). Accreditation should be used when a category relates to an external accreditation process or agency and should include the accreditor''s abbreviation as well as additional descriptive information (e.g. NCATE - Field Experiences, ABET - Multidisciplinary Teams). Because standards change from time to time, we recommend that you do not use the standard number as the only identifier.<br/><br/>  
Click on the link below to search for existing learning outcome text.' 
where 	msg_id = 'cluLearningObjectives-instruct'
and 	grp_name = 'course' 
and 	locale = 'en'
/

--KSCM-1709
--On lightbox drop-down for copying a course/propsal, changes instances of "Code" to "Prefix"
delete from KSMG_MESSAGE where ID = 'd17d3010-ca8f-455f-bb74-80442529fcce'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d17d3010-ca8f-455f-bb74-80442529fcce','selectByCourseCode', 'en', 'course', 'Select by Course Prefix', 'd17d3010-ca8f-455f-bb74-80442529fcce', 1)
/
delete from KSMG_MESSAGE where ID = 'a420434b-9a72-4679-b9ab-243bb3c9a51c'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('a420434b-9a72-4679-b9ab-243bb3c9a51c','selectByProposedCourse', 'en', 'course', 'Select by Proposed Course Prefix', 'a420434b-9a72-4679-b9ab-243bb3c9a51c', 1)
/

--KSCM-1490
--Changes Experiential Hours/Week to Experiential Learning Hours/Week, and Experiential Credits to Experiential Learning Credits on Course Logistics
delete from KSMG_MESSAGE where ID = 'f507d39c-7ca3-490a-a269-a64981083f98'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('f507d39c-7ca3-490a-a269-a64981083f98','courseExperientialHoursPerWeek', 'en', 'course', 'Experiential Learning Hours/Week', 'f2310af9-0264-4ea9-b1e0-51016a102cee', 1)
/
delete from KSMG_MESSAGE where ID = '8fc0746b-60c0-4840-872a-c47226ed7ed8'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('8fc0746b-60c0-4840-872a-c47226ed7ed8','courseExperientialCredits', 'en', 'course', 'Experiential Learning Credits', '8fc0746b-60c0-4840-872a-c47226ed7ed8', 1)
/
--KSCM-1799
--Messages for user labels upon coursecode validation errors
--The retired active date overlapping error had to be built in java code since it uses a dynamic start date
--In CourseCodeValidatorUMD.java
delete from KSMG_MESSAGE where ID = '4a16c5c5-d057-4039-9d49-08b0facb6c49'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('4a16c5c5-d057-4039-9d49-08b0facb6c49', 'validation.course.preventCourseCodeReuse.draft', 'en', 'validation', 'Another course has already been proposed using this course number. Please choose a different course number. Consult <a href="http://www.testudo.umd.edu/ks/documents.html" target="_blank">User Guide</a> for additional options and information.', '90a29441-ca57-4d02-bec3-dca3cabca906', 1)
/
delete from KSMG_MESSAGE where ID = '9b2a95d3-3717-47c0-af8e-a4b3906a156c'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('9b2a95d3-3717-47c0-af8e-a4b3906a156c', 'validation.course.preventCourseCodeReuse.activeSuspended', 'en', 'validation', 'This course number is already in use. Please choose a different course number.  Consult <a href="http://www.testudo.umd.edu/ks/documents.html" target="_blank">User Guide</a> for additional options and information.', '9cf455c7-a412-4af1-b467-a41bb403269c', 1)
/
--KSCM-527
--Change "Customize This Search" to "Switch to Custom Search"
--Change "Return to Advanced Search" to "Switch to Standard Search"
delete from KSMG_MESSAGE where MSG_ID = 'searchPanelReturnToAdvancedSearch' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','d9fb6681-9279-45d0-8b2e-43193990ec8d' ,'en' ,'searchPanelReturnToAdvancedSearch' ,'Switch to Standard Search',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'searchPanelCustomizeSearch' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
 VALUES ('common' ,'3c1e193a-ba24-45d6-81a2-2708899485f8' ,'en' ,'searchPanelCustomizeSearch' ,'Switch to Custom Search',1)
/
--KSCM-1327, KSLAB-2598
--Update documentTool so it can take custom verbage, update verbage.
delete from KSMG_MESSAGE where MSG_ID = 'documentToolInstructions' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','7d015911-027e-4b9e-bada-e679617d1a02' ,'en' ,'documentToolInstructions' ,'A syllabus is required for all new courses, except for cases in which the course topics vary with different offerings (such as special topics courses). Use this section to upload the course syllabus. The syllabus must conform to the Guidelines for Syllabi (see: (<a href="http://www.vpac.umd.edu/policies.html#syllabi" target="_blank">http://www.vpac.umd.edu/policies.html#syllabi</a>) or the course will not be approved. Departments are encouraged to use the course proposal process as an opportunity to ensure that syllabi include reminders about academic integrity, information for students with disabilities, early warning grades, and inclement weather policies. A list of recommended components is available through the Office of the Associate Provost for Faculty Affairs (<a href="http://faculty.umd.edu/teach/syllabus.html" target="_blank">http://faculty.umd.edu/teach/syllabus.html</a>).<br><br>
Along with learning outcomes (which should be entered in the Learning Outcomes section of the proposal), VPAC will be particularly interested in these syllabus components: the course assessment policy, required texts and other resource materials; and the pedagogy, format, and structure of the course.<br><br>
Additional documents may also be uploaded.<br>',1)
/
--KSCM-1648
--changes instruction text for cross listed and jointly offered course
update 	ksmg_message 
set 	msg_value = 'Enter an existing course or proposed course.' 
where 	msg_id = 'cluCourseNumberOrTitle-instruct'
and 	grp_name = 'course' 
/
update 	ksmg_message 
set 	msg_value = 'Enter an existing course or proposed course.' 
where 	msg_id = 'cluJointOfferings-instruct'
and 	grp_name = 'course'
/
--KSCM-990, KSLAB-1888
--VPAC Block Messages, link text and URLs on Main Screen
delete from KSMG_MESSAGE where MSG_ID = 'vpacScreenName' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','eac36349-f97e-47b7-b8bd-020eeced420b' ,'en' ,'vpacScreenName' ,'Official VPAC Agenda',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacBlockTitle' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','a0391304-d002-44cb-aca6-08c5d7be9b71' ,'en' ,'vpacBlockTitle' ,'VPAC',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacBlockDesc' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','37fe799b-c068-4dfe-8a09-c2e50fc94837' ,'en' ,'vpacBlockDesc' ,'Course Proposal Policy Information',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacAboutLinkText' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','432cc1f4-c08c-41b3-bd7f-81d971dcbf83' ,'en' ,'vpacAboutLinkText' ,'About Vice President''s Advisory Committee (VPAC)',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacAboutLinkURL' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','879825db-f138-4eaf-bcb8-52dd1cd0a125' ,'en' ,'vpacAboutLinkURL' ,'http://www.vpac.umd.edu/about.html',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacUniversityPoliciesLinkText' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','f3d3d5ec-ef77-4648-acb5-0af6dee4598b' ,'en' ,'vpacUniversityPoliciesLinkText' ,'University Course Policies & Practices',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacUniversityPoliciesLinkURL' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','80fde8a2-9454-468d-b55b-ca1c046c64c3' ,'en' ,'vpacUniversityPoliciesLinkURL' ,'http://www.vpac.umd.edu/policies.html',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacRepresentativesLinkText' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','2f8b7828-6004-422c-bfcf-79b93e05c177' ,'en' ,'vpacRepresentativesLinkText' ,'VPAC Representatives',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacRepresentativesLinkURL' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','bd580e2f-0e49-40d4-b357-3e4514723e12' ,'en' ,'vpacRepresentativesLinkURL' ,'http://www.vpac.umd.edu/representatives.html',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacMeetingScheduleLinkText' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','8f65df13-de35-4bfe-b3b9-355f008acc45' ,'en' ,'vpacMeetingScheduleLinkText' ,'VPAC Meeting Schedule & Publication Deadline',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacMeetingScheduleLinkURL' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','e93ccd95-a43e-4118-8247-ec3c6e2f6ba2' ,'en' ,'vpacMeetingScheduleLinkURL' ,'http://www.vpac.umd.edu/schedule.html',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacAgendaLinkText' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','d905ad14-de18-4768-a125-6c9107b3119f' ,'en' ,'vpacAgendaLinkText' ,'Official VPAC Agenda',1)
/
-- KSCM-990
-- adds XLS as one of the possible export format
delete from KSMG_MESSAGE where MSG_ID = 'xlsFormat' 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21681A474D5BA3E829514EB91BDD', 'xlsFormat', 'en', 'common', '.xls (Microsoft Excel)', null, 1) 
/
-- KSCM-1915 update section messages on main page.
delete from KSMG_MESSAGE where MSG_ID = 'recentlyViewedDesc' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','eb1964f2-651f-470b-9f13-ee803c56861a' ,'en' ,'recentlyViewedDesc' ,'View courses and proposals that you have recently visited.',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'viewModifyDesc' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','45aa6c75-7427-44f7-9880-155f85f94bcd' ,'en' ,'viewModifyDesc' ,'Use these links to find courses and proposals. Use Find a Course to modify or retire courses.',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'toolsDesc' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','ab5ced11-3eaa-4cea-8b0b-6d63348f00b7' ,'en' ,'toolsDesc' ,'Watch here for course information and analysis tools which are soon to be implemented.',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'createDesc' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','321147b8-bc9c-4af7-94aa-fd173afa78d3' ,'en' ,'createDesc' ,'Propose new courses by selecting the link below.',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'vpacBlockDesc' 
/

INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','73524b54-1aa6-4092-ba99-4ea8dd05b8ae' ,'en' ,'vpacBlockDesc' ,'Find more information on University course review policies and practices.',1)
/

-- KSCM-1881 change 'Cross List' to 'Cross Listing' on review and modify proposal
delete from KSMG_MESSAGE where MSG_ID = 'cluCrosslistedItem' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','b07aa879-1323-4e4b-8ef6-d50b7cc5cf47' ,'en' ,'cluCrosslistedItem' ,'Cross Listing',1)
/

-- KSCM-1883 Change PCC to Affects Program Curriculum (PCC) on course page. Changed AP and IB to AP and IB Accepted
delete from KSMG_MESSAGE where MSG_ID = 'coursePCCInformation' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','9c2acce3-3733-473b-a16f-a918e3577b05' ,'en' ,'coursePCCInformation' ,'Affects Program Curriculum (PCC)',1)
/

delete from KSMG_MESSAGE where MSG_ID = 'courseAPandIB' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','80dac71f-d205-433b-82a5-d40694079981' ,'en' ,'courseAPandIB' ,'AP and IB Accepted',1)
/