package com.example.mynotes.model.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ActivityUtils {

    public static void replaceFragmentInActivity(FragmentManager manager,
                                                 Fragment fragment,
                                                 int containerId){
        if (manager != null && fragment != null)
            manager.beginTransaction().replace(containerId, fragment).commit();
    }

}
