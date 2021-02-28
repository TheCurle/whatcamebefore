package uk.gemwire.whatcamebefore.capabilities.neutrality;

public class EjectionDefaultProvider implements IEjection {

    private int timer = -1;

    @Override
    public void setTimer(int newTime) {
        //System.out.println("Parsing capability for ejection - current timer = " + newTime);
        this.timer = newTime;
    }

    @Override
    public int getTimer() {
        //if(timer != 0) System.out.println("Parsing capability for ejection - current timer = " + timer);
        return timer;
    }
}
