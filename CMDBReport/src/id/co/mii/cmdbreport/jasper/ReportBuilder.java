package id.co.mii.cmdbreport.jasper;

import id.co.mii.cmdbreport.SystemProperties;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.Styles;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class ReportBuilder {

	protected List dataSource;
	protected String title;
	JasperReportBuilder reportBuilder;
	
	public ReportBuilder() {
		dataSource = new ArrayList();	
	}
	
	public abstract void build();
	
	public abstract void printToPdf();
	
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}
	
	public List getDataSource() {
		return dataSource;
	}
	
	public JasperReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	public void setReportBuilder(JasperReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public void show() {
		if (SystemProperties.getBoolean("ARS.report.preview")){
			try {
				reportBuilder.show();
			} catch (DRException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
