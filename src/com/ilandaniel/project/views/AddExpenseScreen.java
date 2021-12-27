package com.ilandaniel.project.views;

import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddExpenseScreen extends BaseScreen {
    private JLabel labelTitle, labelCategories, labelCost, labelCurrency, labelInfo;
    private JButton btnAddExpense, btnCancel;
    private JTextField textFieldCost;
    private JTextArea textAreaInfo;
    private GridBagConstraints constraints;
    private JComboBox<String> comboBoxCategories, comboBoxCurrencies;


    public AddExpenseScreen() {

    }


    @Override
    public void init() {
        labelTitle = new JLabel("Add New Expense");
        labelCategories = new JLabel("Categories: ");
        labelCurrency = new JLabel("Currency: ");
        labelInfo = new JLabel("Info: ");
        labelCost = new JLabel("Cost: ");
        btnAddExpense = new JButton("Add Expense");
        btnCancel = new JButton("Return To Homepage");
        comboBoxCategories = new JComboBox<>();
        comboBoxCurrencies = new JComboBox<>();
        textFieldCost = new JTextField(15);
        textAreaInfo = new JTextArea(3,15);
        textAreaInfo.setLineWrap(true);
        constraints = new GridBagConstraints();

    }

    @Override
    public void start() {
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        this.add(labelTitle, constraints);
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(labelCategories, constraints);


        viewModel.getAllCategories();
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(comboBoxCategories, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(labelCost, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldCost, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(labelCurrency, constraints);

        setCurrencies();
        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(comboBoxCurrencies, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(labelInfo, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        this.add(textAreaInfo, constraints);


        constraints.gridx = 0;
        constraints.gridy = 5;
        this.add(btnAddExpense, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        this.add(btnCancel, constraints);
        setLocationRelativeTo(null);
        this.pack();


        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewModel.showScreen("Home");
            }
        });

        btnAddExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer categoryId = -1;
                String categoryName = comboBoxCategories.getSelectedItem().toString();
                String currency = comboBoxCurrencies.getSelectedItem().toString();
                String cost = textFieldCost.getText();
                String info = textAreaInfo.getText();
                viewModel.getCategoryIdByName(new ExpenseDTO(-1, cost, currency, info, categoryName));


            }
        });

    }


    public void setViewModel(IViewModel viewModel) {

        this.viewModel = viewModel;


    }


    public void showMessage(String msg) {
        Helper.showMessage("Categories", msg);
    }


    public void loadCategoriesNamesIntoComboBox(List<String> names) {
        for (String s : names) {
            comboBoxCategories.addItem(s);
        }
    }

    public void setCurrencies() {
        comboBoxCurrencies.addItem("ILS");
        comboBoxCurrencies.addItem("USD");
        comboBoxCurrencies.addItem("EURO");
    }
}
