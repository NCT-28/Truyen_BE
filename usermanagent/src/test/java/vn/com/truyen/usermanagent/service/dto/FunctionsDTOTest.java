package vn.com.truyen.usermanagent.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.usermanagent.web.rest.TestUtil;

public class FunctionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FunctionsDTO.class);
        FunctionsDTO functionsDTO1 = new FunctionsDTO();
        functionsDTO1.setId(1L);
        FunctionsDTO functionsDTO2 = new FunctionsDTO();
        assertThat(functionsDTO1).isNotEqualTo(functionsDTO2);
        functionsDTO2.setId(functionsDTO1.getId());
        assertThat(functionsDTO1).isEqualTo(functionsDTO2);
        functionsDTO2.setId(2L);
        assertThat(functionsDTO1).isNotEqualTo(functionsDTO2);
        functionsDTO1.setId(null);
        assertThat(functionsDTO1).isNotEqualTo(functionsDTO2);
    }
}
