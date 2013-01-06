
-- KSCM-749
-- need to delete in reverse order of insert        
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-408'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4408'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4308'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4208'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4108'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4008'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.completed.enrolled' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.course.clu.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.course.completed.enrolled'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.completed.enrolled', 'Must have completed or be concurrently enrolled in <course>', SYSDATE, SYSDATE, 'Completed or enrolled in course')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.completed.enrolled', 'kuali.reqComponent.field.type.course.clu.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4008', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed or be concurrently enrolled in MATH220', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4108', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4208', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have completed or be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4308', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have completed or be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4408', '', '', 'en', 'KUALI.RULE', 'Must have completed or be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('PR-408', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.completed.enrolled')
/
-- KSCM-1000
update  ksst_req_com_type_nl_tmpl 
set     template = '$courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.completed.enrolled' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4508, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Completed or be concurrently enrolled in', 'kuali.reqComponent.type.course.completed.enrolled', null, 0)
/
-- KSCM-750
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-409'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4409'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4309'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4209'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4109'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4009'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.enrolled.all' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.course.cluSet.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.course.courseset.completed.enrolled.all'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.enrolled.all', 'Must have completed or be concurrently enrolled in all courses from <courses>', SYSDATE, SYSDATE, 'Completed or enrolled in all courses')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.course.courseset.completed.enrolled.all', 'kuali.reqComponent.field.type.course.cluSet.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4009', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed or be concurrently enrolled in MATH220, MATH140, and MATH110', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4109', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4209', '', '', 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4309', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4409', '', '', 'en', 'KUALI.RULE', 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('PR-409','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4509, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Completed or be concurrently enrolled in', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all', null, 0)
/
-- KSCM-752
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-410'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4410'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4310'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4210'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4110'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4010'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.enrolled.one' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.course.cluSet.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.course.courseset.completed.enrolled.one'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.enrolled.one', 'Must have completed or be concurrently enrolled in 1 of <courses>', SYSDATE, SYSDATE, 'Completed or enrolled in 1 in courses')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.enrolled.one', 'kuali.reqComponent.field.type.course.cluSet.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4010', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed or be concurrently enrolled in MATH220, MATH140, or MATH110', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
-- KSCM-752
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4110', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4210', '', '', 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4310', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4410', '', '', 'en', 'KUALI.RULE', 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PR-410','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4510, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Completed or be concurrently enrolled in', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one', null, 0)
/

-- KSCM-751
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-411'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4411'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4311'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4211'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4111'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4011'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.course.courseset.completed.one' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.course.cluSet.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.course.courseset.completed.one'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.one', 'Must have completed 1 of <courses>', SYSDATE, SYSDATE, 'Completed 1 in courses')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.one', 'kuali.reqComponent.field.type.course.cluSet.id')
/
-- KSCM-751
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4011', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed MATH220, MATH140, or MATH110', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4111', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4211', '', '', 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4311', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have completed #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4411', '', '', 'en', 'KUALI.RULE', 'Must have completed #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4511, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.completed.one', null, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('PR-411','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.one')
/
-- KSCM-753
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-412'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4412'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4312'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4212'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4112'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4012'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.major.org' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.org.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.major.org'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.major.org', 'Must be in a major in <org>', SYSDATE, SYSDATE, 'Major in org')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.major.org', 'kuali.reqComponent.field.type.org.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4012', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must be in a major in College of Behavioral and Social Sciences', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4112', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Department>', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4212', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must be in a major #if($org.getAttributes().get("umType") == "Academic Department")within $org.getLongName() department#{else}in $org.getLongName()#end', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4312', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must be in a major #if($org.getAttributes().get("umType") == "Academic Department")within $org.getLongName() department#{else}in $org.getLongName()#end', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4412', '', '', 'en', 'KUALI.RULE', 'Must be in a major #if($org.getAttributes().get("umType") == "Academic Department")within $org.getLongName() department#{else}in $org.getLongName()#end', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('PR-412','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.major.org')
/
-- KSCM-1000
update  ksst_req_com_type_nl_tmpl 
set     template = 'Major in $org.getLongName()' 
where   owner = 'kuali.reqComponent.type.major.org' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4512, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.major.org', null, 0)
/
-- KSCM-754
delete from KSST_STMT_TYP_JN_RC_TYP where ID = 'PR-413'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4413'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4313'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4213'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4113'
/
delete from KSST_REQ_COM_TYPE_NL_TMPL where ID = '4013'
/
delete from KSST_RCTYP_JN_RCFLDTYP where REQ_COMP_TYPE_ID = 'kuali.reqComponent.type.math.elig' and REQ_COMP_FIELD_TYPE_ID = 'kuali.reqComponent.field.type.course.clu.id'
/
delete from KSST_REQ_COM_TYPE where type_key = 'kuali.reqComponent.type.math.elig'
/

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.math.elig', 'Must have math eligibility of <Eligibility> or higher', SYSDATE, SYSDATE, 'Math eligibility or higher')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.math.elig', 'kuali.reqComponent.field.type.course.clu.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4013', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have math eligibility of MATH115 or higher', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4113', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4213', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have math eligibility of $courseClu.getOfficialIdentifier().getCode() or higher', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4313', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have math eligibility of $courseClu.getOfficialIdentifier().getCode() or higher', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4413', '', '', 'en', 'KUALI.RULE', 'Must have math eligibility of $courseClu.getOfficialIdentifier().getCode() or higher', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('PR-413','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.math.elig')
/
--KSCM-771
update ksst_req_com_type set type_desc = 'Must have completed <course>' where type_key = 'kuali.reqComponent.type.course.completed'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed MATH115' 
where   owner = 'kuali.reqComponent.type.course.completed' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.completed' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.completed' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (427, null, null, 'en', 'KUALI.RULE.CATALOG', '$courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.completed', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (527, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.completed', null, 0)
/
--KSCM-774
update ksst_req_com_type set type_desc = 'Must have completed all courses from <courses>' where type_key = 'kuali.reqComponent.type.course.courseset.completed.all'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed all courses from (MATH113, MATH220, MATH140)' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (402, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}All courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.all', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (502, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.completed.all', null, 0)
/
--KSCM-776
update ksst_req_com_type set type_desc = 'Must have completed a minimum of <n> courses from <courses>' where type_key = 'kuali.reqComponent.type.course.courseset.completed.nof'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed a minimum of 2 courses from (MATH113, MATH220, MATH140)' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof'
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (403, null, null, 'en', 'KUALI.RULE.CATALOG', '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}Minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (503, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.completed.nof', null, 0)
/
--KSCM-778
update ksst_req_com_type set type_desc = 'Must have completed a minimum of <n> credits from <courses>' where type_key = 'kuali.reqComponent.type.course.courseset.credits.completed.nof'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed a minimum of 6 credits from (MATH113, MATH220, MATH140)'
where   owner = 'kuali.reqComponent.type.course.courseset.credits.completed.nof'
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") != "")#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range #end$courseCluSet.getCluSetAsCode()' 
where   owner = 'kuali.reqComponent.type.course.courseset.credits.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.credits.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (405, null, null, 'en', 'KUALI.RULE.CATALOG', 'Minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (505, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', null, 0)
/
--KSCM-779
update ksst_req_com_type set type_desc = 'Must be in the <program> program' where type_key = 'kuali.reqComponent.type.course.program.admitted'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must be in the Hearing and Speech Sciences program'
where   owner = 'kuali.reqComponent.type.course.program.admitted'
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must be in #if($programCluSet.getCluList().size() > 1)one of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.admitted' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must be in #if($programCluSet.getCluList().size() > 1)one of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.admitted' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (426, null, null, 'en', 'KUALI.RULE.CATALOG', 'Must be in #if($programCluSet.getCluList().size() > 1)one of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end', 'kuali.reqComponent.type.course.program.admitted', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (526, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.program.admitted', null, 0)
/
--KSCM-782
update ksst_req_com_type set type_desc = 'Must not be in the <program> program' where type_key = 'kuali.reqComponent.type.course.program.notadmitted'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not be in the Hearing and Speech Sciences program' 
where   owner = 'kuali.reqComponent.type.course.program.notadmitted' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not be in #if($programCluSet.getCluList().size() > 1)any of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.notadmitted' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not be in #if($programCluSet.getCluList().size() > 1)any of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.notadmitted' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (423, null, null, 'en', 'KUALI.RULE.CATALOG', 'Must not be in #if($programCluSet.getCluList().size() > 1)any of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end', 'kuali.reqComponent.type.course.program.notadmitted', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (523, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.program.notadmitted', null, 0)
/
--KSCM-812
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have earned a grade of C or higher in (MATH140, MATH115, MATH220)' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (414, null, null, 'en', 'KUALI.RULE.CATALOG', 'Must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.grade.max', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (514, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.grade.max', null, null)
/
--KSCM-813
update ksst_req_com_type set type_desc = 'Must not have completed <course>' where type_key = 'kuali.reqComponent.type.course.notcompleted'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have completed MATH113' 
where   owner = 'kuali.reqComponent.type.course.notcompleted' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have completed $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.notcompleted' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have completed $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.notcompleted' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (429, null, null, 'en', 'KUALI.RULE.CATALOG', '$courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.notcompleted', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (529, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Must not have completed', 'kuali.reqComponent.type.course.notcompleted', null, 0)
/
--KSCM-811
update ksst_req_com_type set type_desc = 'Must complete a minimum of <n> courses from <courses> with a minimum grade of <GradeType> <grade>' where type_key = 'kuali.reqComponent.type.course.courseset.nof.grade.min'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must complete a minimum of 2 courses with a minimum grade of C from (MATH140, MATH115, MATH220)' 
where   owner = 'kuali.reqComponent.type.course.courseset.nof.grade.min' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of $grade from #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.nof.grade.min' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of $grade from #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.nof.grade.min' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (419, null, null, 'en', 'KUALI.RULE.CATALOG', 'Minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of $grade from #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.nof.grade.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (519, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.nof.grade.min', null, 0)
/
--KSCM-814
update ksst_req_com_type set type_desc = 'Must not have completed any courses from <courses>' where type_key = 'kuali.reqComponent.type.course.courseset.completed.none'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have completed any courses from (MATH110, MATH115, MATH220)' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must not have completed $courseCluSet.getCluSetAsCode()#{else}Must not have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must not have completed#{else}Must not have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (401, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.completed.none', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (501, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Must not have completed', 'kuali.reqComponent.type.course.courseset.completed.none', null, 0)
/
--KSCM-810
update ksst_req_com_type set type_desc = 'Must have earned a minimum of <n> credits' where type_key = 'kuali.reqComponent.type.course.credits.min'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum of 120 credits' 
where   owner = 'kuali.reqComponent.type.course.credits.min' 
and     nl_usuage_type_key = 'KUALI.RULE.EXAMPLE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit")' 
where   owner = 'kuali.reqComponent.type.course.credits.min' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit")' 
where   owner = 'kuali.reqComponent.type.course.credits.min' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit")' 
where   owner = 'kuali.reqComponent.type.course.credits.min' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
-- KSCM-1000
update  ksst_req_com_type_nl_tmpl 
set     template = 'Minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit")' 
where   owner = 'kuali.reqComponent.type.course.credits.min' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4502, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.credits.min', null, 0)
/
--KSCM-851
delete from ksst_req_com_type_nl_tmpl where owner in (
'kuali.reqComponent.type.course.org.credits.completed.min',
'kuali.reqComponent.type.course.courseset.gpa.min',
'kuali.reqComponent.type.course.org.program.admitted',
'kuali.reqComponent.type.course.program.admitted.org.duration',
'kuali.reqComponent.type.course.program.notadmitted.org.duration',
'kuali.reqComponent.type.course.courseset.credits.completed.none',
'kuali.reqComponent.type.course.courseset.credits.completed.max',
'kuali.reqComponent.type.course.test.score.max')
/

delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id in (
'kuali.reqComponent.type.course.org.credits.completed.min',
'kuali.reqComponent.type.course.courseset.gpa.min',
'kuali.reqComponent.type.course.org.program.admitted',
'kuali.reqComponent.type.course.program.admitted.org.duration',
'kuali.reqComponent.type.course.program.notadmitted.org.duration',
'kuali.reqComponent.type.course.courseset.credits.completed.none',
'kuali.reqComponent.type.course.courseset.credits.completed.max',
'kuali.reqComponent.type.course.test.score.max')
/

delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id in (
'kuali.reqComponent.type.course.org.credits.completed.min',
'kuali.reqComponent.type.course.courseset.gpa.min',
'kuali.reqComponent.type.course.org.program.admitted',
'kuali.reqComponent.type.course.program.admitted.org.duration',
'kuali.reqComponent.type.course.program.notadmitted.org.duration',
'kuali.reqComponent.type.course.courseset.credits.completed.none',
'kuali.reqComponent.type.course.courseset.credits.completed.max',
'kuali.reqComponent.type.course.test.score.max')
/

--KSCM-81
delete from ksst_stmt_typ_jn_rc_typ where id = 'CRP-403'
/
delete from ksst_stmt_typ_jn_rc_typ where id = 'CA-403'
/

insert into ksst_stmt_typ_jn_rc_typ (id, req_com_type_id, stmt_type_id )
values('CA-403', 'kuali.reqComponent.type.course.freeform.text', 'kuali.statement.type.course.academicReadiness.antireq')
/
insert into ksst_stmt_typ_jn_rc_typ (id, req_com_type_id, stmt_type_id )
values('CRP-403', 'kuali.reqComponent.type.course.freeform.text', 'kuali.statement.type.course.recommendedPreparation')
/
--KSCM-757
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.recommended.completed'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.recommended.completed'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.recommended.completed'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.recommended.completed'
/
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.course.recommended.completed', 'Completion of <course> recommended', sysdate, 'Completion of course recomended', 0)
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.recommended.completed','kuali.reqComponent.field.type.course.clu.id')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1118, 'en', 'KUALI.RULE', 'Completion of $courseClu.getOfficialIdentifier().getCode() recommended', 'kuali.reqComponent.type.course.recommended.completed', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1119, 'en', 'KUALI.RULE.PREVIEW', 'Completion of $courseClu.getOfficialIdentifier().getCode() recommended', 'kuali.reqComponent.type.course.recommended.completed', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1120, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>', 'kuali.reqComponent.type.course.recommended.completed', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1121, 'en', 'KUALI.RULE.EXAMPLE', 'Completion of MATH115 recommended', 'kuali.reqComponent.type.course.recommended.completed', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('CRP-414', 0, 'kuali.reqComponent.type.course.recommended.completed', 1, 'kuali.statement.type.course.recommendedPreparation')
/
--KSCM-758
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
/
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.course.courseset.recommended.completed.all', 'Completion of all of <courses> recommended', sysdate, 'Completion of all courses recomended', 0)
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.courseset.recommended.completed.all','kuali.reqComponent.field.type.course.cluSet.id')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1124, 'en', 'KUALI.RULE', 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1125, 'en', 'KUALI.RULE.PREVIEW', 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1126, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1127, 'en', 'KUALI.RULE.EXAMPLE', 'Completion of MATH115, MATH220, and MATH140 recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('CRP-415', 0, 'kuali.reqComponent.type.course.courseset.recommended.completed.all', 1, 'kuali.statement.type.course.recommendedPreparation')
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1128, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1129, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.recommended.completed.all', null, 0)
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1122, null, null, 'en', 'KUALI.RULE.CATALOG', '$courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.recommended.completed', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1123, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.recommended.completed', null, 0)
/
--KSCM-756
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.prefix.credits.completed.nof'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.prefix.credits.completed.nof'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.prefix.credits.completed.nof'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.prefix.credits.completed.nof'
/
delete from ksst_req_com_field_type where id = 'kuali.reqComponent.field.type.course.prefix'
/

--create course prefix type
--
insert into ksst_req_com_field_type (id, data_type, descr, name, read_only, ver_nbr)
values ('kuali.reqComponent.field.type.course.prefix', 'string', 'Course CLU prefix', 'Course CLU Prefix', 0, 0)
/
--
--1 create new requirement 
--
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.course.prefix.credits.completed.nof', 'Must have completed <n> credits in <coursePrefix> courses', sysdate, 'Must have completed N credits in courses', 0)
/
--
--2 create link between result component and its field types (variable part of rule)
--
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.prefix.credits.completed.nof','kuali.reqComponent.field.type.value.positive.integer')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.prefix.credits.completed.nof','kuali.reqComponent.field.type.course.prefix')
/
--
--3 create NL translation for the result component/rule
--
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5000, 'en', 'KUALI.RULE', 'Must have completed $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.course.prefix") courses', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5001, 'en', 'KUALI.RULE.PREVIEW', 'Must have completed $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.course.prefix") courses', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5002, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.prefix;reqCompFieldLabel=CoursePrefix> for final credits of <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5003, 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed 6 credits in Criminology and Criminal Justice courses', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', 0)
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (5004, null, null, 'en', 'KUALI.RULE.CATALOG', '$intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.course.prefix") courses', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (5005, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.prefix.credits.completed.nof', null, 0)
/
--
--4 linking the new requirement component type to statement type
--
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('ZZ-1', 0, 'kuali.reqComponent.type.course.prefix.credits.completed.nof', 1, 'kuali.statement.type.course.academicReadiness.prereq')
/
--KSCM-807
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.courseset.no.completed.min'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.courseset.no.completed.min'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.courseset.no.completed.min'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.courseset.no.completed.min'
/
delete from ksst_req_com_field_type where id = 'kuali.reqComponent.field.type.gradePoint'
/
insert into ksst_req_com_field_type (id, data_type, descr, name, read_only, ver_nbr )
values ('kuali.reqComponent.field.type.gradePoint', 'string', 'Grade Point Value', 'Grade Point Value', 0, 0)
/
insert into ksst_req_com_type (type_key, type_desc)
values ('kuali.reqComponent.type.course.courseset.no.completed.min', 'Must have earned a minimum grade point value of <no.> in each of <courses>')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.courseset.no.completed.min', 'kuali.reqComponent.field.type.course.cluSet.id')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.courseset.no.completed.min', 'kuali.reqComponent.field.type.gradePoint')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1130, 'en', 'KUALI.RULE', '#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseCluSet.getCluSetAsCode()#{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end', 'kuali.reqComponent.type.course.courseset.no.completed.min', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1131, 'en', 'KUALI.RULE.PREVIEW', '#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.no.completed.min', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1132, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gradePoint;reqCompFieldLabel=Grade Point> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Course>', 'kuali.reqComponent.type.course.courseset.no.completed.min', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1133, 'en', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum grade point value of 2.0 in each of (MATH115, MATH220, MATH140)', 'kuali.reqComponent.type.course.courseset.no.completed.min', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('PR-414', 0, 'kuali.reqComponent.type.course.courseset.no.completed.min', 1, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq')
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1134, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)Minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseCluSet.getCluSetAsCode()#{else}Minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.no.completed.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1135, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.no.completed.min', null, 0)
/
--KSCM-775
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.standing'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.standing'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.standing'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.standing'
/
delete from ksst_req_com_field_type where id = 'kuali.reqComponent.field.type.operatorOption'
/
delete from ksst_req_com_field_type where id = 'kuali.reqComponent.field.type.classStanding'
/
insert into ksst_req_com_field_type (id, data_type, descr, name, read_only, ver_nbr )
values ('kuali.reqComponent.field.type.classStanding', 'string', 'Class Standing Values', 'Class Standing Values', 0, 0)
/
insert into ksst_req_com_field_type (id, data_type, descr, name, read_only, ver_nbr )
values ('kuali.reqComponent.field.type.operatorOption', 'string', 'Operator options', 'Operator options', 0, 0)
/
insert into ksst_req_com_type (type_key, type_desc)
values ('kuali.reqComponent.type.course.standing', 'Must have <standing> standing <or higher>')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.standing', 'kuali.reqComponent.field.type.classStanding')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.standing', 'kuali.reqComponent.field.type.operatorOption')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1136, 'en', 'KUALI.RULE', 'Must have $fields.get("kuali.reqComponent.field.type.classStanding") standing#if($fields.get("kuali.reqComponent.field.type.operatorOption") != "equal to") $fields.get("kuali.reqComponent.field.type.operatorOption")#end', 'kuali.reqComponent.type.course.standing', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1137, 'en', 'KUALI.RULE.PREVIEW', 'Must have $fields.get("kuali.reqComponent.field.type.classStanding") standing#if($fields.get("kuali.reqComponent.field.type.operatorOption") != "equal to") $fields.get("kuali.reqComponent.field.type.operatorOption")#end', 'kuali.reqComponent.type.course.standing', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1138, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.classStanding;reqCompFieldLabel=Class Standing Values> <reqCompFieldType=kuali.reqComponent.field.type.operatorOption;reqCompFieldLabel=Operator options>', 'kuali.reqComponent.type.course.standing', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1139, 'en', 'KUALI.RULE.EXAMPLE', 'Must have Junior standing or higher', 'kuali.reqComponent.type.course.standing', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('PR-415', 0, 'kuali.reqComponent.type.course.standing', 1, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq')
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1140, null, null, 'en', 'KUALI.RULE.CATALOG', '$fields.get("kuali.reqComponent.field.type.classStanding") standing#if($fields.get("kuali.reqComponent.field.type.operatorOption") != "equal to") $fields.get("kuali.reqComponent.field.type.operatorOption")#end', 'kuali.reqComponent.type.course.standing', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1141, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.standing', null, 0)
/
--KSCM-759
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
/
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.course.courseset.recommended.completed.one', 'Completion of 1 course from <courses> recommended', sysdate, 'Completion of 1 course from courses recomended', 0)
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.courseset.recommended.completed.one','kuali.reqComponent.field.type.course.cluSet.id')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1142, 'en', 'KUALI.RULE', 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1143, 'en', 'KUALI.RULE.PREVIEW', 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1144, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1145, 'en', 'KUALI.RULE.EXAMPLE', 'Completion of MATH115, MATH220, and MATH140 recommended', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('CRP-416', 0, 'kuali.reqComponent.type.course.courseset.recommended.completed.one', 1, 'kuali.statement.type.course.recommendedPreparation')
/
-- KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1146, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1147, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.recommended.completed.one', null, 0)
/

--KSCM-806
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.course.no.completed.min'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.course.no.completed.min'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.course.no.completed.min'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.course.no.completed.min'
/
insert into ksst_req_com_type (type_key, type_desc)
values ('kuali.reqComponent.type.course.course.no.completed.min', 'Must have earned a minimum grade point value of <no.> in <course>')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.course.no.completed.min', 'kuali.reqComponent.field.type.course.clu.id')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id )
values ('kuali.reqComponent.type.course.course.no.completed.min', 'kuali.reqComponent.field.type.gradePoint')
/
insert into ksst_req_com_type_nl_tmpl (id, attr_name, attr_value, language, nl_usuage_type_key, template, owner) 
values ('4420', '', '', 'en', 'KUALI.RULE', 'Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.course.no.completed.min')
/
insert into ksst_req_com_type_nl_tmpl (id, attr_name, attr_value, language, nl_usuage_type_key, template, owner) 
values ('4320', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.course.no.completed.min')
/
insert into ksst_req_com_type_nl_tmpl (id, attr_name, attr_value, language, nl_usuage_type_key, template, owner) 
values ('4120', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gradePoint;reqCompFieldLabel=grade point> in <reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=course>', 'kuali.reqComponent.type.course.course.no.completed.min')
/
insert into ksst_req_com_type_nl_tmpl (id, attr_name, attr_value, language, nl_usuage_type_key, template, owner) 
values ('4020', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum grade point value of 2.0 in math140', 'kuali.reqComponent.type.course.course.no.completed.min')
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('CSP-325', 0, 'kuali.reqComponent.type.course.course.no.completed.min', 1, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq')
/

update ksst_stmt_type set type_desc = 'Add courses to be taken at the same time as this course.' where type_key = 'kuali.statement.type.course.academicReadiness.coreq'
/

--KSCM-754
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.math.elig'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.math.elig'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.math.elig'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.math.elig'
/
delete from ksst_req_com_field_type where id = 'kuali.reqComponent.field.type.math.elig.codes'
/
--
--create math elig code
--
insert into ksst_req_com_field_type (id, data_type, descr, name, read_only, ver_nbr)
values ('kuali.reqComponent.field.type.math.elig.codes', 'string', 'Math Eligiblity Codes', 'Math Elig Codes', 0, 0)
/
--
--1 create new requirement 
--
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.math.elig', 'Must have math eligibility of <Eligibility> or higher', sysdate, 'Math eligibility or higher', 0)
/
--
--2 create link between result component and its field types (variable part of rule)
--
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.math.elig','kuali.reqComponent.field.type.math.elig.codes')
/
--
--3 create NL translation for the result component/rule
--
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5200, 'en', 'KUALI.RULE', 'Must have math eligibility of $fields.get("kuali.reqComponent.field.type.math.elig.codes") or higher', 'kuali.reqComponent.type.math.elig', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5201, 'en', 'KUALI.RULE.PREVIEW', 'Must have math eligibility of $fields.get("kuali.reqComponent.field.type.math.elig.codes") or higher', 'kuali.reqComponent.type.math.elig', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5202, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.math.elig.codes;reqCompFieldLabel=Math Codes>', 'kuali.reqComponent.type.math.elig', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(5203, 'en', 'KUALI.RULE.EXAMPLE', 'Must have math eligibility of MATH001 or higher', 'kuali.reqComponent.type.math.elig', 0)
/
--KSCM-1000
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (5204, null, null, 'en', 'KUALI.RULE.CATALOG', 'Math eligibility of $fields.get("kuali.reqComponent.field.type.math.elig.codes") or higher', 'kuali.reqComponent.type.math.elig', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (5205, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.math.elig', null, 0)
/
--
--4 linking the new requirement component type to statement type
--
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('CSP-310', 0, 'kuali.reqComponent.type.math.elig', 1, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq')
/
-- KSCM-1000
update  ksst_req_com_type_nl_tmpl 
set     template = 'Minimum cumulative GPA of $gpa' 
where   owner = 'kuali.reqComponent.type.course.cumulative.gpa.min' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4501, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.cumulative.gpa.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (413, null, null, 'en', 'KUALI.RULE.CATALOG', 'Minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.grade.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (513, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.courseset.grade.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4503, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.freeform.text', null, null)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (415, null, null, 'en', 'KUALI.RULE.CATALOG', 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end', 'kuali.reqComponent.type.course.permission.org.required', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (515, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.permission.org.required', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (416, null, null, 'en', 'KUALI.RULE.CATALOG', 'Permission of instructor', 'kuali.reqComponent.type.course.permission.instructor.required', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (516, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.permission.instructor.required', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (417, null, null, 'en', 'KUALI.RULE.CATALOG', 'Minimum score of $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()', 'kuali.reqComponent.type.course.test.score.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (517, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.test.score.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4220, null, null, 'en', 'KUALI.RULE.CATALOG', 'Minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.course.no.completed.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4520, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.course.no.completed.min', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (428, null, null, 'en', 'KUALI.RULE.CATALOG', '$courseClu.getOfficialIdentifier().getCode()', 'kuali.reqComponent.type.course.enrolled', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (528, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Concurrently enrolled in', 'kuali.reqComponent.type.course.enrolled', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (404, null, null, 'en', 'KUALI.RULE.CATALOG', '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.enrolled.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (504, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Concurrently enrolled in', 'kuali.reqComponent.type.course.courseset.enrolled.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (409, null, null, 'en', 'KUALI.RULE.CATALOG', '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end', 'kuali.reqComponent.type.course.courseset.enrolled.all', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (509, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'Concurrently enrolled in', 'kuali.reqComponent.type.course.courseset.enrolled.all', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (424, null, null, 'en', 'KUALI.RULE.CATALOG', 'For a maximum of $intValue credits', 'kuali.reqComponent.type.course.credits.repeat.max', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (524, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.credits.repeat.max', null, 0)
/

--KSCM-873
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.regComponent.type.course.program.completed.one'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.regComponent.type.course.program.completed.one'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.regComponent.type.course.program.completed.one'
/
delete from ksst_req_com_type where type_key = 'kuali.regComponent.type.course.program.completed.one'
/
insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.regComponent.type.course.program.completed.one', 'Must be in 1 program of <programs>', sysdate, 'Must be in 1 of the programs', 0)
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.regComponent.type.course.program.completed.one','kuali.reqComponent.field.type.program.cluSet.id')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1148, 'en', 'KUALI.RULE', '#if($programCluSet.getCluList().size() == 1)Must be in $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") program#{else}Must be in 1 of the following programs: $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";")#end', 'kuali.regComponent.type.course.program.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1149, 'en', 'KUALI.RULE.PREVIEW', '#if($programCluSet.getCluList().size() == 1)Must be in $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") program#{else}Must be in 1 of the following programs: $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";")#end', 'kuali.regComponent.type.course.program.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1150, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=programs>', 'kuali.regComponent.type.course.program.completed.one', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1151, 'en', 'KUALI.RULE.EXAMPLE', 'Must be in 1 of the following programs: Central European; Russian and Euroasian Studies; Measurement; Statistics and Evaluation', 'kuali.regComponent.type.course.program.completed.one', 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('PR-416', 0, 'kuali.regComponent.type.course.program.completed.one', 1, 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq')
/

--KSCM-760
delete from ksst_stmt_typ_jn_rc_typ where req_com_type_id = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
/
delete from ksst_req_com_type_nl_tmpl  where owner = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
/
delete from ksst_rctyp_jn_rcfldtyp where req_comp_type_id = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
/
delete from ksst_req_com_type where type_key = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
/

insert into ksst_req_com_type (type_key, type_desc, eff_dt, name, ver_nbr)
values('kuali.reqComponent.type.course.prefix.courses.completed.nof', 'Must have completed <n> courses in <coursePrefix>', sysdate, 'Must have completed N courses in', 0)
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.prefix.courses.completed.nof','kuali.reqComponent.field.type.value.positive.integer')
/
insert into ksst_rctyp_jn_rcfldtyp (req_comp_type_id, req_comp_field_type_id)
values ('kuali.reqComponent.type.course.prefix.courses.completed.nof','kuali.reqComponent.field.type.course.prefix')
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1152, 'en', 'KUALI.RULE', 'Completed $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.course.prefix")', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1153, 'en', 'KUALI.RULE.PREVIEW', 'Completed $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.course.prefix")', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1154, 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.course.prefix;reqCompFieldLabel=CoursePrefix> for final courses of <reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', 0)
/
insert into ksst_req_com_type_nl_tmpl (id, language, nl_usuage_type_key, template, owner, ver_nbr)
values(1155, 'en', 'KUALI.RULE.EXAMPLE', 'Must have completed 6 courses in Criminology and Criminal Justice', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1156, null, null, 'en', 'KUALI.RULE.CATALOG', '$intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.course.prefix")', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', null, 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (1157, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.prefix.courses.completed.nof', null, 0)
/
insert into ksst_stmt_typ_jn_rc_typ (id, ver_nbr, req_com_type_id, sort_order, stmt_type_id)
values ('PR-417', 0, 'kuali.reqComponent.type.course.prefix.courses.completed.nof', 1, 'kuali.statement.type.course.academicReadiness.prereq')
/

-- KSCM-1014
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.content.dept.contact', 'Students who have taken courses with similar or comparable course content may contact the department', SYSDATE, SYSDATE, 'Students who have taken courses with similar or comparable course content may contact the department')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4014', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Students who have taken courses with similar or comparable course content may contact the department', 'kuali.reqComponent.type.course.content.dept.contact')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4114', '', '', 'en', 'KUALI.RULE.COMPOSITION', '', 'kuali.reqComponent.type.course.content.dept.contact')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4214', '', '', 'en', 'KUALI.RULE.CATALOG', 'Students who have taken courses with similar or comparable course content may contact the department', 'kuali.reqComponent.type.course.content.dept.contact')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4314', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Students who have taken courses with similar or comparable course content may contact the department', 'kuali.reqComponent.type.course.content.dept.contact')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4414', '', '', 'en', 'KUALI.RULE', 'Students who have taken courses with similar or comparable course content may contact the department', 'kuali.reqComponent.type.course.content.dept.contact')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL VALUES (4514, null, null, 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.content.dept.contact', null, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-20','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.content.dept.contact')
/
insert into ksst_stmt_typ_jn_rc_typ (id, req_com_type_id, stmt_type_id )
values('CR-404', 'kuali.reqComponent.type.course.content.dept.contact', 'kuali.statement.type.course.academicReadiness.coreq')
/

-- KSCM-1026
insert into ksst_stmt_typ_jn_rc_typ (id, req_com_type_id, stmt_type_id )
values('CP-21', 'kuali.reqComponent.type.course.freeform.text', 'kuali.statement.type.course.academicReadiness.prereq')
/
insert into ksst_stmt_typ_jn_rc_typ (id, req_com_type_id, stmt_type_id )
values('CS-1', 'kuali.reqComponent.type.course.freeform.text', 'kuali.statement.type.course.academicReadiness.studentEligibility')
/

-- KSCM-833/KSCM-1063
update KSST_STMT_TYPE set TYPE_DESC = 'Add conditions that will restrict student enrollment, such as restrictions to majors, credit level requirements, test scores, etc.'
where type_key = 'kuali.statement.type.course.academicReadiness.studentEligibility'
/
update KSST_STMT_TYPE set NAME = 'Restrictions'
where type_key = 'kuali.statement.type.course.academicReadiness.studentEligibility'
/
update KSST_STMT_TYPE set TYPE_DESC = 'Add courses, with or without grade requirements, which a student must have completed in order to enroll.'
where type_key = 'kuali.statement.type.course.academicReadiness.prereq'
/

DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.antireq'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.creditConstraints'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.credit.restriction'
/
DELETE FROM KSST_STMT_TYP_JN_STMT_TYP WHERE CHLD_STMT_TYPE_ID = 'kuali.statement.type.course.credit.repeatable'
/

INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.studentEligibility','2',5,'kuali.statement.type.course.enrollmentEligibility',0)
/

INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.prereq','3',2,'kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.coreq','4',3,'kuali.statement.type.course.enrollmentEligibility',0)
/

INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.recommendedPreparation','5',4,'kuali.statement.type.course.enrollmentEligibility',0)
/

INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.creditConstraints','6',6,'kuali.statement.type.course',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.credit.restriction','7',7,'kuali.statement.type.course.creditConstraints',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.credit.repeatable','8',8,'kuali.statement.type.course.creditConstraints',0)
/

DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.courseset.grade.max' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq'
/
DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.org.required' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq'
/
DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.permission.instructor.required' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq'
/
DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.test.score.min' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.prereq'
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-6','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.one')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-7','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.enrolled.one')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-8','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.completed.enrolled')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-9','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.enrolled.all')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-11','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.math.elig')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-12','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.no.completed.min')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-14','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.course.no.completed.min')
/

INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-2', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.courseset.grade.max')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-5', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.permission.instructor.required')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-6', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.permission.org.required')
/
--Turning off this rule for now
--INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-7', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.test.score.min')
--/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-8', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.credits.min')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-9', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-10', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.notcompleted')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-11', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.courseset.completed.none')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-12', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.major.org')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CS-13', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.standing')
/

DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.cumulative.gpa.min' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq'
/
DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.credits.min' AND STMT_TYPE_ID = 'kuali.statement.type.course.academicReadiness.coreq'
/

DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.completed' AND STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation'
/
DELETE from KSST_STMT_TYP_JN_RC_TYP where REQ_COM_TYPE_ID = 'kuali.reqComponent.type.course.prefix.credits.completed.nof' AND STMT_TYPE_ID = 'kuali.statement.type.course.recommendedPreparation'
/

--KSCM 783/1101
UPDATE KSST_REQ_COM_TYPE SET TYPE_DESC = 'Permission of <curriculum org> required - enrollment restriction' WHERE TYPE_KEY = 'kuali.reqComponent.type.course.permission.org.required'
/

-- KSCM-1101
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.permission.org.required.preco', 'Permission of <curriculum org> required - prerequisite or corequisite exception', SYSDATE, SYSDATE, 'Permission of curriculum org required - prerequisite or corequisite exception')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.permission.org.required.preco', 'kuali.reqComponent.field.type.org.id')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4015', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Permission of English Department Required', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4115', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Organization>', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4215', '', '', 'en', 'KUALI.RULE.CATALOG', 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4315', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4415', '', '', 'en', 'KUALI.RULE', 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER)VALUES  ('4515', '', '', 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-22','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CR-405','kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.permission.org.required.preco')
/
--KSCM-1437
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must be concurrently enrolled in #if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end'
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.nof'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl   
set     template = 'Must be concurrently enrolled in #if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end'
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.nof'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.all'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.all'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
--KSCM-1459
update  ksst_req_com_type_nl_tmpl 
set     template = 'Permission of instructor' 
where   owner = 'kuali.reqComponent.type.course.permission.instructor.required'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Permission of instructor' 
where   owner = 'kuali.reqComponent.type.course.permission.instructor.required'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end' 
where   owner = 'kuali.reqComponent.type.course.permission.org.required'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end' 
where   owner = 'kuali.reqComponent.type.course.permission.org.required'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/

--KSCM-1342
UPDATE KSST_REQ_COM_TYPE SET TYPE_DESC = 'Permission of instructor required - enrollment restriction' WHERE TYPE_KEY = 'kuali.reqComponent.type.course.permission.instructor.required'
/
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.permission.instructor.required.preco', 'Permission of instructor required - prerequisite or corequisite exception', SYSDATE, SYSDATE, 'Permission of instructor required - prerequisite or corequisite exception')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4016', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Permission of instructor required', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4116', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.person.id;reqCompFieldLabel=Instructor>', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4216', '', '', 'en', 'KUALI.RULE.CATALOG', 'Permission of instructor', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4316', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Permission of instructor', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4416', '', '', 'en', 'KUALI.RULE', 'Permission of instructor', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) VALUES ('4516', '', '', 'en', 'KUALI.RULE.CATALOG.PREFIX', 'none', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CP-4','kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) VALUES ('CR-406','kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.permission.instructor.required.preco')
/