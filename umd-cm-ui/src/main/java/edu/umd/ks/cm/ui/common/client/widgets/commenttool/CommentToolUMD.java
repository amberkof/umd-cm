package edu.umd.ks.cm.ui.common.client.widgets.commenttool;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.core.comments.ui.client.widgets.commenttool.CommentTool;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTML;

public class CommentToolUMD extends CommentTool {

    private boolean editModeViewComment = false;

    public CommentToolUMD(Enum<?> viewEnum, String viewName, String commentTypeKey, String title) {
        super(viewEnum, viewName, commentTypeKey, title);
    }
    
    @Override
    protected void setCommentsWarningText(VerticalFlowPanel contentPanel)
    {
    	htmlLabel = new HTML("<b>All comments posted here will be visible " +
    			"to authors and reviewers during the approval process, and will be visible " +
    			"to all system users after the approval process, whether the proposal is approved, " +
    			"cancelled, withdrawn or rejected.</b>");
    	contentPanel.add(htmlLabel);
    }
    
    
    
    //Used to get the number of comments that will be used in the Comments(x) row 
    //UMD only wants to include non decision type comments in the count
    @Override
    protected int getDecisionsCommentsCount(List<CommentInfo> commentInfos)
    {
    	int countOfDecisionComments=0;
    	for(CommentInfo comment:commentInfos)
    	{
    		if(!comment.getType().startsWith("kuali.comment.type.workflowDecisionRationale"))
    			countOfDecisionComments++;
    	}
    	return countOfDecisionComments;
    }
    
    //Configures that table cell that displays past comment text
    @Override
    protected KSLabel commentTextLabelProperties(RichTextInfo commentRT)
    {
    	String commentText = commentRT.getPlain();
    	KSLabel commentTextLabel = new KSLabel(commentText);
        commentTextLabel.getElement().getStyle().setPaddingRight(20d, Style.Unit.PX);
        commentTextLabel.setWidth("360px");
        commentTextLabel.getElement().getStyle().setProperty("wordWrap", "break-word");
        return commentTextLabel;
    }
    
    @Override
    protected void redrawCommentsTable(List<CommentInfo> commentInfos) {
        super.redrawCommentsTable(commentInfos);

        if (isEditModeViewComment()) {
            super.setEditMode(EditMode.VIEW_COMMENT);
        }
    }

    public boolean isEditModeViewComment() {
        return editModeViewComment;
    }

    public void setEditModeViewComment(boolean editModeViewComment) {
        this.editModeViewComment = editModeViewComment;
    }
}
