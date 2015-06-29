package com.crypto.enums;

/**
 * Trade condition type, defines the kind of trade condition is checked.
 * <p/>
 * Created by Jan Wicherink on 19-6-15.
 */
public enum TradeConditionType {

    POS_MACD_CHANGE("MACD <b>%s</b> stijgt door nullijn in een periode van <b>%d</b>"),             // MACD rises from below zero to above zero
    NEG_MACD_CHANGE("MACD <b>%s</b> daalt door nullijn  in een periode van <b>%d</b>"),             // MACD moves from above zero to below zero
    MACD_POSITIVE("MACD <b>%s</b> moet positief zijn voor een periode van <b>%d</b>"),              // MACD must be positive, greater then zero
    MACD_NEGATIVE("MACD <b>%s</b> moet negatief zijn voor een periode van <b>%d</b>"),              //MACD must be less then zero
    BTC_GT_TREND("Koers hoger dan trendlijn <b>%s</b> over een periode van <b>%d</b>"),             // Cryptcoin rate greater then trendline
    BTC_LT_TREND("Koers lager dan trendlijn <b>%s</b> over een periode van <b>%d</b>"),             // Cryptocoin rate less then trendline
    MACD_GT_PERC_TREND("MACD <b>%s</b> moet hoger zijn dan <b>%3.2f</b> procent</b> van trendlijn <b>%s</b> voor een periode van <b>%d</b>"), // MACD must be greater then a percentage of a trend
    MACD_LS_PERC_TREND("MACD <b>%s</b> moet lager zijn dan <b>%3.2f procent</b> van trendlijn <b>%s</b> voor een periode van <b>%d</b>"), //
    POS_MACD_PERC("Stijging van MACD <b>%s</b> met <b>%d procent</b> ten opzichte van MACD <b>%s</b> in een periode van <b>%d</b>"), // Positive MACD change in terms of percentage of a reference MACD
    NEG_MACD_PERC("Daling van MACD <b>%s</b> met <b>%3.2d procent</b> ten opzichte van MACD <b>%s</b> in een periode van <b>%d</b>"), // Negative MACD change in terms of percentage of a reference MACD
    MACD_INCREASE("Tussen <b>%3.2f en %3.2f procent</b>  stijging van MACD <b>%s</b>  in een periode van <b>%d</b>"), // Gradual MACD increase over given period
    MACD_DECREASE("Tussen <b>%3.2f en %3.2f procent</b> daling van MACD <b>%s</b>  in een periode van <b>%d</b>"), // Gradual MACD decrease over given period
    MACD_DIFF_GT_PERC("Verschil tussen MACD <b>%s</b> en MACD <b>%s </b> moet groter zijn dan <b>%3.2f</b> procent van MACD <b>%s</b> voor een periode van <b>%d</b>"), // MACD difference must be a percentage of reference MACD
    TREND_DIFF_GT_PERC("Trendverschil tussen <b>%s</b> en <b>%s </b> moet groter zijn dan <b>%3.2f</b> procent van trend <b>%s</b> voor een periode van <b>%d</b>"), // Trend difference must be a percentage of reference Trend
    MACD_DIFF_LT_PERC("Verschil tussen MACD <b>%s</b> en MACD <b>%s </b> moet kleiner zijn dan <b>%3.2f</b> procent van MACD <b>%s</b> voor een periode van <b>%d</b>"), //MACD difference must be less then a percentage of reference MACD
    TREND_DIFF_LT_PERC("Trendverschil tussen <b>%s</b> en <b>%s </b> moet kleiner zijn dan <b>%3.2f</b> procent van trend <b>%s</b> voor een periode van <b>%d</b>"),  // Trend difference must be less then a percentage of reference Trend
    TREND_INCREASE("Tussen <b>%3.2f en %3.2f procent</b> stijging van trend <b>%s</b> in een periode van <b>%d</b>"), // Gradual trend decrease over given period
    TREND_DECREASE("Tussen <b>%3.2f en %3.2f procent</b> daling van trend <b>%s</b> in een periode van <b>%d</b>"), // Gradual trend decrease over given period
    POS_TREND_CHANGE("Positieve trendverandering voor trend <b>%s</b> in een periode van <b>%d</b>"),  // Positive trend change
    NEG_TREND_CHANGE("Negatieve trendverandering voor trend <b>%s</b> in een periode van <b>%d</b>"), //Negative trend change
    POS_TREND("Positieve trend voor trend <b>%s</b> in een periode van <b>%d</b>"), // Positive trend
    NEG_TREND("Negatieve trend voor trend <b>%s</b> in een periode van <b>%d</b>"), // Negative trend
    BTC_LT_RATE("Koers lager dan waarde <b>%s</b> over een periode van <b>%d</b>"), // Exchange rate less then value
    BTC_GT_RATE("Koers hoger dan waarde <b>%s</b> over een periode van <b>%d</b>"), // Exchange rate greater then value
    DELTA_GT_PERC_TREND("Trendverandering lager dan waarde <b>%3.2f</b> procent van trend <b>%s</b> : over een periode van <b>%d</b>"), // Trend change less than a percentage of trend
    DELTA_LT_PERC_TREND("Trendverandering lager dan waarde <b>%3.2f</b> procent van trend <b>%s</b> : over een periode van <b>%d</b>"), // Trend change less than a percentage of trend
    BTC_GT_PERC_TREND("Koers hoger dan <b>%3.2f procent</b> van trendlijn <b>%s</b> over een periode van <b>%d</b>"), // Cryptcoin rate greater then percentage of trendline
    BTC_LT_PERC_TREND("Koers lager dan <b>%3.2f procent</b> van trendlijn <b>%s</b> over een periode van <b>%d</b>"); // Cryptocoin rate less then percentage of trendline

    private String messageFormat;

    /**
     * Constructor
     *
     * @param messageFormat the messasge format used to be displayed to the user of the trade condition rule
     */
    TradeConditionType(final String messageFormat) {
        this.messageFormat = messageFormat;
    }
}
