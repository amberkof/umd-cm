--Delete some vestigial lrc data that was causing problems with variable credits
delete from KSLR_RESCOMP_JN_RESVALUE where KSLR_RESCOMP_JN_RESVALUE.RESULT_ID like'kuali.creditType.credit.degree.%'
/
delete from KSLR_RESULT_VALUE where KSLR_RESULT_VALUE.RSLT_COMP_ID  like'kuali.creditType.credit.degree.%'
/
delete from KSLR_RESCOMP_ATTR where KSLR_RESCOMP_ATTR.OWNER like'kuali.creditType.credit.degree.%'
/
delete from KSLR_RESCOMP where KSLR_RESCOMP.ID like'kuali.creditType.credit.degree.%'
/