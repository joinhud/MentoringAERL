package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.RandomMarks;
import com.epam.aerl.mentoring.type.Subject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class RandomMarksBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(RandomMarks.class)) {
                field.setAccessible(true);
                RandomMarks annotation = field.getAnnotation(RandomMarks.class);
                Map<Subject, Integer> resultField = new HashMap<>();
                for (Subject subject : Subject.values()) {
                    resultField.put(subject, randomIntRange(annotation.minMark(), annotation.maxMark()));
                }
                ReflectionUtils.setField(field, bean, resultField);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private int randomIntRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
