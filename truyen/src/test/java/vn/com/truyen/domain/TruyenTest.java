package vn.com.truyen.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import vn.com.truyen.web.rest.TestUtil;

public class TruyenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Truyen.class);
        Truyen truyen1 = new Truyen();
        truyen1.setId(1L);
        Truyen truyen2 = new Truyen();
        truyen2.setId(truyen1.getId());
        assertThat(truyen1).isEqualTo(truyen2);
        truyen2.setId(2L);
        assertThat(truyen1).isNotEqualTo(truyen2);
        truyen1.setId(null);
        assertThat(truyen1).isNotEqualTo(truyen2);
    }
}
