package id.co.mii.cmdbreport.jasper;


	import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import id.co.mii.cmdbreport.bo.BusinessService;

	import java.awt.Color;
import java.util.Locale;

import net.sf.dynamicreports.examples.miscellaneous.NoDataReport;
	import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.HyperLinkBuilder;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;

	public class BtpnTemplates {
		public static final StyleBuilder rootStyle;
		public static final StyleBuilder boldStyle;
		public static final StyleBuilder italicStyle;
		public static final StyleBuilder boldCenteredStyle;
		public static final StyleBuilder bold12CenteredStyle;
		public static final StyleBuilder bold18CenteredStyle;
		public static final StyleBuilder bold22CenteredStyle;
		public static final StyleBuilder columnStyle;
		public static final StyleBuilder columnTitleStyle;
		public static final StyleBuilder groupStyle;
		public static final StyleBuilder subtotalStyle;
		public static final StyleBuilder pageHeaderStyle;
		
		public static final ReportTemplateBuilder reportTemplate;
		public static final CurrencyType currencyType;
		public static final ComponentBuilder<?, ?> dynamicReportsComponent;
		public static final ComponentBuilder<?, ?> btpnLogoComponent;
		public static final ComponentBuilder<?, ?> footerComponent;

		static {
			rootStyle           = stl.style().setPadding(2);
			boldStyle           = stl.style(rootStyle).bold();
			italicStyle         = stl.style(rootStyle).italic();
			boldCenteredStyle   = stl.style(boldStyle)
			                         .setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
			bold12CenteredStyle = stl.style(boldCenteredStyle)
			                         .setFontSize(12);
			bold18CenteredStyle = stl.style(boldCenteredStyle)
			                         .setFontSize(18);
			bold22CenteredStyle = stl.style(boldCenteredStyle)
	                             .setFontSize(22);
			columnStyle         = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE);
			columnTitleStyle    = stl.style(columnStyle)
//			                         .setBorder(stl.pen1Point())
			                         .setHorizontalAlignment(HorizontalAlignment.CENTER)
			                         .setBackgroundColor(Color.LIGHT_GRAY)
			                         .bold();
			groupStyle          = stl.style(boldStyle)
			                         .setHorizontalAlignment(HorizontalAlignment.LEFT);
			subtotalStyle       = stl.style(boldStyle)
			                         .setTopBorder(stl.pen1Point());
//			whiteFont = stl.font(
			pageHeaderStyle = stl.style(bold12CenteredStyle)
					.setBackgroundColor(Color.decode("#FF6600"))
					.setPadding(2);

			StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
			StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle)
			                                          .setBackgroundColor(new Color(170, 170, 170));
			StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle)
			                                          .setBackgroundColor(new Color(140, 140, 140));
			StyleBuilder crosstabCellStyle       = stl.style(columnStyle)
			                                          .setBorder(stl.pen1Point());

			TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
				.setHeadingStyle(0, stl.style(rootStyle).bold());

			reportTemplate = template()
			                   .setLocale(Locale.ENGLISH)
			                   .setColumnStyle(columnStyle)
			                   .setColumnTitleStyle(columnTitleStyle)
			                   .setGroupStyle(groupStyle)
			                   .setGroupTitleStyle(groupStyle)
			                   .setSubtotalStyle(subtotalStyle)
			                   .setPageHeaderStyle(pageHeaderStyle)
			                   .highlightDetailEvenRows()
			                   .crosstabHighlightEvenRows()
			                   .setCrosstabGroupStyle(crosstabGroupStyle)
			                   .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
			                   .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
			                   .setCrosstabCellStyle(crosstabCellStyle)
			                   .setTableOfContentsCustomizer(tableOfContentsCustomizer);

			currencyType = new CurrencyType();

			HyperLinkBuilder link = hyperLink("http://www.dynamicreports.org");
			dynamicReportsComponent =
			  cmp.horizontalList(
			  	cmp.image("asset/logo_btpn.jpg").setFixedDimension(100, 80),
			  	cmp.verticalList(
			  		cmp.text("DynamicReports").setStyle(bold22CenteredStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
			  		cmp.text("http://www.dynamicreports.org").setStyle(italicStyle).setHyperLink(link))).setFixedWidth(300);
			
			btpnLogoComponent = cmp.horizontalList(
					cmp.image("asset/logo_btpn.jpg").setFixedDimension(100, 80)).setFixedWidth(150);

			footerComponent = cmp.pageNumber()
			                     .setStyle(
			                     	stl.style(boldCenteredStyle)
			                     	   .setTopBorder(stl.pen1Point()));
		 
		}

		/**
		 * Creates custom component which is possible to add to any report band component
		 */
		public static ComponentBuilder<?, ?> createTitleComponent(String label) {
			return cmp.horizontalList()
			        .add(
			        	btpnLogoComponent,
			        	cmp.text(label).setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
			        .newRow()
			        .add(cmp.line())
			        .newRow()
			        .add(cmp.verticalGap(10));
		}
		
		/***
		 * Creates custom component from FieldBuilder object
		 * @param field FieldBuilder<?>
		 * @return ComponentBuilder<?, ?>
		 */
		public static ComponentBuilder<?, ?> createTitleComponent(FieldBuilder<?> field) {
			TextFieldBuilder<?> txtfield = Components.text(field);			
			return cmp.horizontalList()
			        .add(
			        	btpnLogoComponent,
			        	txtfield.setStyle(bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
			        .newRow()
			        .add(cmp.line())
			        .newRow()
			        .add(cmp.verticalGap(10));
		}
		
		/***
		 * Create custom component if data is not available
		 * @param title
		 * @return ComponentBuilder<?, ?>
		 */
		public static ComponentBuilder<?,?> createNoDataComponent(String title){
			return cmp.horizontalList().newRow()
					.add(BtpnTemplates.createPageHeaderComponent(title))
					.newRow()
					.add(cmp.verticalGap(70))
					.add(Components.text("Data not available").setStyle(boldStyle));
		}
		
		public static ComponentBuilder<?, ?> createPageHeaderComponent(String label){
			return cmp.horizontalList().add(
					cmp.text(label)
					.setStyle(pageHeaderStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER)
					);
		}

		public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
			return new CurrencyValueFormatter(label);
		}

		public static class CurrencyType extends BigDecimalType {
			private static final long serialVersionUID = 1L;

			@Override
			public String getPattern() {
				return "$ #,###.00";
			}
		}

		private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
			private static final long serialVersionUID = 1L;

			private String label;

			public CurrencyValueFormatter(String label) {
				this.label = label;
			}

			@Override
			public String format(Number value, ReportParameters reportParameters) {
				return label + currencyType.valueToString(value, reportParameters.getLocale());
			}
		}
}
