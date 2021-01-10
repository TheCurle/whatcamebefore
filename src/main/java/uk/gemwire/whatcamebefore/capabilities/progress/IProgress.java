package uk.gemwire.whatcamebefore.capabilities.progress;

public interface IProgress {

    void setProgress(int level, int xp);

    int getProgressLevel();

    int getProgressXP();
}
