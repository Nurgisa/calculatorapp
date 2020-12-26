package org.example;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	private List<TimeTable> timeTables = new ArrayList<>();
	Calendar cal = Calendar.getInstance();

	public HomePage(final PageParameters parameters) {
		super(parameters);

		TextField startDate = new TextField("startDate", Model.of());
		TextField monthCnt = new TextField("monthCnt", Model.of());
		TextField creditSum = new TextField("creditSum", Model.of());
		TextField percent = new TextField("percent", Model.of());


		Form form = new Form("form"){

			@Override
			protected void onSubmit(){
				timeTables.clear();
				try {
					Date start = new SimpleDateFormat("dd/MM/yyyy").parse(startDate.getValue()+"");
					start.setDate(5);
					cal.setTime(start);
					double remains = Double.parseDouble(creditSum.getValue());
					for(int i = 0; i < Integer.parseInt(monthCnt.getValue()); i++){
						int countOfDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						double debtPayment = Integer.parseInt(creditSum.getValue())/Integer.parseInt(monthCnt.getValue());
						double percentPayment =  Double.parseDouble(percent.getValue())/100*remains*countOfDay/360;
						remains = remains - debtPayment;
						double monthlyPayment = debtPayment+percentPayment;
						cal.add(Calendar.MONTH, +1);
						TimeTable timeTable = new TimeTable(i+1, cal.getTime(), countOfDay, debtPayment, percentPayment, remains, monthlyPayment);
						timeTables.add(timeTable);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				super.onSubmit();
			}
		};
		form.add(new Label("startDateSpan", "Дата выдачи кредита:"));
		form.add(startDate);
		form.add(new Label("monthCntSpan", "Срок кредита в мес:"));
		form.add(monthCnt);
		form.add(new Label("creditSumSpan", "Сумма кредита:"));
		form.add(creditSum);
		form.add(new Label("percentSpan", "Процентная ставка:"));
		form.add(percent);
		form.add(new Button("submitButton"));
		add(form);

		ListView<TimeTable> listView = new ListView<>("timeTable", timeTables) {
			@Override
			protected void populateItem(ListItem<TimeTable> item) {
				item.add(new Label("number", new PropertyModel(item.getModel(), "id")));
				item.add(new Label("date", new PropertyModel(item.getModel(), "date")));
				item.add(new Label("countOfDay", new PropertyModel(item.getModel(), "countOfDay")));
				item.add(new Label("debtPayment", new PropertyModel(item.getModel(), "debtPayment")));
				item.add(new Label("percentPayment", new PropertyModel(item.getModel(), "percentPayment")));
				item.add(new Label("remains", new PropertyModel(item.getModel(), "remains")));
				item.add(new Label("monthlyPayment", new PropertyModel(item.getModel(), "monthlyPayment")));
			}
		};

		add(listView);

	}
}
