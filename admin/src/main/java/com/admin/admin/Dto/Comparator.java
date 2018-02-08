package com.admin.admin.Dto;

public class Comparator implements java.util.Comparator<CategoryDto> {
    @Override
    public int compare(CategoryDto o1, CategoryDto o2) {
        if(o1.getNumberOfQuestions()==o2.getNumberOfQuestions())
            return 0;
        else if(o1.getNumberOfQuestions()<o2.getNumberOfQuestions())
            return 1;
        else
            return -1;
    }
}
