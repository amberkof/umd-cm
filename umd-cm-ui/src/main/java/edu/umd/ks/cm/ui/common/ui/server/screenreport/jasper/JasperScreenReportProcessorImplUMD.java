package edu.umd.ks.cm.ui.common.ui.server.screenreport.jasper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.util.ExportElement;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.server.screenreport.jasper.JasperScreenReportProcessorImpl;
import org.kuali.student.common.ui.server.screenreport.jasper.KSCollectionDataSource;
import org.kuali.student.common.ui.server.screenreport.jasper.KSCustomDataSource;
import org.kuali.student.r1.common.assembly.data.Data;
public class JasperScreenReportProcessorImplUMD extends JasperScreenReportProcessorImpl {

		final Logger LOG = Logger.getLogger(JasperScreenReportProcessorImpl.class);

		protected static final String PROPERTIES_FILE = "jasper.UMD.properties";

		public JasperScreenReportProcessorImplUMD() {
	        super();
	        try {
	            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
	            jasperProperties.load(inputStream);
	        } catch (FileNotFoundException e) {
	            LOG.error(e);
	        } catch (IOException e) {
	            LOG.error(e);
	        }
	    }
		
		@Override
		protected JasperPrint prepare(String template, String reportTitle, Data dataMap, List<ExportElement> dataList) throws JRException {
	        // Compile base report
			String templateLocation = (String) jasperProperties.get(template);
			String subreportLocation=null;
			String subreportTemplate=null;
			//Allows us to add .DOC .XLS etc. extensions to the jasper template, which allows us to have different
			//templates for different Export File Types.
			if(templateLocation==null){
				//defaults PDF to display the DOC template
				if(exportFileType==ExportUtils.PDF){
					template=""+template+"."+ExportUtils.DOC;
				}
				//sets template to name.template.fileextionsion
				//file extension is initialized in the method that calls prepare
				else{
					template=""+template+"."+exportFileType;
				}
	        	templateLocation= (String) jasperProperties.get(template);
	        }
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(templateLocation);
	      	JasperReport jreport = JasperCompileManager.compileReport(is);

	        // Preparing parameters
	        Map parameters = new HashMap();
	        parameters.put("ReportTitle", reportTitle);

	        // Add Subreport
	        if(subreportLocation==null){
	        	subreportLocation = (String) jasperProperties.get(template + ".subreport");
	        }
	        if (subreportLocation != null) {
	            InputStream subis = this.getClass().getClassLoader().getResourceAsStream(subreportLocation);
	            JasperReport subreport = JasperCompileManager.compileReport(subis);
	            parameters.put("SubReport", subreport);
	        }
	       // Fill the report with the data from the UI.
	        JasperPrint jprint;
	        if (dataMap != null) {
	            jprint = JasperFillManager.fillReport(jreport, parameters, new KSCustomDataSource(dataMap.iterator()));
	        } else {
	        	KSCollectionDataSource temp=new KSCollectionDataSource(dataList, null);
	            jprint = JasperFillManager.fillReport(jreport, parameters, temp);

	        }
	        return jprint;
	    }
}
