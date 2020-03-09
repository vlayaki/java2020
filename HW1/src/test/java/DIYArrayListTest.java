import hw.DIYArrayList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DIYArrayListTest {

    @Test
    void given_empty_list_size_should_return_0() {
        DIYArrayList<Integer> list = new DIYArrayList<>();
        assertEquals(0, list.size());
    }

    @Test
    void given_list_with_48_elements_size_should_return_48() {
        DIYArrayList<Integer> list = createDIYList(48);
        assertEquals(48, list.size());
    }

    @Test
    void given_empty_list_when_add_28_elements_size_should_return_28_elements() {
        DIYArrayList<Integer> list = new DIYArrayList<>();
        list.addAll(createArrayList(28));
        assertEquals(28, list.size());
    }

    @Test
    void given_list_with_15_elements_when_add_26_elements_should_return_41(){
        DIYArrayList<Integer> list = createDIYList(15);
        list.addAll(createArrayList(26));
        assertEquals(41, list.size());
    }

    @Test
    void when_add_elements_to_array_should_return_correct_res() {
        ArrayList<Integer> elementsToAdd = createArrayList(26);
        ArrayList<Integer> arrayList = createArrayList(15);
        arrayList.addAll(elementsToAdd);
        DIYArrayList<Integer> list = createDIYList(15);
        list.addAll(elementsToAdd);
        assertArrayEquals(arrayList.toArray(), list.toArray());
    }

    private ArrayList<Integer> createArrayList(int size) {
        return IntStream
                .range(0, size)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private DIYArrayList<Integer> createDIYList(int size) {
        return IntStream
                .range(0, size)
                .collect(DIYArrayList::new, DIYArrayList::add, DIYArrayList::addAll);
    }

}
