package com.finastra.apps.gateway.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.finastra.apps.gateway.web.rest.TestUtil;

public class DependentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dependent.class);
        Dependent dependent1 = new Dependent();
        dependent1.setId(1L);
        Dependent dependent2 = new Dependent();
        dependent2.setId(dependent1.getId());
        assertThat(dependent1).isEqualTo(dependent2);
        dependent2.setId(2L);
        assertThat(dependent1).isNotEqualTo(dependent2);
        dependent1.setId(null);
        assertThat(dependent1).isNotEqualTo(dependent2);
    }
}
