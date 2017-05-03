
/**
 * Display.java
 * Used to display the GUI
 * Display given 8 items in a JTable
 */

import javax.swing.*;
import javax.swing.text.html.ObjectView;
import java.awt.*;

public class Display {

    private static Details d;

    public Display(Details d){

        this.d = d;
        JFrame frame = new JFrame("Current Prices");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getTable(frame);

    }

    private void getTable(JFrame frame) {

        Object columnNames[] = {"Symbol","Security Name","Price"};
        Object rowData[][] = new Object[8][3];

        rowData[0] = new Object[]{"FB", d.getRawData("FB", "SecurityName"), d.getRawData("FB", "Price")};
        rowData[1] = new Object[]{"VRTU", d.getRawData("VRTU", "SecurityName"), d.getRawData("VRTU", "Price")};
        rowData[2] = new Object[]{"MSFT", d.getRawData("MSFT", "SecurityName"), d.getRawData("MSFT", "Price")};
        rowData[3] = new Object[]{"GOOGL", d.getRawData("GOOGL", "SecurityName"), d.getRawData("GOOGL", "Price")};
        rowData[4] = new Object[]{"YHOO", d.getRawData("YHOO", "SecurityName"), d.getRawData("YHOO", "Price")};
        rowData[5] = new Object[]{"XLNX", d.getRawData("XLNX", "SecurityName"), d.getRawData("XLNX", "Price")};
        rowData[6] = new Object[]{"TSLA", d.getRawData("TSLA", "SecurityName"), d.getRawData("TSLA", "Price")};
        rowData[7] = new Object[]{"TXN", d.getRawData("TXN", "SecurityName"), d.getRawData("TXN", "Price")};

        JTable table = new JTable(rowData,columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800,198);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
