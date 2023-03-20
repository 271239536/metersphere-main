package io.metersphere.validate;

import io.metersphere.exception.CustomFieldValidateException;
import io.metersphere.commons.utils.DateUtils;
import io.metersphere.dto.CustomFieldDao;
import io.metersphere.i18n.Translator;
import org.apache.commons.lang3.StringUtils;

public class CustomFieldDateValidator extends AbstractCustomFieldValidator {

    public void validate(CustomFieldDao customField, String value) throws CustomFieldValidateException {
        validateRequired(customField, value);
        try {
            if (StringUtils.isNotBlank(value)) {
                DateUtils.getDate(value);
            }
        } catch (Exception e) {
            CustomFieldValidateException.throwException(String.format(Translator.get("custom_field_date_tip"), customField.getName(), DateUtils.DATE_PATTERM));
        }
    }
}
