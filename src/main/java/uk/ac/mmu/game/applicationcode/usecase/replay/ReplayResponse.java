package uk.ac.mmu.game.applicationcode.usecase.replay;

import uk.ac.mmu.game.applicationcode.domainmodel.GameRecord;

public class ReplayResponse {
    private final GameRecord record;
    private final boolean success;
    private final String errorMessage;

    private ReplayResponse(GameRecord record, boolean success, String errorMessage) {
        this.record = record;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public static ReplayResponse success(GameRecord record) {
        return new ReplayResponse(record, true, null);
    }

    public static ReplayResponse failure(String errorMessage) {
        return new ReplayResponse(null, false, errorMessage);
    }

    public GameRecord getRecord() {
        return record;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}