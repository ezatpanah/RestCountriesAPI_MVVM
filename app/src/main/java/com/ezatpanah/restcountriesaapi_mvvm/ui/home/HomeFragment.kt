package com.ezatpanah.restcountriesaapi_mvvm.ui.home

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ezatpanah.restcountriesaapi_mvvm.R
import com.ezatpanah.restcountriesaapi_mvvm.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
                    binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
                        override fun onTransitionCompleted(p0: MotionLayout?, currentConstraintId: Int) {
                            if (currentConstraintId == R.id.nextPage) {
                                motionLayout.animate()
                                    .alpha(0f)
                                    .setDuration(250)
                                    .setListener(object : Animator.AnimatorListener {
                                        override fun onAnimationStart(p0: Animator?) {}
                                        override fun onAnimationEnd(p0: Animator?) {
                                            findNavController().navigate(R.id.action_homeFragment_to_countriesListFragment)
                                        }
                                        override fun onAnimationCancel(p0: Animator?) {}
                                        override fun onAnimationRepeat(p0: Animator?) {}
                                    })
                            }
                        }
                        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
                        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
                        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
                    })
            }

        }


    override fun onResume() {
        super.onResume()
        binding.motionLayout.startLayoutAnimation()
    }
}