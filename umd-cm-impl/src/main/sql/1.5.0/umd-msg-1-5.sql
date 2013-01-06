-- KSCM-2125 Update Instructions for Dependency Analysis Result Panel
delete from KSMG_MESSAGE where MSG_ID = 'cluSetApprovedProgram-instruct' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('clusetmanagement','5c22a604-11ae-41d6-8224-407d519c3549' ,'en' ,'cluSetApprovedProgram-instruct' ,'Enter the program major code. For example, 22080 for the Sociology bachelor''s degree, SOCYM for the Sociology master''s degree, or SOCYD for the Sociology doctoral degree. Use Advanced Search to find the program major code if not known.',1)
/
