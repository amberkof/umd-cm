package edu.umd.ks.cm.course.service.assembler;
 
import java.util.Iterator;
import java.util.List;

import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.course.service.assembler.FormatAssembler;
public class FormatAssemblerUMD extends FormatAssembler {
    private BOAssembler<ActivityInfo, CluInfo> activityAssembler;
    private CluService cluService;
    
	private final String ACTIVITY_TYPE_LECTURE = "kuali.lu.type.activity.LectureORSeminar";
	private final String ACTIVITY_TYPE_DISCUSSION = "kuali.lu.type.activity.Discussion";
	private final String ACTIVITY_TYPE_LAB = "kuali.lu.type.activity.Lab";
	private final String ACTIVITY_TYPE_EXPERIENTIAL= "kuali.lu.type.activity.ExperientialLearningOROther";

 
	   
	@Override
	public FormatInfo assemble(CluInfo clu, FormatInfo formatInfo,
			boolean shallowBuild,ContextInfo contextInfo) throws AssemblyException {
		
	    
	    
		FormatInfo format = super.assemble(clu, formatInfo, shallowBuild, contextInfo);
		
		if (format == null){
			return null;
		}
		
		ActivityInfo activityLecture = null;
		ActivityInfo activityDiscussion = null;
		ActivityInfo activityLab = null;
		ActivityInfo activityExperiential = null;
		
		//Loop through the list of activities and remove special types to sort
		List<ActivityInfo> activites = format.getActivities();
		for(Iterator<ActivityInfo> iter = activites.iterator();iter.hasNext();){
			
			ActivityInfo activity = iter.next();
			
			if(ACTIVITY_TYPE_LECTURE.equals(activity.getTypeKey())){
				activityLecture = activity;
				iter.remove();
			}else if(ACTIVITY_TYPE_DISCUSSION.equals(activity.getTypeKey())){
				activityDiscussion = activity;
				iter.remove();
			}else if(ACTIVITY_TYPE_LAB.equals(activity.getTypeKey())){
				activityLab = activity;
				iter.remove();
			}else if(ACTIVITY_TYPE_EXPERIENTIAL.equals(activity.getTypeKey())){
				activityExperiential = activity;
				iter.remove();
			}
		}
		
		//Add the rest in order
		if(activityLecture!=null){
			activites.add(activityLecture);
		}
		if(activityDiscussion!=null){
			activites.add(activityDiscussion);
		}
		if(activityLab!=null){
			activites.add(activityLab);
		}
		if(activityExperiential!=null){
			activites.add(activityExperiential);
		}
		
		return format;
	}
}
