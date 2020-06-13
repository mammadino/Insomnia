import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * it represents middle panel of the application that consists of two main parts:
 * top panel that manages sending url
 * header, query, auth, body tabs
 *
 * @author Mohammad Salehi Vaziri
 */
public class MiddlePanel extends JPanel {
    private JPanel topPanel;
    private JComboBox<String> method;
    private JTextField urlField;
    private JButton send;
    private JTabbedPane tabs;
    private JPanel header;
    private JPanel query;
    private JPanel auth;
    private JPanel formData;
    private JPanel noBody;
    private JPanel json;
    private JPanel binary;
    private ArrayList<JPanel> headers;
    private ArrayList<JPanel> queries;
    private ArrayList<JPanel> data;
    private JTextField binaryPath;

    /**
     * Instantiates a new Middle panel.
     */
    public MiddlePanel() {
        super();
        setLayout(new BorderLayout());

        topPanelInit();
        add(topPanel, BorderLayout.NORTH);

        tabsInit();
        add(tabs, BorderLayout.CENTER);
    }

    public JTextField getBinaryPath() {
        return binaryPath;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public JPanel getHeader() {
        return header;
    }

    public JPanel getQuery() {
        return query;
    }

    public JPanel getAuth() {
        return auth;
    }

    public JPanel getFormData() {
        return formData;
    }

    public JPanel getNoBody() {
        return noBody;
    }

    public JPanel getJson() {
        return json;
    }

    public JComboBox<String> getMethod() {
        return method;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public JButton getSend() {
        return send;
    }

    public ArrayList<JPanel> getHeaders() {
        return headers;
    }

    public ArrayList<JPanel> getQueries() {
        return queries;
    }

    public ArrayList<JPanel> getData() {
        return data;
    }

    public void setUrlText(String url){
        urlField.setText(url);
    }
    public void setSelectedMethod(String methodName){
        method.setSelectedItem(methodName);
    }

    //initializes top panel with a combo box, a text field and a button
    private void topPanelInit() {
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(0, 0));

        String[] methods = {"GET", "POST", "PUT", "PATCH", "DELETE"};
        method = new JComboBox<>(methods);
        method.setPreferredSize(new Dimension(method.getPreferredSize().width + 25, method.getPreferredSize().height));

        urlField = new JTextField("Enter URL here");
        urlField.setFont(new Font("Arial", Font.PLAIN, 12));
        urlField.setForeground(new Color(187, 187, 187));
        urlField.addFocusListener(new PromptTextHandler());

//        JPanel buttonsPanel = new JPanel();
//        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
         send = new JButton("Send");
        send.setPreferredSize(new Dimension(send.getPreferredSize().width, send.getPreferredSize().height + 21));
//        JButton save = new JButton("Save");
//        save.setPreferredSize(new Dimension(save.getPreferredSize().width, save.getPreferredSize().height));
//        buttonsPanel.add(send);
//        buttonsPanel.add(save);

        topPanel.add(method, BorderLayout.WEST);
        topPanel.add(urlField, BorderLayout.CENTER);
        topPanel.add(send, BorderLayout.EAST);
    }

    //initializes the tabs including header, query, auth, form data, JSON, no body
    private void tabsInit() {
        tabs = new JTabbedPane();

        //this ArrayList holds panels that will be shown in header tab
        // each panel contains a header and a value
        headers = new ArrayList<>();
        header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(46, 47, 43));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JScrollPane headerScrollable = new JScrollPane(header);

        //this ArrayList holds panels that will be shown in query tab
        // each panel contains a name and a value
        queries = new ArrayList<>();
        query = new JPanel();
        query.setLayout(new BoxLayout(query, BoxLayout.Y_AXIS));
        query.setBackground(new Color(46, 47, 43));
        query.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JScrollPane queryScrollable = new JScrollPane(query);

        authInit();

        //this ArrayList holds panels that will be shown in form data tab
        // each panel contains a name and a value
        data = new ArrayList<>();
        formData = new JPanel();
        formData.setLayout(new BoxLayout(formData, BoxLayout.Y_AXIS));
        formData.setBackground(new Color(46, 47, 43));
        formData.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        JScrollPane formDataScrollable = new JScrollPane(formData);

        noBodyInit();

        jsonInit();

        binaryInit();

        tabs.add(noBody, "Body");
        tabs.add(auth, "Auth");
        tabs.add(queryScrollable, "Query");
        tabs.add(headerScrollable, "Header");

        tabs.setFont(new Font(null, Font.PLAIN, 13));

        tabs.setForegroundAt(0, new Color(187, 187, 187));
        tabs.setForegroundAt(1, new Color(187, 187, 187));
        tabs.setForegroundAt(2, new Color(187, 187, 187));
        tabs.setForegroundAt(3, new Color(187, 187, 187));
        //initializes header, query and form data with "adding new" text fields
        updateHeaderQueryForm(headers, 1);
        updateHeaderQueryForm(queries, 2);
        updateHeaderQueryForm(data, 3);

        JLabel bodyTabLabel = new JLabel("No Body");
        bodyTabLabel.addMouseListener(new selectTabHandler());
        tabs.setTabComponentAt(0, bodyTabLabel);
    }

    //initializes no body tab
    private void noBodyInit(){
        noBody = new JPanel(new BorderLayout(50,50));
        noBody.setBackground(new Color(46, 47, 43));
        JLabel label = new JLabel("Select a body type from above");
        label.setFont(new Font(null, Font.BOLD, 14));
        label.setBackground(new Color(46, 47, 43));
        label.setForeground(Color.GRAY);
        label.setHorizontalAlignment(JLabel.CENTER);
        noBody.add(label, BorderLayout.CENTER);
    }

    //initializes JSON tab
    private void jsonInit(){
        json = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea("...");
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(46, 47, 43));
        json.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void binaryInit(){
        binary = new JPanel(new BorderLayout());
        binary.setBackground(new Color(46, 47, 43));
        binary.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 20));
        binaryPath = new JTextField("No File Selected");
        binaryPath.setEditable(false);
        binaryPath.setPreferredSize(new Dimension(binary.getPreferredSize().width, 35));
        binaryPath.setBackground(new Color(45, 45, 45));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,20));
        buttons.setBackground(new Color(46, 47, 43));
        JButton reset = new JButton("Reset File");
        reset.setPreferredSize(new Dimension(reset.getPreferredSize().width + 7, reset.getPreferredSize().height + 7));
        reset.setBorder(BorderFactory.createEmptyBorder());
        reset.setFont(new Font(null, Font.PLAIN, 12));
        reset.setBackground(new Color(46, 47, 43));
        reset.setForeground(Color.gray);
        reset.setFocusable(false);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                binaryPath.setText("No File Selected");
            }
        });
        JButton chooser = new JButton("Choose File");

        chooser.setPreferredSize(new Dimension(chooser.getPreferredSize().width + 15, chooser.getPreferredSize().height + 7));
        chooser.setFocusable(false);
        chooser.setBackground(new Color(46, 47, 43));
        chooser.setFont(new Font(null, Font.PLAIN, 14));
        chooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                    binaryPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        buttons.add(reset);
        buttons.add(chooser);
        binary.add(binaryPath, BorderLayout.NORTH);
        binary.add(buttons);

    }

    //creates a panel that includes two text fields one for name/header and one for value
    //and a check box and a delete button. items ArrayList is the ArrayList that created item will be added to
    //type indicates the item belongs to which panel(0 for new 1 for header 2 for query 3 for form data)
    //new boolean shows if a "adding new" item must be created
    public JPanel createItem(ArrayList<JPanel> items, int type, boolean newItem, String name, String value) {

        JPanel panel = new JPanel();
        panel.setBackground(new Color(46, 47, 43));
        panel.setLayout(new BorderLayout(10, 0));
        panel.setMaximumSize(new Dimension(2000, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));

        JPanel textFields = new JPanel();
        textFields.setBackground(new Color(46, 47, 43));
        textFields.setLayout(new BoxLayout(textFields, BoxLayout.X_AXIS));

        ImageIcon headerIcon;
        JLabel label = new JLabel();
        if (newItem) {
            headerIcon = new ImageIcon(this.getClass().getResource("res/settingIcon.png"));
            label.addMouseListener(new deleteAllHandler(items, type));
        }
        else{
            headerIcon = new ImageIcon(this.getClass().getResource("res/header.png"));
        }
        label.setIcon(headerIcon);
        panel.add(label, BorderLayout.WEST);

        JTextField textField1 = new JTextField();
        if (newItem){
            textField1 = new JTextField(name);
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    String tmpName;
                    if(type == 1)
                        tmpName = "Header";
                    else
                        tmpName = "Name";
                    addHeaderQueryForm(items,createItem(items, type, false, tmpName, "Value"), type);
                }
            });
        }
        textField1.setText(name);
        textField1.addFocusListener(new PromptTextHandler());
        textField1.setForeground(Color.GRAY);
        textField1.setMaximumSize(new Dimension(2000, 25));
        textField1.setBackground(new Color(46, 47, 43));
        textField1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));

        JTextField textField2 = new JTextField();
        if (newItem){
            textField2.setText(value);
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    String tmpName;
                    if(type == 1)
                        tmpName = "Header";
                    else
                        tmpName = "Name";
                    addHeaderQueryForm(items,createItem(items, type, false, tmpName, "Value"), type);
                }
            });
        } else {
            textField2.setText(value);
            textField2.addFocusListener(new PromptTextHandler());
        }
        textField2.setForeground(Color.GRAY);
        textField2.setMaximumSize(new Dimension(2000, 25));
        textField2.setBackground(new Color(46, 47, 43));
        textField2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));

        textFields.add(textField1);
        textFields.add(Box.createRigidArea(new Dimension(20, 0)));
        textFields.add(textField2);

        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(46, 47, 43));
        //new item does not need buttons for enabling and deleting
        if (newItem)
            buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));
        else  {
            buttons.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            JCheckBox checkBox = new JCheckBox();
            checkBox.addActionListener(new CheckBoxActionHandler(textField1, textField2));
            checkBox.setSelected(true);

            ImageIcon delIcon = new ImageIcon(this.getClass().getResource("res/delete.png"));
            JButton deleteB = new JButton(delIcon);
            deleteB.setBackground(new Color(46, 47, 43));
            deleteB.setBorder(BorderFactory.createEmptyBorder());
            deleteB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    items.remove(panel);
                    updateHeaderQueryForm(items, type);
                    updateUI();
                }
            });
            deleteB.setToolTipText("Delete this item");
            buttons.add(checkBox);
            buttons.add(deleteB);
        }
        panel.add(textFields, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.EAST);
        return panel;
    }

    //adding a new item to items ArrayList that belongs to one of form data, query
    //or header tabs
    public void addHeaderQueryForm(ArrayList<JPanel> items,JPanel item, int type) {
            items.add(item);
            updateHeaderQueryForm(items, type);
            updateUI();
    }

    //updates form data, query or header tab using elements in items ArrayList
    private void updateHeaderQueryForm(ArrayList<JPanel> items, int type) {
        JPanel target;
        String name, value = "New Value";
        if (type == 1) {
            target = header;
            name = "New Header";
        }
        else if (type == 2) {
            target = query;
            name = "New Name";
        }
        else {
            target = formData;
            name = "New Name";
        }
        target.removeAll();
        for (JPanel jPanel : items)
            target.add(jPanel);
        target.add(createItem(items,type, true, name, value));
    }

    //initializes auth tab
    private void authInit(){
        auth = new JPanel();
        auth.setLayout(new BoxLayout(auth, BoxLayout.Y_AXIS));
        auth.setBackground(new Color(46, 47, 43));
        auth.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JTextField textFieldToken = new JTextField();
        JTextField textFieldPrefix = new JTextField();
        JCheckBox checkBox = new JCheckBox();
        JPanel[] panels = new JPanel[3];
        JLabel[] labels = new JLabel[3];
        labels[0] = new JLabel("TOKEN");
        labels[1] = new JLabel("PREFIX");
        labels[2] = new JLabel("ENABLED");
        for (int i = 0; i < 3; i++){
            panels[i] = new JPanel();
            panels[i].setBackground(new Color(46, 47, 43));
            panels[i].setLayout(new BorderLayout(10, 0));
            panels[i].setMaximumSize(new Dimension(2000, 50));
            panels[i].setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 20));

            labels[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 20));
            labels[i].setForeground(Color.LIGHT_GRAY);
            labels[i].setFont(new Font(null, Font.PLAIN, 10));
        }

        textFieldToken.setForeground(Color.LIGHT_GRAY);
        textFieldToken.setBackground(new Color(46, 47, 43));
        textFieldToken.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));
        panels[0].add(labels[0], BorderLayout.WEST);
        panels[0].add(textFieldToken, BorderLayout.CENTER);

        textFieldPrefix.setForeground(Color.LIGHT_GRAY);
        textFieldPrefix.setBackground(new Color(46, 47, 43));
        textFieldPrefix.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));
        panels[1].add(labels[1], BorderLayout.WEST);
        panels[1].add(textFieldPrefix, BorderLayout.CENTER);

        checkBox.setSelected(true);
        checkBox.addActionListener(new CheckBoxActionHandler(textFieldToken, textFieldPrefix));
        panels[2].add(labels[2], BorderLayout.WEST);
        panels[2].add(checkBox, BorderLayout.CENTER);

        auth.add(panels[0]);
        auth.add(panels[1]);
        auth.add(panels[2]);

    }

    //handle prompt text for textFields using FocusListener
    private static class PromptTextHandler implements FocusListener {
        //holds the text that was in text field by default
        private String prevText;

        @Override
        public void focusGained(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            prevText = field.getText();
            if (field.getText().equals("Enter URL here") || field.getText().equals("Header") || field.getText().equals("Value")
            || field.getText().equals("Name"))
                field.setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            if (field.getText().equals("")) {
                switch (prevText) {
                    case "Enter URL here":
                        field.setText("Enter URL here");
                        break;
                    case "Header":
                        field.setText("Header");
                        break;
                    case "Value":
                        field.setText("Value");
                        break;
                    case "Name":
                        field.setText("Name");
                        break;
                    default:
                }
            }
        }
    }

    //it handles functionality of check box and just darkens textFields to
    //show they are disabled
    private static class CheckBoxActionHandler implements ActionListener {

        private JTextField textField1;
        private JTextField textField2;

        //initializes fields with textFields that the action should be performed on
        CheckBoxActionHandler(JTextField textField1, JTextField textField2) {
            this.textField1 = textField1;
            this.textField2 = textField2;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox cb = (JCheckBox) e.getSource();
            if (!cb.isSelected()) {
                textField1.setForeground(new Color(76, 76, 76));
                textField2.setForeground(new Color(76, 76, 76));
                textField1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(76, 76, 76)));
                textField2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(76, 76, 76)));

            } else {
                textField1.setForeground(Color.GRAY);
                textField2.setForeground(Color.GRAY);
                textField1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));
                textField2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(107, 107, 107)));
            }
        }
    }

    //shows a popup menu with "delete all" item when setting icon is clicked
    // and deletes all items from given ArrayList
    private class deleteAllHandler extends MouseAdapter{
        //items to be deleted
        private ArrayList<JPanel> items;
        //the type of items being deleted(form data, query or header)
        int type;


        public deleteAllHandler(ArrayList<JPanel> headers, int type) {
            this.items = headers;
            this.type = type;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("delete all");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (items.size() > 0) {
                        items.subList(0, items.size()).clear();
                    }
                    updateHeaderQueryForm(items, type);
                    updateUI();
                }
            });
            popupMenu.add(menuItem);
            if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON1) {
                popupMenu.show(e.getComponent(),
                        e.getX(), e.getY() + 5);
            }
        }
    }

    public void removeAllItems(){
        if (headers.size() > 0) {
            headers.subList(0, headers.size()).clear();
        }
        if (data.size() > 0) {
            data.subList(0, data.size()).clear();
        }
        if (queries.size() > 0) {
            queries.subList(0, queries.size()).clear();
        }
        ((JTextArea) ((JScrollPane) json.getComponent(0)).getViewport().getView()).setText("...");
        binaryPath.setText("No File Selected");
        updateHeaderQueryForm(headers, 1);
        updateHeaderQueryForm(queries, 2);
        updateHeaderQueryForm(data, 3);
        updateUI();
    }

    public void addSendRequestActionHandler(ActionListener listener){
        send.addActionListener(listener);
    }
    //shows a popup menu when first tab is clicked to select another tab
    //and the selected tab will be shown
    private class selectTabHandler extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuItem1 = new JMenuItem("Form Data");
            JMenuItem menuItem2 = new JMenuItem("JSON");
            JMenuItem menuItem3 = new JMenuItem("Binary");
            JMenuItem menuItem4 = new JMenuItem("No Body");
            menuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel formDataLabel = new JLabel("Form Data");
                    formDataLabel.addMouseListener(new selectTabHandler());
                    tabs.setTabComponentAt(0, formDataLabel);
                    tabs.setComponentAt(0, new JScrollPane(formData));
                    tabs.setSelectedIndex(0);
                    revalidate();
                }
            });
            menuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel jsonLabel = new JLabel("JSON");
                    jsonLabel.addMouseListener(new selectTabHandler());
                    tabs.setTabComponentAt(0, jsonLabel);
                    tabs.setComponentAt(0, json);
                    tabs.setSelectedIndex(0);
                    revalidate();
                }
            });
            menuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel binaryLabel = new JLabel("Binary");
                    binaryLabel.addMouseListener(new selectTabHandler());
                    tabs.setTabComponentAt(0, binaryLabel);
                    tabs.setComponentAt(0, binary);
                    tabs.setSelectedIndex(0);
                    revalidate();
                }
            });
            menuItem4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JLabel noBodyLabel = new JLabel("No Body");
                    noBodyLabel.addMouseListener(new selectTabHandler());
                    tabs.setTabComponentAt(0, noBodyLabel);
                    tabs.setComponentAt(0, noBody);
                    tabs.setSelectedIndex(0);
                    revalidate();
                }
            });
            popupMenu.add(menuItem1);
            popupMenu.add(menuItem2);
            popupMenu.add(menuItem3);
            popupMenu.add(menuItem4);
            if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON1) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY() + 5);
            }
        }

    }
}
