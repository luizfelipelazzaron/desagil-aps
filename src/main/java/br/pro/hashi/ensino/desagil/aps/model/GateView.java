package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class GateView implements ActionListener, MouseListener {
    private final Gate gate;

    private JCheckBox box;

    public GateView(Gate gate){
        this.gate = gate;
    }
}
