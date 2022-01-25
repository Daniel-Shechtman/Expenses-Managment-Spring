package com.ilandaniel.project.views;

import com.ilandaniel.project.dtos.ExpenseDTO;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Add new expense screen
 */
public class AddExpenseScreen extends BaseScreen {
    private JLabel labelTitle, labelCategories, labelCost, labelCurrency, labelInfo;
    private JButton btnAddExpense, btnCancel;
    private JTextField textFieldCost;
    private JTextArea textAreaInfo;
    private GridBagConstraints constraints;
    private JComboBox<String> comboBoxCategories, comboBoxCurrencies;


    public AddExpenseScreen() {

    }

    /**
     * initializing the gui components
     */
    @Override
    public void init() {
        //setting all the vars
        labelTitle = new JLabel("Add New Expense");
        labelCategories = new JLabel("Categories: ");
        labelCurrency = new JLabel("Currency: ");
        labelInfo = new JLabel("Info: ");
        labelCost = new JLabel("Cost: ");
        btnAddExpense = new JButton("Add Expense");
        btnCancel = new JButton("Return To Homepage");
        //creating the combo box that displays the categories and the currencies
        comboBoxCategories = new JComboBox<>();
        comboBoxCurrencies = new JComboBox<>();
        //setting the field and textarea size
        textFieldCost = new JTextField(15);
        textAreaInfo = new JTextArea(3, 15);
        textAreaInfo.setLineWrap(true);
        constraints = new GridBagConstraints();

    }

    @Override
    public void start() {
        //setting the jFrame properties
        this.setLayout(new GridBagLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //placing the components on the grid
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
        //setting the location of the screen to the center
        setLocationRelativeTo(null);
        this.pack();

        //adding action listener to the cancel button
        btnCancel.addActionListener(e -> viewModel.showScreen("Home"));
        //adding action listener to the add new expense button
        btnAddExpense.addActionListener(e -> {
            String categoryName = Objects.requireNonNull(comboBoxCategories.getSelectedItem()).toString();
            String currency = Objects.requireNonNull(comboBoxCurrencies.getSelectedItem()).toString();
            String cost = textFieldCost.getText();
            String info = textAreaInfo.getText();
            viewModel.getCategoryIdByName(new ExpenseDTO(-1, cost, currency, info, categoryName));


        });

    }


    public void setViewModel(IViewModel viewModel) {

        this.viewModel = viewModel;


    }

    /**
     * load the categories names into the combobox
     *
     * @param names
     */
    public void loadCategoriesNamesIntoComboBox(List<String> names) {
        for (String s : names) {
            comboBoxCategories.addItem(s);
        }
    }

    /**
     * load currencies into combox
     */
    public void setCurrencies() {
        for (Map.Entry<String, Float> set : Helper.currencies.entrySet()) {
            comboBoxCurrencies.addItem(set.getKey());
        }
    }
}
