interface Command {
    void execute(String[] cmdParts) throws ExEquipmentRecordMissing,ExDoubleBorrow,ExSetIsNotAvailable, ExOverlappingRequestAndBorrow,ExOverlappingRequest,ExNegativeRequestDays,ExInavlidRequestDays, ExDuplicateMemberID, ExEquipmentSetNotAvailable,ExDuplicateEquipment,ExInvalidDate,ExInvalidDateFormat,ExEquipmentNotFound,ExMemberNotFound;
}
