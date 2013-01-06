--add missing cluset
insert into KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN, OBJ_ID, VER_NBR) values ('a86e81a8-7e7f-4448-a3b0-f9192c312bf4', null, null, 'caca160f-a7ba-4b4b-8154-995cf8cc6519', 0)
/
insert into KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, ADMIN_ORG_ID, EFF_DT, EXPIR_DT, REFERENCEABLE, REUSABLE, NAME, ST, TYPE, RT_DESCR_ID, MEM_QUERY_ID, OBJ_ID) values ('b952617a-3972-47e8-a896-c1285c9d9802', null, TIMESTAMP '2011-11-16 18:33:19', null, TIMESTAMP '2011-11-16 18:33:19', 0, null, TIMESTAMP '2011-11-16 18:33:19', null, 0, 1, 'CORE: Advanced Studies Development of Knowledge (AK)', 'active', 'kuali.cluSet.type.CreditCourse', 'a86e81a8-7e7f-4448-a3b0-f9192c312bf4', null, '7d87dee6-56bc-4b29-9f00-c5a97406b3a9')
/
insert into KSLU_CLU_SET_JN_CLU_SET (CLU_SET_PARENT_ID, CLU_SET_CHILD_ID) values ('4b044876-c846-4b6e-9fcf-f2f34c20134e', 'b952617a-3972-47e8-a896-c1285c9d9802')
/

--Add SIS code attributes
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'General Education', 'cb222be8-d920-4df1-a8ca-a4fadb822351', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DSHS', 'eb2a67b7-39ea-47f0-95e5-9e0155dcf8d2', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DSHU', '2e943947-a148-4794-bd08-baf75af53a35', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DSNL', '6ac1f2db-cd9c-4646-8aa4-126c370a032b', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DSNS', '19fa9597-aeb4-4b15-92e7-b47a17b70cec', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DSSP', 'bdfab784-e170-415d-830c-426e3b720456', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DVCC', 'cf1a0533-8c10-4e1e-aa16-35cf38b28f5a', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'DVUP', 'd89fe369-9a3b-44af-bf65-753565e51f55', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'EXLG', '76db5959-a3f7-47bc-b3f0-7242bd51c9cb', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'FSAR', 'e03c56bc-d03d-4e3d-be4d-337bc8b0897f', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'FSAW', '00a62f41-e178-40ec-a1b9-15335490a337', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'FSMA', '62da34df-edf0-4d0f-955b-1c4f5b5a601e', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'FSOC', '2ce71b30-3cb2-4888-a2d0-1629c854cde5', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'FSPW', 'c24cd9f9-0743-4450-b61e-06d408c7e8d0', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'SCIS', 'e05e6def-0c27-46b6-bd36-41fdc2927808', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'Core', '4b044876-c846-4b6e-9fcf-f2f34c20134e', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'SH', 'ea24a638-4913-4029-a598-c77fb5840d98', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'PL', 'a085000f-4b64-4ee8-999e-90b105519c32', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'PS', 'd8974da7-81a7-4da5-9e89-cca718267469', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'ML', '2a3dead2-a7ed-4a5b-aca2-1302d9c4c402', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'HO', '51d0480d-8e16-46f0-994e-39688ceb2f4e', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'LL', '06c407bb-883d-481e-86c0-d85aeff0a9dd', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'HL', 'c861a73a-b285-46a3-a554-141f7c57e676', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'HA', 'e7cb097b-413a-4950-9599-a89140adaf49', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'AE', '84966135-2eb2-4386-b9ff-e62d642fc739', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'CS', 'c853d15c-4e02-430f-8796-3b23c8107d0b', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'XL', '15f5b794-f324-4883-b03c-db5795a2b785', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'XP', '130ff2c7-fb08-4325-9b54-8ee4e3d83b27', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'SB', '0c4119ef-5f87-4bbd-8efb-e23a33eac47d', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'AK', 'b952617a-3972-47e8-a896-c1285c9d9802', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'LS', '3d934ce7-29f8-4a8e-be4d-954ea044b8ce', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'IE', 'cd395b52-0775-4b02-8d6e-7854eabe69cd', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'MS', '97e565db-bd59-4c70-95d9-fa4fb6ae0524', sys_guid(), 0)
/
insert into KSLU_CLU_SET_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER, OBJ_ID, VER_NBR) values (sys_guid(), 'sisCode', 'Diversity', 'fc35f9fc-2d6d-421e-8239-85eb639b8f48', sys_guid(), 0)
/