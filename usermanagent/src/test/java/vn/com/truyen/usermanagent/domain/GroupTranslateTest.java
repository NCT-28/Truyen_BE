package vn.com.truyen.usermanagent.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.usermanagent.web.rest.TestUtil;

public class GroupTranslateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupTranslate.class);
        GroupTranslate groupTranslate1 = new GroupTranslate();
        groupTranslate1.setId(1L);
        GroupTranslate groupTranslate2 = new GroupTranslate();
        groupTranslate2.setId(groupTranslate1.getId());
        assertThat(groupTranslate1).isEqualTo(groupTranslate2);
        groupTranslate2.setId(2L);
        assertThat(groupTranslate1).isNotEqualTo(groupTranslate2);
        groupTranslate1.setId(null);
        assertThat(groupTranslate1).isNotEqualTo(groupTranslate2);
    }
}
