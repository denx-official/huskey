package utility;

public class CheckArgs {
    private HuskeyArgs hkArgs;

    public CheckArgs(HuskeyArgs hkArgs) {
        this.hkArgs= hkArgs;
    }

    public void run()  {
        throw new IllegalArgumentException();
    }
}
