-- KSCM-2058 Change Dependency Analysis Filters Order and Wording
-- KSCM-2081 fixing filters (renaming cd on prereq and restrictions)

update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=1, VAL = 'Cross Listing'
where ENUM_KEY='kuali.dependency.course.types' and CD='crossListed'
/
update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=2, VAL = 'Joint Offering'
where ENUM_KEY='kuali.dependency.course.types' and CD='joint'
/
update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=3, VAL = 'Prerequisites', CD='kuali.statement.type.course.academicReadiness.prereq'
where ENUM_KEY='kuali.dependency.course.types' and CD='kuali.statement.type.course.academicReadiness.studentEligibilityPrereq'
/
update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=4, VAL = 'Corequisite'
where ENUM_KEY='kuali.dependency.course.types' and CD='kuali.statement.type.course.academicReadiness.coreq'
/
update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=5, VAL = 'Recommended'
where ENUM_KEY='kuali.dependency.course.types' and CD='kuali.statement.type.course.recommendedPreparation'
/
update CM.KSEM_ENUM_VAL_T 
set SORT_KEY=6, VAL = 'Restrictions', CD='kuali.statement.type.course.academicReadiness.studentEligibility'
where ENUM_KEY='kuali.dependency.course.types' and CD='kuali.statement.type.course.credit.restriction'
/
delete from CM.KSEM_ENUM_VAL_T
where ENUM_KEY='kuali.dependency.course.types' and CD='kuali.statement.type.course.academicReadiness.antireq'
/