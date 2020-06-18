package vn.com.truyen.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.web.rest.TestUtil;

public class TruyenDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TruyenDTO.class);
        TruyenDTO truyenDTO1 = new TruyenDTO();
        truyenDTO1.setId(1L);
        TruyenDTO truyenDTO2 = new TruyenDTO();
        assertThat(truyenDTO1).isNotEqualTo(truyenDTO2);
        truyenDTO2.setId(truyenDTO1.getId());
        assertThat(truyenDTO1).isEqualTo(truyenDTO2);
        truyenDTO2.setId(2L);
        assertThat(truyenDTO1).isNotEqualTo(truyenDTO2);
        truyenDTO1.setId(null);
        assertThat(truyenDTO1).isNotEqualTo(truyenDTO2);
    }
}
