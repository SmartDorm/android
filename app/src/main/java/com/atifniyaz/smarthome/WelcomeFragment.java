package com.atifniyaz.smarthome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class WelcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_welcome, container, false);

        LinearLayout nextButton = rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(view -> {
            WelcomeViewPager pager = getActivity().findViewById(R.id.pager);
            pager.setCurrentItem(1);
            pager.setPagingEnabled(true);
        });

        return rootView;
    }

}

