package com.tom.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    public void shouldReturnProperNextInOrderCategory() {

        Category actualCategory = Category.values()[Category.values().length  - 1];
        Category nextCategory = Category.getNextInOrder(actualCategory);

        assertThat(nextCategory).isEqualTo(Category.values()[0]);
    }

    @Test
    public void shouldReturnProperPreviousInOrderCategory() {

        Category actualCategory = Category.values()[0];
        Category prevCategory = Category.getPrevInOrder(actualCategory);

        assertThat(prevCategory).isEqualTo(Category.values()[Category.values().length  - 1]);
    }

}