package br.pro.hashi.ensino.desagil.aps.model.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class GateView implements ActionListener, MouseListener {
    private final Gate gate;

    private final JCheckBox checkbox;

    public GateView(Gate gate) {
        this.gate = gate;

        checkbox = new JCheckBox();
        JLabel checkLabel = new JLabel("1");

        addMouseListener(this);
        update();


    }

}
