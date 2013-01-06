--KSCM-1327 New Doc Tool Section Instructions
delete from KSMG_MESSAGE where MSG_ID = 'documentToolInstructions'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('common','7d015911-027e-4b9e-bada-e679617d1a02' ,'en' ,'documentToolInstructions' ,'A syllabus is required for all new courses, except for cases in which the course topics vary with different offerings (such as special topics courses). Use this section to upload the course syllabus. The syllabus must conform to the Guidelines for Syllabi (see: (<a href="http://www.vpac.umd.edu/policies.html#syllabi" target="_blank">http://www.vpac.umd.edu/policies.html#syllabi</a>) or the course will not be approved. Departments are encouraged to use the course proposal process as an opportunity to ensure that syllabi include reminders about academic integrity, information for students with disabilities, early warning grades, and inclement weather policies. A list of recommended components is available through the Office of the Associate Provost for Faculty Affairs (<a href="http://faculty.umd.edu/teach/syllabus.html" target="_blank">http://faculty.umd.edu/teach/syllabus.html</a>).<br><br>
Along with learning outcomes (which should be entered in the Learning Outcomes section of the proposal), VPAC will be particularly interested in these syllabus components: the course assessment policy, required texts and other resource materials; and the pedagogy, format, and structure of the course.<br><br>
Additional documents may also be uploaded.<br>',1)
/
--1810
--#1 Text underneath proposal title field change to:
delete from KSMG_MESSAGE where MSG_ID = 'cluProposalTitle-instruct'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','4d3820f2-d815-4f3c-a42d-ab6e6b98227e' ,'en' ,'cluProposalTitle-instruct' ,'Please include the <b>course prefix, course number, and course title,</b> in the proposal title. The proposal title is used for identifying the proposal through the approval process.',1)
/
-- #2 Remove the question mark/help for proposal title field
delete from KSMG_MESSAGE where MSG_ID = 'cluProposalTitle-help'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','9d80fc31-f10d-4697-80f0-68e3528f4974' ,'en','cluProposalTitle-help','',1)
/
-- #3 Remove the instruct from course title field
delete from KSMG_MESSAGE where MSG_ID = 'courseTitle-instruct'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','313f4aeb-8199-4c96-a506-0cfa0deeeb20' ,'en','courseTitle-instruct','',1)
/
delete from KSMG_MESSAGE where MSG_ID = 'courseProposedProposalTitle-instruct'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','4f49-441f-a586-3a7153513de8' ,'en','courseProposedProposalTitle-instruct','Please include the <b>course prefix, course number, and course title,</b> in the proposal title. The proposal title is used for identifying the proposal through the approval process.',1)
/