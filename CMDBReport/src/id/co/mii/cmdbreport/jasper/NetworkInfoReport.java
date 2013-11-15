package id.co.mii.cmdbreport.jasper;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class NetworkInfoReport extends ReportBuilder {

	public NetworkInfoReport() {
		super();
		reportBuilder = new JasperReportBuilder();
		this.title = "Network Information";
	}
	
	@Override
	public void build() {
		FieldBuilder<String> hostnameField = 
			DynamicReports.field("hostname", DataTypes.stringType());
		FieldBuilder<String> ipAddressField = 
				DynamicReports.field("ipAddress", DataTypes.stringType());
		FieldBuilder<String> serialNumberField = 
				DynamicReports.field("serialNumber", DataTypes.stringType());
		FieldBuilder<String> modelField = 
				DynamicReports.field("model", DataTypes.stringType());
		FieldBuilder<String> manufacturerField = 
				DynamicReports.field("manufactureName", DataTypes.stringType());
		FieldBuilder<String> typeField = 
				DynamicReports.field("type", DataTypes.stringType());
		
		
		TextColumnBuilder<String> hostnameColumn = Columns.column("Hostname", hostnameField);
		
		reportBuilder.addColumn(Columns.reportRowNumberColumn("No").setWidth(20));
		reportBuilder.addColumn(hostnameColumn);
		reportBuilder.addColumn(Columns.column("Type", typeField));
		reportBuilder.addColumn(Columns.column("IP Address", ipAddressField));
		reportBuilder.addColumn(Columns.column("Serial Number", serialNumberField));
		reportBuilder.addColumn(Columns.column("Model", modelField));
		reportBuilder.addColumn(Columns.column("Manufacturer Name", manufacturerField));
		reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		
		reportBuilder.setTemplate(BtpnTemplates.reportTemplate);
		reportBuilder.addPageHeader(BtpnTemplates.createPageHeaderComponent(title));
		reportBuilder.setDataSource(new JRBeanCollectionDataSource(dataSource));
		reportBuilder.sortBy(hostnameColumn);
		reportBuilder.noData(BtpnTemplates.createNoDataComponent(title));
	}

	@Override
	public void printToPdf() {		
	}
}
