delete from KSMG_MESSAGE where ID = '0653abfd-e591-457d-a0c6-2fe6b09ed40b'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('0653abfd-e591-457d-a0c6-2fe6b09ed40b', 'lo.queryParam.loDescPlain-name', 'en', 'course', 'Keyword in Learning Outcome', '0AD63445-0626-75FA-08D4-219C5E1ABFC1', 1)
/
delete from KSMG_MESSAGE where ID = '48879ae1-7656-40a9-b6c6-bb5d95ae4507'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('48879ae1-7656-40a9-b6c6-bb5d95ae4507', 'lo.queryParam.loOptionalCategoryName-name', 'en', 'course', 'Learning Outcome Category', 'D4EE9911-6F17-86CB-A1A9-9741D3DF8855', 2)
/
delete from KSMG_MESSAGE where ID = 'd02bb835-d842-43af-b129-430faaaf7d55'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d02bb835-d842-43af-b129-430faaaf7d55', 'lu.queryParam.luOptionalCode-name', 'en', 'course', 'Course Prefix and/or Number (for example, ENGL101)', '12EB0495-3E25-808D-9472-DC21797EEF6A', 1)
/
delete from KSMG_MESSAGE where ID = 'd97dad38-b7af-43d8-8d42-72e73cc48701'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d97dad38-b7af-43d8-8d42-72e73cc48701', 'lu.queryParam.luOptionalStudySubjectArea-name', 'en', 'course', 'Course Area Prefix', 'EC9C3413-B9E3-76E4-B52C-0EACEC4A2B58', 1)
/
delete from KSMG_MESSAGE where ID = 'b4418cad-41ee-4341-88a4-b6e9dfbcaaf2'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b4418cad-41ee-4341-88a4-b6e9dfbcaaf2', 'kuali.lu.lookup.advanced.learningObjectives.byCourseAndProgram-title', 'en', 'course', 'Learning Outcome', 'AB93E524-3BD2-9628-8915-8369A11ABCED', 1)
/
delete from KSMG_MESSAGE where ID = '0cfbd523-9342-4fba-80d2-fff62a34e127'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('0cfbd523-9342-4fba-80d2-fff62a34e127', 'kuali.lu.lookup.advanced.learningObjectives-name', 'en', 'course', 'Search for Learning Outcomes', '20F1492B-761D-37F9-D17D-2AC27FB79840', 2)
/
delete from KSMG_MESSAGE where ID = '7efb1e4d-e0da-489a-a7be-2e808edb8a87'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('7efb1e4d-e0da-489a-a7be-2e808edb8a87', 'cluLearningResultAudit-help', 'en', 'course', 'Audit is a registration option for students who wish to experience a course without formal assessment. It implies neither attendance nor any other effort in the course. A student may register to audit a course which has been designated as available under the audit option and in which space is available. The notation of AUD will be placed on the transcript for each course audited.', '406F256E-E7A3-6285-DCA7-558F8569B1BF', 1)
/
delete from KSMG_MESSAGE where ID = 'c5ef837b-19af-4572-b779-9c9f298e6603'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('c5ef837b-19af-4572-b779-9c9f298e6603', 'cluLearningResultPassFail-help', 'en', 'course', 'Pass-Fail is an undergraduate registration option which allows eligible students to have their course grade displayed as Pass or Fail on the academic record based on the submitted grade of the instructor.', 'DEA78669-13E7-B190-2A02-E172C99E22E4', 1)
/
delete from KSMG_MESSAGE where ID = '3a6cdf51-b788-4cea-b499-0f19d90bc645'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('3a6cdf51-b788-4cea-b499-0f19d90bc645', 'cluLearningResultStudentRegistration-instruct', 'en', 'course', 'Specify any options with which the course may be offered. It is recommended that departments include all registration options when seeking approval for a course.', '13E13E27-F444-44D2-4573-232379EA5760', 1)
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcome Categories'
where 	msg_id = 'loCategories'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcomes'
where 	msg_id = 'cluLearningObjectives'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcome'
where 	msg_id = 'cluLearningObjective'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Course Prefix'
where 	msg_id = 'cluSubjectCode'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = '${maxLength} characters max for each Learning Outcome'
where 	msg_id = 'cluLOInstructions'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Use this tool to build a bullet-point style outline with a maximum of ${maxLength} characters for each learning outcome.<br/>Or click the following link to search for existing learning outcomes.'
where 	msg_id = 'cluLearningObjectives-instruct'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Allow students to select audit option.'
where 	msg_id = 'cluLearningResultAuditText'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Pass-Fail'
where 	msg_id = 'cluLearningResultPassFail'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Allow students to select pass-fail option.'
where 	msg_id = 'cluLearningResultPassFailText'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Transcript Title' 
where  	msg_id = 'cluShortTitle' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

update  ksmg_message
set	    msg_value = 'Reason for Proposal'
where   msg_id = 'cluProposalRationale'
and		grp_name = 'course'
and		locale = 'en'
/

delete from KSMG_MESSAGE where ID = '9acbabdd-c34b-4a8f-8088-da6d63a32bce'
/
INSERT INTO KSMG_MESSAGE  (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('9acbabdd-c34b-4a8f-8088-da6d63a32bce','cluLearningResultGradesAssessments-instruct','en','course','How will you grade this course? Please see the <a href="http://vpac.umd.edu/policies.html" target="_blank">Course Policies and Practices</a> page for additional information.','6D1514C9-02A0-1E65-544C-E5C4D482D51C','1')
/

update 	ksmg_message 
set 	msg_value = 'Grading Method' 
where  	msg_id = 'cluLearningResultGradesAssessments' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

delete from KSMG_MESSAGE where ID = '5b5cd7fc-b72f-4ba7-80ac-1f9b864dbbc0'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('5b5cd7fc-b72f-4ba7-80ac-1f9b864dbbc0', 'remainingChars', 'en', 'common', 'characters left', '165E3CAA-BE63-BB3A-0704-19E4F3A3225A', 2)
/

update 	ksmg_message 
set 	msg_value = 'This will be used in evaluating the proposal. It will not be published in the catalog.<br>Include a list of programs in which this course will be used.' 
where  	msg_id = 'cluProposalRationale-instruct'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update  ksmg_message 
set     msg_value = 'Description and Reason for Proposal' 
where   msg_id = 'cluProposalTitleSection'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Credit Options' 
where  	msg_id = 'cluLearningResultOutcomeType' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'List any unit which has the authority to administer of offer this course in addition to the Curriculum Oversight unit.' 
where 	msg_id = 'cluAdminOrg-instruct' 
and 	locale = 'en'
and 	grp_name = 'course'
/

update ksmg_message 
set 	msg_value = 'Additional Scheduling Unit' 
where 	msg_id = 'cluAdminOrg' 
and 	locale = 'en'
and 	grp_name = 'course'
/

update 	ksmg_message 
set 	msg_value = 'Curriculum Oversight Unit' 
where 	msg_id = 'cluCurriculumOversight' 
and 	locale = 'en'
and 	grp_name = 'course'
/

update ksmg_message 
set 	msg_value = 'Cross List, Offer Jointly' 
where 	msg_id = 'cluCrossListedVersionJoin' 
and 	locale = 'en'
and 	grp_name = 'course'
/

update ksmg_message
set msg_value='This is the unit(s) responsible for the content of the program. This selection will determine the workflow/approval process for this proposal.'
where msg_id='cluCurriculumOversight-instruct'
/

update 	KSLR_RESCOMP 
set 	name = 'Satisfactory/Fail' 
where 	name = 'Satisfactory/unsatisfactory'
/

update 	ksmg_message 
set 	msg_value = ' ' 
where 	msg_id = 'cluLOInstructions' 
and 	locale = 'en'
and 	grp_name = 'course'
/

update  ksmg_message 
set     msg_value = 'Credit Option(s)' 
where   msg_id = 'cluLearningResultOutcomes'
and     grp_name = 'course' 
and     locale = 'en'
/

update  ksmg_message 
set     msg_value = 'Credit Option' 
where   msg_id = 'cluLearningResultOutcome'
and     grp_name = 'course' 
and     locale = 'en'
/

update  ksmg_message 
set     msg_value = 'Add Additional Learning Outcome' 
where   msg_id = 'cluAddLOs'
and     grp_name = 'course' 
and     locale = 'en'
/

update  ksmg_message
set     msg_value = 'My Action List'
where   msg_id = 'actionList'
/

delete from KSMG_MESSAGE where ID = 'd15f6ed6-9c78-49ab-a26a-adf8fd30774a'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d15f6ed6-9c78-49ab-a26a-adf8fd30774a', 'cluLOCategoryBrowse', 'en', 'course', 'Browse for categories', '35D03F03-EEFC-B979-A43C-E5A9D80F31CE', 1)
/
delete from KSMG_MESSAGE where ID = 'bcf56fa7-c770-4c8a-a7b7-ee376030e1d3'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('bcf56fa7-c770-4c8a-a7b7-ee376030e1d3', 'cluLOCategoryBrowsePopup', 'en', 'course', 'Browse for categories', '427D3BAF-60D3-FE6F-12C7-BBF77B02AC80', 1)
/
delete from KSMG_MESSAGE where ID = '33147994-d116-495a-99a5-c4da5e190c70'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('33147994-d116-495a-99a5-c4da5e190c70', 'validation.validChars.credit.option.numeric', 'en', 'validation', 'Can only contain a value in one of the following formats: 0 or .5 or 1 or 1.0 or 1.5', '3035C737-B2E0-70A9-E2C1-7A629C9919E3', 1)
/

update 	KSMG_MESSAGE 
set 	MSG_ID='validation.validChars.gpa9.99'
where 	LOCALE='en' 
and  	MSG_ID='validation.validChars.gpa9.9' 
and  	MSG_VALUE='Can only contain a GPA in the form 9.9.' 
and 	GRP_NAME='validation'
/
 
update 	KSMG_MESSAGE 
set 	MSG_VALUE='Can only contain a GPA in the form 9.99.'
where 	LOCALE='en' 
and  	MSG_ID='validation.validChars.gpa9.99' 
and 	GRP_NAME='validation'
/
   
UPDATE KSST_STMT_TYPE 
SET NAME = 'Recommended' 
where TYPE_KEY = 'kuali.statement.type.course.recommendedPreparation'
/

update 	KSMG_MESSAGE 
set 	MSG_VALUE='Testudo'
where 	LOCALE='en' 
and  	MSG_ID='applicationTitleLabel' 
and 	GRP_NAME='common'
/

update 	ksmg_message 
set 	msg_value = 'Modify Course' 
where 	msg_id = 'cluModifyItem'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Retire Course' 
where 	msg_id = 'cluRetireItem'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'This will be used in evaluating the proposal. It will not be published in the catalog. Include a list of programs in which this course will be used.' 
where 	msg_id = 'cluProposalRationale-instruct'
and     grp_name = 'course' 
and     locale = 'en'
/

delete from KSMG_MESSAGE where ID = 'ac18b23a-0f02-40b2-9f72-d44e65c19b51'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ac18b23a-0f02-40b2-9f72-d44e65c19b51', 'additionalCourseInformation', 'en', 'course', 'Additional Course Information', 'afb0d601-95f7-426e-aaf1-e4842724a675', 1)
/

delete from KSMG_MESSAGE where ID = 'e9e2e254-8f22-41f9-87f2-b8d983d8f131'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('e9e2e254-8f22-41f9-87f2-b8d983d8f131', 'additionalCourseInformation-instruct', 'en', 'course', 'Provide additional information that is important to know about this course other than<br/> its description and requisites. This information will be published in the catalog.', '1ff10ac3-302f-453b-9e4a-067a7bd19512', 1)
/
delete from KSMG_MESSAGE where ID = 'cc2939b5-fc2e-42d7-9f91-b45af98c38cb'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('cc2939b5-fc2e-42d7-9f91-b45af98c38cb', 'lo.resultColumn.loCluCode-name', 'en', 'course', 'Course', 'b60cb61c-ac3a-42f2-b7e4-a045038214af', 1)
/
delete from KSMG_MESSAGE where ID = 'fe73120e-507d-424a-bc7d-cb4384d22a61'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('fe73120e-507d-424a-bc7d-cb4384d22a61', 'lo.resultColumn.categoryName-name', 'en', 'course', 'Learning Outcome Category', '5ed09b26-74ea-40d6-a2a4-28285a9995ba', 1)
/
delete from KSMG_MESSAGE where ID = '97c4b10c-12cd-4769-86d2-cfa318e189c9'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('97c4b10c-12cd-4769-86d2-cfa318e189c9', 'lo.resultColumn.loDescPlain-name', 'en', 'course', 'Learning Outcome', 'dd991139-c877-4eb5-a15a-f0dbdff738b7', 1)
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcomes' 
where 	msg_id = 'program.menu.sections.learningObjectives'
and     grp_name = 'program' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Search for word in Learning Outcomes' 
where 	msg_id = 'cluLOWordSearch'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcomes' 
where 	msg_id = 'los'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Search for Learning Outcomes' 
where 	msg_id = 'cluLOSearch'
and     grp_name = 'course' 
and     locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Learning Outcomes' 
where 	msg_id = 'lo.title'
and     grp_name = 'program' 
and     locale = 'en'
/

delete from KSMG_MESSAGE where ID = '3bf66599-5f4a-4528-80cd-d8245a1f57db'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('3bf66599-5f4a-4528-80cd-d8245a1f57db', 'cluAuthorsAndCollaborators', 'en', 'course', 'Collaborators', '8597182f-12f7-4212-8929-5b203d2d04aa', 1)
/

update 	ksmg_message 
set 	msg_value = 'Must be in one of the following formats: # or #.5' 
where 	msg_id = 'validation.validChars.credit.option.numeric'
and     grp_name = 'validation' 
and     locale = 'en'
/

delete from KSMG_MESSAGE where ID = 'a1cd179c-6589-4295-b205-a9e2ca391d59'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('a1cd179c-6589-4295-b205-a9e2ca391d59', 'continue', 'en', 'course', 'Continue', 'b6fb3c47-d8f3-4be9-951f-a803431bd6fb', 1)
/
-- KSCM-950
update 	ksmg_message 
set 	msg_value = 'Cross List, Jointly Offer, Formerly, and Credit Only Granted For'
where 	msg_id = 'cluCrossListedVersionJoin'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Cross List'
where 	msg_id = 'cluCrosslistedItem'
and 	grp_name = 'course' 
and 	locale = 'en'
/

update KSLR_RESCOMP 
set name = 'Regular (Letter)'
where id = 'kuali.resultComponent.grade.letter'
/

delete from KSMG_MESSAGE where ID = '47c27136-4553-49de-b9d0-0dc198aa6c70'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('47c27136-4553-49de-b9d0-0dc198aa6c70', 'courseRetirementComment', 'en', 'course', 'Other Comments', 'de7b30a8-340b-4d57-a810-cb9001e1ff33', 1)
/

delete from KSMG_MESSAGE where ID = '4d52bed8-2944-40c5-b461-e2246c6db34c'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('4d52bed8-2944-40c5-b461-e2246c6db34c', 'additionalCourseInformation-examples', 'en', 'course', '- May fulfill a requirement for General Education only if taken concurrently with X course.<br/>- Priority in enrollment will be given to students in X program.<br/>- X course must be taken in the following semester.<br/>- One class per week will be held at the Library of Congress.', '68632e91-f9ed-4ef1-a16a-ca4c05781f90', '1')
/

delete from KSMG_MESSAGE where ID = '4f46f554-bf0d-43cb-925d-4940aeeb7c29'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('4f46f554-bf0d-43cb-925d-4940aeeb7c29', 'cluLearningResultStudentRegistration-help', 'en', 'course', 'If a course has been approved with pass-fail and audit registration options, a department can determine on a term-by-term basis whether to schedule the course with those options.', '50484007-E0C6-D30A-6893-FD8BDB0328CE', 1)
/

delete from KSMG_MESSAGE where ID = '6bfac7fb-0b87-49ca-b41a-2e4808de0f28'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('6bfac7fb-0b87-49ca-b41a-2e4808de0f28', 'courseRepeatCredits', 'en', 'course', 'Repeat Credits', '16099805-9FB0-E5AD-1FD1-B8072E816043', 1)
/
delete from KSMG_MESSAGE where ID = '5f8c8e8c-2536-40bf-ae81-d9d46fa8f6f4'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('5f8c8e8c-2536-40bf-ae81-d9d46fa8f6f4', 'courseRepeatCredits-instruct', 'en', 'course', 'Repeatable courses must use a course number that ends in "8" or "9", e.g., FMST 498.', 'CD7ADEBA-51BC-FB64-4707-7FBF69200490', 1)
/
delete from KSMG_MESSAGE where ID = 'b8819b5a-2f19-4ea2-80f3-4f770cd92388'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b8819b5a-2f19-4ea2-80f3-4f770cd92388', 'repeatableIfContentDiffersText', 'en', 'course', 'Only if content differs.', '5F530C85-D77F-EFA3-F06C-453E5B1EED38', 1)
/
delete from KSMG_MESSAGE where ID = '6c08e75d-9c03-4dda-9b2d-9eef53c19c84'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('6c08e75d-9c03-4dda-9b2d-9eef53c19c84', 'courseRepeatCreditsText', 'en', 'course', 'Allow students to repeat for up to', '832A58A2-58EB-8838-6078-E02EFDCF5EDB', 1)
/
delete from KSMG_MESSAGE where ID = '18afed4f-bc30-4ee4-8654-16c0953ffcf2'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('18afed4f-bc30-4ee4-8654-16c0953ffcf2', 'repeatableNumCreditsText', 'en', 'course', 'credits.', '03DB6E39-BF5C-0739-8977-5E136C726098', 1)
/

delete from KSMG_MESSAGE where ID = 'ee74623c-28d7-4b3d-ab1b-b7bf99246f88'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ee74623c-28d7-4b3d-ab1b-b7bf99246f88', 'courseGoverance', 'en', 'course', 'Course Governance', '6666CB42-F577-52BC-4A34-68821D058495', 1)
/

update ksmg_message set msg_value = '' where msg_id = 'cluCourseNumber-help'
/

delete from KSMG_MESSAGE where ID = 'b9567b6b-8fa2-46b9-81cb-794694cbb6c3'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b9567b6b-8fa2-46b9-81cb-794694cbb6c3', 'cluCurriculumOversight-help', 'en', 'course', 'If a course prefix has already been selected, the curriculum oversight unit will be set to the unit that has responsibility for courses with that prefix. The curriculum oversight unit setting will determine the approval process for this proposal.', 'D68E3020-207A-E4CF-CA63-6CED410415B0', 1)
/

delete from KSMG_MESSAGE where ID = 'cc00c66a-1ada-4a2f-9056-97f9d152514b'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('cc00c66a-1ada-4a2f-9056-97f9d152514b', 'cluCourseFormerly', 'en', 'course', 'Formerly', '7AF92F1C-5F3D-1F82-43ED-507B416D1BDB', 1)
/
delete from KSMG_MESSAGE where ID = '2c530d89-0e3f-4da3-b2c7-56637263bee0'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('2c530d89-0e3f-4da3-b2c7-56637263bee0', 'cluCourseFormerly-instruct', 'en', 'course', 'If this course was previously offered using a different course prefix and/or number, identify the former course information below.', '1696B692-E28A-E22D-C8D5-FA4A7D467FB6', 1)
/
delete from KSMG_MESSAGE where ID = '7c8d4852-ea59-4d97-b4ff-0cff78e8b995'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('7c8d4852-ea59-4d97-b4ff-0cff78e8b995', 'cluCourseFormerly-help', 'en', 'course', 'If this course was previously offered in a pilot format (soft numbered) using a Special Topics course, identify the course prefix, number and suffix.', '2045F3F4-DC6E-9EEB-5CA7-FCE9BB5432FE', 1)
/

-- KSCM-950
delete from KSMG_MESSAGE where ID = '6E83628D75B44306B13CFE7F048B8196'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('6E83628D75B44306B13CFE7F048B8196', 'creditGrantedFor', 'en', 'course', 'Credit Only Granted For', '9DD17F110F5C4DDE97C596C522755A26', 1)
/
delete from KSMG_MESSAGE where ID = '62CAC76B2ABC400EA0961DA73A308014'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('62CAC76B2ABC400EA0961DA73A308014', 'creditGrantedFor-instruct', 'en', 'course', 'Credit will only be granted for one of the following courses.', '053C7CE24E7A42F1985103D71120D91C', 1)
/

delete from KSMG_MESSAGE where ID = 'b1679c7b-9dc3-4015-b7e1-f9ee772e4748'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b1679c7b-9dc3-4015-b7e1-f9ee772e4748', 'coursePrefixNumberLabel', 'en', 'course', 'Course Prefix/Number', '0C10B6D0-F2FE-C56D-B482-CAD6FD350AAD', 1)
/
delete from KSMG_MESSAGE where ID = '760e85ad-1734-4219-8c78-8852d4c299a7'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('760e85ad-1734-4219-8c78-8852d4c299a7', 'repeatableNumCreditsLabel', 'en', 'course', 'Repeatable for Credit', '7EFB103F-186A-1838-F7CC-DB5D19819FC4', 1)
/

delete from KSMG_MESSAGE where ID = '7308cca9-2c43-4dac-8339-edeaea4def4d'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('7308cca9-2c43-4dac-8339-edeaea4def4d', 'cluLearningResultOutcomeType-help', 'en', 'course', '<p>This value determines how you will state the Total Credits for this proposed course. This credit value will appear in the course catalog, but can vary for specific course offerings in the case of variable credits.</p><p>Fixed credit courses have a set credit value in the catalog and will always be offered for registration for that credit value.</p><p>Variable credit courses have a range of credit values in the catalog (i.e. 1 - 3 credits) and can be offered for registration for any of the credit values, depending on how the department wants to offer the course in a particular registration period.</p>', 'dbad9d2e-ff94-4f46-a303-f7f5eb4945be', 1)
/

update 	ksmg_message 
set 	msg_value = 'Total Credits' 
where  	msg_id = 'cluLearningResultsOutcomeFixedCredits' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Minimum Credits' 
where  	msg_id = 'cluLearningResultsOutcomeMinCredits' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

update 	ksmg_message 
set 	msg_value = 'Maximum Credits' 
where  	msg_id = 'cluLearningResultsOutcomeMaxCredits' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

delete from KSMG_MESSAGE where ID = '3eec0df0-456a-42f4-af2c-b8a6f9d859c0'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('3eec0df0-456a-42f4-af2c-b8a6f9d859c0', 'minimumContactHours', 'en', 'course', '(Minimum ${0} contact hours)', '55de72e4-b720-4048-8cc7-13441733831c', 1)
/

delete from KSMG_MESSAGE where ID = '507869ad-4d9f-437e-9478-a0b272b9560f'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('507869ad-4d9f-437e-9478-a0b272b9560f', 'validation.minCredits', 'en', 'validation', 'Minimum Credits cannot be equal to or greater than Maximum Credits', '655ccb01-effb-4331-b7ba-7f9db10bd0cd', 1)
/

delete from KSMG_MESSAGE where ID = 'b399a4ea-7a62-47a2-a26c-7ea70ce26348'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b399a4ea-7a62-47a2-a26c-7ea70ce26348', 'creditInfo', 'en', 'course', 'Credit Information', '00d69877-2704-4380-8a65-2869026ffc2b', 1)
/

delete from KSMG_MESSAGE where ID = 'b1e67811-b48d-423a-8b7a-18e79b89ac70'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b1e67811-b48d-423a-8b7a-18e79b89ac70', 'cluJointOfferingsDescription', 'en', 'course', 'A jointly offered course refers to two or more courses that may "meet with" one another with the same meeting location, day/time, and instructor when deployed. However, the title, description, and learning outcomes for each course may differ. This generally occurs when the courses cover a common subject area.', '920A5D28-C2E9-22D9-D06A-BD1F774F2B9D', 1)
/

delete from KSMG_MESSAGE where ID = '86a0166c-1dd4-42d2-8511-799aae5d55e7'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('86a0166c-1dd4-42d2-8511-799aae5d55e7', 'cluCrossListedDescription', 'en', 'course', 'A cross listed course refers to a single course which is offered using multiple course prefix and/or numbers. All offerings of this course across the cross listed relationship must have the same title, description, and learning outcomes. (Suffixed courses cannot be cross listed at the curricular level)', '6F317B64-4089-78FB-62B8-5DF9072DA987', 1)
/


delete from KSMG_MESSAGE where ID = 'ba43500d-d4bc-4ad1-b348-628642334629'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('ba43500d-d4bc-4ad1-b348-628642334629', 'commitmentPerWeek', 'en', 'course', 'Hour Commitment per Week', 'a3c720e1-b584-442d-bc33-95a2302fa64b', 1)
/

delete from KSMG_MESSAGE where ID = 'a8cd107d-260e-4dbc-b074-6bb5204a5b53'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('a8cd107d-260e-4dbc-b074-6bb5204a5b53', 'commitmentPerWeek-help', 'en', 'course', 'The University of Maryland follows the Maryland Higher Education Commission''s policies on "contact hours," a shorthand term referring to the amount of time required in various forms of education to earn credit ', '2b16acca-7261-4c9f-b4b1-b4944cc0d61d', 1)
/

delete from KSMG_MESSAGE where ID = 'fa97c670-7cf2-4771-bb16-628d363a8d50'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('fa97c670-7cf2-4771-bb16-628d363a8d50', 'commitmentPerWeek-instruct', 'en', 'course', 'Enter the hours per week for each activity type.  For more information on contact hours see the <a href="http://vpac.umd.edu/policies.html" target="_blank">Course Policies and Practices</a>.', '87d0df31-06c0-48fe-a2e2-1a26e5e9a617', 1)
/

delete from KSMG_MESSAGE where ID = 'f0e38420-b8a5-40f0-a736-10a05312f914'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('f0e38420-b8a5-40f0-a736-10a05312f914', 'semesterType-instruct', 'en', 'course', 'Choose either standard or nonstandard semester.', '73319262-3ce5-430a-ba83-0ef35dad941f', 1)
/
delete from KSMG_MESSAGE where ID = '17ceeffc-3532-4fc5-95cc-8e991099d785'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('17ceeffc-3532-4fc5-95cc-8e991099d785', 'semesterType', 'en', 'course', 'Semester Type', '4fcaa0c1-79c1-4f44-a363-4db840f13a9d', 1)
/

delete from KSMG_MESSAGE where ID = 'bce39aae-6ffe-46fb-89fc-bc402d3f257e'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('bce39aae-6ffe-46fb-89fc-bc402d3f257e', 'activityTypeExplanation', 'en', 'course', 'Explanation', 'CFB2D0A6-DFA8-CD37-5A7C-A13746A36443', 1)
/
delete from KSMG_MESSAGE where ID = 'd1b327c8-c067-4034-be5a-4db19dee0912'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d1b327c8-c067-4034-be5a-4db19dee0912', 'activityTypeExplanation-instruct', 'en', 'course', 'Provide information about how you will offer the course.', '057600DA-C4AE-5FF6-E9A6-F2837AC1BC40', 1)
/

delete from KSMG_MESSAGE where ID = 'f7c0520b-e868-40f9-8996-86e127335ae9'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('f7c0520b-e868-40f9-8996-86e127335ae9', 'standard.creditRatio-help', 'en', 'course', 'An easy-to-remember approximation of the policy for the regular academic semester is that standard 3-credit lecture/discussion courses must meet three hours per week (45 contact hours per term); laboratories require twice as many hours (90 hours per term), and internships require three times as many hours (135 hours per term).  Online courses must be comparable.', 'AAD74A0E-7010-31D3-A227-CB58C2596A6D', 1)
/

delete from KSMG_MESSAGE where ID = '1f36a8bb-97d6-4e8c-a568-a8df8c9eb00f'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('1f36a8bb-97d6-4e8c-a568-a8df8c9eb00f', 'nonstandard.calculatedContactHours-help', 'en', 'course', 'An easy-to-remember approximation of the policy for the regular academic semester is that standard 3-credit lecture/discussion courses must meet three hours per week (45 contact hours per term); laboratories require twice as many hours (90 hours per term), and internships require three times as many hours (135 hours per term).  Online courses must be comparable.', '1FA0EB72-8770-21A3-04F8-197791060E82', 1)
/

update 	ksmg_message 
set 	msg_value = 'My Action List' 
where  	msg_id = 'wrapperPanelTitleHome' 
and 	grp_name = 'common' 
and 	locale = 'en'
/

--KSCM-637
delete from KSMG_MESSAGE where ID = '7c535961-d9f6-4f75-8833-6f2e3c584d26'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('7c535961-d9f6-4f75-8833-6f2e3c584d26', 'cluDescription-examples', 'en', 'course', 'HIST 344 - Revolutionary Russia: An exploration of the roots, dynamics, and consequences of the Russian Revolution of 1917. Major interpretations of the fall of tsarism, social and political forces at play, Leninism and Stalinism.', '0745B645-7556-62E3-D4D7-7CC78CFEE3E4', 1)
/

--KSCM-1275
update 	ksmg_message 
set 	msg_value = 'Course Prefix and Number' 
where  	msg_id = 'cluCourseNumberOrTitle' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

--KSCM-646
delete from KSMG_MESSAGE where ID = '240de3c5-ba45-4749-91ec-c948fdb0b7d1'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('240de3c5-ba45-4749-91ec-c948fdb0b7d1', 'courseAPandIBText', 'en', 'course', 'Advanced Placement (AP) or International Baccalaurate (IB) can be attributed to this course.', '2015487F-48E2-9195-8CE2-45EC23CE1ABE', 1)
/
delete from KSMG_MESSAGE where ID = '80dac71f-d205-433b-82a5-d40694079981'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('80dac71f-d205-433b-82a5-d40694079981', 'courseAPandIB', 'en', 'course', 'AP and IB', '53C5B9DB-956C-C981-9F02-C36749E1ED75', 1)
/
delete from KSMG_MESSAGE where ID = '61a23231-a9c6-4084-b640-86d0dd659d28'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('61a23231-a9c6-4084-b640-86d0dd659d28', 'additionalInformationSection', 'en', 'course', 'Additional Information', '5CFF32F0-2E68-1D2D-4B70-BC3EC4A47DD4', 1)
/
delete from KSMG_MESSAGE where ID = '9c2acce3-3733-473b-a16f-a918e3577b05'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('9c2acce3-3733-473b-a16f-a918e3577b05', 'coursePCCInformation', 'en', 'course', 'PCC Information', '32225F8D-51EB-44B8-12E3-D8DEB79BAB71', 1)
/
delete from KSMG_MESSAGE where ID = 'd775ce9b-4ecd-421f-9992-56df0a9b3739'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('d775ce9b-4ecd-421f-9992-56df0a9b3739', 'coursePCCInformation-instruct', 'en', 'course', 'Will this change affect the requirements of an academic degree program?', 'A6703E74-3BD0-8614-EDAE-E1EE2272F89C', 1)
/
delete from KSMG_MESSAGE where ID = 'c97400bc-4b38-49f6-a7b6-6cc881c7d022'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('c97400bc-4b38-49f6-a7b6-6cc881c7d022', 'courseCoreGeneralEducation', 'en', 'course', 'CORE and General Education', 'EFDAB635-EF95-FF64-E9C5-98DB356F83CC', 1)
/

--KSCM-719
update 	ksmg_message 
set 	msg_value = 'Browse for Categories' 
where  	msg_id = 'cluLOCategoryBrowsePopup' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

--KSCM-1315
update 	ksmg_message 
set 	msg_value = 'Define By' 
where  	msg_id = 'swappablePanelSearchBy' 
and 	grp_name = 'common' 
and 	locale = 'en'
/

--KSCM-1355: Updating messages for the collaborator name suggest box
delete from KSMG_MESSAGE where ID = '410017e5-d5bb-4d19-bded-3bdb3a9dbc49'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('410017e5-d5bb-4d19-bded-3bdb3a9dbc49', 'collaboratorNameSuggest', 'en', 'course', 'Directory ID', '11b96b15-23cc-4971-b913-1b87632b8dd0', 1)
/
delete from KSMG_MESSAGE where ID = 'c596008b-5ecd-480c-a8e5-8ab72e80ecd0'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('c596008b-5ecd-480c-a8e5-8ab72e80ecd0', 'collaboratorNameSuggest-help', 'en', 'course', 'Directory ID is the username used to login to a variety of systems, including Exchange and PHR. To search by name, click Advanced Search.', '11d5e3ba-41cd-4eef-b9db-79306ce21417', 1)
/

--KSCM-678
update 	ksmg_message 
set 	msg_value = 'This description will appear in the catalog. Do not begin descriptions with ''This course will...''. Do not include pre/co-requisite information or the title.'
where  	msg_id = 'cluDescription-instruct' 
and 	grp_name = 'course' 
and 	locale = 'en'
/

delete from KSMG_MESSAGE where ID = '3fe47539-5869-46e5-aced-5aac5b9f69a4'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('3fe47539-5869-46e5-aced-5aac5b9f69a4', 'courseActivityTotalContactHours', 'en', 'course', 'Course Contact Hours', '05afe8d2-5b86-4056-aa84-088945673691', 1)
/

delete from KSMG_MESSAGE where ID = '6f616e2c-b90b-413a-a357-fefb577460c9'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('6f616e2c-b90b-413a-a357-fefb577460c9', 'courseActivityTotalCredits', 'en', 'course', 'Total Activity Credits', '7243b61a-823a-4c68-9022-caef58f669c8', 1)
/

delete from KSMG_MESSAGE where ID = 'f367c5e7-0012-44bc-b70a-c52f4f405f77'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('f367c5e7-0012-44bc-b70a-c52f4f405f77', 'creditsPerActivityType', 'en', 'course', 'Credits per Activity Type', '021bc57d-8aa8-42d3-9785-06df86eaeb2e', 1)
/

delete from KSMG_MESSAGE where ID = '37d64a0e-5819-4199-a96d-fc9b86bea338'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('37d64a0e-5819-4199-a96d-fc9b86bea338', 'creditsPerActivityType-help', 'en', 'course', 'The University of Maryland follows the Maryland Higher Education Commission''s policies on "contact hours," a shorthand term referring to the amount of time required in various forms of education to earn credit  ', 'aaf0451b-d4cf-49ba-a972-59f80ab6185b', 1)
/

delete from KSMG_MESSAGE where ID = '41aed6f1-9400-448d-92c3-5a62674a4854'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('41aed6f1-9400-448d-92c3-5a62674a4854', 'creditsPerActivityType-instruct', 'en', 'course', 'Enter the number of credits for each activity type.  For more information on contact hours see the <a href="http://vpac.umd.edu/policies.html" target="_blank">Course Policies and Practices</a>.', '913037b7-2a4e-4d00-94fb-565532491c47', 1)
/

delete from KSMG_MESSAGE where ID = 'ed117c6a-dfd3-4970-8cb5-e401a1ea951b'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('ed117c6a-dfd3-4970-8cb5-e401a1ea951b', 'courseFormatCreditRatio-help', 'en', 'course', 'An easy-to-remember approximation of the policy for the regular academic semester is that standard 3-credit lecture/discussion courses must meet three hours per week (45 contact hours per term); laboratories require twice as many hours (90 hours per term), and internships require three times as many hours (135 hours per term).  Online courses must be comparable.', '80233c0a-c961-4361-82cd-09e46d3654a5', 1)
/

delete from KSMG_MESSAGE where ID = '93fe24dd-eef4-4625-8d6a-61f59f44ea0b'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('93fe24dd-eef4-4625-8d6a-61f59f44ea0b', 'courseFormatCreditRatio', 'en', 'course', 'Credit Ratio ', '86c2e8c2-868f-4100-9602-9a808b800767', 1)
/

delete from KSMG_MESSAGE where ID = 'e7a21c3c-e5c7-4fe4-8bfb-3f30819981a9'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('e7a21c3c-e5c7-4fe4-8bfb-3f30819981a9', 'courseFormatContactHours', 'en', 'course', 'Contact Hours ', '86d6200b-f373-41ec-9f72-18dfa6b9dedf', 1)
/

delete from KSMG_MESSAGE where ID = 'ebe4e729-f15b-4e24-9b26-40760f9266d5'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('ebe4e729-f15b-4e24-9b26-40760f9266d5', 'courseFormatContactHours-help', 'en', 'course', 'An easy-to-remember approximation of the policy for the regular academic semester is that standard 3-credit lecture/discussion courses must meet three hours per week (45 contact hours per term); laboratories require twice as many hours (90 hours per term), and internships require three times as many hours (135 hours per term).  Online courses must be comparable. ', '0f33eab9-8984-4f51-882a-0d78a7259ae7', 1)
/

delete from KSMG_MESSAGE where ID = '1f91949d-c945-4580-b84a-e6261b0efede'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('1f91949d-c945-4580-b84a-e6261b0efede', 'courseLectureHoursPerWeek', 'en', 'course', 'Lecture Hours/Week', '30638b2f-6dd7-4d09-bbf1-c5d4aabfb678', 1)
/

delete from KSMG_MESSAGE where ID = '2fb47b7f-1c41-43dc-81fc-736be862816a'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('2fb47b7f-1c41-43dc-81fc-736be862816a', 'courseDiscussionHoursPerWeek', 'en', 'course', 'Discussion Hours/Week', '775e60ba-4d77-45f9-b2e5-2bacac65acaa', 1)
/

delete from KSMG_MESSAGE where ID = 'b32e6e4c-943e-4804-b8fd-b9404f9a33e2'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('b32e6e4c-943e-4804-b8fd-b9404f9a33e2', 'courseLabHoursPerWeek', 'en', 'course', 'Lab Hours/Week', 'f23effad-3c1a-4d08-9837-bae43302f704', 1)
/

delete from KSMG_MESSAGE where ID = 'f507d39c-7ca3-490a-a269-a64981083f98'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('f507d39c-7ca3-490a-a269-a64981083f98', 'courseExperientialHoursPerWeek', 'en', 'course', 'Experiential Hours/Week', 'f2310af9-0264-4ea9-b1e0-51016a102cee', 1)
/

delete from KSMG_MESSAGE where ID = '37717dd0-6dcd-40b9-8eac-2c1be4ebc9df'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('37717dd0-6dcd-40b9-8eac-2c1be4ebc9df', 'courseLectureCredits', 'en', 'course', 'Lecture Credits', '144b0f0c-caa3-4a0b-b66e-dc28f9119430', 1)
/

delete from KSMG_MESSAGE where ID = '884d6d2d-883f-4ada-9055-6841c6cfa21a'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('884d6d2d-883f-4ada-9055-6841c6cfa21a', 'courseDiscussionCredits', 'en', 'course', 'Discussion Credits', '7154b02c-970a-449e-b2b8-86116b3f098d', 1)
/

delete from KSMG_MESSAGE where ID = '260291f0-97b3-426d-b46e-8d6455fb5f67'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('260291f0-97b3-426d-b46e-8d6455fb5f67', 'courseLabCredits', 'en', 'course', 'Lab Credits', '9d897788-eb38-47cd-9b57-a8a136f738e1', 1)
/

delete from KSMG_MESSAGE where ID = '8fc0746b-60c0-4840-872a-c47226ed7ed8'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('8fc0746b-60c0-4840-872a-c47226ed7ed8', 'courseExperientialCredits', 'en', 'course', 'Experiential Credits', '05952378-661e-48ff-a22a-347a79e568c4', 1)
/

delete from KSMG_MESSAGE where ID = '298855ee-6a48-4fa4-981c-c0d0e82e3edc'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('298855ee-6a48-4fa4-981c-c0d0e82e3edc', 'courseAllowableCredits', 'en', 'course', 'Allowable Credits based upon Contact Hours', '7021a9d7-ed56-4b7c-9dbb-79d5f536a037', 1)
/

delete from KSMG_MESSAGE where ID = 'CAD0A18ED4944CCF87BEC204591A9790'
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) VALUES ('CAD0A18ED4944CCF87BEC204591A9790','kuali.lu.lookup.advanced.learningObjectives.byCourse-title', 'en', 'course', 'Learning Outcomes', 'C57A8B11EB5949F581AA08EBD877B002', 1)
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