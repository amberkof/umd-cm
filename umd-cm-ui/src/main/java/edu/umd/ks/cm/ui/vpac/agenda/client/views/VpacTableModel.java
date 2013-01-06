package edu.umd.ks.cm.ui.vpac.agenda.client.views;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.util.ExportElement;

import edu.umd.ks.cm.ui.common.client.util.ExportElementUMD;

public class VpacTableModel {

	List<VpacTableRow> rows = new ArrayList<VpacTableRow>();
	
	/**
	 * This method will convert our VpacTableModel into a jasper
	 * model.
	 * @return
	 */
	public List<ExportElement> toJasperModel(){
		List<ExportElement> jasperRows = new ArrayList<ExportElement>();
		
		// define the headers
		ExportElementUMD header=new ExportElementUMD();
		
		// Put the header strings into the jasper model
		header.setHeader1("Curriculum Oversight Unit");
		header.setHeader2("Course Code");
		header.setHeader3("Course Title");
		header.setHeader4("Proposal Type");
		header.setFieldValue(""+getRows().size());
		jasperRows.add(header);
		
		// Put the rows into the jasper model subsets. The Subsets contain the search results for each column
		for(VpacTableRow row:getRows())
		{
			ExportElementUMD searchResultElement= new ExportElementUMD();
			//iterates over each column of the row, determining which subset to add the search result to
			for(int i=0;i<4;i++)
			{
				switch(i)
				{
					case 0:searchResultElement.setHeader1(row.getCurriculumOversightUnit());
					break;
					case 1:searchResultElement.setHeader2(row.getCourseCode());
					break;
					case 2:searchResultElement.setHeader3(row.getCourseTitle());
					break;
					default:searchResultElement.setHeader4(row.getProposalType());
					break;
				}
			}
			jasperRows.add(searchResultElement);
		}
		return jasperRows;
	}
	public List<VpacTableRow> getRows() {
		return rows;
	}

	public void setRows(List<VpacTableRow> rows) {
		this.rows = rows;
	}
}
