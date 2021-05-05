package cmd;

import utility.HuskeyArgs;

abstract class Cmd {
    protected HuskeyArgs hkArgs;

    public Cmd(HuskeyArgs hkArgs) {
        this.hkArgs = hkArgs;
    }

    abstract void run();
}
