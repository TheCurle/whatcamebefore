package uk.gemwire.whatcamebefore.capabilities.progress;

public class ProgressDefaultProvider implements IProgress {

    private String progress;

    @Override
    public void setProgress(int level, int xp) {
        this.progress = Integer.toString(level).concat(":").concat(Integer.toString(xp));
    }

    @Override
    public int getProgressXP() {
        if(progress == null || progress.length() == 0) {
            return 0;
        } else {
            System.out.println("Parsing capability for XP - current progress = ".concat(progress));
            return Integer.parseInt(progress.substring(progress.indexOf(":") + 1));
        }
    }

    @Override
    public int getProgressLevel() {
        if(progress == null || progress.length() == 0) {
            return 0;
        } else {
            System.out.println("Parsing capability for level - current progress = ".concat(progress));
            return Integer.parseInt(progress.substring(0, progress.indexOf(':')));
        }
    }
}
