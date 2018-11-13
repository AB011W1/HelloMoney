package com.barclays.ussd.utils;

public enum PaginationEnum {
SPACED(" "), LISTED("\\\\n"), NOT_REQD("");

/** paginationType. */
private String paginationType;

private PaginationEnum(String paginationType) {
    this.setPaginationType(paginationType);
}

public void setPaginationType(String paginationType) {
    this.paginationType = paginationType;
}

public String getPaginationType() {
    return paginationType;
}
}