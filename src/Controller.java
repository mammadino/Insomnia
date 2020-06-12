import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller {
    private InsomniaView view;
    private HttpManager model;
    private RightPanel rightPanel;
    private LeftPanel leftPanel;
    private MiddlePanel middlePanel;

    public Controller(InsomniaView view, HttpManager model) {
        this.view = view;
        this.model = model;

        rightPanel = view.getRightPanel();
        middlePanel = view.getMiddlePanel();
        leftPanel = view.getLeftPanel();
        leftPanel.requestsListModelInit(model.getRequests());
        leftPanel.addCreateNewRequestHandler(new NewRequestHandler());
        leftPanel.addListSelectionHandler(new RequestsListSelectionHandler());
    }

    private class RequestsListSelectionHandler implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList<Request> requestJList = (JList<Request>) e.getSource();
            Request selectedRequest = requestJList.getSelectedValue();
            updateFrame(selectedRequest);
        }
    }

    private class NewRequestHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Request request = new Request(leftPanel.getNewRequestNameTextField().getText(),
                    (String) leftPanel.getNewRequestMethodsComboBox().getSelectedItem());
            model.setCurrentRequest(request);
            model.saveRequest(request);
            leftPanel.addRequest(request);

        }
    }

    private void updateFrame(Request selectedRequest){
        middlePanel.setSelectedMethod(selectedRequest.getMethod());
        middlePanel.setUrlText(selectedRequest.getUrl());
        middlePanel.removeAllItems();
        for (String key: selectedRequest.getHeaders().keySet()){
            JPanel item = middlePanel.createItem(middlePanel.getHeaders(), 1, false, key, selectedRequest.getHeaders().get(key));
            middlePanel.addHeaderQueryForm(middlePanel.getHeaders(), item, 1);
        }
        for (String name: selectedRequest.getFormData().keySet()){
            JPanel item = middlePanel.createItem(middlePanel.getData(), 3, false, name, selectedRequest.getFormData().get(name));
            middlePanel.addHeaderQueryForm(middlePanel.getData(), item, 3);
        }
        for (String name: selectedRequest.getQueries().keySet()){
            JPanel item = middlePanel.createItem(middlePanel.getQueries(), 2, false, name, selectedRequest.getQueries().get(name));
            middlePanel.addHeaderQueryForm(middlePanel.getQueries(), item, 2);
        }
        if (selectedRequest.getJson().length() != 0){
            ((JTextArea)((JScrollPane)middlePanel.getJson().getComponent(0)).getViewport().getView()).setText(selectedRequest.getJson());
        }
        if (selectedRequest.getResponse() != null) {
            rightPanel.addStatusLine(selectedRequest.getResponse().getCode(), selectedRequest.getResponse().getReasonPhrase());
            rightPanel.addTimeLabel(selectedRequest.getResponseTime());
            try {
                rightPanel.addSizeLabel(selectedRequest.getResponse().getEntity().getContent().readAllBytes().length);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            for (Header header : selectedRequest.getResponse().getHeaders())
                rightPanel.addHeader(header.getName(), header.getValue());
            try {
                ((JTextArea) ((JScrollPane) rightPanel.getPreview().getComponent(0)).getViewport().getView()).setText(EntityUtils.toString(selectedRequest.getResponse().getEntity()));
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        }
    }
}