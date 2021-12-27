package com.ilandaniel.project.views;


import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ReportsScreen extends BaseScreen {
    private JPanel panelNorth, panelCenter;
    private JLabel labelFromDate, labelToDate,labelInstruction;
    private JTextField  tfFromDate, tfToDate;
    private JButton btnCancel, btnGetReport;
    private JTable tableExpenses;
    private JScrollPane scrollPane;
    private GridBagConstraints gridBagConstraints;





    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void init() {
        scrollPane = new JScrollPane();
        tableExpenses = new JTable();
        panelNorth = new JPanel(new GridBagLayout());
        panelCenter = new JPanel(new BorderLayout());
        labelFromDate = new JLabel("From Date: ");
        labelToDate = new JLabel("To Date: ");
        tfFromDate = new JTextField (15);
        tfToDate = new JTextField (15);
        labelInstruction = new JLabel("<html><font color='red'>Valid date format: DD-MM-YYYY<br><br>Minimum year: 2021<br>Max year: 2100</font></html>");
        btnCancel = new JButton("Cancel");
        btnGetReport = new JButton("Get Report");
        gridBagConstraints = new GridBagConstraints();


    }

    @Override
    public void start() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelNorth.add(labelFromDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(tfFromDate, gridBagConstraints);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridheight=3;
        panelNorth.add(labelInstruction,gridBagConstraints);

        gridBagConstraints.gridheight=1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panelNorth.add(labelToDate, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(tfToDate, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelNorth.add(btnGetReport, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        panelNorth.add(btnCancel, gridBagConstraints);


        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));


        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        this.setMinimumSize(new Dimension(700,300));





        btnGetReport.addActionListener(e -> {
            String fromDate = tfFromDate.getText();
            String toDate = tfToDate.getText();
            viewModel.getReport(fromDate,toDate);
        });

        btnCancel.addActionListener(e -> viewModel.showScreen("Home"));
    }

    public void loadTableExpenses(List<Expense> expensesList) {
        DisplayExpense(expensesList);


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
                return switch (column) {
                    case 1 -> ImageIcon.class;
                    case 6 -> Date.class;
                    default -> Object.class;
                };
            }

        };

        //setting the column name
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date"};
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
            String categoryName = expense.getCategoryName();
            String path = Helper.getIconPathByCategoryName(categoryName);
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
            objects[0] = i++;
            objects[1] = icon;
            objects[2] = categoryName;
            objects[3] = expense.getCurrency();
            objects[4] = expense.getCost();
            objects[5] = expense.getInfo();
            objects[6] = expense.getDateCreated();

            aModel.addRow(objects);
        }

        //binding the jtable to the model
        this.tableExpenses.setModel(aModel);
        scrollPane.setViewportView(tableExpenses);
    }
}
