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
    private final Light light;
    private final JCheckBox[] inputs;
    private final Image image;
    private final Switch[] switches;

    public GateView(Gate gate) {
        super();
        this.gate = gate;

        int inputSize = gate.getInputSize();
        switches = new Switch[inputSize];
        this.inputs = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputs[i] = new JCheckBox();
            gate.connect(i, switches[i]);
        }

        light = new Light(255, 0, 0);
        light.connect(0, gate);

        int marginLeft, marginTop;

        marginLeft = 10;
        marginTop = 30;

        for (int i = 0; i < inputSize; i++) {
            if (inputSize == 2) {
                add(inputs[i], marginLeft, marginTop + 20 * i);
            } else {
                add(inputs[i], marginLeft, marginTop + 12);
            }
            inputs[i].addItemListener(this);
        }
        // Carregamento das imagens
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

//        color = Color.BLACK;
        addMouseListener(this);

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
        // ...e chamamos repaint para atualizar a tela. rsrsrs
        repaint();
    }

    private boolean clickInsideCircle(int x, int y) {
        if (Math.pow(x - 130, 2) + Math.pow(y - 50, 2) <= Math.pow(10, 2)) {
            System.out.println("line 93 [debug purpose]: Click event inside the circle");
            return true;
        } else {
            System.out.println("line 96 [debug purpose]: Click event outside the circle");
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        System.out.println("line 108 [debug purpose]: (x,y): (" + x + "," + y + ")");

        // Se o clique foi dentro do circulo colorido/preto...
        if (clickInsideCircle(x, y)) {
            /*
             * (x-x0)² + (y-y0)² <= r²
             * ...então abrimos a janela seletora de cor...
             */
//            this.trueColor = JColorChooser.showDialog(this, null, this.color);
            Color oldColor = light.getColor();
            Color newColor = JColorChooser.showDialog(this, null, oldColor);

            if (newColor != null) {
                light.setColor(newColor);
            }
            // ...e chamamos repaint para atualizar a tela.
            update();
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

        g.setColor(light.getColor());
//        g.fillRect(120,30,40,40); // linha comentada
        g.fillOval(120, 40, 20, 20);

        getToolkit().sync();
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        update();
    }
}