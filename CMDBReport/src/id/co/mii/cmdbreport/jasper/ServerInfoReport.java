package id.co.mii.cmdbreport.jasper;

import id.co.mii.cmdbreport.bo.OperatingSystem;
import id.co.mii.cmdbreport.bo.Person;
import id.co.mii.cmdbreport.bo.ServerInfo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.SortBuilder;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.OrderType;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.StretchType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.components.headertoolbar.actions.SortAction;
import net.sf.jasperreports.components.sort.actions.SortData;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ServerInfoReport extends ReportBuilder {
		
	List<ServerInfo> serverInfos;
	
	public ServerInfoReport() {
		super();
		reportBuilder = new JasperReportBuilder();
	}
	
	public ServerInfoReport(List<ServerInfo> serverInfos){
		this();
		this.serverInfos = serverInfos;
	}
		
	@Override
	public void build(){
		StyleBuilder nameStyle = Styles.style().bold();
//		nameStyle.setVerticalAlignment(VerticalAlignment.TOP);
		nameStyle.setPadding(Styles.padding(1));
		nameStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
		nameStyle.setFontSize(8);
		
		StyleBuilder valueStyle = Styles.style().setHorizontalAlignment(HorizontalAlignment.LEFT);
		valueStyle.setFontSize(8);		
		StyleBuilder verticalAlignTopStyle = Styles.style().setVerticalAlignment(VerticalAlignment.TOP);	
		
		FieldBuilder<String> hostnameField = DynamicReports.field("hostname", DataTypes.stringType());
		FieldBuilder<Person> adminField = DynamicReports.field("serverAdmin", Person.class);
		FieldBuilder<OperatingSystem> osField = DynamicReports.field("os", OperatingSystem.class);
		FieldBuilder<String> modelField = DynamicReports.field("model", DataTypes.stringType());
		FieldBuilder<String> descField = DynamicReports.field("description", DataTypes.stringType());
		FieldBuilder<String> ipAddressField = DynamicReports.field("ipAddress", DataTypes.stringType());
		FieldBuilder<String> cpuField = DynamicReports.field("cpu", DataTypes.stringType());
		FieldBuilder<String> rackField = DynamicReports.field("rackName", DataTypes.stringType());
		FieldBuilder<String> isVmSvrField = DynamicReports.field("isVirtualServer", DataTypes.stringType());
		FieldBuilder<String> totalMemField = DynamicReports.field("totalMemory", DataTypes.stringType());
		
		//horizontalBuilder for Column#1
//		TextColumnBuilder<String> hostnameColumn 
		
		HorizontalListBuilder builder1 = Components.horizontalFlowList();
		builder1.add(Components.text("Hostname:").setWidth(30).setStyle(nameStyle));
		builder1.add(Components.text(hostnameField).setStyle(valueStyle));
		
		builder1.newRow();
		builder1.add(Components.text("Admin:").setWidth(25).setStyle(nameStyle));
		builder1.add(Components.text(adminField).setStyle(valueStyle));
		
		builder1.newRow();
		builder1.add(Components.text("OS:").setWidth(20).setStyle(nameStyle));
		builder1.add(Components.text(osField).setStyle(valueStyle));
		
		builder1.newRow().setStretchType(StretchType.RELATIVE_TO_BAND_HEIGHT);
		builder1.add(Components.text("Model:").setStyle(nameStyle).setWidth(30));
		builder1.add(Components.hListCell(Components.text(modelField).setStyle(valueStyle)).widthExpand());
		
		//horizontalBuilder for Column#2
		HorizontalListBuilder builder2 = Components.horizontalFlowList();
		builder2.add(Components.text("Description:").setWidth(30).setStyle(nameStyle));
		builder2.add(Components.hListCell(Components.text(descField).setStyle(valueStyle)).widthFloat());
		
		builder2.newFlowRow();
		builder2.add(Components.text("Is VS:").setWidth(30).setStyle(nameStyle));
		builder2.add(Components.text(isVmSvrField).setStyle(valueStyle).setWidth(30));
		builder2.add(Components.text("Rack Name:").setWidth(30).setStyle(nameStyle));
		builder2.add(Components.text(rackField).setStyle(valueStyle));
		
		builder2.newFlowRow();
		builder2.add(Components.text("Total Memory:").setStyle(nameStyle).setWidth(35));
		builder2.add(Components.text(totalMemField).setStyle(valueStyle));
		builder2.newFlowRow();
//		builder2.add(Components.text("Memory Count:").setStyle(nameStyle));
		
		HorizontalListBuilder builder3 = Components.horizontalList();
		builder3.add(Components.text("IP Address:").setStyle(nameStyle).setWidth(35));
		builder3.add(Components.text(ipAddressField).setStyle(valueStyle));
		
		builder3.newFlowRow();
		builder3.add(Components.text("Location:").setStyle(nameStyle).setWidth(35));
		builder3.newFlowRow();
		builder3.add(Components.text("Processor Count:").setStyle(nameStyle).setWidth(50));
		builder3.add(Components.text(cpuField).setStyle(valueStyle));		
		
		ComponentColumnBuilder col1 = Columns.componentColumn(builder1);
		ComponentColumnBuilder col2 = Columns.componentColumn(builder2);
		ComponentColumnBuilder col3 = Columns.componentColumn(builder3);
				
		TextColumnBuilder<Integer> rowNum = Columns.reportRowNumberColumn();
		rowNum.setStyle(verticalAlignTopStyle);
		rowNum.setWidth(10);
		
		reportBuilder.addColumn(rowNum);
		reportBuilder.addColumn(col1);
		reportBuilder.addColumn(col2);
		reportBuilder.addColumn(col3);
		reportBuilder.addPageHeader(BtpnTemplates.createPageHeaderComponent("Server Information"));
		reportBuilder.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		reportBuilder.setTemplate(BtpnTemplates.reportTemplate);
		reportBuilder.setDataSource(new JRBeanCollectionDataSource(dataSource));
		reportBuilder.addPageFooter(BtpnTemplates.footerComponent);
//		reportBuilder.sortBy(new SortBuilder(hostnameField));
		
	}

	@Override
	public void printToPdf() {
		try {
			reportBuilder.toPdf(new FileOutputStream("E:\\Temp\\server_report.pdf"));
		} catch (FileNotFoundException | DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JasperReportBuilder getReportBuilder() {
		return reportBuilder;
	}
}
