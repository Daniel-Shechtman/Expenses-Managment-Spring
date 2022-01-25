package com.ilandaniel.project.views;

import com.ilandaniel.project.classes.Expense;
import com.ilandaniel.project.helpers.Helper;
import com.ilandaniel.project.interfaces.IViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Home screen that navigates to all others screens(expense,category manage,reports)
 */
public class HomeScreen extends BaseScreen {
    private JPanel panelNorth, panelCenter;
    private JTable tableExpense;
    List<Expense> expenseList = new ArrayList<>();
    private JLabel labelTitle;
    private JButton btnAddExpense, btnManageCategory, btnGetReports, btnLogout;
    private GridBagConstraints bagConstraints;
    private IViewModel viewModel;
    private JScrollPane scrollPane;

    public HomeScreen() {

    }

    public void init() {
        //initializing the components
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

    /**
     * load expeneses into expenses table when app start
     */
    private void initTable() {
        viewModel.initTableExpenses(Helper.loggedInAccount.getId());
    }

    public void start() {
        //setting the jFrame properties
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //placing the components on the grid
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

        //adding the panel to the jFrame
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        //setting the location of the screen to be at the center of the screen
        setLocationRelativeTo(null);

        //setting the size of the jFrame
        this.setMinimumSize(new Dimension(900, 600));

        //adding action listener to the manage category button
        btnManageCategory.addActionListener(e -> viewModel.showScreen("Category"));

        //adding action listener to the add expense button
        btnAddExpense.addActionListener(e -> viewModel.showScreen("AddExpense"));

        //adding action listener to the get reports button
        btnGetReports.addActionListener(e -> viewModel.showScreen("Reports"));

        //adding action listener to the logout button
        btnLogout.addActionListener(e -> viewModel.logout());
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * load expenses into expenses table
     *
     * @param expenseList
     */
    public void loadTableExpenses(List<Expense> expenseList) {
        this.expenseList = expenseList;
        DisplayExpense(expenseList);
    }

    /**
     * display format for the table.
     *
     * @param expenseList
     */
    private void DisplayExpense(List<Expense> expenseList) {
        DefaultTableModel aModel = new DefaultTableModel() {

            //setting the jTable read only
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
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
        Object[] headers = {"#", "Icon", "Category", "Currency", "Cost", "Info", "Date", ""};
        aModel.setColumnIdentifiers(headers);
        if (expenseList == null) {
            this.tableExpense.setModel(aModel);
            return;
        }

        int[] size = {25, 50, 100, 60, 110, 315, 120, 100};
        Object[] objects = new Object[8];
        ListIterator<Expense> expenseListIterator = expenseList.listIterator();
        int i = 1;
        SwingUtilities.invokeLater(() -> {
            int k = 0;
            for (int col : size) {
                TableColumn column = tableExpense.getColumnModel().getColumn(k++);
                column.setMinWidth(col);
                column.setMaxWidth(col);
                column.setPreferredWidth(col);
            }
        });

        //populating the tableModel
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
            objects[7] = expense.getId();

            aModel.addRow(objects);
        }

        SwingUtilities.invokeLater(() -> {
            tableExpense.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
            tableExpense.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JTextField()));
        });

        //binding the jTable to the model
        this.tableExpense.setModel(aModel);
        scrollPane.setViewportView(tableExpense);
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {

        //CONSTRUCTOR
        public ButtonRenderer() {
            //SET BUTTON PROPERTIES
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj,
                                                       boolean selected, boolean focused, int row, int col) {

            //SET PASSED OBJECT AS BUTTON TEXT
            setText((obj == null) ? "" : "Delete");

            return this;
        }

    }

    //BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor {
        protected JButton btn;
        private String lbl;

        public ButtonEditor(JTextField txt) {
            super(txt);

            btn = new JButton();
            btn.setOpaque(true);

            //WHEN BUTTON IS CLICKED
            btn.addActionListener(e -> fireEditingStopped());
        }

        //OVERRIDE A COUPLE OF METHODS
        @Override
        public Component getTableCellEditorComponent(JTable table, Object obj,
                                                     boolean selected, int row, int col) {

            //SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
            lbl = (obj == null) ? "" : obj.toString();
            btn.setText("Delete");
            viewModel.deleteSelected(Integer.parseInt(lbl));

            return btn;
        }

        //IF BUTTON CELL VALUE CHANGES,IF CLICKED THAT IS
        @Override
        public Object getCellEditorValue() {

            //SET IT TO FALSE NOW THAT ITS CLICKED
            return "Delete";
        }

        @Override
        public boolean stopCellEditing() {

            //SET CLICKED TO FALSE FIRST
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            // TODO Auto-generated method stub
            super.fireEditingStopped();
        }
    }


}
