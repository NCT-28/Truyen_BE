package vn.com.truyen.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.web.rest.TestUtil;

public class ChuongTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chuong.class);
        Chuong chuong1 = new Chuong();
        chuong1.setId(1L);
        Chuong chuong2 = new Chuong();
        chuong2.setId(chuong1.getId());
        assertThat(chuong1).isEqualTo(chuong2);
        chuong2.setId(2L);
        assertThat(chuong1).isNotEqualTo(chuong2);
        chuong1.setId(null);
        assertThat(chuong1).isNotEqualTo(chuong2);
    }
}
