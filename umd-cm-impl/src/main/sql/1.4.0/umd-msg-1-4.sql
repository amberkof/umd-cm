-- updated cm messages for release 1.4
-- KSCM-2125 Update Instructions for Dependency Analysis Result Panel
delete from KSMG_MESSAGE where MSG_ID = 'dependencyAnalysisInstructions' 
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR)
  VALUES ('course','5104924c-d94c-401d-bff0-d9464eaf0fcf' ,'en' ,'dependencyAnalysisInstructions' ,'Use the red arrows to expand/collapse the results below. Arrows pointing to the right indicate that additional information is available. Expanded views show additional information about relationships to the target course. <br><br><b>Please note:</b> the expanded view shows <u>only</u> the rule statement that contains the target course. Click on the "View Course" link for a complete listing of the rule statements for that course.<br>',1)
/