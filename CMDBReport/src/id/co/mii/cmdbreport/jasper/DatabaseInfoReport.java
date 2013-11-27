/**
 * 
 */
package id.co.mii.cmdbreport.jasper;

import id.co.mii.cmdbreport.bo.DatabaseInfo;

import java.util.List;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.SortBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Erwien
 *
 */
public class DatabaseInfoReport extends ReportBuilder {

	List<DatabaseInfo> dbInfos;
	
	public DatabaseInfoReport() {
		super();		
		reportBuilder = new JasperReportBuilder();
		this.title = "Database Information";
	}
	/* (non-Javadoc)
	 * @see id.co.mii.cmdbreport.jasper.ReportBuilder#build()
	 */
	@Override
	public void build() {
		FieldBuilder<String> dbNameField = 
				DynamicReports.field("databaseName", DataTypes.stringType());
		FieldBuilder<String> dbSoftwareNameField = 
				DynamicReports.field("databaseSoftwareName", DataTypes.stringType());
		FieldBuilder<String> dbSoftwareVersionField = 
				DynamicReports.field("databaseSoftwareVersion", DataTypes.stringType());
		FieldBuilder<String> serverHostingField = 
				DynamicReports.field("serverHosting", DataTypes.stringType());
		
		TextColumnBuilder<String> dbNameColumn = Columns.column("Database Name", dbNameField).setStyle(BtpnTemplates.columnStyle);
		
				
		reportBuilder.addColumn(Columns.reportRowNumberColumn("No").setWidth(20).setHorizontalAlignment(HorizontalAlignment.CENTER));
		reportBuilder.addColumn(dbNameColumn);
		reportBuilder.addColumn(Columns.column("Software Name", dbSoftwareNameField));
		reportBuilder.addColumn(Columns.column("Software Version", dbSoftwareVersionField)
				.setHorizontalAlignment(HorizontalAlignment.CENTER));
		reportBuilder.addColumn(Columns.column("Server Hosting", serverHostingField));
		
		reportBuilder.addPageHeader(BtpnTemplates.createPageHeaderComponent(this.title));
		reportBuilder.setTemplate(BtpnTemplates.reportTemplate);
		reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		reportBuilder.setDataSource(new JRBeanCollectionDataSource(dataSource));
		reportBuilder.sortBy(dbNameColumn);
		reportBuilder.noData(BtpnTemplates.createNoDataComponent(this.title));
		reportBuilder.addPageFooter(BtpnTemplates.footerComponent);
				
	}

	/* (non-Javadoc)
	 * @see id.co.mii.cmdbreport.jasper.ReportBuilder#printToPdf()
	 */
	@Override
	public void printToPdf() {
//		handled by Main class

	}

}
