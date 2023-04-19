import com.example.market.FruitStore;
import com.example.market.merchants.Robbie;
import com.example.market.merchants.Sparta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationTest {

    FruitStore fruitStore;

    @BeforeEach
    void init() {
        fruitStore = new FruitStore();
    }

    @Test
    @DisplayName("성공 케이스")
    void test1() {
        assertThat(fruitStore.store("사과", 5, Robbie.class)).isEqualTo("판매가 완료되었습니다.");
        System.out.println();
        assertThat(fruitStore.store("딸기", 50, Robbie.class)).isEqualTo("판매가 완료되었습니다.");
    }

    @Test
    @DisplayName("맞지 않는 수량")
    void test2() {
        assertThat(fruitStore.store("딸기", 150, Robbie.class)).isEqualTo("수량을 다시 확인해주세요!");
    }

    @Test
    @DisplayName("맞지 않는 과일")
    void test3() {
        assertThat(fruitStore.store("오렌지", 10, Robbie.class)).isEqualTo("해당하는 과일은 판매하지 않습니다.");
    }

    @Test
    @DisplayName("맞지 않는 상인")
    void test4() {
        assertThat(fruitStore.store("사과", 5, Sparta.class)).isEqualTo("해당 판매자는 영업을 하지 않습니다.");
    }
}
