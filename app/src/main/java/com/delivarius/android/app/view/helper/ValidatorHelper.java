package com.delivarius.android.app.view.helper;

import java.util.Collection;

public class ValidatorHelper {

    public static boolean isEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }

    public static boolean isAnyEmpty(String... strings){
        if(strings != null){
            for (String s : strings){
                if(isEmpty(s))
                    return true;
            }
            return false;
        }
        return true;
    }
    public static boolean isAllEmpty(String... strings){
        if(strings != null){
            for (String s : strings){
                if(!isEmpty(s))
                    return false;
            }
            return true;
        }
        return true;
    }

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

}
