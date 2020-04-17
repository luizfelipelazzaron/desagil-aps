package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GateView extends FixedPanel implements ItemListener {
    private final Gate gate;
    private final JCheckBox[] inputs;
    private final JCheckBox result;
    private Color color;
    private final Switch[] switches;

    public GateView(Gate gate) {
        super(180, 250);
        this.gate = gate;

        int inputSize = gate.getInputSize();
        switches = new Switch[inputSize];
        this.inputs = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputs[i] = new JCheckBox();
            gate.connect(i, switches[i]);
        }

        result = new JCheckBox();

        int marginLeft, marginTop;

        marginLeft = 10;
        marginTop = 30;

        JLabel inputsLabel = new JLabel("Entrada:");
        add(inputsLabel, marginLeft, 10, 200, 15);

        for (int i = 0; i < inputSize; i++) {
            add(inputs[i], marginLeft, marginTop + 20 * i, 200, 15);
            inputs[i].addItemListener(this);
        }

        JLabel saidaLabel = new JLabel("Saída:");

        add(saidaLabel, marginLeft, marginTop + 20 * inputSize, 200, 15);
        add(result, marginLeft, marginTop + 20 + 20 * inputSize, 200, 15);

        color = Color.BLACK;

        result.setEnabled(false);

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputs[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        boolean result = this.gate.read();

        this.result.setSelected(result);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(color);
//        g.fillRect(300,400,10,10);

        getToolkit().sync();
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        update();
    }
}