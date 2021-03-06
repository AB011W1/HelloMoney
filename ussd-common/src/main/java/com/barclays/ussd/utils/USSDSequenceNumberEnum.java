/**
 * USSDInputParamsEnum.java
 */
package com.barclays.ussd.utils;

// TODO: Auto-generated Javadoc
/**
 * The Enum USSDInputParamsEnum.
 *
 * @author BTCI
 */
public enum USSDSequenceNumberEnum {
SEQUENCE_NUMBER_ENDS(-1),
SEQUENCE_NUMBER_ONE(1),
SEQUENCE_NUMBER_TWO(2),
SEQUENCE_NUMBER_THREE(3),
SEQUENCE_NUMBER_FOUR(4),
SEQUENCE_NUMBER_FIVE(5),
SEQUENCE_NUMBER_SIX(6),
SEQUENCE_NUMBER_SEVEN(7),
SEQUENCE_NUMBER_EIGHT(8),
SEQUENCE_NUMBER_NINE(9),
SEQUENCE_NUMBER_TEN(10),
SEQUENCE_NUMBER_ELEVEN(11),
SEQUENCE_NUMBER_TWELVE(12),
SEQUENCE_NUMBER_THIRTEEN(13),
SEQUENCE_NUMBER_FOURTEEN(14),
SEQUENCE_NUMBER_FIFTEEN(15),
//CR82
SEQUENCE_NUMBER_SIXTEEN(16),
SEQUENCE_NUMBER_SEVENTEEN(17),
SEQUENCE_NUMBER_EIGHTTEEN(18),
SEQUENCE_NUMBER_NINETEEN(19),
SEQUENCE_NUMBER_TWENTY(20),
SEQUENCE_NUMBER_TWENTYONE(21),
SEQUENCE_NUMBER_TWENTYTWO(22),
SEQUENCE_NUMBER_TWENTYTHREE(23),
SEQUENCE_NUMBER_TWENTYFOUR(24),
SEQUENCE_NUMBER_TWENTYFIVE(25),
SEQUENCE_NUMBER_TWENTYSIX(26),
SEQUENCE_NUMBER_TWENTYSEVEN(27),
SEQUENCE_NUMBER_TWENTYEIGHT(28),
SEQUENCE_NUMBER_TWENTYNINE(29),
SEQUENCE_NUMBER_THIRTY(30),
SEQUENCE_NUMBER_THIRTYONE(31),
SEQUENCE_NUMBER_THIRTYTWO(32),
SEQUENCE_NUMBER_THIRTYTHREE(33),
SEQUENCE_NUMBER_THIRTYFOUR(34),
SEQUENCE_NUMBER_THIRTYFIVE(35),
SEQUENCE_NUMBER_THIRTYSIX(36),
SEQUENCE_NUMBER_THIRTYSEVEN(37),
SEQUENCE_NUMBER_THIRTYEIGHT(38),
SEQUENCE_NUMBER_THIRTYNINE(39),
SEQUENCE_NUMBER_FOURTY(40),
SEQUENCE_NUMBER_FOURTYONE(41),
SEQUENCE_NUMBER_FOURTYTWO(42),
SEQUENCE_NUMBER_FOURTYTHREE(43),
SEQUNCE_NUMBER_FOURTYFOUR(44),
SEQUNCE_NUMBER_FOURTYFIVE(45)
;
private int sequenceNo;

private USSDSequenceNumberEnum(final int sequenceNo) {
    this.sequenceNo = sequenceNo;
}

public int getSequenceNo() {
    return sequenceNo;
}
}
