package com.ilandaniel.project.views;


import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.helpers.pdf.PdfCreator;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.*;

public class ReportsScreen extends BaseScreen {
    private JPanel panelNorth, panelCenter,panelSouth;
    private JFileChooser fileChooser;
    private JLabel labelFromDate, labelToDate,labelInstruction,labelSumOfExpenses,labelExpensesTotal;
    private JTextField  tfFromDate, tfToDate;
    private JButton btnCancel, btnGetReport, btnSaveReportAsPdf;
    private JComboBox comboBoxCurrencies;
    private JTable tableExpenses;
    private JScrollPane scrollPane;
    private GridBagConstraints gridBagConstraints;
    private String currentCurrency = "ILS";
    private double totalSum = 0;
    List<Expense> expensesList = new LinkedList<>();





    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void init() {
        fileChooser = new JFileChooser();
        scrollPane = new JScrollPane();
        tableExpenses = new JTable();
        panelNorth = new JPanel(new GridBagLayout());
        panelCenter = new JPanel(new BorderLayout());
        panelSouth = new JPanel(new FlowLayout());
        labelFromDate = new JLabel("From Date: ");
        labelToDate = new JLabel("To Date: ");
        labelSumOfExpenses = new JLabel("The Total Sum Of Selected Expenses: ");
        labelExpensesTotal = new JLabel("0");
        tfFromDate = new JTextField (15);
        tfToDate = new JTextField (15);
        comboBoxCurrencies = new JComboBox();
        labelInstruction = new JLabel("<html><font color='red'>Valid date format: DD-MM-YYYY<br><br>Minimum year: 2021<br>Max year: 2100</font></html>");
        btnCancel = new JButton("Cancel");
        btnGetReport = new JButton("Get Report");
        btnSaveReportAsPdf = new JButton("Save Report As PDF");
        gridBagConstraints = new GridBagConstraints();


    }

    @Override
    public void start() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // init file chooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(filter);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setDialogTitle("select path to save file");


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

        setComboBoxCurrencies();
        ActionListener cbActionListener = new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = (String) comboBoxCurrencies.getSelectedItem();//get the selected item
                if(!s.equals(currentCurrency)){
                    setLabelSumCurrencies(s);
                    currentCurrency = s;
                }

            }
        };
        comboBoxCurrencies.addActionListener(cbActionListener);
        panelSouth.add(labelSumOfExpenses);
        panelSouth.add(labelExpensesTotal);
        panelSouth.add(comboBoxCurrencies);


        btnSaveReportAsPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                int userSelection = fileChooser.showSaveDialog(b.getParent());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    String[][] strData = saveDataAssStrDoubleArr();
                    PdfCreator pdfCreator = new PdfCreator();
                    pdfCreator.createPdfFile(fileToSave.getPath(), strData);

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Helper.showMessage("Report", "Report Saved");
                        }
                    });
                }
            }
        });


        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth,BorderLayout.SOUTH);

        setLocationRelativeTo(null);

        this.setMinimumSize(new Dimension(900,400));



        btnGetReport.addActionListener(e -> {
            String fromDate = tfFromDate.getText();
            String toDate = tfToDate.getText();
            viewModel.getReport(fromDate,toDate);
        });

        btnCancel.addActionListener(e -> viewModel.showScreen("Home"));
    }

    private String[][] saveDataAssStrDoubleArr() {
        String [][] data = new String[expensesList.size()][];

        int i = 0;
        for(Expense expense : expensesList)
        {
            data[i] = new String[5];
            data[i][0] = expense.getCategoryName();
            data[i][1] = expense.getCurrency();
            data[i][2] = String.valueOf(expense.getCost());
            data[i][3] = expense.getInfo();
            data[i][4] = expense.getDateCreated().toString();
        }

        return data;
    }

    public void loadTableExpenses(List<Expense> expensesList) {
        this.expensesList = expensesList;
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
            float cost = expense.getCost();
            String curr = expense.getCurrency();
            objects[0] = i++;
            objects[1] = icon;
            objects[2] = categoryName;
            objects[3] = curr;
            objects[4] = cost;
            objects[5] = expense.getInfo();
            objects[6] = expense.getDateCreated();

            totalSum+=cost*calcCurrencyRate(curr);
            aModel.addRow(objects);
        }
        labelExpensesTotal.setText(String.format("%.2f",totalSum));
        //binding the jtable to the model
        this.tableExpenses.setModel(aModel);
        scrollPane.setViewportView(tableExpenses);
    }

    private double calcCurrencyRate(String currency) {
        switch (comboBoxCurrencies.getSelectedItem().toString()){
            case "ILS":
                return Helper.currencies.get(currency);
            case "USD":
                if(currency.equals("USD")) return 1.0;
                else if(currency.equals("ILS")) return 0.32;
                else return 1.13;
            case "EURO":
                if(currency.equals("EURO")) return 1.0;
                else if(currency.equals("USD")) return 0.89;
                else return 0.28;

        }
        return 0;
    }

    private void setLabelSumCurrencies(String selectedCurrency){
        switch (currentCurrency){
            case "ILS":
                if(selectedCurrency.equals("USD")){
                    totalSum = 0.321485*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));

                }
                else {
                    totalSum = 0.28466*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));
                }
                break;
            case "USD":
                if(selectedCurrency.equals("ILS")){
                    totalSum = 3.10601*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));
                }
                else {
                    totalSum = 0.8851454*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));
                }
                break;
            case "EURO":
                if(selectedCurrency.equals("ILS")){
                    totalSum = 3.5140201*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));
                }
                else {
                    totalSum = 1.1298116*totalSum;
                    labelExpensesTotal.setText(String.format("%.2f",totalSum));
                }
                break;
        }
    }

    private void setComboBoxCurrencies(){
        for(Map.Entry set:Helper.currencies.entrySet()){
            comboBoxCurrencies.addItem(set.getKey());
        }
    }
}
