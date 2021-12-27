package com.ilandaniel.project.views;


import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class ReportsScreen extends BaseScreen {
    private JLabel labelFromDate, labelToDate;
    private JTextField  tfFromDate, tfToDate;
    private JButton btnCancel, btnGetReport;
    private JTable tableExpenses;
    private JScrollPane scrollPane;
    private GridBagConstraints gridBagConstraints;
    private List<Expense> expensesList;




    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void init() {
        scrollPane = new JScrollPane();
        tableExpenses = new JTable();
        labelFromDate = new JLabel("From Date: ");
        labelToDate = new JLabel("To Date: ");
        tfFromDate = new JTextField (15);
        tfToDate = new JTextField (15);
        btnCancel = new JButton("Cancel");
        btnGetReport = new JButton("Get Report");
        gridBagConstraints = new GridBagConstraints();
        expensesList = new ArrayList<>();

    }

    @Override
    public void start() {
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(labelFromDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        this.add(tfFromDate, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(labelToDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        this.add(tfToDate, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(btnGetReport, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        this.add(btnCancel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        scrollPane.getViewport().add(tableExpenses);
        this.add(scrollPane, gridBagConstraints);

        gridBagConstraints.gridwidth = 1;

        this.pack();

        btnGetReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromDate = tfFromDate.getText();
                String toDate = tfToDate.getText();
                viewModel.getReport(fromDate,toDate);
            }
        });
    }

    public void loadTableExpenses(List<Expense> expensesList) {
        this.expensesList = expensesList;
        DisplayExpense(expensesList);

        String[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date", "Actions"};
        Object[][] objects = new Object[expensesList.size()][];
        Vector v3 = new Vector();
        int i = 1;

    }

    private void DisplayExpense(List<Expense> expenseList) {
        DefaultTableModel aModel = new DefaultTableModel() {

            //setting the jtable read only
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {

                    case 1:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }

        };

        //setting the column name
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date", "Actions"};
        aModel.setColumnIdentifiers(headers);
        if (expenseList == null) {
            this.tableExpenses.setModel(aModel);
            return;
        }

        Object[] objects = new Object[8];
        ListIterator<Expense> expenseListIterator = expenseList.listIterator();
        int i = 1;

        //populating the tablemodel
        while (expenseListIterator.hasNext()) {
            Expense expense = expenseListIterator.next();
            //viewModel.getCategoryNameById(expense.getCategoryId());
            //ImageIcon icon = new ImageIcon(Helper.getIconPathByCategoryName(expense.getCategoryName()));
            String categoryName = expense.getCategoryName();
            String path = Helper.getIconPathByCategoryName(categoryName);
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            //Icon icon1 = new ImageIcon(path);
            objects[0] = i++;
            objects[1] = icon;
            objects[2] = categoryName;
            objects[3] = expense.getCurrency();
            objects[4] = expense.getCost();
            objects[5] = expense.getInfo();
            objects[6] = expense.getDateCreated();
            objects[7] = "D";

            aModel.addRow(objects);
        }

        //binding the jtable to the model
        this.tableExpenses.setModel(aModel);
        scrollPane.setViewportView(tableExpenses);
    }
}
