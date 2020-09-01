package vn.com.truyen.usermanagent.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.usermanagent.web.rest.TestUtil;

public class FunctionsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functions.class);
        Functions functions1 = new Functions();
        functions1.setId(1L);
        Functions functions2 = new Functions();
        functions2.setId(functions1.getId());
        assertThat(functions1).isEqualTo(functions2);
        functions2.setId(2L);
        assertThat(functions1).isNotEqualTo(functions2);
        functions1.setId(null);
        assertThat(functions1).isNotEqualTo(functions2);
    }
}
