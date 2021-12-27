package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class HomeScreen extends BaseScreen {
    private JPanel panelNorth, panelCenter;
    private JTable tableExpense;
    List<Expense> expenseList = new ArrayList<>();
    private JLabel labelTitle;
    private JButton btnAddExpense, btnManageCategory, btnGetReports, btnLogout;
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
        btnLogout = new JButton("Logout");
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
        panelNorth.add(btnLogout, bagConstraints);


        panelCenter.add(scrollPane, BorderLayout.CENTER);
        panelCenter.setBorder(new EmptyBorder(10, 0, 0, 0));



        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        setLocationRelativeTo(null);

        this.setMinimumSize(new Dimension(800,400));

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

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.logout();
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
                    case 6:
                        return Date.class;
                    default:
                        return Object.class;
                }
            }

        };

        //setting the column name
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date"};
        aModel.setColumnIdentifiers(headers);
        if (expenseList == null) {
            this.tableExpense.setModel(aModel);
            return;
        }

        int [] size= {25,50,60,60,110,375,120};
        Object[] objects = new Object[7];
        ListIterator<Expense> expenseListIterator = expenseList.listIterator();
        int i = 1;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int k =0;
                for (int col:size){
                    TableColumn column = tableExpense.getColumnModel().getColumn(k++);
                    column.setMinWidth(col);
                    column.setMaxWidth(col);
                    column.setPreferredWidth(col);
                }
            }
        });

        //populating the tablemodel
        while (expenseListIterator.hasNext()) {
            Expense expense = expenseListIterator.next();
            String categoryName = expense.getCategoryName();
            String path = Helper.getIconPathByCategoryName(categoryName);
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
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
        this.tableExpense.setModel(aModel);
        scrollPane.setViewportView(tableExpense);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
