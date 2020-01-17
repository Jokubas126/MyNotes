package com.example.mynotes.model.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager manager,
                                             Fragment fragment,
                                             int containerId){
        if (manager != null && fragment != null)
            manager.beginTransaction().add(containerId, fragment).commit();
    }

    public static void addFragmentToActivity(FragmentManager manager,
                                             Fragment fragment,
                                             int containerId,
                                             Bundle arguments){
        if (manager != null && fragment != null){
            fragment.setArguments(arguments);
            manager.beginTransaction().add(containerId, fragment).commit();
        }
    }

    public static void replaceFragmentInActivity(FragmentManager manager,
                                                 Fragment fragment,
                                                 int containerId){
        if (manager != null && fragment != null)
            manager.beginTransaction().replace(containerId, fragment).commit();
    }

    public static void replaceFragmentInActivity(FragmentManager manager,
                                                 Fragment fragment,
                                                 int containerId,
                                                 Bundle arguments){
        if (manager != null && fragment != null){
            fragment.setArguments(arguments);
            manager.beginTransaction().replace(containerId, fragment).commit();
        }

    }
}
