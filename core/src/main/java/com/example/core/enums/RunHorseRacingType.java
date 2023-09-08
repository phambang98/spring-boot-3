package com.example.core.enums;

public enum RunHorseRacingType {
    PREPARE("PREPARE", "THỜI GIAN CÁ CƯỢC"),
    PROCESS("PROCESS", "THỜI GIAN ĐUA"),
    RESULT("RESULT", "THỜI GIAN TRẢ RA KẾT QUẢ");


    RunHorseRacingType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
