-- KSCM-760
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.course.prefix")' 
where   owner = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.course.prefix")' 
where   owner = 'kuali.reqComponent.type.course.prefix.courses.completed.nof'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/

-- KSCM-1338
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.all' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))Must have completed $courseCluSet.getCluSetAsCode()#{else}Must have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c))#end#if($intValue > 1)$intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}$intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.nof' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.one' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.one' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.one' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.all' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.all' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.all' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.one' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.one' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'completed or be concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.enrolled.one' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must have earned a minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum grade of $grade in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseCluSet.getCluSetAsCode()#{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.no.completed.min'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #{else}Must have earned a minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.no.completed.min'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}each course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.no.completed.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must not have earned a grade of $grade or higher in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.grade.max' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must not have completed $courseCluSet.getCluSetAsCode()#{else}Must not have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must not have completed#{else}Must not have completed #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must not have completed #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}any courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.completed.none' 
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Must be concurrently enrolled in #if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end'
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.nof'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl   
set     template = 'Must be concurrently enrolled in #if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end#if($intValue > 1)a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #end$courses#{else}a minimum of $intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end'
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.nof'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl   
set     template = 'concurrently enrolled in #if(($intValue == 1 && $courseCluSet.getCluList().size() == 1) || ($courseCluSet.getCluList().size() <= 1))$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#if($intValue > 1)$intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode()#{else}#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#end#{else}$intValue $NLHelper.getProperGrammar($intValue, "course") from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end'
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.nof'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.all'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.all'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'concurrently enrolled in #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.enrolled.all'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end recommended' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end recommended' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", and"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" and"+$courseCluSet.getCluSetAsCode().substring($c))#end$courses#{else}all courses from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.all'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range $courseCluSet.getCluSetAsCode()#end#end recommended' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
and     nl_usuage_type_key = 'KUALI.RULE'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'Completion of #if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#set($d=$courseCluSet.getCluSetAsCode().lastIndexOf(")"))#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(1,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c, $d))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end recommended' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
and     nl_usuage_type_key = 'KUALI.RULE.PREVIEW'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '#if($courseCluSet.getCluList().size() <= 1)$courseCluSet.getCluSetAsCode()#{else}#if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")#set($a=$courseCluSet.getCluSetAsCode().lastIndexOf(","))#set($b=1)#set($c=$a+$b)#if($courseCluSet.getCluList().size() > 2)#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+", or"+$courseCluSet.getCluSetAsCode().substring($c))#{else}#set($courses=$courseCluSet.getCluSetAsCode().substring(0,$a)+" or"+$courseCluSet.getCluSetAsCode().substring($c))#end$courses#{else}1 course from #set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.recommended.completed.one'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/

-- KSCM-1460
update  ksst_req_com_type_nl_tmpl 
set     template = 'permission of instructor' 
where   owner = 'kuali.reqComponent.type.course.permission.instructor.required.preco'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'permission of instructor' 
where   owner = 'kuali.reqComponent.type.course.permission.instructor.required'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit") from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.credits.completed.nof'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = '$intValue $NLHelper.getProperGrammar($intValue, "course") with a minimum grade of $grade from #if($courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision") == "")$courseCluSet.getCluSetAsCode()#{else}#set($prefix=${courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalDivision").toUpperCase()})$prefix$courseCluSet.getQueryValueFromParam("lu.queryParam.luOptionalCrsNoRange") course range#end' 
where   owner = 'kuali.reqComponent.type.course.courseset.nof.grade.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'completed or be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.completed.enrolled'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must have math eligibility of $fields.get("kuali.reqComponent.field.type.math.elig.codes") or higher' 
where   owner = 'kuali.reqComponent.type.math.elig'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum grade point value of $fields.get("kuali.reqComponent.field.type.gradePoint") in $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.course.no.completed.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'students who have taken courses with similar or comparable course content may contact the department' 
where   owner = 'kuali.reqComponent.type.course.content.dept.contact'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end' 
where   owner = 'kuali.reqComponent.type.course.permission.org.required.preco'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must be in a major #if($org.getAttributes().get("umType") == "Academic Department")within $org.getLongName() department#{else}in $org.getLongName()#end' 
where   owner = 'kuali.reqComponent.type.major.org'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum cumulative GPA of $gpa' 
where   owner = 'kuali.reqComponent.type.course.cumulative.gpa.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must have earned a minimum of $intValue $NLHelper.getProperGrammar($intValue, "credit")' 
where   owner = 'kuali.reqComponent.type.course.credits.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'permission of #if($org.getAttributes().get("umType") == "Academic Department")$org.getLongName() department#{else}$org.getLongName()#end' 
where   owner = 'kuali.reqComponent.type.course.permission.org.required'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'minimum score of $fields.get(''kuali.reqComponent.field.type.test.score'') on $testCluSet.getCluSetAsLongName()' 
where   owner = 'kuali.reqComponent.type.course.test.score.min'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must not be in #if($programCluSet.getCluList().size() > 1)any of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.notadmitted'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must be in #if($programCluSet.getCluList().size() > 1)one of the following programs #end$NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet,";") #if($programCluSet.getCluList().size() == 1)program#end' 
where   owner = 'kuali.reqComponent.type.course.program.admitted'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.enrolled'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
update  ksst_req_com_type_nl_tmpl 
set     template = 'must not have completed $courseClu.getOfficialIdentifier().getCode()' 
where   owner = 'kuali.reqComponent.type.course.notcompleted'
and     nl_usuage_type_key = 'KUALI.RULE.CATALOG'
/
-- KSCM-1717
delete from ksst_req_com_type_nl_tmpl where nl_usuage_type_key = 'KUALI.RULE.CATALOG.PREFIX'
/
