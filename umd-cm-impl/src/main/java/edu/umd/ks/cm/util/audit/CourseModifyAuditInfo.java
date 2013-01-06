package edu.umd.ks.cm.util.audit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.CourseInfo;

@XmlRootElement
public class CourseModifyAuditInfo implements Serializable {

    @XmlElement
    private String userId;

    @XmlElement
    private String cluId;

    @XmlElement
    private CourseInfo courseInfo;

//    @XmlElement
//    private List<StatementTreeViewInfo> statementsList;

    @XmlElement
    private String studentEligibilityNL;

    @XmlElement
    private String prereqNL;

    @XmlElement
    private String coreqNL;

    @XmlElement
    private String recommendedPreparationNL;

    @XmlElement
    private String repeatableNL;

	public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

//    public void setStatementsList(List<StatementTreeViewInfo> statementsList) {
//        this.statementsList = statementsList;
//    }

    public void setStudentEligibilityNL(String studentEligibilityNL) {
		this.studentEligibilityNL = studentEligibilityNL;
	}

	public void setPrereqNL(String prereqNL) {
		this.prereqNL = prereqNL;
	}

	public void setCoreqNL(String coreqNL) {
		this.coreqNL = coreqNL;
	}

	public void setRecommendedPreparationNL(String recommendedPreparationNL) {
		this.recommendedPreparationNL = recommendedPreparationNL;
	}

	public void setRepeatableNL(String repeatableNL) {
		this.repeatableNL = repeatableNL;
	}
}
