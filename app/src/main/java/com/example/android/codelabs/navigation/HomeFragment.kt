/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val btn = view.findViewById<Button>(R.id.navigate_destination_button)

        // done STEP 5 - Set an OnClickListener, using Navigation.createNavigateOnClickListener()
        // btn.setOnClickListener {
        //   findNavController().navigate(R.id.flow_step_one_dest)
        // }
        // val button = view.findViewById<Button>(R.id.navigate_destination_button)
        //       button?.setOnClickListener {
        //            findNavController().navigate(R.id.flow_step_one_dest)
        //        }
        //done END STEP 5

        /** NOTE: Navigation.createNavigateOnClickListener(<DESTINATION>) doesn't have an option for NavOptions - specify an action here tho */

        // APPROACH #1:
        // val options = NavOptions.Builder()
        //      .setEnterAnim(R.anim.slide_in_right).
        //      .setExitAnim(R.anim.slide_out_left)
        //      .setPopEnterAnim(R.anim.slide_in_left)
        //      .setPopExitAnim(R.anim.slide_out_right)
        //      .build()

        // APPROACH #2: use KTX sugar
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        btn.setOnClickListener {
            findNavController().navigate(R.id.flow_step_one_dest, null, options)
        }

        val b2 = view.findViewById<Button>(R.id.navigate_action_button)
        b2.setOnClickListener {
            val action = HomeFragmentDirections.nextAction()
            // PROBLEM: results in Unresolved reference: flowStepNumber
            // action.flowStepNumber = 1
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.main_menu, menu)
    }

}
