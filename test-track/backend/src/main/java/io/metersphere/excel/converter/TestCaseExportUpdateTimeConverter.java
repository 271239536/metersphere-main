package io.metersphere.excel.converter;

import io.metersphere.commons.utils.DateUtils;
import io.metersphere.dto.TestCaseDTO;

public class TestCaseExportUpdateTimeConverter implements TestCaseExportConverter {

    @Override
    public String parse(TestCaseDTO testCase) {
        return DateUtils.getTimeString(testCase.getUpdateTime());
    }
}
