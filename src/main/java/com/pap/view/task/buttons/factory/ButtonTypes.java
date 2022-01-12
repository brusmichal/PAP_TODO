package com.pap.view.task.buttons.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ButtonTypes{
    TASKS("TASKS","highlight-page-button","switch-button-off"),
    DONE("DONE","highlight-page-button","switch-button-off"),
    REPORTS("REPORTS","highlight-page-button","switch-button-off"),
    LOGOUT("LOGOUT", "log-out-button", "highlight-log-out-button"),
    SORT("SORTING", "sort-button-highlighted", "sort-button"),
    PERIOD ("PERIOD", "time-period-button-highlighted", "time-period-button");

    public static final String ACTIVE_STYLE = "switch-button-on";
    private final String text;
    private final String highlightedStyle;
    private final String normalStyle;
}
