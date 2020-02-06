package com.tom.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("Method getNextInOrder() should return first Category item after giving it last")
    public void shouldReturnFirstCategoryAfterGivingLastCategory() {

        Category actualCategory = Category.values()[Category.values().length  - 1];
        Category nextCategory = Category.getNextInOrder(actualCategory);

        assertThat(nextCategory).isEqualTo(Category.values()[0]);
    }

    @Test
    @DisplayName("Method getPrevInOrder() should return last Category item after giving it first")
    public void shouldReturnLastCategoryAfterGivingFirstCategory() {

        Category actualCategory = Category.values()[0];
        Category prevCategory = Category.getPrevInOrder(actualCategory);

        assertThat(prevCategory).isEqualTo(Category.values()[Category.values().length  - 1]);
    }

}