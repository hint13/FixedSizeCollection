package dev.h13j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedSizeCollectionTest {

    @Test
    void whenNewCollectionWithAnySize_thenInitialSizeZero() {
        var collection = new FixedSizeCollection<Integer>(3);
        assertThat(collection).hasSize(0);
    }
}