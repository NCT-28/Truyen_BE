package vn.com.truyen.usermanagent.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FunctionsMapperTest {

    private FunctionsMapper functionsMapper;

    @BeforeEach
    public void setUp() {
        functionsMapper = new FunctionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(functionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(functionsMapper.fromId(null)).isNull();
    }
}
