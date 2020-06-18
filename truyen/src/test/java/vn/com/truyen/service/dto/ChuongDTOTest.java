package vn.com.truyen.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.web.rest.TestUtil;

public class ChuongDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChuongDTO.class);
        ChuongDTO chuongDTO1 = new ChuongDTO();
        chuongDTO1.setId(1L);
        ChuongDTO chuongDTO2 = new ChuongDTO();
        assertThat(chuongDTO1).isNotEqualTo(chuongDTO2);
        chuongDTO2.setId(chuongDTO1.getId());
        assertThat(chuongDTO1).isEqualTo(chuongDTO2);
        chuongDTO2.setId(2L);
        assertThat(chuongDTO1).isNotEqualTo(chuongDTO2);
        chuongDTO1.setId(null);
        assertThat(chuongDTO1).isNotEqualTo(chuongDTO2);
    }
}
