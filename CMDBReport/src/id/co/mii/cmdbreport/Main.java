package id.co.mii.cmdbreport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.co.mii.cmdbreport.jasper.AppInfoReport;
import id.co.mii.cmdbreport.jasper.DatabaseInfoReport;
import id.co.mii.cmdbreport.jasper.NetworkInfoReport;
import id.co.mii.cmdbreport.jasper.ReportBuilder;
import id.co.mii.cmdbreport.jasper.ServerInfoReport;

import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.AttachmentValue;
import com.bmc.arsys.api.Constants;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.bmc.cmdb.api.CMDBInstanceList;

public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
	static int statusFieldId;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("properties/log4j.properties");
		SystemProperties.loadProperties("properties/cmdbreport.properties");
		statusFieldId = SystemProperties.getInt("ARS.fieldid.statusjob");//TODO: jangan lupa diupdate sebelum upload ke production
		
		Main prog = new Main();
		System.out.println(prog.getClass().getProtectionDomain().getCodeSource().getLocation());		
		CMDBReport factory = new CMDBReport();
		factory.connect();
		
		CMDBInstanceList instanceList = null;
		String startNodeId = args[0];
		String requestId = args[1];
		
		try {
			prog.updateJobStatus(factory.getContext(), requestId, "In Progress");
			instanceList = factory.walkTheGraph(startNodeId,4);
			factory.classifyInstance(instanceList);			
		} catch (ARException e) {
			e.printStackTrace();
			prog.updateJobStatus(factory.getContext(), requestId, "Error when reading CMDB");
			
		}	
		
		//concatenate report
		prog.updateJobStatus(factory.getContext(), requestId, "Creating PDF file");
		JasperConcatenatedReportBuilder concatenatedReportBuilder = new JasperConcatenatedReportBuilder();
		concatenatedReportBuilder.concatenate(				
				prog.createReport(new AppInfoReport(), factory.getApplicationInfos()),
				prog.createReport(new ServerInfoReport(), factory.getServerList()),
				prog.createReport(new DatabaseInfoReport(), factory.getDatabaseInfos()),
				prog.createReport(new NetworkInfoReport(), factory.getNetworkInfos())
				);
		concatenatedReportBuilder.setContinuousPageNumbering(true);
		
		try {
			File file = new File(SystemProperties.getString("ARS.report.output")+"\\"+
					factory.getBusinessService().getName()+"_report.pdf");
			concatenatedReportBuilder.toPdf(new FileOutputStream(file));
			log.info("Exporting  file : "+file.getAbsolutePath());
//			prog.createAttachment(factory.getContext(), file,requestId);
			
		} catch (FileNotFoundException | DRException e) {
			e.printStackTrace();
		}
		
	}
	
	private JasperReportBuilder createReport(ReportBuilder builder, List source){
		builder.setDataSource(source);
		builder.build();		
		try {
			if (SystemProperties.getBoolean("ARS.report.preview", false))
				builder.getReportBuilder().show();
		} catch (DRException e) {
			e.printStackTrace();
		}
		return builder.getReportBuilder();
	}
	
	private void createAttachment(ARServerUser context, File file, String requestId) {
		
		int fieldId = SystemProperties.getInt("ARS.fieldid.attachment"); //TODO: jangan lupa diupdate sebelum upload ke production
		try {
			
			AttachmentValue attachmentValue = new AttachmentValue(file.getAbsolutePath());
			attachmentValue.setLocationType(AttachmentValue.AR_LOC_BUFFER);
			attachmentValue.setName(file.getName());
						
			Entry entry = new Entry();
			entry.put(fieldId, new Value(attachmentValue));
			context.setEntry("BTPN:JobFormAPI", requestId, entry, new Timestamp(), Constants.AR_JOIN_SETOPTION_REF);
			updateJobStatus(context, requestId, "Completed");
		} catch (ARException | IOException e) {
			e.printStackTrace();
			updateJobStatus(context, requestId, "Error");			
		}
	}

	private void updateJobStatus(ARServerUser context, String requestId, String status) {
		Entry entry = new Entry();
		entry.put(statusFieldId, new Value(status));
		try {
			context.setEntry("BTPN:JobFormAPI", requestId, entry,new Timestamp(), Constants.AR_JOIN_SETOPTION_REF);
		} catch (ARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
