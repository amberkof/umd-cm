update KSPR_PROPOSAL_TYPE set name='Course Creation Proposal' where TYPE_KEY='kuali.proposal.type.course.create'
/
update KSPR_PROPOSAL_TYPE set name='Course Creation Admin Proposal' where TYPE_KEY='kuali.proposal.type.course.create.admin'
/
update KSPR_PROPOSAL_TYPE set name='Course Modification Proposal' where TYPE_KEY='kuali.proposal.type.course.modify'
/
update KSPR_PROPOSAL_TYPE set name='Course Modification Admin Proposal' where TYPE_KEY='kuali.proposal.type.course.modify.admin'
/
update KSPR_PROPOSAL_TYPE set name='Course Retirement Proposal' where TYPE_KEY='kuali.proposal.type.course.retire'
/
update KSPR_PROPOSAL_TYPE set name='Program Creation Proposal' where TYPE_KEY='kuali.proposal.type.majorDiscipline.create'
/
update KSPR_PROPOSAL_TYPE set name='Program Modification Proposal' where TYPE_KEY='kuali.proposal.type.majorDiscipline.modify'
/


ALTER TABLE KSPR_PROPOSAL MODIFY (RATIONALE VARCHAR2(400))
/