package uk.gemwire.whatcamebefore.capabilities;

public interface IProgress {

    void setProgress(int level, int xp);

    int getProgressLevel();

    int getProgressXP();
}
