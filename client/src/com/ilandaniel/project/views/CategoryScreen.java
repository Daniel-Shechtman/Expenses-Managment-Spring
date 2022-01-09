package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Category;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryScreen extends BaseScreen {


    private JLabel labelTitle, labelCategories, labelNewCategory;
    private JButton btnAddCategory, btnDeleteCategory, btnCancel;
    private JTextField textFieldCategory;
    private GridBagConstraints constraints;
    private JComboBox<String> comboBoxCategories;
    private List<String> categoriesList;

    public CategoryScreen() {

    }


    public void init() {
        labelTitle = new JLabel("Manage Categories");
        labelCategories = new JLabel("Categories: ");
        labelNewCategory = new JLabel("New Category Name: ");
        btnAddCategory = new JButton("Add Category");
        btnDeleteCategory = new JButton("Delete Category");
        btnCancel = new JButton("Return To Homepage");
        comboBoxCategories = new JComboBox<>();
        textFieldCategory = new JTextField(15);
        constraints = new GridBagConstraints();
        categoriesList = new ArrayList<>();
    }


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
        this.add(labelNewCategory, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        this.add(textFieldCategory, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(btnAddCategory, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        this.add(btnDeleteCategory, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 4;
        this.add(btnCancel, constraints);
        setLocationRelativeTo(null);
        this.pack();


        btnCancel.addActionListener(e -> viewModel.showScreen("Home"));

        btnAddCategory.addActionListener(e -> {
            String name = textFieldCategory.getText();
            viewModel.addCategory(new Category(name));
        });

        btnDeleteCategory.addActionListener(e -> {
            String categoryName = Objects.requireNonNull(comboBoxCategories.getSelectedItem()).toString();
            viewModel.deleteCategory(categoryName);
        });
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;

    }

    public void addCategory(String category) {
        textFieldCategory.setText("");
        categoriesList.add(category);
        updateCategoriesList();
    }

    public void loadCategoriesNamesIntoComboBox(List<String> names) {
        for (String s : names) {
            comboBoxCategories.addItem(s);
            categoriesList.add(s);
        }
    }

    public void updateCategoriesList() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(categoriesList.toArray());
        comboBoxCategories.setModel(comboBoxModel);
    }

    public void deleteCategory(String category) {
        categoriesList.remove(category);
        updateCategoriesList();
    }


}
