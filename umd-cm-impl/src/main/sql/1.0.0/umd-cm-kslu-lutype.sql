
DELETE FROM KSLU_LUTYPE WHERE type_key = 'kuali.lu.type.activity.LectureORSeminar'
/
INSERT INTO KSLU_LUTYPE (type_key, type_desc, eff_dt, expir_dt, name, obj_id, dlvr_mthd, instr_frmt, ver_nbr )
VALUES ('kuali.lu.type.activity.LectureORSeminar', 'Instructor presentation of course materials.', '01/JAN/00', NULL, 'Lecture or Seminar', '39f83cee-7473-46ab-80cd-d5876621c5dd', NULL, NULL, '0')
/

DELETE FROM KSLU_LUTYPE WHERE type_key = 'kuali.lu.type.activity.ExperientialLearningOROther'
/
INSERT INTO KSLU_LUTYPE (type_key, type_desc, eff_dt, expir_dt, name, obj_id, dlvr_mthd, instr_frmt, ver_nbr )
VALUES ('kuali.lu.type.activity.ExperientialLearningOROther', 'Individual activity in authentic non-academic setting arranged by instructor.', '01/JAN/00', NULL, 'Experiential Learning/Other', '8074fb30-0604-49c9-b526-3f9d1045dfbe', NULL, NULL, '0')
/