package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private final Gate gate;
    private final JCheckBox[] inputs;
    private final JCheckBox result;
    private final Image image;
    private final Switch[] switches;
    //private final Light light;
    private Color color;

    public GateView(Gate gate) {
        super(180, 250);
        this.gate = gate;
        //light = new Light(255,0,0);

        //Color color = light.getColor();

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
        //add(inputsLabel, marginLeft, 10, 200, 15);

        for (int i = 0; i < inputSize; i++) {
            if (inputSize == 2) {
                add(inputs[i], marginLeft, marginTop + 20 * i, 20, 15);
                inputs[i].addItemListener(this);
            } else {
                add(inputs[i], marginLeft, marginTop + 12, 20, 15);
                inputs[i].addItemListener(this);
            }
        }

        JLabel saidaLabel = new JLabel("Saída:");

        //add(saidaLabel, marginLeft + 40, marginTop + 12, 20, 15);
        add(result, marginLeft + 110, marginTop + 12, 20, 15);

        // Carregamento das imagens
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

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
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if (x >= 210 && x < 235 && y >= 311 && y < 336) {

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);

            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 20, 25, 100, 50, this);

        g.setColor(color);
        g.fillRect(120,30,40,40);

        getToolkit().sync();
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        update();
    }
}