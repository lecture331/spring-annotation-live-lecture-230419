import com.example.annotation.Controller;
import com.example.annotation.Service;
import com.example.bean.BeanFactory;
import com.example.controller.CourseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectTest {

    private Reflections reflections;
    private BeanFactory beanFactory;

    @BeforeEach
    void init() {
        reflections = new Reflections("com.example");
        // CourseController.class, CourseService,class
        Set<Class<?>> beans = getBeansByAnnotation(Controller.class, Service.class);
        beanFactory = new BeanFactory(beans);
    }

    private Set<Class<?>> getBeansByAnnotation(Class<? extends Annotation> ... args) {
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation : args) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    void annotationTest() {
        CourseController bean = beanFactory.getBean(CourseController.class);
        assertThat(bean).isNotNull();
        assertThat(bean.getCourseService()).isNotNull();
        bean.createCourse();
    }

}
