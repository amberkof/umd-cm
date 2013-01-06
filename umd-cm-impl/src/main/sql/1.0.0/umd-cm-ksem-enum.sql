delete from ksem_enum_val_t where obj_id = '74923eab-dbfe-4402-857d-08d235861ae2'
/
delete from ksem_enum_val_t where obj_id = '2f647ed9-635d-4b8f-85be-3f773150f878'
/
delete from ksem_enum_val_t where obj_id = '2a4b46f4-0383-4d5b-8398-6b43d990707a'
/
delete from ksem_enum_val_t where obj_id = '0a9d3b14-1e2a-4d51-96e5-1d3e36142a3b'
/
delete from ksem_enum_t where obj_id = 'a4d8a707-f19a-4939-afcf-c730c9c5734c'
/
insert into ksem_enum_t (enum_key, descr, eff_dt, expir_dt, name, obj_id, ver_nbr)
values ('kuali.enum.rule.gradePointValue', 'Grade Point Value Enumeration', sysdate, null, 'Grade Point Value Enumeration', 'a4d8a707-f19a-4939-afcf-c730c9c5734c', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('6cde0265-40a7-4b82-89d9-866269603313', 'GP 1.0', '1.0', sysdate, null, '1', '1.0', 'kuali.enum.rule.gradePointValue', '74923eab-dbfe-4402-857d-08d235861ae2', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('bf00ef80-dfc5-4f90-9cb0-50e76cfd5c40', 'GP 2.0', '2.0', sysdate, null, '2', '2.0', 'kuali.enum.rule.gradePointValue', '2f647ed9-635d-4b8f-85be-3f773150f878', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('915cc795-dfb2-4566-8083-3e0ab2e37b3c', 'GP 3.0', '3.0', sysdate, null, '3', '3.0', 'kuali.enum.rule.gradePointValue', '2a4b46f4-0383-4d5b-8398-6b43d990707a', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('5de9c1cc-bdd6-4db6-8040-038cd2254fc6', 'GP 4.0', '4.0', sysdate, null, '4', '4.0', 'kuali.enum.rule.gradePointValue', '0a9d3b14-1e2a-4d51-96e5-1d3e36142a3b', '0')
/

delete from ksem_enum_val_t where obj_id = '83e36c45-a6fc-45e6-a9e1-38c955fdb821'
/
delete from ksem_enum_val_t where obj_id = '8c80391a-c602-4c1a-aaa2-b226591135f8'
/
delete from ksem_enum_val_t where obj_id = '2729509a-4dc4-439e-b350-8dd507a75be8'
/
delete from ksem_enum_val_t where obj_id = 'c0bc7c0a-bb7a-49b5-a3dc-75301d5c3b2b'
/
delete from ksem_enum_t where obj_id = '0f0626c3-cfe5-4077-b62c-e79e62cb0143'
/
insert into ksem_enum_t (enum_key, descr, eff_dt, expir_dt, name, obj_id, ver_nbr)
values ('kuali.enum.rule.standing', 'Class Standing Values', sysdate, null, 'Class Standing Values Enumeration', '0f0626c3-cfe5-4077-b62c-e79e62cb0143', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('314e14a7-0da1-44b3-8575-b2fe3de89524', 'Class Standing Freshman', '01', sysdate, null, '1', 'Freshman', 'kuali.enum.rule.standing', 'c0bc7c0a-bb7a-49b5-a3dc-75301d5c3b2b', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('54b61c89-c7b2-438a-97ae-36344553ebe1', 'Class Standing Sophomore', '02', sysdate, null, '2', 'Sophomore', 'kuali.enum.rule.standing', '2729509a-4dc4-439e-b350-8dd507a75be8', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('4590151e-34fd-4b72-a392-8470378818ec', 'Class Standing Junior', '03', sysdate, null, '3', 'Junior', 'kuali.enum.rule.standing', '8c80391a-c602-4c1a-aaa2-b226591135f8', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('e5ff5177-9627-40f6-98e3-d614a4e9c6be', 'Class Standing Senior', '04', sysdate, null, '4', 'Senior', 'kuali.enum.rule.standing', '83e36c45-a6fc-45e6-a9e1-38c955fdb821', '0')
/

delete from ksem_enum_val_t where obj_id = '8d047666-51b8-410b-8484-07a9a939cca5'
/
delete from ksem_enum_val_t where obj_id = '74a32791-d0a2-4e29-b86a-dfa9fa093560'
/
delete from ksem_enum_val_t where obj_id = '8e7abe8b-1aaf-4b90-8efb-e700946fe54a'
/
delete from ksem_enum_t where obj_id = 'a1b153c0-43d2-41f4-9eeb-7ea086153d44'
/
insert into ksem_enum_t (enum_key, descr, eff_dt, expir_dt, name, obj_id, ver_nbr)
values ('kuali.enum.rule.operatorOption', 'Operator options', sysdate, null, 'Operator options Enumeration', 'a1b153c0-43d2-41f4-9eeb-7ea086153d44', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('1292dc8b-c884-4814-ab81-f0677980b2cb', 'Operator option or higher', 'or higher', sysdate, null, '1', 'or higher', 'kuali.enum.rule.operatorOption', '8e7abe8b-1aaf-4b90-8efb-e700946fe54a', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('0c5e88c8-deb7-4e3f-bd29-fecd7188912b', 'Operator option or lower', 'or lower', sysdate, null, '2', 'or lower', 'kuali.enum.rule.operatorOption', '74a32791-d0a2-4e29-b86a-dfa9fa093560', '0')
/
insert into ksem_enum_val_t (id, abbrev_val, cd, eff_dt, expir_dt, sort_key, val, enum_key, obj_id, ver_nbr )
values ('8f11217c-302e-4a8f-b092-cb033a9935c1', 'Operator option equal to', 'equal to', sysdate, null, '3', 'equal to', 'kuali.enum.rule.operatorOption', '8d047666-51b8-410b-8484-07a9a939cca5', '0')
/

--kscm-754
delete from ksem_enum_val_t where obj_id = '8C5FFB93-1E04-967B-95A5-CAB70AA7B554'
/
delete from ksem_enum_val_t where obj_id = 'DECCB9D9-FF9F-C5C0-C923-791FC93F9866'
/
delete from ksem_enum_val_t where obj_id = 'E30FB83E-3051-A5FF-9E9B-541EB4130D9B'
/
delete from ksem_enum_val_t where obj_id = '49D3E559-5417-8FE3-DC7A-50D311C6404E'
/
delete from ksem_enum_val_t where obj_id = '3D3B52A4-FDC0-6756-55F4-712430E480A2'
/
delete from ksem_enum_val_t where obj_id = '884D61D1-126A-06EF-99AE-072A2FA797EE'
/
delete from ksem_enum_val_t where obj_id = '44E07ED5-D79D-4075-087B-2D65980C0C18'
/
delete from ksem_enum_val_t where obj_id = 'E3EE507F-2D89-B508-142D-104EF5AE21FE'
/
delete from ksem_enum_val_t where obj_id = 'AF582CC7-566F-B6CE-EBA7-585B17874326'
/
delete from ksem_enum_val_t where obj_id = 'F2F22D21-852C-6DCA-1059-511FE85E47B6'
/
delete from ksem_enum_val_t where obj_id = 'A71836E6-EE6F-8148-34BA-D115A91A7B0C'
/
delete from ksem_enum_val_t where obj_id = '0C00099C-1BDA-4AC1-AE96-CD02D71F8CC3'
/
delete from ksem_enum_val_t where obj_id = '24FA3EF8-EF1E-0168-9653-0664350A9D8A'
/
delete from ksem_enum_val_t where obj_id = 'B295CC3F-5586-CEBE-32B7-8D439E3CD92B'
/
delete from ksem_enum_val_t where obj_id = '4DA0B37C-23B4-6C9A-1871-513975C21E44'
/
delete from ksem_enum_val_t where obj_id = 'AAB3C520-5B54-BEA6-343E-393F1C38BA7E'
/
delete from ksem_enum_val_t where obj_id = '6C542682-5FE2-198B-3388-2DB430EE9B46'
/
delete from ksem_enum_t where obj_id = '6D538D15-41EE-4F86-6FDE-F23F6DE5E800'
/

insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.enum.type.math.codes', 'Math Codes', TIMESTAMP '2011-11-07 00:00:00', null, 'Math Codes', '6D538D15-41EE-4F86-6FDE-F23F6DE5E800', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) 
values ('a97c1207-13b6-4025-9fe2-64266dac2b99', 'Math Code', '00', TIMESTAMP '2011-11-07 00:00:00', null, 0, 'NONE', 'kuali.enum.type.math.codes', '8C5FFB93-1E04-967B-95A5-CAB70AA7B554', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) 
values ('62c9e7bc-16a5-4e84-9853-ca67195873d6', 'Math Code', '05', TIMESTAMP '2011-11-07 00:00:00', null, 1, 'MATH001', 'kuali.enum.type.math.codes', 'DECCB9D9-FF9F-C5C0-C923-791FC93F9866', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) 
values ('370bbcb1-5f84-4dc5-a4fd-ce6e7d994ff3', 'Math Code', '06', TIMESTAMP '2011-11-07 00:00:00', null, 2, 'MATH003', 'kuali.enum.type.math.codes', 'E30FB83E-3051-A5FF-9E9B-541EB4130D9B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('abd5bf4b-e862-44a8-9bc6-4e2853c2e5ce', 'Math Code', '09', TIMESTAMP '2011-11-07 00:00:00', null, 3, 'MATH010', 'kuali.enum.type.math.codes', '49D3E559-5417-8FE3-DC7A-50D311C6404E', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('4abd992b-5946-441d-a215-682c15308a8b', 'Math Code', '10', TIMESTAMP '2011-11-07 00:00:00', null, 4, 'MATH001', 'kuali.enum.type.math.codes', '3D3B52A4-FDC0-6756-55F4-712430E480A2', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('41223777-c52a-45ab-a572-301e485c4c79', 'Math Code', '13', TIMESTAMP '2011-11-07 00:00:00', null, 5, 'MATH013', 'kuali.enum.type.math.codes', '884D61D1-126A-06EF-99AE-072A2FA797EE', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('61c5acb2-7653-4ea0-a206-b689274f5863', 'Math Code', '14', TIMESTAMP '2011-11-07 00:00:00', null, 6, 'MATH011', 'kuali.enum.type.math.codes', '44E07ED5-D79D-4075-087B-2D65980C0C18', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('306cff56-0608-46b4-85fa-7dc0c778505b', 'Math Code', '15', TIMESTAMP '2011-11-07 00:00:00', null, 7, 'MATH015', 'kuali.enum.type.math.codes', 'E3EE507F-2D89-B508-142D-104EF5AE21FE', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('67129add-1f87-4f9d-bd75-fc9bbcda3fb0', 'Math Code', '20', TIMESTAMP '2011-11-07 00:00:00', null, 8, 'MATH002', 'kuali.enum.type.math.codes', 'AF582CC7-566F-B6CE-EBA7-585B17874326', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('327feb19-b082-4381-a703-a5f821342a88', 'Math Code', '30', TIMESTAMP '2011-11-07 00:00:00', null, 9, 'MATH110', 'kuali.enum.type.math.codes', 'F2F22D21-852C-6DCA-1059-511FE85E47B6', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('ee1fe34f-539f-4058-a0fc-6fc8540593c1', 'Math Code', '32', TIMESTAMP '2011-11-07 00:00:00', null, 10, 'MATH112', 'kuali.enum.type.math.codes', 'A71836E6-EE6F-8148-34BA-D115A91A7B0C', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('53509570-11cd-4e91-8e62-7429184cdd2c', 'Math Code', '35', TIMESTAMP '2011-11-07 00:00:00', null, 11, 'MATH113', 'kuali.enum.type.math.codes', '0C00099C-1BDA-4AC1-AE96-CD02D71F8CC3', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('ce630170-41f6-4d68-a6e0-789ad3ae4c54', 'Math Code', '40', TIMESTAMP '2011-11-07 00:00:00', null, 12, 'STAT100', 'kuali.enum.type.math.codes', '24FA3EF8-EF1E-0168-9653-0664350A9D8A', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('d9fe9c57-1691-40b7-b8bf-f9e3b5a09633', 'Math Code', '50', TIMESTAMP '2011-11-07 00:00:00', null, 13, 'MATH111', 'kuali.enum.type.math.codes', 'B295CC3F-5586-CEBE-32B7-8D439E3CD92B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('d3d271da-767c-4871-a892-dfc525d5e53c', 'Math Code', '60', TIMESTAMP '2011-11-07 00:00:00', null, 14, 'MATH115', 'kuali.enum.type.math.codes', '4DA0B37C-23B4-6C9A-1871-513975C21E44', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('193571da-7783-4728-ac88-2e35c4c2b272', 'Math Code', '70', TIMESTAMP '2011-11-07 00:00:00', null, 15, 'MATH220', 'kuali.enum.type.math.codes', 'AAB3C520-5B54-BEA6-343E-393F1C38BA7E', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('9a72b82b-4b66-42a7-8f70-23ecb5e6bc6f', 'Math Code', '80', TIMESTAMP '2011-11-07 00:00:00', null, 16, 'MATH140', 'kuali.enum.type.math.codes', '6C542682-5FE2-198B-3388-2DB430EE9B46', 1)
/

delete from ksem_enum_val_t  where enum_key = 'kuali.enum.activity.creditRatio'
/
delete from ksem_enum_t  where enum_key = 'kuali.enum.activity.creditRatio'
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.enum.activity.creditRatio', 'Credit Ratios for Activities', '01/JAN/00', null, 'Credit Ratio', '18C3C227-7E90-FF0B-4D52-8B2D92331CEA', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('c555fffa-2089-4bf4-ae69-064dfbfecf83', 'Lecture or Seminar Credit Ratio', 'kuali.lu.type.activity.LectureORSeminar.ratio', '01/JAN/00', null, 0, '1:1', 'kuali.enum.activity.creditRatio', '9EA16316-5AA7-739F-3DE7-135E2B4CA30E', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('8cbcfa29-ca80-4b79-9f6a-3413374f4395', 'Discussion Credit Ratio', 'kuali.lu.type.activity.Discussion.ratio', '01/JAN/00', null, 1, '1:1', 'kuali.enum.activity.creditRatio', 'F6EBDFE0-5C84-C024-6FD3-6F8F06076269', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('c6c1042f-8028-432f-97a9-51d9488b8548', 'Lab Credit Ratio', 'kuali.lu.type.activity.Lab.ratio', '01/JAN/00', null, 2, '1:2', 'kuali.enum.activity.creditRatio', '1B4FB5B8-48A8-B492-D8F7-A92E2A0E597C', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('c099dedb-b3ea-4125-870f-81a458949ab8', 'Experiental Learning/Other Credit Ratio', 'kuali.lu.type.activity.ExperientialLearningOROther.ratio', '01/JAN/00', null, 3, '1:3', 'kuali.enum.activity.creditRatio', '77871FA1-83E3-1651-3802-D1A34DDA1C7A', 1)
/

delete from ksem_enum_val_t  where enum_key = 'kuali.enum.semesterType'
/
delete from ksem_enum_t  where enum_key = 'kuali.enum.semesterType'
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.enum.semesterType', 'Semester Types', '01/JAN/00', null, 'Semester Type', 'B0503DCC-E430-B11C-B234-94075583E12C', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('81b56ee8-88d5-435f-8ec3-2e26fde03067', 'Standard Semester (15 weeks)', 'semesterType.standard', '01/JAN/00', null, 0, 'Standard Semester (15 weeks)', 'kuali.enum.semesterType', '9EA16316-5AA7-739F-3DE7-135E2B4CA30E', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('685b99b3-5d91-477d-a932-33684e6b6418', 'Nonstandard Format', 'semesterType.nonStandard', '01/JAN/00', null, 1, 'Nonstandard Format', 'kuali.enum.semesterType', 'F6EBDFE0-5C84-C024-6FD3-6F8F06076269', 1)
/

-- KSCM-646
delete from ksem_enum_val_t  where enum_key = 'umd.cm.enum.coreGeneralEducation'
/
delete from ksem_enum_t  where enum_key = 'umd.cm.enum.coreGeneralEducation'
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('umd.cm.enum.coreGeneralEducation', 'Core and General Education requirements', '01/JAN/00', null, 'Core and General Education', 'A0A04999-83B7-A898-CFB4-B2D42727CCA6', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('1f00d432-5976-45d3-bec8-ad88d7048d89', 'CORE', 'umd.cm.enum.coreGeneralEducation.core', '01/JAN/00', null, 0, 'This course has been approved to fulfill a CORE requirement.', 'umd.cm.enum.coreGeneralEducation', '87A0B4B5-EF4A-0E31-2F17-34EEEC4B4EE0', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('7f123a9e-11a5-4972-abda-df2d36173494', 'General Education', 'umd.cm.enum.coreGeneralEducation.general', '01/JAN/00', null, 1, 'This course has been approved to fulfill a General Education requirement.', 'umd.cm.enum.coreGeneralEducation', 'A7E0FFE4-F671-2CAC-9B71-13A16DDEC589', 1)
/
delete from ksem_enum_val_t  where enum_key = 'umd.cm.enum.PCCInformation'
/
delete from ksem_enum_t  where enum_key = 'umd.cm.enum.PCCInformation'
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('umd.cm.enum.PCCInformation', 'PCC Information', '01/JAN/00', null, 'PCC Information', '8755C89D-2F46-7889-5011-E5B2D9E72C49', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('e91bd58f-5ab2-403c-99e4-60da663af60b', 'Proposal submitted', 'umd.cm.enum.PCCInformation.proposal.submitted', '01/JAN/00', null, 0, 'Yes - PCC proposal submitted', 'umd.cm.enum.PCCInformation', '185027CC-A2D1-1A98-2C0E-B4ECCD33DC7F', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('60d5d0da-c9cd-4479-b0f4-45bf80c83ffd', 'Proposal not submitted', 'umd.cm.enum.PCCInformation.proposal.not.submitted', '01/JAN/00', null, 1, 'Yes - PCC proposal not submitted', 'umd.cm.enum.PCCInformation', 'CCB9173A-67AE-F082-6F3C-7C0109540FA9', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('5b5fbc48-ec91-4443-ae1e-2c208262ef32', 'No proposal', 'umd.cm.enum.PCCInformation.proposal.no', '01/JAN/00', null, 2, 'No', 'umd.cm.enum.PCCInformation', 'E3CE43CC-B4E7-2324-00F7-BE05EF296D1E', 1)
/
