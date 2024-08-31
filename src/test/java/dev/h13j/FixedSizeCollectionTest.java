package dev.h13j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class FixedSizeCollectionTest {

    @SuppressWarnings({"ConstantValue"})
    @Test
    void whenNewCollectionWithAnySize_thenInitialSizeZeroAndItsEmpty() {
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

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    void whenAddAllFromEmptyList_thenNothingChanging() {
        var collection = new FixedSizeCollection<String>(3);
        var emptyList = new ArrayList<String>();

        assertThat(collection.addAll(emptyList)).isFalse();
        assertThat(collection).hasSize(0);
        assertThat(collection.hasNull()).isFalse();
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
    void whenAddElementsToFullList_thenReturnFalseAndIsFullTrue() {
        var collection = new FixedSizeCollection<Integer>(2);
        collection.add(1);
        collection.add(2);

        assertThat(collection).hasSize(2);
        assertThat(collection.isFull()).isTrue();

        collection.add(3);
        assertThat(collection).hasSize(2);
        assertThat(collection.isFull()).isTrue();
        collection.addAll(List.of(3, 4));
        assertThat(collection).hasSize(2);
        assertThat(collection.isFull()).isTrue();
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    void whenManualIterateAndCallNextForEmptyCollection_thenRaiseException() {
        var collection = new FixedSizeCollection<Integer>(10);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    var iterator = collection.iterator();
                    iterator.next();
                });
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    void whenManualIterateAndCallRemoveForEmptyCollection_thenRaiseException() {
        var collection = new FixedSizeCollection<Integer>(10);

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {
                    var iterator = collection.iterator();
                    iterator.remove();
                });
    }

    @Test
    void whenSearchExistentElement_thenFoundIt() {
        var collection = new FixedSizeCollection<String>(6);

        assertThat(collection.contains("a")).isFalse();

        collection.addAll(List.of("a", "b", "c", "f"));

        assertThat(collection.contains(null)).isFalse();
        assertThat(collection.hasNull()).isFalse();

        assertThat(collection.contains("a")).isTrue();
        assertThat(collection.contains("d")).isFalse();

        collection.add(null);
        collection.add("z");
        assertThat(collection.contains(null)).isTrue();
        assertThat(collection.contains("z")).isTrue();
        assertThat(collection.hasNull()).isTrue();
    }

    @Test
    void whenSearchExistentElements_thenFoundThem() {
        var collection = new FixedSizeCollection<String>(6);
        assertThat(collection.containsAll(new ArrayList<String>())).isFalse();

        collection.addAll(List.of("a", "b", "c", "f", "e", "h"));
        var searchedCollection = new FixedSizeCollection<String>(2);
        searchedCollection.addAll(List.of("b", "f"));
        assertThat(collection.containsAll(searchedCollection)).isTrue();

        var otherCollection = new FixedSizeCollection<String>(2);
        otherCollection.addAll(List.of("a", "s"));
        assertThat(collection.containsAll(otherCollection)).isFalse();
    }

    @Test
    void whenRemoveElement_thenItRemovedAndSizeDecreased() {
        var collection = new FixedSizeCollection<String>(6);
        collection.addAll(List.of("a", "b", "c", "c", "d", "e"));

        assertThat(collection.remove("c")).isTrue();
        assertThat(collection).hasSize(4)
                .containsExactlyInAnyOrderElementsOf(List.of("a", "b", "d", "e"));

        assertThat(collection.remove("z")).isFalse();
        assertThat(collection).hasSize(4)
                .containsExactlyInAnyOrderElementsOf(List.of("a", "b", "d", "e"));
    }

    @Test
    void whenRemoveNullElement_thenItRemovedAndSizeDecreased() {
        var collection = new FixedSizeCollection<Integer>(5);
        collection.add(1);
        collection.add(2);
        collection.add(null);
        collection.add(null);
        collection.add(5);

        assertThat(collection.remove(null)).isTrue();
        assertThat(collection).hasSize(3)
                .containsExactlyInAnyOrder(1, 2, 5);
        assertThat(collection.hasNull()).isFalse();
    }

    @Test
    void whenRemoveElements_thenTheyRemovedAndSizeDecreased() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));

        assertThat(collection.removeAll(List.of("c", "a"))).isTrue();
        assertThat(collection).hasSize(1)
                .containsOnly("b");
    }

    @Test
    void whenRemoveEmptyList_thenNothingChanged() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));

        assertThat(collection.removeAll(new ArrayList<String>())).isFalse();
        assertThat(collection).hasSize(5);
    }

    @Test
    void whenClearCollection_thenSizeZero() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));
        assertThat(collection).hasSize(5);

        collection.clear();
        assertThat(collection).hasSize(0);
    }

    @Test
    void whenToArray_thenReturnNewArray() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));

        Object[] actual = collection.toArray();
        assertThat(actual).isEqualTo(new Object[]{"a", "b", "c", "c", "c"});
    }

    @Test
    void whenToArrayWithType_thenReturnNewArray() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));

        String[] actual = collection.toArray(new String[0]);
        assertThat(actual).isEqualTo(new String[]{"a", "b", "c", "c", "c"});
    }

    @Test
    void whenToString_thenReturnStringOfElements() {
        var collection = new FixedSizeCollection<String>(5);
        collection.addAll(List.of("a", "b", "c", "c", "c"));

        assertThat(collection.toString()).isEqualTo("a b c c c");
    }

    @Test
    void whenRetainAll_thenModifyCollection() {
        var collection = new FixedSizeCollection<String>(3);
        collection.addAll(List.of("a", "b", "c"));

        final var retainCollection = new FixedSizeCollection<String>(3);
        retainCollection.addAll(List.of("a", "c", "d"));

        assertThat(collection.retainAll(retainCollection)).isTrue();
        assertThat(collection).hasSize(2)
                .containsExactlyInAnyOrder("a", "c");
    }

    @Test
    void whenRetainAllWithEmptyCollection_thenNothingChanged() {
        var collection = new FixedSizeCollection<String>(3);
        collection.addAll(List.of("a", "b", "c"));
        final var retainCollection = new FixedSizeCollection<String>(3);

        assertThat(collection.retainAll(retainCollection)).isFalse();
        assertThat(collection).hasSize(3)
                .containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void whenRemoveIfEven_thenElementsRemoved() {
        var collection = new FixedSizeCollection<Integer>(10);
        collection.addAll(List.of(1, 2, 3, 4, 5));
        collection.add(null);
        collection.addAll(List.of(7, 8, 9, 10));

        assertThat(collection.removeIf((e) -> e % 2 == 0)).isTrue();
        assertThat(collection).hasSize(5)
                .containsExactlyInAnyOrder(1, 3, 5, 7, 9);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void whenRemoveIfHasNullFilter_thenRaiseException() {
        var collection = new FixedSizeCollection<Integer>(10);
        collection.addAll(List.of(1, 2, 3, 4, 5));
        collection.add(null);
        collection.addAll(List.of(7, 8, 9, 10));
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> collection.removeIf(null));
    }
}
