package edu.umd.ks.cm.ui.common.client.util;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.util.ExportElement;

public class ExportElementUMD extends ExportElement {

	protected String header1;
	protected String header2;
	protected String header3;
	protected String header4;

	public ExportElementUMD() {
		super();
	}

	public String getHeader1() {
		return header1;
	}
	public void setHeader1(String header1) {
		this.header1 = header1;
	}
	public String getHeader2() {
		return header2;
	}
	public void setHeader2(String header2) {
		this.header2 = header2;
	}
	public String getHeader3() {
		return header3;
	}
	public void setHeader3(String header3) {
		this.header3 = header3;
	}
	public String getHeader4() {
		return header4;
	}
	public void setHeader4(String header4) {
		this.header4 = header4;
	}

	@Override
	public boolean isDataEmpty() {
		if (super.isDataEmpty()==true && header1==null && header2==null && header3==null && header4==null) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isEmpty() {
		if (super.isEmpty()==true &&  header1==null && header2==null && header3==null && header4==null) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + " - " + header1 + " - " + header2 + " - " + header3 + " - " + header4;
	}

}
