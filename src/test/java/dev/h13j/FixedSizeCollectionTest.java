package dev.h13j;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FixedSizeCollectionTest {

    @Test
    void whenNewCollectionWithAnySize_thenInitialSizeZeroAndIsEmpty() {
        var collection = new FixedSizeCollection<Integer>(3);
        assertThat(collection).hasSize(0);
        assertThat(collection.isEmpty()).isTrue();
    }

    @Test
    void whenAddOneElementToNewCollection_thenSizeOneAndElementIsPresent() {
        var collection = new FixedSizeCollection<Integer>(3);
        collection.add(13);
        assertThat(collection).hasSize(1)
                .containsExactlyInAnyOrderElementsOf(List.of(13));
    }

    @Test
    void whenAddOneNullElementToNewCollection_thenSizeOneAndElementIsPresent() {
        var collection = new FixedSizeCollection<Integer>(3);
        collection.add(null);
        assertThat(collection.size()).isEqualTo(1);
        assertThat(collection).containsOnlyNulls();
    }

    @Test
    void whenAddElement_thenReturnSuccess() {
        var collection = new FixedSizeCollection<Integer>(1);
        assertThat(collection.add(13)).isTrue();
        assertThat(collection.add(14)).isFalse();
        assertThat(collection).hasSize(1);
    }

    @Test
    void whenAddAllElements_thenReturnSuccess() {
        var collection = new FixedSizeCollection<Integer>(5);
        collection.add(1);
        collection.add(2);

        var collection2 = List.of(2, 3);

        assertThat(collection.addAll(collection2)).isTrue();
        assertThat(collection).hasSize(4)
                .containsExactlyInAnyOrder(1, 2, 2, 3);
    }

    @Test
    void whenSearchExistentElement_thenFoundIt() {
        var collection = new FixedSizeCollection<String>(6);
        collection.addAll(List.of("a", "b", "c", "f"));

        assertThat(collection.contains(null)).isFalse();
        assertThat(collection.hasNull()).isFalse();

        assertThat(collection.contains("a")).isTrue();
        assertThat(collection.contains("d")).isFalse();

        collection.add(null);
        assertThat(collection.contains(null)).isTrue();
        assertThat(collection.hasNull()).isTrue();
    }

    @Test
    void whenSearchExistentElements_thenFoundThem() {
        var collection = new FixedSizeCollection<String>(6);
        collection.addAll(List.of("a", "b", "c", "f", "e", "h"));
        var searchedCollection = new FixedSizeCollection<String>(2);
        searchedCollection.addAll(List.of("b", "f"));
        assertThat(collection.containsAll(searchedCollection)).isTrue();

        var otherCollection = new FixedSizeCollection<String>(2);
        otherCollection.addAll(List.of("a", "s"));
        assertThat(collection.containsAll(otherCollection)).isFalse();
    }
}