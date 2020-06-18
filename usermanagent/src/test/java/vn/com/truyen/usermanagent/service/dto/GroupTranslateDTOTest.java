package vn.com.truyen.usermanagent.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.usermanagent.web.rest.TestUtil;

public class GroupTranslateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupTranslateDTO.class);
        GroupTranslateDTO groupTranslateDTO1 = new GroupTranslateDTO();
        groupTranslateDTO1.setId(1L);
        GroupTranslateDTO groupTranslateDTO2 = new GroupTranslateDTO();
        assertThat(groupTranslateDTO1).isNotEqualTo(groupTranslateDTO2);
        groupTranslateDTO2.setId(groupTranslateDTO1.getId());
        assertThat(groupTranslateDTO1).isEqualTo(groupTranslateDTO2);
        groupTranslateDTO2.setId(2L);
        assertThat(groupTranslateDTO1).isNotEqualTo(groupTranslateDTO2);
        groupTranslateDTO1.setId(null);
        assertThat(groupTranslateDTO1).isNotEqualTo(groupTranslateDTO2);
    }
}
