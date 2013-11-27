package id.co.mii.cmdbreport.jasper;

import id.co.mii.cmdbreport.bo.BusinessService;
import id.co.mii.cmdbreport.bo.Person;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.SortBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.FillerBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.PaddingBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AppInfoReport extends ReportBuilder {

	public AppInfoReport() {
		super();
		reportBuilder = new JasperReportBuilder();
		this.title = "Application Information";
	}
	
	@Override
	public void build() {
		PaddingBuilder paddingBuilder = Styles.padding(5);
		StyleBuilder bottomBorder = 
				Styles.style().setVerticalAlignment(VerticalAlignment.MIDDLE).setBottomBorder(Styles.pen1Point());
		StyleBuilder boldStyle = Styles.style().bold().setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(paddingBuilder);
		StyleBuilder leftBorder = Styles.style().setLeftBorder(Styles.pen1Point());
		FillerBuilder theFiller = Components.filler().setStyle(leftBorder);
		
		FieldBuilder<String> appNameField = DynamicReports.field("applicationName", DataTypes.stringType());
		FieldBuilder<String> appTypeField = DynamicReports.field("applicationType", DataTypes.stringType());
		FieldBuilder<String> descField = DynamicReports.field("description", DataTypes.stringType());
		FieldBuilder<String> versionField = DynamicReports.field("version", DataTypes.stringType());
		FieldBuilder<String> servicePackField = DynamicReports.field("servicePack", DataTypes.stringType());
		FieldBuilder<String> patchNumberField = DynamicReports.field("patchNumber", DataTypes.stringType());
		FieldBuilder<Person> ownerDeptField = DynamicReports.field("applicationOwner", Person.class);
		FieldBuilder<String> ownerMailField = DynamicReports.field("ownerEmail", DataTypes.stringType());
		FieldBuilder<String> hostingField = DynamicReports.field("serverHosting", DataTypes.stringType());
		FieldBuilder<String> appIntegrationField = DynamicReports.field("applicationIntegration", DataTypes.stringType());
		FieldBuilder<BusinessService> bussServiceField = DynamicReports.field("businessService", BusinessService.class);		
		FieldBuilder<Integer> totalPhysicalField = DynamicReports.field("totalPhysicalServer", DataTypes.integerType());
		
		HorizontalListBuilder builder = Components.horizontalFlowList().setStyle(bottomBorder);
		builder.add(Components.text("Application Name:").setStyle(boldStyle).setWidth(35));
		builder.add(Components.text(appNameField));
		
		builder.add(theFiller);
		builder.add(Components.text("Application Type:").setStyle(boldStyle).setWidth(35));
		builder.add(Components.text(appTypeField));
				
		builder.newFlowRow().setStyle(bottomBorder);
		builder.add(Components.text("Description:").setStyle(boldStyle).setWidth(15));
		builder.add(Components.hListCell(Components.text(descField)));
		
		builder.newFlowRow().setStyle(bottomBorder);
		builder.add(Components.text("App Version:").setStyle(boldStyle).setWidth(42));
		builder.add(Components.hListCell(Components.text(versionField)).widthExpand());
		builder.add(theFiller);
		builder.add(Components.text("Service Pack:").setStyle(boldStyle).setWidth(45));
		builder.add(Components.hListCell(Components.text(servicePackField)));
		builder.add(theFiller);
		builder.add(Components.text("Patch Number:").setStyle(boldStyle).setWidth(45));
		builder.add(Components.hListCell(Components.text(patchNumberField)));
		
		builder.newFlowRow().setStyle(bottomBorder);
		builder.add(Components.text("Owner Department:").setStyle(boldStyle).setWidth(40));
		builder.add(Components.hListCell(Components.text(ownerDeptField)));
		builder.add(theFiller);
		builder.add(Components.text("Owner Email:").setStyle(boldStyle).setWidth(30));
		builder.add(Components.hListCell(Components.text(ownerMailField)));
		
		builder.newFlowRow().setStyle(bottomBorder);
		builder.add(Components.text("Server Hosting:").setStyle(boldStyle).setWidth(20));
		builder.add(Components.hListCell(Components.text(hostingField)).widthFloat());
		
		builder.newFlowRow();
		builder.add(Components.text("Total Physical Server: ").setStyle(boldStyle).setWidth(30));
		builder.add(Components.text(totalPhysicalField).setHorizontalAlignment(HorizontalAlignment.LEFT).setWidth(40));
		builder.add(theFiller);
		builder.add(Components.text("App Integrations: ").setStyle(boldStyle).setWidth(30));
		builder.add(Components.hListCell(Components.text(appIntegrationField)));
		
		ComponentColumnBuilder col1 = Columns.componentColumn(builder);
	
		reportBuilder.addColumn(col1);
		reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		reportBuilder.setTemplate(Templates.reportTemplate);
		reportBuilder.addPageFooter(BtpnTemplates.footerComponent);
		reportBuilder.addTitle(BtpnTemplates.createTitleComponent(bussServiceField));
		reportBuilder.addPageHeader(BtpnTemplates.createPageHeaderComponent(this.title));
		reportBuilder.setDataSource(new JRBeanCollectionDataSource(dataSource));
		reportBuilder.setTemplate(BtpnTemplates.reportTemplate);
		reportBuilder.noData(BtpnTemplates.createNoDataComponent(title));
		
	}

	@Override
	public void printToPdf() {
		try {
			reportBuilder.toPdf(new FileOutputStream("E:\\Temp\\app_report.pdf"));
		} catch (FileNotFoundException | DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
