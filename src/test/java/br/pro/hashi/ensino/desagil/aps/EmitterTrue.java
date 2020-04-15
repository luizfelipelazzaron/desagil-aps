package br.pro.hashi.ensino.desagil.aps;

import br.pro.hashi.ensino.desagil.aps.model.Emitter;

public class EmitterTrue implements Emitter {
    @Override
    public boolean read() {
        return true;
    }
}
