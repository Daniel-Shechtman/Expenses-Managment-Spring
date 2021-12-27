package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class HomeScreen extends BaseScreen {
    private JPanel panelNorth, panelCenter;
    private JTable tableExpense;
    List<Expense> expenseList = new ArrayList<>();
    private JLabel labelTitle;
    private JButton btnAddExpense, btnManageCategory, btnGetReports, btnDeleteSelected;
    private GridBagConstraints bagConstraints;
    private IViewModel viewModel;
    private JScrollPane scrollPane;
    private String categoryName;

    public HomeScreen() {

    }

    public void init() {
        tableExpense = new JTable();
        scrollPane = new JScrollPane();
        panelNorth = new JPanel(new GridBagLayout());
        panelCenter = new JPanel(new BorderLayout());
        labelTitle = new JLabel("Expense manager");
        btnAddExpense = new JButton("Add new expense");
        btnManageCategory = new JButton("Manage Category's");
        btnGetReports = new JButton("Get Reports");
        btnDeleteSelected = new JButton("Delete selected");
        bagConstraints = new GridBagConstraints();
        initTable();
    }

    private void initTable() {
        viewModel.initTableExpenses(Helper.loggedInAccount.getId());
    }

    public void start() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        bagConstraints.gridwidth = 5;
        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        panelNorth.add(labelTitle, bagConstraints);

        bagConstraints.gridwidth = 2;
        bagConstraints.gridy = 1;
        panelNorth.add(btnAddExpense, bagConstraints);

        bagConstraints.gridwidth = 1;
        bagConstraints.gridx = 3;
        panelNorth.add(btnManageCategory, bagConstraints);

        bagConstraints.gridx = 4;
        panelNorth.add(btnGetReports, bagConstraints);

        bagConstraints.gridx = 5;
        panelNorth.add(btnDeleteSelected, bagConstraints);


        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));


        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        //homeViewModel.setExpensesList(Helper.loggedInAccount.getId());

        this.setSize(800, 400);

        btnManageCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.showScreen("Category");
            }
        });

        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.showScreen("AddExpense");
            }
        });

        btnGetReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.showScreen("Reports");
            }
        });
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }


    public void showMessage(String msg) {
        Helper.showMessage("Homepage", msg);
    }

    public void loadTableExpenses(List<Expense> expenseList) {
        this.expenseList = expenseList;
        DisplayExpense(expenseList);

        String[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date", "Actions"};
        Object[][] objects = new Object[expenseList.size()][];
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
            this.tableExpense.setModel(aModel);
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
        this.tableExpense.setModel(aModel);
        scrollPane.setViewportView(tableExpense);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
