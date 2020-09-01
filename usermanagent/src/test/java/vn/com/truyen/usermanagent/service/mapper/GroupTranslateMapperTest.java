package vn.com.truyen.usermanagent.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupTranslateMapperTest {

    private GroupTranslateMapper groupTranslateMapper;

    @BeforeEach
    public void setUp() {
        groupTranslateMapper = new GroupTranslateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(groupTranslateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(groupTranslateMapper.fromId(null)).isNull();
    }
}
