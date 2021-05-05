package cmd;

import types.HuskeyArgs;

abstract class Cmd {
    protected HuskeyArgs hkArgs;

    public Cmd(HuskeyArgs hkArgs) {
        this.hkArgs = hkArgs;
    }

    abstract void run();
}
